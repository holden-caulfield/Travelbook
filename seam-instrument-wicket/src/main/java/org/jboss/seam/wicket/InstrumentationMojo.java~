package org.jboss.seam.wicket;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.LoaderClassPath;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.jboss.seam.wicket.ioc.JavassistInstrumentor;

/**
 * This mojo takes classes specified with the "includes" parameter in the plugin configuration
 * and instruments them with Seam's Wicket JavassistInstrumentor.
 * @goal instrument
 * @requiresDependencyResolution
 * @phase process-classes
 */
public class InstrumentationMojo extends AbstractMojo
{
   /**
    * Location of the file.
    * 
    * @parameter expression="${project.build.directory}/classes"
    * @required
    */
   private File classesDirectory;

   public File getClassesDirectory()
   {
      return classesDirectory;
   }

   public void setClassesDirectory(File classesDirectory)
   {
      this.classesDirectory = classesDirectory;
   }

   /**
    * Maven project we are building
    * 
    * @parameter expression="${project}"
    * @required
    */
   private MavenProject project;

   /**
    * A set of file patterns representing classes to instrument
    * 
    * @parameter alias="includes"
    */
   private String[] includes = new String[0];

   public String[] getIncludes()
   {
      return includes;
   }

   public void setIncludes(String[] includes)
   {
      this.includes = includes;
   }

   /**
    * Whether to only instrument classes annotated with @SeamWicketComponent
    * @parameter alias="scanAnnotations"
    */
   private boolean scanAnnotations = false;
   
   public boolean isScanAnnotations()
   {
      return scanAnnotations;
   }

   public void setScanAnnotations(boolean scanAnnotations)
   {
      this.scanAnnotations = scanAnnotations;
   }


   /**
    * @parameter default-value="${project.runtimeClasspathElements}"
    * @readonly
    */
   private List<String> runtimeClassPath;

   /**
    * Same as above, but compiled regexps
    */
   private List<Pattern> mIncludePatterns;

   public void execute() throws MojoExecutionException
   {

      try
      {
         // make sure target/classes has been created
         if (!classesDirectory.exists())
         {
            throw new IllegalStateException("target/classes directory does not exist");
         }

         ClassPool classPool = new ClassPool();
         classPool.insertClassPath(classesDirectory.getPath());
         // append system classpath
         ClassLoader system = Object.class.getClassLoader();
         if (system == null)
         {
            system = Thread.currentThread().getContextClassLoader();
         }
         classPool.insertClassPath(new LoaderClassPath(system));

         for (Object path : project.getCompileClasspathElements())
         {
            File dir = new File((String) path);
            if (dir.exists())
            {
               classPool.insertClassPath((String) path);
            }
         }
         for (String path : runtimeClassPath)
         {
            classPool.insertClassPath((String) path);
         }

         mIncludePatterns = new ArrayList<Pattern>();
         for (String pat : includes)
         {
            mIncludePatterns.add(Pattern.compile(pat));
         }

         Map<String, CtClass> instrumentedClasses = new HashMap<String, CtClass>();

         JavassistInstrumentor instrumentor = new JavassistInstrumentor(classPool, scanAnnotations);

         List<String> classes = new ArrayList<String>();
         visitDir(classesDirectory, classes);

         for (String path : classes)
         {
            instrumentedClasses.put(path, instrumentor.instrumentClass(filenameToClassname(path)));
         }
         for (Map.Entry<String, CtClass> entry : instrumentedClasses.entrySet())
         {
            if (entry.getValue().isModified())
               entry.getValue().writeFile(classesDirectory.getPath());
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw new MojoExecutionException("Error instrumenting wicket code", e);
      }
   }

   private void visitDir(File classesDir, List<String> classes)
   {
      for (File file : classesDir.listFiles())
      {
         if (file.getPath().endsWith(".class"))
         {
            for (Pattern pat : mIncludePatterns)
            {
               if (pat.matcher(file.getPath()).find())
               {
                  classes.add(file.getPath());
                  break;
               }
            }
         }
         else if (file.isDirectory())
            visitDir(file, classes);
      }
   }

   protected String filenameToClassname(String filename)
   {
      filename = filename.substring(classesDirectory.getPath().length() + 1);
      return filename.substring(0, filename.lastIndexOf(".class")).replace('/', '.').replace('\\', '.');
   }

}
