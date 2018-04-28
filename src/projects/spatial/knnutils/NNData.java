package projects.spatial.knnutils;

/**<tt>NNData</tt> is a simple "struct-like" class that stores 
 * intermediate results of nearest neighbor queries.
 * 
 * @author Jason Filippou (jasonfil@cs.umd.edu)
 * 
 * @since April 2015
 */
public class NNData<T> {
	
	/**
	 * The current best guess about the element closest
	 * to an anchor element in nearest-neighbor searching.
	 */
	public T bestGuess;
	
	/**
	 * The distance between the current best guess and 
	 * the anchor element.
	 */
	public double bestDist;
	
	/**
	 * Simple constructor that stores its arguments.
	 * @param bestGuess The current best guess.
	 * @param bestDist The distance between the current best guess and the
	 * anchor element.
	 */
	public NNData(T bestGuess, double bestDist){
		this.bestGuess = bestGuess;
		this.bestDist = bestDist;
	}

}
