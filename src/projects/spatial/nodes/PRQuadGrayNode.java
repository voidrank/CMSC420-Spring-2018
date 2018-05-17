package projects.spatial.nodes;

import projects.spatial.kdpoint.KDPoint;

/** <p>A <tt>PRQuadGrayNode</tt> is a gray (&quot;mixed&quot;) {@link PRQuadNode}. It
 * maintains the following invariants: </p>
 * <ul>
 *      <li>Its children pointer buffer is non-<tt>null</tt> and has a length of 4.</li>
 *      <li>If there is at least one black node child, the total number of {@link KDPoint}s stored
 *      by <b>all</b> of the children is greater than the bucketing parameter (because if it is equal to it
 *      or smaller, we can prune the node.</li>
 * </ul>
 *
 * <p><b>YOU ***** MUST ***** IMPLEMENT THIS CLASS!</b></p>
 *
 *  @author --- YOUR NAME HERE! ---
 */

public class PRQuadGrayNode extends PRQuadNode{

    /**
     *  The number of children of the quadtree. Hardcoded to 4 by the constructor.
     *  Invariant: this buffer should be non-null and have a length of 4.
     */
    private PRQuadNode[] children; // White nodes will be represented with null.

    /**
     * Creates a <tt>PRQuadGrayNode</tt> with the provided {@link KDPoint} as a centroid;
     * @param centroid A {@link KDPoint} that will act as the centroid of the space spanned by the current nodes.
     */
    public PRQuadGrayNode(KDPoint centroid){
        this.centroid = new KDPoint(centroid); // Remember: KDPoints are mutable!
        children = new PRQuadNode[4];
    }


    /**
     * <p>Insertion into a {@link PRQuadGrayNode} consists of navigating to the appropriate child
     * and recursively inserting elements into it. If the child is a white node, memory should be allocated for a
     * {@link PRQuadBlackNode} which will contain the provided {@link KDPoint} If it's a {@link PRQuadBlackNode},
     * refer to {@link PRQuadBlackNode#insert(KDPoint)} for details on how the insertion is performed. If it's a {@link PRQuadGrayNode},
     * the current method would be called recursively. Polymorphism will allow for the appropriate <tt>insert</tt> to be called
     * based on the child object's runtime object.</p>
     * @param p A {@link KDPoint} to insert into the subtree rooted at the current {@link PRQuadGrayNode}.
     * @return The subtree rooted at the current node, potentially adjusted after insertion.
     * @see PRQuadBlackNode#insert(KDPoint)
     */
    @Override
    public PRQuadNode insert(KDPoint p) {
        /* ***** IMPLEMENT THIS! *** */
        return null;
    }

    /**
     * <p>Deleting a {@link KDPoint} from a {@link PRQuadGrayNode} consists of recursing to the appropriate
     * {@link PRQuadBlackNode} child to find the provided {@link KDPoint}. If no such child exists, the search has
     * <b>necessarily failed</b>; <b>no changes should then be made to the subtree rooted at the current node!</b></p>
     *
     * <p>Polymorphism will allow for the recursive call to be made into the appropriate <tt>delete</tt> method.
     * Importantly, after the recursive deletion call, it needs to be determined if the current {@link PRQuadGrayNode}
     * needs to be collapsed into a {@link PRQuadBlackNode}. This can only happen if it has no gray children, and one of the
     * following two conditions are satisfied:</p>
     *
     * <ol>
     *     <li>The deletion left it with a single black child. Then, there is no reason to further subdivide the quadrant,
     *     and we can replace <tt>this</tt> with a {@link PRQuadBlackNode} that contains the {@link KDPoint}s that the single
     *     black child contains.</li>
     *     <li>After the deletion, the <b>total</b> number of {@link KDPoint}s contained by <b>all</b> the black children
     *     is <b>equal to or smaller than</b> the bucketing parameter. We can then similarly replace <tt>this</tt> with a
     *     {@link PRQuadBlackNode} over the {@link KDPoint}s contained by the black children.</li>
     *  </ol>
     *
     * @param p A {@link KDPoint} to delete from the tree rooted at the current node.
     * @return The subtree rooted at the current node, potentially adjusted after deletion.
     */
    @Override
    public PRQuadNode delete(KDPoint p) {
        /* ***** IMPLEMENT THIS! *** */
        return null;
    }

    @Override
    public boolean search(KDPoint p){
        /* ***** IMPLEMENT THIS! *** */
        return false;
    }

    @Override
    public int height(){
        /* ***** IMPLEMENT THIS! *** */
        return 0;
    }

    @Override
    public int count(){
        /* ***** IMPLEMENT THIS! *** */
        return 0;
    }
}

