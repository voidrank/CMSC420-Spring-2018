package projects.spatial.node;

import projects.spatial.kdpoint.KDPoint;

/**
 * <p><tt>PRQuadNode</tt> is an <tt>abstract class</tt> used to provide the common structure that all
 * implementing subclasses will share. </p>
 */
public abstract class PRQuadNode {

    protected PRQuadNode[] children; // White nodes will be represented with null.
    protected KDPoint[] points;
    protected int maxPoints; // Bucketing parameter

    /**
     * <p>Only common piece of the interface. Subclasses can use this constructor's functionality
     * to implement their own constructors, using super(). They can then of course add more instructions to their
     * own constructors.</p>
     */
    public PRQuadNode(){
        children = new PRQuadNode[4];
        points = new KDPoint[maxPoints];
    }

    /**
     *
     */
    public abstract void insert(KDPoint p);

    /**
     *
     */
    public abstract PRQuadNode delete(KDPoint p);

}
