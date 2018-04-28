package projects.spatial.knnutils;

import projects.spatial.kdpoint.KDPoint;

import java.io.Serializable;
import java.util.Comparator;

/**<tt>KNNComparator</tt> is a {@link Serializable} {@link Comparator} used for sorting {@link KDPoint}s
 * based on the {@link KDPoint#distance(KDPoint, KDPoint)} to an anchor {@link KDPoint}. It is tremendously
 * useful for testing KNN queries.
 * 
 * <b>Note: this comparator imposes orderings that are inconsistent with {@link KDPoint#equals(Object)}.</b>
 *   
 * @author Jason Filippou (jasonfil@cs.umd.edu)
 * @param <T> A {@link KDPoint} object.
 */
public class KNNComparator<T extends KDPoint> implements Comparator<T>, Serializable {

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
