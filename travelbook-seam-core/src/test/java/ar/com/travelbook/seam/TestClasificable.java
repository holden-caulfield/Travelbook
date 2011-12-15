package ar.com.travelbook.seam;

import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.mock.SeamTest;
import org.jboss.seam.security.Identity;
import org.testng.Assert;
import org.testng.annotations.Test;

import ar.com.travelbook.domain.ActivityPublic;
import ar.com.travelbook.domain.Comment;
import ar.com.travelbook.domain.AccomodationTicketPublic;
import ar.com.travelbook.domain.Place;
import ar.com.travelbook.domain.TransportTicketPublic;
import ar.com.travelbook.domain.User;
import ar.com.travelbook.domain.Travel;
import ar.com.travelbook.wizard.action.accomodation.AccomodationPublicFilter;
import ar.com.travelbook.wizard.action.itinerary.PlaceFilter;
import ar.com.travelbook.wizard.action.transport.TransportPublicFilter;

@SuppressWarnings({ "unchecked", "deprecation" })
public class TestClasificable extends SeamTest {

	@Test
	public void TestOrdenLugar() throws Exception {
		new ComponentTest(){
			@Override
			protected void testComponents() throws Exception {

				PlaceFilter lugarFilter = (PlaceFilter) getValue("#{placeFilter}");
				assert lugarFilter != null;
				
				List<Place> places = (List<Place>) getValue("#{places}");
				Assert.assertEquals(places.get(0).getName(), "Miami");
				Assert.assertEquals(places.get(1).getName(), "Buenos Aires");
				Assert.assertEquals(places.get(2).getName(), "Dallas");
			}
		}.run();
	}
	
	@Test
	public void TestOrdenTransporte() throws Exception {
		new ComponentTest(){
			@Override
			protected void testComponents() throws Exception {
				Travel viaje = (Travel)getValue("#{travel}");
				PlaceFilter lugarFilter = (PlaceFilter) getValue("#{placeFilter}");
				assert lugarFilter != null;
				List<Place> places = (List<Place>) getValue("#{places}");
				assert places.get(0) != null;
				viaje.addDestination(places.get(0));
				viaje.addDestination(places.get(1));
				
				setValue("#{testSimpleProperties.selectedItem}",new Integer(1));
				TransportPublicFilter traslado = (TransportPublicFilter) getValue("#{transporteFilter}");
				assert traslado != null;
				List<TransportTicketPublic> traslados = (List<TransportTicketPublic>) getValue("#{transportesContratados}");
				Assert.assertEquals(traslados.get(0).getTransport().getName(), "LAPA");
				Assert.assertEquals(traslados.get(1).getTransport().getName(), "Mi Empresa");
				Assert.assertEquals(traslados.get(2).getTransport().getName(), "rapido del norte");
				Assert.assertEquals(traslados.get(3).getTransport().getName(), "flecha bus");
			}
		}.run();
	}
	
	@Test
	public void TestVotarTransporte() throws Exception {
		new ComponentTest(){
			
			@Override
			protected void testComponents() throws Exception {
				Identity identity = (Identity)getValue("#{identity}");
				identity.setUsername("cruz");
				User usuario = (User) getValue("#{user}");
				
				Travel viaje = (Travel)getValue("#{travel}");
				PlaceFilter lugarFilter = (PlaceFilter) getValue("#{placeFilter}");
				assert lugarFilter != null;
				List<Place> places = (List<Place>) getValue("#{places}");
				assert places.get(0) != null;
				viaje.addDestination(places.get(0));
				viaje.addDestination(places.get(1));
				
				setValue("#{testSimpleProperties.selectedItem}",new Integer(1));
				TransportPublicFilter traslado = (TransportPublicFilter) getValue("#{transporteFilter}");
				assert traslado != null;
				List<TransportTicketPublic> traslados = (List<TransportTicketPublic>) getValue("#{transportesContratados}");
				Assert.assertEquals(traslados.get(0).getTransport().getName(), "LAPA");
				
				usuario.vote(traslados.get(0).getTransport(), true);
				usuario.vote(traslados.get(1).getTransport(), true);
				usuario.vote(traslados.get(2).getTransport(), true);
				usuario.vote(traslados.get(3).getTransport(), true);
				Session session = (Session) getValue("#{session}");
				session.flush();
			}
		}.run();
	}
	
	@Test
	public void TestOrdenAlojamiento() throws Exception {
		new ComponentTest(){
			@Override
			protected void testComponents() throws Exception {
				Travel viaje = (Travel)getValue("#{travel}");
				PlaceFilter lugarFilter = (PlaceFilter) getValue("#{placeFilter}");
				assert lugarFilter != null;
				List<Place> places = (List<Place>) getValue("#{places}");
				assert places.get(0) != null;
				viaje.addDestination(places.get(0));
				viaje.addDestination(places.get(1));
				
				setValue("#{testSimpleProperties.selectedItem}",new Integer(1));
				AccomodationPublicFilter alojamiento = (AccomodationPublicFilter) getValue("#{accomodationFilter}");
				assert alojamiento != null;
				List<AccomodationTicketPublic> estadias = (List<AccomodationTicketPublic>) getValue("#{publicAccomodations}");
				Assert.assertEquals(estadias.get(0).getAccomodation().getName(), "hoteles albertarrio");
				Assert.assertEquals(estadias.get(1).getAccomodation().getName(), "hoteles tuli");
			}
		}.run();
	}
	
	@Test
	public void TestOrdenActividad() throws Exception {
		new ComponentTest(){
			@Override
			protected void testComponents() throws Exception {
				Travel viaje = (Travel)getValue("#{travel}");
				PlaceFilter lugarFilter = (PlaceFilter) getValue("#{placeFilter}");
				assert lugarFilter != null;
				List<Place> places = (List<Place>) getValue("#{places}");
				assert places.get(0) != null;
				viaje.addDestination(places.get(0));
				viaje.addDestination(places.get(1));
				
				setValue("#{testSimpleProperties.selectedItem}",new Integer(1));
				getValue("#{activityFilter}");
				List<ActivityPublic> actividades = (List<ActivityPublic>) getValue("#{actividadesContratadas}");
				Assert.assertEquals(actividades.get(0).getProvider().getName(), "Recital de los Maiquel Yaxon");
				Assert.assertEquals(actividades.get(1).getProvider().getName(), "Bailar Tango en la Viruta");
			}
		}.run();
	}

	@Test
	public void TestAgregarComentario() throws Exception {
		new ComponentTest() {
			@Override
			protected void testComponents() throws Exception {
				Identity identity = (Identity)getValue("#{identity}");
				identity.setUsername("cruz");
				User usuario = (User) getValue("#{usuario}");
				Comment comentario = new Comment(usuario,"texto");
				
				PlaceFilter lugarFilter = (PlaceFilter) getValue("#{placeFilter}");
				assert lugarFilter != null;
				List<Place> places = (List<Place>) getValue("#{places}");
				
				places.get(0).getComments().add(comentario);
			}
		}.run();
	}
	@Test
	public void TestAgregarVoto() throws Exception {
		new ComponentTest() {
			@Override
			protected void testComponents() throws Exception {
				Identity identity = (Identity)getValue("#{identity}");
				identity.setUsername("cruz");
				User usuario = (User) getValue("#{user}");
				
				PlaceFilter lugarFilter = (PlaceFilter) getValue("#{placeFilter}");
				assert lugarFilter != null;
				List<Place> places = (List<Place>) getValue("#{places}");
				
				usuario.vote(places.get(0), true);
				Assert.assertEquals(places.get(0).getVotesRatio(), 31);
			}
		}.run();
	}
	@Test
	public void TestObtenerComentario() throws Exception {
		new ComponentTest() {
			@Override
			protected void testComponents() throws Exception {
				PlaceFilter lugarFilter = (PlaceFilter) getValue("#{placeFilter}");
				assert lugarFilter != null;
				List<Place> places = (List<Place>) getValue("#{places}");
				Assert.assertEquals(places.get(0).getName(), "Miami");
				Comment comentario = places.get(0).getComments().get(0);
				Assert.assertEquals(comentario.getText(),"Mejor Ciudad del Mundo");
				Assert.assertEquals(comentario.getUset().getUsername(),"cruz");
			}
		}.run();
	}
}
