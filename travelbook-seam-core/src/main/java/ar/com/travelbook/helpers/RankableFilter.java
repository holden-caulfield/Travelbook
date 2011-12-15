package ar.com.travelbook.helpers;

/**
 * Rankable a Base filter of Rankable Entities. 
 * Adds ordering by votes.
 * 
 * @author cruz
 *
 */
public class RankableFilter extends AbstractFilter{
	private static final long serialVersionUID = 1L;
	
	public RankableFilter(){
		super(false, "rankable.votesRatio");
	}
	
}
