package ar.com.travelbook.seam;
import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.mock.SeamTest;
import org.testng.annotations.Test;

import ar.com.travelbook.domain.Place;

/**
 * Tests list of places
 * 
 * @author cruz
 *
 */
public class TestListPlaces extends SeamTest{
	
	@Test
	public void testCargarLugares() throws Exception{
		
		new ComponentTest(){
			
			@SuppressWarnings("unchecked")
			@Override
			protected void testComponents() throws Exception {
				EntityManager manager = (EntityManager) getValue("#{entityManager}");
				List<Place> list = manager.createQuery("from Place").getResultList();
				assert list.size() == 6;
			}
		}.run();
		assert true;
	}
	
	
}
