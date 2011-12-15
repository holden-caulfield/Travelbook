package ar.com.travelbook.seam;

import javax.security.auth.login.LoginException;

import org.jboss.seam.mock.SeamTest;
import org.jboss.seam.security.Identity;
import org.testng.annotations.Test;

/**
 * Tests authentication of user
 * 
 * @author cruz
 * 
 */
public class TestUserLog extends SeamTest {

	/**
	 * Tests valid user and password
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUserLog() throws Exception {
		new ComponentTest() {
			@SuppressWarnings("deprecation")
			@Override
			protected void testComponents() throws Exception {
				Identity identity = (Identity) this.getValue("#{identity}");
				identity.setUsername("cruz");
				identity.setPassword("cruz");
				identity.authenticate();
			}
		}.run();

	}

	/**
	 * Tests invalid password in a valid user
	 * 
	 * @throws Exception
	 */
	@Test(expectedExceptions = LoginException.class)
	public void testUserInvalidLog() throws Exception {
		new ComponentTest() {
			@SuppressWarnings("deprecation")
			@Override
			protected void testComponents() throws Exception {
				Identity identity = (Identity) this.getValue("#{identity}");
				identity.setUsername("cruz");
				identity.setPassword("nopass");
				identity.authenticate();
			}
		}.run();

	}

	/**
	 * Test invalid user (and invalid password)
	 * 
	 * @throws Exception
	 */
	@Test(expectedExceptions = LoginException.class)
	public void testUserInexistantLog() throws Exception {
		new ComponentTest() {
			@SuppressWarnings("deprecation")
			@Override
			protected void testComponents() throws Exception {
				Identity identity = (Identity) this.getValue("#{identity}");
				identity.setUsername("nonexistantuser");
				identity.setPassword("nopass");
				identity.authenticate();
			}
		}.run();

	}

}
