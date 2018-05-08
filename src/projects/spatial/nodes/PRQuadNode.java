package projects.spatial.nodes;
import projects.spatial.kdpoint.KDPoint;

/**
 * <p><tt>PRQuadNode</tt> is an <tt>abstract class</tt> used to provide the common structure that all
 * implementing subclasses will share.  It is an abstraction over nodes of a Point-Region (PR)- QuadTree.
 * Consult the lecture slides and the textbook to review the different kinds of nodes in a PR-QuadTree, what they
 * should contain and how they should implement insertion and deletion. </p>
 *
 * <p><b>YOU SHOULD ***NOT*** EDIT THIS CLASS!</b> If you do, you risk <b>not passing our tests!</b></p>
 *
 * @author <a href="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 */
public abstract class PRQuadNode {

    /**
     * The centroid of the current node. Its dimensions allow us to direct incoming {@link KDPoint}s
     * to the appropriate subtree.
     * <b>INVARIANT:</b> <tt>centroid != null</tt>
     */
    protected KDPoint centroid;

    /**
     * Inserts the given point in the subtree rooted at the current node.
     *
     * @param p A {@link KDPoint}.
     */
    public abstract void insert(KDPoint p);

    /**
     * Deletes the given point from the subtree rooted at the current node. If the
     * point is <b>not</b> in the subtree, <b>no changes</b>  should be performed in the subtree.
     *
     * @param p A {@link KDPoint}.
     * @return The current subtree, adjusted after the results of deletion.
     */
    public abstract PRQuadNode delete(KDPoint p);

    /**
     * Searches the subtree rooted at the current node for the provided {@link KDPoint}.
     *
     * @param p The {@link KDPoint} to search for.
     * @return <tt>true</tt> if <tt>p</tt> was found in the subtree rooted at the current nodes, <tt>false</tt> otherwise.
     */
    public abstract boolean search(KDPoint p);

    /**
     * Return the height of the subtree rooted at the current nodes. The height is defined similarly to
     * AVL trees, as follows:
     * <ol>
     *      <li>The height of a <tt>null</tt> tree (no nodes) is -1 (minus 1).</li>
     *      <li>The height of a tree that consists of a single node (a "stub" tree) is 0 (zero). </li>
     *      <li>The height of a tree that consists of four children is the maximum height of its children <b>plus one</b>.</li>
     * </ol>
     * <p>
     *
     * @return the height of the subtree rooted at the current node.
     */
    public abstract int height();

    /**
     * Return the total number of {@link KDPoint}s contained in the subtree rooted at the current node.
     *
     * @return the total number of {@link KDPoint}s contained in the subtree rooted at the current node.
     */
    public abstract int count();
}


