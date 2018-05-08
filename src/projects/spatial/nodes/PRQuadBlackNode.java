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

    /**
     * The default bucketsize for all of our black nodes will be 1, and this is something
     * that the interface also communicates to consumers.
     */
    public static final int DEFAULT_BUCKETSIZE = 1;

    /**
     *  The number of {@link KDPoint}s held by the current nodes.
     */
    private KDPoint[] points;

    /**
     *  The bucketing parameter that controls the total number of {@link KDPoint}s
     * that this node is allowed to hold.
     */
    private int bucketSize;


    /**
     * Creates a {@link PRQuadBlackNode} with the default bucket size and a provided {@link KDPoint}.
     * @param p The {@link KDPoint} with which we want to initialize <tt>this</tt>.
     * @see #DEFAULT_BUCKETSIZE
     * @see #PRQuadBlackNode(int, KDPoint)
     */
    public PRQuadBlackNode(KDPoint p){
        this(DEFAULT_BUCKETSIZE, p);
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
        this.bucketSize = bucketSize;
        points = new KDPoint[bucketSize];
        points[0] = new KDPoint(p); // Invariant: points.length >= 1
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
        return 0; // All black nodes have a height of 0.
    }

    @Override
    public int count(){
        /* ***** IMPLEMENT THIS! *** */
        return 0;
    }

    /** Returns all the {@link KDPoint}s contained by the {@link PRQuadBlackNode}. <b>INVARIANT</b>: the returned
     * {@link List}'s length can only be between 1 and bucket-size inclusive.
     *
     * @return A {@link List} that contains all the {@link KDPoint}s that are contained by the node. It is
     * guaranteed, by the invariants, that the {@link List} will not be empty, and it will also <b>not</b> be
     * a <tt>null</tt> reference.
     */
    public List<KDPoint> getPoints(){
        /* ***** IMPLEMENT THIS! **** */
        return null;
    }
}
