package ar.com.travelbook;

import org.jboss.seam.wicket.SeamWebApplication;

public class WicketSeamApplication extends SeamWebApplication {
	@Override
	public Class<LogIn> getLoginPage() {
		return LogIn.class;
	}

	@Override
	public Class<Home> getHomePage() {
		return Home.class;
	}

	@Override
	protected void init() {
		super.init();
	}

}
