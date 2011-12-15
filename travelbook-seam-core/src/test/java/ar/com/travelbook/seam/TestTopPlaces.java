package ar.com.travelbook.seam;
import java.util.List;

import org.jboss.seam.mock.SeamTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import ar.com.travelbook.domain.Place;

/**
 * Tests list of places
 * 
 * @author cruz
 *
 */
public class TestTopPlaces extends SeamTest{
	
	@Test
	public void testLoadTopPlaces() throws Exception{
		
		new ComponentTest(){
			
			@SuppressWarnings("unchecked")
			@Override
			protected void testComponents() throws Exception {
				List<Place> places = (List<Place>) getValue("#{topPlaces}");
				assert places.size() == 4;
				Assert.assertEquals(places.get(0).getName() , "Miami");
			}
		}.run();
		assert true;
	}
	
}
