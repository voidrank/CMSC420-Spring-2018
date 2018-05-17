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


    /**
     * <p>Inserting a {@link KDPoint} into a {@link PRQuadBlackNode} can have one of two outcomes:</p>
     *
     * <ol>
     *     <li>If, after the insertion, the node's capacity is still <b>SMALLER THAN OR EQUAL TO </b> the bucketing parameter,
     *     we should simply store the {@link KDPoint} internally.</li>
     *
     *     <li>If, after the insertion, the node's capacity <b>SURPASSES</b> the bucketing parameter, we will have to
     *     <b>SPLIT</b> the current {@link PRQuadBlackNode} into a {@link PRQuadGrayNode} which will recursively insert
     *     all the available{@link KDPoint}s. This pprocess will continue until we reach a {@link PRQuadGrayNode}
     *     which successfully separates all the {@link KDPoint}s of the quadrant it represents. Programmatically speaking,
     *     this means that the method will polymorphically call itself, splitting black nodes into gray nodes as long as
     *     is required for there to be a set of 4 quadrants that separate the points between them. This is one of the major
     *     bottlenecks in PR-QuadTrees; the presence of a pair of {@link KDPoint}s with a very small {@link
     *     KDPoint#distance(KDPoint) distance} between them can negatively impact search in certain subplanes, because
     *     the subtrees through which those subplanes will be modelled will be &quot;unnecessarily&quot; tall.</li>
     * </ol>
     *
     * @param p A {@link KDPoint} to insert into the subtree rooted at the current node.
     * @return The subtree rooted at the current node, potentially adjusted after insertion.
     */
    @Override
    public PRQuadNode insert(KDPoint p) {
        throw UNIMPL_METHOD; // Erase this after you implement the method!
    }


    /**
     * <p><b>Successfully</b> deleting a {@link KDPoint} from a {@link PRQuadBlackNode} always decrements its capacity by 1. If, after
     * deletion, the capacity is at least 1, then no further changes need to be made to the node. Otherwise, it can
     * be scrapped and turned into a white node.</p>
     *
     * <p>If the provided {@link KDPoint} is <b>not</b> contained by <tt>this</tt>, no changes should be made to the internal
     * structure of <tt>this</tt>, which should be returned as is.</p>
     * @param p The {@link KDPoint} to delete from <tt>this</tt>.
     * @return Either <tt>this</tt> or <tt>null</tt>, depending on whether the node underflows.
     */
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
