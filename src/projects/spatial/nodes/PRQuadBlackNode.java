package projects.spatial.nodes;

import projects.spatial.kdpoint.KDPoint;


/** <p>A <tt>PRQuadBlackNode</tt> is a black {@link PRQuadNode}. It maintains the following
 * invariants: </p>
 * <ul>
 *  <li>It does <b>not</b> have children.</li>
 *  <li>It contains at least one {@link KDPoint} </li>
 * </ul>
 * @author <a href="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 */
public class PRQuadBlackNode extends PRQuadNode {


    /**
     * The default bucketsize for all of our black nodes will be 1.
     */
    private static final int DEFAULT_BUCKETSIZE = 1;

    /**
     *  The number of {@link KDPoint}s held by the current nodes.
     */
    private KDPoint[] points;

    /**
     *  The bucketing parameter that controls the total number of {@link KDPoint}s
     * that this nodes is allowed to hold.
     */
    private int maxPoints;

    /**
     *  Creates a <tt>PRQuadBlackNode</tt> with the provided parameters.  
     * @param bucketSize The maximum number of points that the current <tt>PRQuadBlackNode</tt> is allowed to hold. Must be at least 1.
     * @throws RuntimeException if <tt>bucketSize</tt> &le; 0
     */
    public PRQuadBlackNode(int bucketSize){
        if(bucketSize < 1)
            throw new RuntimeException("bucketSize needs to be at least 1!");
        points = new KDPoint[bucketSize];
        maxPoints = bucketSize;
    }

    /**
     * Creates a <tt>PRQuadBlackNode</tt> with the provided parameters.
     * @param bucketSize The maximum number of points that the current <tt>PRQuadBlackNode</tt> is allowed to hold.
     *                   Must be at least 1.
     * @param p A {@link KDPoint} to initialize the current <tt>PRQuadBlackNode</tt> with.
     */
    public PRQuadBlackNode(int bucketSize, KDPoint p){
        this(bucketSize);
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
}
