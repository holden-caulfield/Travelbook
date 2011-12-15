package ar.com.travelbook.common;

import javax.security.auth.login.LoginException;

import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.jboss.seam.annotations.In;
import org.jboss.seam.security.Identity;

import ar.com.travelbook.WicketSeamApplication;

public class WebSite extends WebPage {
	
	public WebSite() {
		addLinks();
	}

	private void addLinks() {
		add(new Link("home2"){
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick() {
				sendToHome();
			} 
		});
		Form form;
		form = new Form("signIn", new SeamCompoundPropertyModel("org.jboss.seam.security.identity")){
			private static final long serialVersionUID = 1L;
			
			@In Identity identity;
			
			@Override
			public boolean isVisible() {
				return !identity.isLoggedIn();
			}
			
			@Override
			protected void onSubmit() {
				try {
					identity.authenticate();
				} catch (LoginException e) {
					this.info("Usuario Inválido");
					return;
				}
				sendToHome();
			}

		};
		add(form);
		FeedbackPanel loginMessageFeedbackPanel = new FeedbackPanel("loginMessageFeedbackPanel");
		add(loginMessageFeedbackPanel);
		loginMessageFeedbackPanel.setOutputMarkupId(true);
		form.add(new TextField("username"));
		form.add(new PasswordTextField("password"));

		WebMarkupContainer signOff=new WebMarkupContainer("signOff") {
			private static final long serialVersionUID = 1L;
			@In Identity identity;
			
			@Override
			public boolean isVisible() {
				return identity.isLoggedIn();
			}
		}; 
		add(signOff);
		PropertyModel user = new ContextSeamPropertyModel("user", "username");
		signOff.add(new Label("userLogged", user));
		signOff.add(new Link("signOffButton"){
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onClick() {
				identity.logout();
				sendToHome();
			}

		});
	}


	public void interceptLogin() {
		WicketSeamApplication app = (WicketSeamApplication)this.getApplication();
		Page signin = PageSolver.solvePage(app.getLoginPage());
		redirectToInterceptPage(signin);
	}

	@In Identity identity;
	
	public void sendToHome() {
		WicketSeamApplication app = (WicketSeamApplication)this.getApplication();
		setResponsePage(app.getHomePage());
	}

	public WebSite(PageParameters params) {
		super(params);
		addLinks();
	}
	
	
}
