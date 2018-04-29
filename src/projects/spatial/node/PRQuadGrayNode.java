package projects.spatial.node;

import projects.spatial.kdpoint.KDPoint;

/** <p>A <tt><PRQuadGrayNode/tt> is a gray ("mixed") {@link PRQuadNode}. It
 * maintains the following invariants: </p>
 * <ul>
 *  <li>Its children pointer buffer is non-<tt>null</tt>has a length of 4.</li>
 *  <li>At least one of its children is a gray node. This invariant also means
 *  that there cannot be any gray nodes with a single black node child; those should be collapsed
 *  into black nodes containing the points of the former child.</li>
 * </ul>
 * @author <a href="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
*/
public class PRQuadGrayNode extends PRQuadNode{

    /**
     * Inserts the given point in the subtree rooted at the current node.
     *
     * @param p A {@link KDPoint}.
     */
    @Override
    public void insert(KDPoint p) {

    }

    /**
     * Deletes the given point from the subtree rooted at the current node. If the
     * point is not in the subtree, no changes should be performed in the subtree.
     *
     * @param p
     */
    @Override
    public PRQuadNode delete(KDPoint p) {
        return null;
    }
}
