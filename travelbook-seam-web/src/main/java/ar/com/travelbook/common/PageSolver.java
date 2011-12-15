package ar.com.travelbook.common;

import org.apache.wicket.Page;

public class PageSolver {

	public static Page solvePage(Class<? extends Page> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
}
