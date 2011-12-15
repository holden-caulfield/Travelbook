package ar.com.travelbook.seam;

import java.util.List;

import org.jboss.seam.mock.SeamTest;
import org.jboss.seam.security.Identity;
import org.testng.Assert;
import org.testng.annotations.Test;

import ar.com.travelbook.domain.Rankable;

/**
 * Tests the recommender runs correctly
 * 
 * @author cruz
 *
 */
public class TestUserRecomender extends SeamTest {
	
	@Test
	public void testRecommendedItems() throws Exception{
		new ComponentTest(){
			@SuppressWarnings({ "deprecation", "unchecked" })
			@Override
			protected void testComponents() throws Exception {
				Identity identity = (Identity)getValue("#{identity}");
				identity.setUsername("juan");
				getValue("#{user}");
				
				List<Rankable> recomendaciones = (List<Rankable>) getValue("#{recommendationsForUser}");
				Assert.assertNotNull(recomendaciones);
				Assert.assertNotSame(recomendaciones.size(), 0);
			}
		}.run();

	}

}
