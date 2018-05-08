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

    private static RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");

    /* *************************************************************************
     ************** PLACE YOUR PRIVATE METHODS AND FIELDS HERE: ****************
     ***************************************************************************/



    /* ***************************************************************************** */
    /* ******************* PUBLIC (INTERFACE) METHODS ****************************** */
    /* ***************************************************************************** */


    /**
     * Creates a {@link PRQuadGrayNode}  with the provided {@link KDPoint} as a centroid;
     * @param centroid A {@link KDPoint} that will act as the centroid of the space spanned by the current
     *                 node.
     */
    public PRQuadGrayNode(KDPoint centroid){
        throw UNIMPL_METHOD; // Erase this after you implement the method!
    }

    @Override
    public void insert(KDPoint p) {
        throw UNIMPL_METHOD; // Erase this after you implement the method!

    }

    @Override
    public PRQuadNode delete(KDPoint p) {
        throw UNIMPL_METHOD; // Erase this after you implement the method!

    }

    @Override
    public boolean search(KDPoint p){
        throw UNIMPL_METHOD; // Erase this after you implement the method!
    }

    @Override
    public int height(){
        throw UNIMPL_METHOD; // Erase this after you implement the method!
    }

    @Override
    public int count(){
        throw UNIMPL_METHOD; // Erase this after you implement the method!
    }
}

