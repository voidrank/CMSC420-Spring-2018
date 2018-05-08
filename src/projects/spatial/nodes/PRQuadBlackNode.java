package projects.spatial.nodes;

import projects.spatial.kdpoint.KDPoint;
import java.util.List;


/** <p>A {@link PRQuadBlackNode} is a &quot;black&quot; {@link PRQuadNode}. It maintains the following
 * invariants: </p>
 * <ul>
 *  <li>It does <b>not</b> have children.</li>
 *  <li><b>Once created</b>, it will contain at least one {@link KDPoint}. </li>
 * </ul>
 *
 * <p><b>YOU ***** MUST ***** IMPLEMENT THIS CLASS!</b></p>
 *
 * @author --- YOUR NAME HERE! ---
 */
public class PRQuadBlackNode extends PRQuadNode {

    private static final RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");

    /* ****************************************************************************** */
    /* ***** YOU SHOULD LEAVE THE FOLLOWING PUBLICLY AVAILABLE CONSTANT AS IS. ****** */
    /* ****************************************************************************** */

    /**
     * The default bucket size for all of our black nodes will be 1, and this is something
     * that the interface also communicates to consumers.
     */
    public static final int DEFAULT_BUCKETSIZE = 1;

    /* ************************************************************************* */
    /* ************** PLACE YOUR PRIVATE METHODS AND FIELDS HERE: **************** */
    /* ************************************************************************** */





    /* ***************************************************************************** */
    /* ******************* PUBLIC (INTERFACE) METHODS ****************************** */
    /* ***************************************************************************** */

    /**
     * Creates a {@link PRQuadBlackNode} with the default bucket size and a provided {@link KDPoint}.
     * @param p The {@link KDPoint} with which we want to initialize <tt>this</tt>.
     * @see #DEFAULT_BUCKETSIZE
     * @see #PRQuadBlackNode(int, KDPoint)
     */
    public PRQuadBlackNode(KDPoint p){
        throw UNIMPL_METHOD; // Erase this after you implement the method!
    }

    /**
     * Creates a {@link PRQuadBlackNode} with the provided parameters.
     * @param bucketSize The maximum number of points that the current {@link PRQuadBlackNode} is allowed to hold.
     *                   Must be at least 1.
     * @param p A {@link KDPoint} to initialize the current {@link PRQuadBlackNode} with.
     *
     * @see #PRQuadBlackNode(KDPoint)
     */
    public PRQuadBlackNode(int bucketSize, KDPoint p){
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

    /** Returns all the {@link KDPoint}s contained by the {@link PRQuadBlackNode}. <b>INVARIANT</b>: the returned
     * {@link List}'s length can only be between 1 and bucket-size inclusive.
     *
     * @return A {@link List} that contains all the {@link KDPoint}s that are contained by the node. It is
     * guaranteed, by the invariants, that the {@link List} will not be empty, and it will also <b>not</b> be
     * a <tt>null</tt> reference.
     */
    public List<KDPoint> getPoints(){
        throw UNIMPL_METHOD; // Erase this after you implement the method!
    }
}
