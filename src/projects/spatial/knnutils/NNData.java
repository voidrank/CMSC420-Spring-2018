package projects.spatial.knnutils;

/**<p>{@link NNData} is a simple "struct-like" class that stores
 * intermediate results of nearest neighbor queries. </p>
 *
 * @author <a href = "mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 *
 * @param <T> The type of {@link Object} held by the container.
 *
 * @see KNNComparator
 */
public class NNData<T> {
	
	/**
	 * The current best guess about the element closest to an anchor element in nearest-neighbor
	 * searching. Declared <tt>public</tt> to facilitate access by client code.
	 */
	public T bestGuess;
	
	/**
	 * The distance between the current best guess and 
	 * the anchor element. Declared <tt>public</tt> to facilitate access by client code.
	 */
	public double bestDist;
	
	/**
	 * Simple constructor that stores its arguments.
	 * @param bestGuess The current best guess.
	 * @param bestDist The distance between the current best guess and the &quot;anchor&quot; element.
	 */
	public NNData(T bestGuess, double bestDist){
		this.bestGuess = bestGuess;
		this.bestDist = bestDist;
	}

}
