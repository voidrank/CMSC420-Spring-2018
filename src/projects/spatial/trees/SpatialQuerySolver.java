package projects.spatial.trees;
import projects.spatial.kdpoint.KDPoint;
import projects.spatial.knnutils.BoundedPriorityQueue;

import java.util.Collection;

/**
 * <p><tt>SpatialQuerySolver</tt> is an interface that declares methods for range and k-NN queries over {@link KDPoint}s.</p>
 *
 *  <p>Minor detail: since {@link SpatialQuerySolver} is an <b>interface</b>, all of its methods are implicitly
 *  <tt>public</tt>, so the explicit scope modifier is <b>not needed</b> in the source.</p>
 *
 * @author <a href="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 *
 * @see KDPoint
 * @see BoundedPriorityQueue
 * @see SpatialDictionary
 */
public interface SpatialQuerySolver {

    /**
     * Performs a range query. Returns all the {@link KDPoint}s whose {@link KDPoint#distance(KDPoint) distance} from
     * <tt>p</tt> is at most <tt>range</tt>, <b>INCLUSIVE</b>.
     * @param p The query {@link KDPoint}.
     * @param range The maximum {@link KDPoint#distance(KDPoint, KDPoint)} from <tt>p</tt>
     * that we allow a {@link KDPoint} to have if it should be part of the solution.
     * @return A {@link Collection} over all {@link KDPoint}s which satisfy our query. The
     * {@link Collection} will be empty if there are no points which satisfy the query.
     * @see KDPoint
     */
    Collection<KDPoint> range(KDPoint p, double range);

    /** Performs a nearest neighbor query. Returns the {@link KDPoint} which is closest to
     * <tt>p</tt>, as dictated by {@link KDPoint#distance(KDPoint) distance(KDPoint p)}.
     * @param p The query {@link KDPoint}.
     * @return The solution to the nearest neighbor query. This method will return <tt>null</tt> if
     * there are no points other than <tt>p</tt> in the tree.
     * @see KDPoint
     */
    KDPoint nearestNeighbor(KDPoint p);

    /**
     * Performs a k-nearest neighbors query on the <tt>SpatialTree</tt>. Returns the <em>k</em>
     * {@link KDPoint}s which are nearest to <tt>p</tt>, as dictated by
     * {@link KDPoint#distance(KDPoint)}.
     * The {@link KDPoint}s are sorted in ascending order of distance.
     * @param k A positive integer denoting the amount of neighbors to return.
     * @param p The query point.
     * @return A {@link BoundedPriorityQueue} containing the k-nearest neighbors of <tt>p</tt>.
     * This queue will be empty if the tree contains only <tt>p</tt>.
     * @throws RuntimeException If <tt>k&lt;=0</tt>.
     * @see KDPoint
     * @see BoundedPriorityQueue
     */
    BoundedPriorityQueue<KDPoint> kNearestNeighbors(int k, KDPoint p);
}
