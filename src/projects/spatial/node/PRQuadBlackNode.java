package projects.spatial.node;

import projects.spatial.kdpoint.KDPoint;


/** <p>A <tt><PRQuadBlackNode/tt> is a black {@link PRQuadNode}. It maintains the following
 * invariants: </p>
 * <ul>
 *  <li>It does <b>not</b> have children.</li>
 *  <li>It contains at least one {@link KDPoint} </li>
 * </ul>
 * @author <a href="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 */
public class PRQuadBlackNode extends PRQuadNode {


    /**
     *  The number of {@link KDPoint}s held by the current node.  
     */
    private KDPoint[] points;

    /**
     *  The bucketing parameter that controls the total number of {@link KDPoint}s
     * that this node is allowed to hold.   
     */
    private int maxPoints;

    /**
     *  Creates a <tt>PRQuadBlackNode</tt> with the provided parameters.  
     * @param exp The
     */
    public PRQuadBlackNode(int exp, int bucketSize){
        super(exp);
        if(bucketSize < 1)
            throw new RuntimeException("bucketSize needs to be at least 1!");
        points = new KDPoint[bucketSize];
        maxPoints = bucketSize;
    }


    public PRQuadBlackNode(int exp, int bucketSize, KDPoint p){
        this(exp, bucketSize);
        points[0] = new KDPoint(p);
    }

    /**
     *  Inserts the given point in the subtree rooted at the current node.   
     *
     * @param p A {@link KDPoint}.
     */
    @Override
    public void insert(KDPoint p) {

    }


    /**
     * <p>Deletes the given point from the subtree rooted at the current node. If the
     * point is not in the subtree, no changes should be performed in the subtree.</p>
     *
     * @param p
     */
    @Override
    public PRQuadNode delete(KDPoint p) {
        return null;
    }
}
