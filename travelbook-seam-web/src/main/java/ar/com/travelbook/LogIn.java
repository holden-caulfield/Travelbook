package ar.com.travelbook;

import javax.security.auth.login.LoginException;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.jboss.seam.annotations.In;
import org.jboss.seam.security.Identity;

import ar.com.travelbook.common.PageSolver;
import ar.com.travelbook.common.SeamCompoundPropertyModel;
import ar.com.travelbook.common.WebSite;

public class LogIn extends WebSite {

	@In private Identity identity;
	
	private void logInForm() {
		Form form = new Form("loginForm", new SeamCompoundPropertyModel("org.jboss.seam.security.identity")){
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onSubmit() {
				try {
					identity.authenticate();
				} catch (LoginException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				if(!continueToOriginalDestination())
					setResponsePage(PageSolver.solvePage(HomeLoggedIn.class));
			}
		};
		add(form);
		form.add(new TextField("username"));
		form.add(new PasswordTextField("password"));
		
	}
	
	public LogIn(){
		this.logInForm();
		this.add(new SignUpPanel("register"));
	}

}
