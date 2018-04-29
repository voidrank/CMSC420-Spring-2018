package projects.spatial.nodes;

import projects.spatial.kdpoint.KDPoint;
import projects.spatial.knnutils.BoundedPriorityQueue;
import projects.spatial.knnutils.NNData;

import java.util.Collection;

import static projects.spatial.kdpoint.KDPoint.distance;

/**
 * <p><tt>KDTreeNode</tt> is an abstraction over nodes of a KD-Tree. It will be used extensively by
 * {@link projects.spatial.trees.KDTree} to implement its functionality.</p>
 * @author <a href = "mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 */
public class KDTreeNode {

    private KDPoint p;
    private int height;
    private KDTreeNode left, right;
    private static final double INFTY = -1; // Encoding infinity with a negative number is safer than Double.MAX_VALUE for our purposes.

    /* Node-specific methods. Technically, they're also accessible
     * by the container class, but let's pretend they're not for visibility
     * purposes.
     */

    public  boolean coordLargerOrEqual(KDPoint p1, KDPoint p2, int dim){
        return p1.coords[dim] >= p2.coords[dim];
    }

    public  KDPoint findMinOfDim(int soughtDim, int currDim, int dims){
        int nextDim = (currDim + 1) % dims;
        if(soughtDim == currDim)
            if(left != null)
                return left.findMinOfDim(soughtDim, nextDim,  dims);
            else
                return new KDPoint(p);
        else{
            KDPoint leftVal = (left != null) ? left.findMinOfDim(soughtDim, nextDim, dims) : p,
                    rightVal = (right != null) ? right.findMinOfDim(soughtDim, nextDim, dims) : p;
            return min(leftVal, p, rightVal, soughtDim);
        }

    }

    public  int max(int a, int b){
        return a >= b ? a : b;
    }

    // We follow a clockwise approach when we have equality
    // cases: L is preferred over C is preferred over R whenever any pair
    // of those nodes is equal w.r.t the coordinate "dim".
    public  KDPoint min(KDPoint left, KDPoint curr, KDPoint right, int dim){
        if(coordLargerOrEqual(right, curr, dim)) // R >= C
            if(coordLargerOrEqual(curr, left, dim)) // C >= L
                return left; // R >= C >= L
            else // L >= C
                return curr; // R >= C && L >= C, and we only care about min
        else // C >= R
            if(coordLargerOrEqual(right, left, dim)) // R >= L
                return left; // C >= R >= L
            else // L >= R
                return right;  // C >= R && L >= R, , and we only care about min
    }

    /* Methods useful for pruning out large areas of the tree during range
     * queries. The first one checks to see if the anchor point is too far
     * away from the currently considered point in the increasing direction,
     * for instance, +x, +y, +z etc.
     */
    public  boolean anchorTooFarAwayFromAbove(KDPoint anchor, KDPoint p, double range, int coord){
        return (anchor.coords[coord] - range) > p.coords[coord];
    }

    /* While the second one does the converse thing. */
    public  boolean anchorTooFarAwayFromBelow(KDPoint anchor, KDPoint p, double range, int coord){
        return (anchor.coords[coord] + range) < p.coords[coord];
    }

    /* Interface methods for KDTreeNode... Those will be called by the
     * container class.
     */
    public KDTreeNode(KDPoint p){
        this.p = new KDPoint(p);
        height = 0;
    }

    public  void insert(KDPoint pIn, int currDim, int dims){
        int nextDim = (currDim + 1) % dims;
        if(coordLargerOrEqual(pIn, p, currDim)) // Go right
            if(right == null)
                right = new KDTreeNode(pIn);
            else
                right.insert(pIn, nextDim, dims);
        else // Go left
            if(left == null)
                left = new KDTreeNode(pIn);
            else
                left.insert(pIn, nextDim, dims);
        height = max(height(left), height(right)) + 1;
    }

    public  KDTreeNode delete(KDPoint pIn, int currDim, int dims){
        int nextDim = (currDim + 1) % dims;
        if(p.equals(pIn)){
            if(right != null) {
                p = right.findMinOfDim(currDim, nextDim, dims);
                right = right.delete(p, nextDim, dims);
            } else if (left != null){
                p = left.findMinOfDim(currDim, nextDim, dims);
                right = left.delete(p, nextDim, dims); // Special case delete.
                left = null;
            } else { // Leaf nodes
                return null;
            }
        } else if(coordLargerOrEqual(pIn, p, currDim) && right != null)
            right = right.delete(pIn, nextDim, dims);
        else if(left != null)
            left = left.delete(pIn, nextDim, dims);
        height = max(height(left), height(right)) + 1;
        return this;
    }

    public  boolean search(KDPoint pIn, int currDim, int dims){
        int nextDim = (currDim + 1) % dims;
        if(pIn.equals(p))
            return true;
        else
        if(coordLargerOrEqual(pIn, p, currDim) && right != null)
            return right.search(pIn, nextDim, dims);
        else if(left != null)
            return left.search(pIn, nextDim, dims);
        return false;
    }

    public  void range(KDPoint anchor, Collection<KDPoint> results,
                       double range, int currDim , int dims){
        int nextDim = (currDim + 1) % dims;
        // Search pruning step: Calculate distance between current and anchor
        // point. If you find it to be above range, compare the current coordinate
        // between the current point and the anchor point, and recurse to the appropriate
        // subtree such that you find responses faster.
        double dist = distance(anchor, p);
        if(dist >= range){ // Either outside range or exactly within range.
            if(dist == range) // If exactly on rim of edge, add point.
                results.add(new KDPoint(p));
            // We structured this condition in a double nested if condition
            // because the following logic applies to both cases, i.e point
            // in rim or point outside rim.
            if(!anchorTooFarAwayFromAbove(p, anchor, range, currDim) && right != null)
                right.range(anchor, results,  range, nextDim, dims);
            if(!anchorTooFarAwayFromBelow(p, anchor, range, currDim) && left != null)
                left.range(anchor, results, range, nextDim, dims);
        } else { // Within range entirely.
            if(!anchor.equals(p)) // Recall that we don't want to report the anchor point itself.
                results.add(new KDPoint(p));
            // In this case, we are within the range in our entirety,
            // so we need to recurse to both chidren, provided they exist.
            if(left != null)
                left.range(anchor, results, range, nextDim, dims);
            if(right != null)
                right.range(anchor, results, range, nextDim, dims);
        }
    }

    public  NNData<KDPoint> nearestNeighbor(KDPoint anchor, int currDim,
                                            NNData<KDPoint> n, int dims){
        //if(distance(p, anchor) >= n.bestDist && n.bestDist != INFTY)
        //return n; // The current best guess is still the best.
        int nextDim = (currDim +1)%dims;
        if((distance(p, anchor) < n.bestDist || n.bestDist == INFTY) && !p.equals(anchor)){
            n.bestDist = distance(p, anchor);
            n.bestGuess = new KDPoint(p); // TODO: Could make a method called update() for NNData...
        }
        KDTreeNode picked = null, notPicked = null;
        if(p.coords[currDim] <= anchor.coords[currDim] && right != null){
            picked = right;
            notPicked = left;
        }
        else if(left != null){
            picked = left;
            notPicked = right;
        }

        // Recurse to the subtree likeliest to give you a better answer first.
        if(picked != null)
            n = picked.nearestNeighbor(anchor, nextDim, n, dims);

        // It is possible that you have to recurse to the other subtree, if
        // the hypersphere constructed by the anchor and radius intersects
        // the splitting plane defined by p and currDim. We check for this here.
        if(Math.abs(p.coords[currDim] - anchor.coords[currDim] ) <= Math.sqrt(n.bestDist) && notPicked != null)
            n = notPicked.nearestNeighbor(anchor, nextDim, n, dims);
        return n;
    }

    public  void kNearestNeighbors(int k, KDPoint anchor, BoundedPriorityQueue<KDPoint> queue, int currDim, int dims){

        // Invariant: queue != null

        //if(distance(p, anchor) >= n.bestDist && n.bestDist != INFTY)
        //return n; // The current best guess is still the best.

        int nextDim = (currDim +1)%dims;
        if(!p.equals(anchor))
            queue.enqueue(new KDPoint(p), distance(anchor, p)); // May change the queue, may not, depending on the distance.
        KDTreeNode picked = null, notPicked = null;
        if(p.coords[currDim] <= anchor.coords[currDim]){
            picked = right;
            notPicked = left;
        }
        else {
            picked = left;
            notPicked = right;
        }
        // Recurse to the subtree likeliest to give you a better answer first, if it exists.
        if(picked != null)
            picked.kNearestNeighbors(k, anchor, queue, nextDim, dims);

        /* It is possible that you have to recurse to the other subtree. You have to do this if:
         *	(a) We have fewer than k nearest neighbors stored in our queue OR
         *	(b) We have k nearest neighbors stored and the candidate circle defined by the anchor point
         *	and the MAXIMUM priority neighbor crosses the splitting plane.
         *
         * Naturally, in order to recurse to the other subtree, the other subtree has to exist.
         * We check for this also.
         */
        double candidateCircleRadius = queue.isEmpty() ? 0 : distance(anchor, queue.last());
        if((queue.size() < k || Math.abs(p.coords[currDim] - anchor.coords[currDim] ) <= candidateCircleRadius) && notPicked != null)
            notPicked.kNearestNeighbors(k, anchor, queue, nextDim, dims);
    }

    public int height(){
        return height;
    }

    private int height(KDTreeNode n){
        return (n == null) ? - 1 : n.height;
    }

    public KDPoint getPoint(){
        return new KDPoint(p);
    }



} /* ***************** END OF NODE CLASS IMPLEMENTATION ***************** */