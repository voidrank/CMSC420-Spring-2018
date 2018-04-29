package projects.spatial.trees;
import projects.spatial.kdpoint.KDPoint;
import projects.spatial.knnutils.BoundedPriorityQueue;
import java.util.Collection;

/**
 * <p>A <tt>SpatialTree</tt> is a tree-based data structure that acts as an index
 * over {@link KDPoint}s and answers range and k-nearest neighbors queries.</p>
 */
public interface SpatialTree {
    /**
     * Inserts <tt>p</tt> into the <tt>SpatialTree</tt>.
     * @param p The {@link KDPoint} to insert into the tree.
     */
    public void insert(KDPoint p);

    /**
     * Deletes <tt>p</tt> from the <tt>KDTree</tt>. If <tt>p</tt> is not in the
     * tree, this method performs no changes to the tree.
     * @param p The {@link KDPoint} to delete from the tree.
     */
    public void delete(KDPoint p);

    /**
     * Searches the tree for <tt>p</tt> and reports if it found it.
     * @param p The {@link KDPoint} to look for in the tree.
     * @return <tt>true</tt> if <tt>p</tt> is in the tree, <tt>false</tt> otherwise.
     */
    public boolean search(KDPoint p);

    /**
     * Performs a range query on this <tt>SpatialTree</tt>. Returns all the {@link KDPoint}s whose
     * {@link KDPoint#distance(KDPoint) distance(KDPoint p)} distance} from  <tt>p</tt> is at
     * most <tt>range</tt>, <b>INCLUSIVE</b>.
     * @param p The query {@link KDPoint}.
     * @param range The maximum {@link KDPoint#distance(KDPoint, KDPoint)} from <tt>p</tt>
     * that we allow a {@link KDPoint} to have if it should be part of the solution.
     * @return A {@link Collection} over all {@link KDPoint}s which satisfy our query. The
     * {@link Collection} will be empty if there are no points which satisfy the query.
     */
    public Collection<KDPoint> range(KDPoint p, double range);


    /** Performs a nearest neighbor query on the <tt>SpatialTree</tt>.
     * Returns the {@link KDPoint} which is closest to <tt>p</tt>, as dictated by
     * {@link KDPoint#distance(KDPoint) distance(KDPoint p)}.
     * @param p The query {@link KDPoint}.
     * @return The solution to the nearest neighbor query. This method will return <tt>null</tt> if
     * there are no points other than <tt>p</tt> in the tree.
     */
    public KDPoint nearestNeighbor(KDPoint p);

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
     */
    public BoundedPriorityQueue<KDPoint> kNearestNeighbors(int k, KDPoint p);

    /**
     * Return the height of the<tt>SpatialTree</tt>. The height is defined similarly to
     * AVL trees, as follows:
     * <ol>
     *   <li>The height of a null tree (no nodes) is -1 (minus 1).</li>
     *   <li>The height of a tree that consists of a single nodes (a "stub" tree) is 0 (zero). </li>
     *   <li>The height of a tree that consists of four children is the maximum height of its children plus one.</li>
     *</ol>
     *
     * Note that the data field <tt>height</tt> is <tt>protected</tt>, so it's only accessible by derived classes.
     * This is a public method, so it can be called by callers outside the scope of this class (such as
     * {@link projects.spatial.trees.PRQuadTree})
     *
     * @return the height of the subtree rooted at the current nodes.
     * @see #height
     */
    public int height();

    /**
     * Reports whether the <tt>SpatialTree</tt> is empty, that is, it contains zero
     * <tt>KDPoint</tt>s.
     * @return <tt>true</tt> iff {@link #count()} == 0, false otherwise.
     */
    public boolean isEmpty();

    /** Returns the number of elements in the <tt>SpatialTree</tt>.
     *  @return The number of elements in the <tt>SpatialTree</tt>.
     */
    public int count();
}
