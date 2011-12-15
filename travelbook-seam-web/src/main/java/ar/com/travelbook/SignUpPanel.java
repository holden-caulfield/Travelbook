package ar.com.travelbook;

import javax.persistence.EntityManager;
import javax.security.auth.login.LoginException;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.jboss.seam.annotations.In;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

import ar.com.travelbook.common.PageSolver;
import ar.com.travelbook.common.SeamCompoundPropertyModel;
import ar.com.travelbook.domain.User;

public class SignUpPanel extends Panel {
	private static final long serialVersionUID = 1L;
	
	public SignUpPanel(String id) {
		super(id);
		Form form = new Form("signForm", new SeamCompoundPropertyModel("newUser")){
			private static final long serialVersionUID = 1L;

			@In EntityManager entityManager;
			
			@In Credentials credentials;
			
			@In private Identity identity;
			
			@Override
			protected void onSubmit() {
				User user = (User) this.getModelObject();
				entityManager.persist(user);
				entityManager.flush();
				credentials.setUsername(user.getUsername());
				credentials.setPassword(user.getPassword());
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
		form.add(new TextField("name"));
		form.add(new TextField("username"));
		PasswordTextField password = new PasswordTextField("password");
		
		PasswordTextField passwordConfirm = new PasswordTextField("signPasswordConfirm", 
				new Model());
		form.add(password);
		form.add(passwordConfirm);
		form.add(new EqualPasswordInputValidator(password, passwordConfirm)); 
		

	}

}
