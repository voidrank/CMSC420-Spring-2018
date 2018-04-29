package projects.spatial.nodes;

import projects.spatial.kdpoint.KDPoint;
import projects.spatial.knnutils.NNData;
import projects.spatial.knnutils.BoundedPriorityQueue;
import java.util.Collection;
/**
 * <p><tt>PRQuadNode</tt> is an <tt>abstract class</tt> used to provide the common structure that all
 * implementing subclasses will share.  It is an abstraction over nodes of a Point-Region (PR)- QuadTree.
 * Consult the lecture slides and the textbook to review the different kinds of nodes in a PR-QuadTree, what they
 * should contain and how they should implement insertion and deletion. </p>
 *
 * @author <a href="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 */
public abstract class PRQuadNode {

    /**
     * The centroid of the current gray nodes. Its dimensions allow us to direct incoming {@link KDPoint}s
     * to the appropriate subtree.
     * Invariant: centroid != null
     */
    protected KDPoint centroid;

    /**
     * Inserts the given point in the subtree rooted at the current nodes.
     *
     * @param p A {@link KDPoint}.
     */
    public abstract void insert(KDPoint p);

    /**
     * Deletes the given point from the subtree rooted at the current nodes. If the
     * point is not in the subtree, no changes should be performed in the subtree.
     *
     * @param p A {@link KDPoint}.
     * @return The current subtree, adjusted after the results of deletion.
     */
    public abstract PRQuadNode delete(KDPoint p);

    /**
     * Searches the subtree rooted at the current nodes for the provided {@link KDPoint}.
     *
     * @param p The {@link KDPoint} to search for.
     * @return <tt>true</tt> if <tt>p</tt> was found in the subtree rooted at the current nodes, <tt>false</tt> otherwise.
     */
    public abstract boolean search(KDPoint p);

    /**
     * Return the height of the subtree rooted at the current nodes. The height is defined similarly to
     * AVL trees, as follows:
     * <ol>
     * <li>The height of a null tree (no nodes) is -1 (minus 1).</li>
     * <li>The height of a tree that consists of a single nodes (a "stub" tree) is 0 (zero). </li>
     * <li>The height of a tree that consists of four children is the maximum height of its children plus one.</li>
     * </ol>
     * <p>
     * Note that the data field <tt>height</tt> is <tt>protected</tt>, so it's only accessible by derived classes.
     * This is a public method, so it can be called by callers outside the scope of this class (such as
     * {@link projects.spatial.trees.PRQuadTree})
     *
     * @return the height of the subtree rooted at the current nodes.
     * @see #height
     */
    public abstract int height();

    /**
     * Return the total number of {@link KDPoint}s contained in the subtree rooted at the current nodes.
     *
     * @return the total number of {@link KDPoint}s contained in the subtree rooted at the current nodes.
     */
    public abstract int count();

    /**
     * Fills the provided {@link Collection} with all the {@link KDPoint}s that satisfy the range query provided.
     * Range searches are considered <b>inclusive</b>; that is, points that lie <b>exactly</b> on the hypersphere
     * with centroid <tt>anchor</tt> and (squared) radius <tt>range</tt> <b>should</b> be reported by the query.
     *
     * Invariants:
     * <ol>
     *     <li><tt>results != null</tt></li>
     *     <li>range, currDim, dims >= 0</li>
     * </ol>
     * @param anchor A {@link KDPoint} representing the "anchor" (centroid) of the range search.
     * @param results A {@link java.util.Collection}
     * @param range
     * @param currDim
     * @param dims
     */
    public abstract void range(KDPoint anchor, Collection<KDPoint> results,
                               double range, int currDim, int dims);

    /**
     *
     */
    public abstract NNData<KDPoint> nearestNeighbor(KDPoint anchor, int currDim,
                                                    NNData<KDPoint> n, int dims);

    /**
     *
     */
    public abstract void kNearestNeighbors(int k, KDPoint anchor, BoundedPriorityQueue<KDPoint> queue, int currDim,
                                           int dims);

}


