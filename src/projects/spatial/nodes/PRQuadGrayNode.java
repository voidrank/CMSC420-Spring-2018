package projects.spatial.nodes;

import projects.spatial.kdpoint.KDPoint;

/** <p>A <tt>PRQuadGrayNode</tt> is a gray (&quot;mixed&quot;) {@link PRQuadNode}. It
 * maintains the following invariants: </p>
 * <ul>
 *  <li>Its children pointer buffer is non-<tt>null</tt>has a length of 4.</li>
 *  <li>At least one of its children is a gray nodes. This invariant also means
 *  that there cannot be any gray nodes with a single black nodes child; those should be collapsed
 *  into black nodes containing the points of the former child.</li>
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

    @Override
    public void insert(KDPoint p) {
        /* ***** IMPLEMENT THIS! *** */
    }

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

