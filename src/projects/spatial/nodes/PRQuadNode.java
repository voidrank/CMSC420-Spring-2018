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
     * @see PRQuadNode#k
     */
    protected KDPoint centroid;

    /**
     * <p>The exponent to which 2 (two) is raised to characterize the length of the current quadrant's size. For example,
     * if k=4, the length of the quadrant that is "spanned" by the current {@link PRQuadNode} is 2^4 = 16. If we
     * were to temporarily consider the current quadrant as being a mini version of POSITIVE cartesian space (so, the upper
     * right quadrant of all those function graphs that we learned to make in middle / high school, that has only positive
     * 'x's and 'y's), then the centroid   of the quadrant would have coordinates (8, 8). In general, it would have coordinates
     * ( 2^(k-1), 2^(k-1) ). </p>
     *
     * <p>It is also possible for this parameter to be negative. This will just mean that the produced quadrant
     * will have a size length equal to negative powers of 2, such as -1/2, -1/8, etc. This is <b>completely fine</b>
     * with respect to the type {@link KDPoint}, which has <tt>double</tt> accuracy and can fit {@link KDPoint} instances
     * whose coordinates are non-integer numbers.</p>
     *
     *  <p>Given this parameter, you can probably imagine implementations that do <b>not</b> require using the parameter
     *      {@link PRQuadNode#centroid centroid}. It is <b>completely fine</b> if you do <b>not</b> want to use this parameter at all:
     *      we are just including it there because we feel your code will be cleaner if you do.</p>
     *
     * @see PRQuadNode#centroid
     */
    protected int k;

    /**
     * Inserts the given point in the subtree rooted at the current node. Returns the updated subtree.
     *
     * @param p A {@link KDPoint} to insert into the subtree rooted at the current node.
     * @param k The side length of the quadrant spanned by the current {@link PRQuadNode}. It is important that this value
     *          is <b>updated</b> per recursive call, such that the recursively generated quadrants have the appropriate side
     *          length and can further drive {@link KDPoint}s to the appropriate children nodes!
     * @return The subtree rooted at the current node, potentially adjusted after insertion.
     */
    public abstract PRQuadNode insert(KDPoint p, int k);

    /**
     * Deletes the given point from the subtree rooted at the current node. If the
     * point is <b>not</b> in the subtree, <b>no changes</b>  should be performed in the subtree.
     *
     * @param p A {@link KDPoint} to delete from the tree rooted at the current node.
     * @return The subtree rooted at the current node, potentially adjusted after deletion.
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


