package projects.spatial.knnutils;

import projects.spatial.kdpoint.KDPoint;

import java.io.Serializable;
import java.util.Comparator;

/**<p><tt>KNNComparator</tt> is a {@link Serializable} {@link Comparator} used for sorting {@link KDPoint}s
 * based on the {@link KDPoint#distance(KDPoint, KDPoint)} to an anchor {@link KDPoint}. It is very
 * useful for <b>testing</b> KNN queries.</p>
 * 
 * <p><b>Note: this comparator imposes orderings that are inconsistent with {@link KDPoint#equals(Object)}.</b></p>
 *   
 * @author <a href ="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 * @param <T> A {@link KDPoint} type.
 * @see NNData
 */
public class KNNComparator<T extends KDPoint> implements Comparator<T>, Serializable {

	/**
	 * The &quot;anchor&quot;point for which we want to calculate the nearest neighbors.
	 */
	private T anchor;

	/**
	 * A default serial version ID so that the compiler doesn't complain.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Our constructor will store the anchor point that we want to base all future {@link KDPoint} comparisons on.
	 * @param arg The {@link KDPoint} object used as the basis of all future comparisons. 
	 */
	public KNNComparator(T arg) {
		anchor = arg;
	}

	@Override
	public int compare(T o1, T o2) {
		if(o1.distance(anchor) < o2.distance(anchor))
			return -1;
		else if(o1.distance(anchor) == o2.distance(anchor))
			return 0;
		else
			return 1;

	}
}
