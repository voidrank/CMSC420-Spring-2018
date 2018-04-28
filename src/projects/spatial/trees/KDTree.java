package projects.spatial.trees;
import projects.spatial.kdpoint.KDPoint;
import projects.spatial.knnutils.*;


import java.util.Collection;
import java.util.LinkedList;

import static projects.spatial.kdpoint.KDPoint.distance;

/**
 * <tt>KDTree</tt> implements <em>K</em>-D Trees. <em>K</em> is a positive integer.
 *  By default, <em>k=2</em>. <tt>KDTree</tt> supports standard insertion, deletion and
 *  search routines, and also allows for range searching and nearest neighbor queries.
 *
 * @author Jason Filippou (jasonfil@cs.umd.edu)
 *
 */
public class KDTree {

	/* Private KDNode class: Will implement
     * a KD-Tree node abstraction. */
	private class Node {

		private KDPoint p;
		private int height;
		private Node left, right;

			/* Node-specific methods. Technically, they're also accessible
			 * by the container class, but let's pretend they're not for visibility
			 * purposes.
			 */

		private boolean coordLargerOrEqual(KDPoint p1, KDPoint p2, int dim){
			return p1.coords[dim] >= p2.coords[dim];
		}

		private KDPoint findMinOfDim(int soughtDim, int currDim){
			int nextDim = (currDim + 1) % dims;
			if(soughtDim == currDim)
				if(left != null)
					return left.findMinOfDim(soughtDim, nextDim);
				else
					return p;
			else{
				KDPoint leftVal = (left != null) ? left.findMinOfDim(soughtDim, nextDim) : p,
						rightVal = (right != null) ? right.findMinOfDim(soughtDim, nextDim) : p;
				return min(leftVal, p, rightVal, soughtDim);
			}

		}

		private int height(Node n){
			return n == null ? -1 : n.height;
		}

		private int max(int a, int b){
			return a >= b ? a : b;
		}

		// We follow a clockwise approach when we have equality
		// cases: L is preferred over C is preferred over R whenever any pair
		// of those nodes is equal w.r.t the coordinate "dim".
		private KDPoint min(KDPoint left, KDPoint curr, KDPoint right, int dim){
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
		private boolean anchorTooFarAwayFromAbove(KDPoint anchor, KDPoint p, double range, int coord){
			return (anchor.coords[coord] - range) > p.coords[coord];
		}

		/* While the second one does the converse thing. */
		private boolean anchorTooFarAwayFromBelow(KDPoint anchor, KDPoint p, double range, int coord){
			return (anchor.coords[coord] + range) < p.coords[coord];
		}

		/* Interface methods for Node... Those will be called by the
         * container class.
         */
		public Node(KDPoint p){
			this.p = p;
			height = 0;
		}

		private void insert(KDPoint pIn, int currDim){
			int nextDim = (currDim + 1) % dims;
			if(coordLargerOrEqual(pIn, p, currDim)) // Go right
				if(right == null)
					right = new Node(pIn);
				else
					right.insert(pIn, nextDim);
			else // Go left
				if(left == null)
					left = new Node(pIn);
				else
					left.insert(pIn, nextDim);
			height = max(height(left), height(right)) + 1;
		}

		private Node delete(KDPoint pIn, int currDim){
			int nextDim = (currDim + 1) % dims;
			if(p.equals(pIn)){
				if(right != null) {
					p = right.findMinOfDim(currDim, nextDim);
					right = right.delete(p, nextDim);
				} else if (left != null){
					p = left.findMinOfDim(currDim, nextDim);
					right = left.delete(p, nextDim); // Special case delete.
					left = null;
				} else { // Leaf node
					return null;
				}
			} else if(coordLargerOrEqual(pIn, p, currDim) && right != null)
				right = right.delete(pIn, nextDim);
			else if(left != null)
				left = left.delete(pIn, nextDim);
			height = max(height(left), height(right)) + 1;
			return this;
		}

		private boolean search(KDPoint pIn, int currDim){
			int nextDim = (currDim + 1) % dims;
			if(pIn.equals(p))
				return true;
			else
			if(coordLargerOrEqual(pIn, p, currDim) && right != null)
				return right.search(pIn, nextDim);
			else if(left != null)
				return left.search(pIn, nextDim);
			return false;
		}

		private void range(KDPoint anchor, Collection<KDPoint> results,
						   double range, int currDim ){
			int nextDim = (currDim + 1) % dims;
			// Search pruning step: Calculate distance between current and anchor
			// point. If you find it to be above range, compare the current coordinate
			// between the current point and the anchor point, and recurse to the appropriate
			// subtree such that you find responses faster.
			double dist = distance(anchor, p);
			if(dist >= range){ // Either outside range or exactly within range.
				if(dist == range) // If exactly on rim of edge, add point.
					results.add(p);
				// We structured this condition in a double nested if condition
				// because the following logic applies to both cases, i.e point
				// in rim or point outside rim.
				if(!anchorTooFarAwayFromAbove(p, anchor, range, currDim) && right != null)
					right.range(anchor, results,  range, nextDim);
				if(!anchorTooFarAwayFromBelow(p, anchor, range, currDim) && left != null)
					left.range(anchor, results, range, nextDim);
			} else { // Within range entirely.
				if(!anchor.equals(p)) // Recall that we don't want to report the anchor point itself.
					results.add(p);
				// In this case, we are within the range in our entirety,
				// so we need to recurse to both chidren, provided they exist.
				if(left != null)
					left.range(anchor, results, range, nextDim);
				if(right != null)
					right.range(anchor, results, range, nextDim);
			}
		}

		private NNData<KDPoint> nearestNeighbor(KDPoint anchor, int currDim,
												NNData<KDPoint> n){
			//if(distance(p, anchor) >= n.bestDist && n.bestDist != INFTY)
			//return n; // The current best guess is still the best.
			int nextDim = (currDim +1)%dims;
			if((distance(p, anchor) < n.bestDist || n.bestDist == INFTY) && !p.equals(anchor)){
				n.bestDist = distance(p, anchor);
				n.bestGuess = p; // TODO: Could make a method called update() for NNData...
			}
			Node picked = null, notPicked = null;
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
				n = picked.nearestNeighbor(anchor, nextDim, n);

			// It is possible that you have to recurse to the other subtree, if
			// the hypersphere constructed by the anchor and radius intersects
			// the splitting plane defined by p and currDim. We check for this here.
			if(Math.abs(p.coords[currDim] - anchor.coords[currDim] ) <= Math.sqrt(n.bestDist) && notPicked != null)
				n = notPicked.nearestNeighbor(anchor, nextDim, n);
			return n;
		}

		private void kNearestNeighbors(int k, KDPoint anchor, BoundedPriorityQueue<KDPoint> queue, int currDim){
			//if(distance(p, anchor) >= n.bestDist && n.bestDist != INFTY)
			//return n; // The current best guess is still the best.
			int nextDim = (currDim +1)%dims;
			if(!p.equals(anchor))
				queue.enqueue(p, distance(anchor, p)); // May change the queue, may not, depending on the distance.
			Node picked = null, notPicked = null;
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
				picked.kNearestNeighbors(k, anchor, queue, nextDim);

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
				notPicked.kNearestNeighbors(k, anchor, queue, nextDim);
		}

	} // End Node class

	private Node root;
	private int dims;
	private static final double INFTY = -1; // Encoding infinity with a negative number is safer than Double.MAX_VALUE for our purposes.

	/**
	 * Default constructor constructs <tt>this</tt> with <em>k=2</em>.
	 */
	public KDTree(){
		this(2);
	}

	/**
	 * This constructor requires that the user provide the value for <em>k</em>.
	 * @param k The dimensionality of <tt>this</tt>.
	 * @throws RuntimeException if <tt>k&lt;=0</tt>.
	 */
	public KDTree(int k){
		if(k <= 0)
			throw new RuntimeException("The value of k provided, " + k + ", is invalid: Please provide a positive integer.");
		dims = k;
		root = null;
	}

	/**
	 * Inserts <tt>p</tt> into the <tt>KDTree</tt>.
	 * @param p The {@link KDPoint} to insert into the tree.
	 */
	public void insert(KDPoint p){
		if(root == null)
			root = new Node(p);
		else
			root.insert(p, 0);

	}

	/**
	 * Deletes <tt>p</tt> from the <tt>KDTree</tt>. If <tt>p</tt> is not in the
	 * tree, this method performs no changes to the tree.
	 * @param p The {@link KDPoint} to delete from the tree.
	 */
	public void delete(KDPoint p){
		if(root == null)
			return;
		else root = root.delete(p, 0);
	}

	/**
	 * Searches the tree for <tt>p</tt> and reports if it found it.
	 * @param p The {@link KDPoint} to look for in the tree.
	 * @return <tt>true</tt> iff <tt>p</tt> is in the tree.
	 */
	public boolean search(KDPoint p){
		if(root == null)
			return false;
		else
			return root.search(p, 0);
	}

	/**
	 * Returns the root node of the <tt>KDTree</tt>.
	 * @return The {@link KDPoint} located at the root of the tree, or <tt>null</tt>
	 * if the tree is empty.
	 */
	public KDPoint getRoot(){
		return root == null ? null : root.p;
	}

	/**
	 * Performs a range query on the KD-Tree. Returns all the {@link KDPoint}s whose
	 * {@link KDPoint#distance(KDPoint) distance(KDPoint p)} distance} from  <tt>p</tt> is at most <tt>range</tt>.
	 * @param p The query {@link KDPoint}.
	 * @param range The maximum {@link KDPoint#distance(KDPoint, KDPoint)} from <tt>p</tt>
	 * that we allow a {@link KDPoint} to have if it should be part of the solution.
	 * @return A {@link Collection} over all {@link KDPoint}s which satisfy our query. The
	 * {@linkplain Collection} will be empty if there are no points which satisfy the query.
	 */
	public Collection<KDPoint> range(KDPoint p, double range){
		LinkedList<KDPoint> pts = new LinkedList<KDPoint>();
		if(root == null)
			return pts; // empty
		else
			root.range(p, pts, range, 0);
		return pts;
	}

	/** Performs a nearest neighbor query on the <tt>KDTree</tt>. Returns the {@link KDPoint}
	 * which is closest to <tt>p</tt>, as dictated by {@link KDPoint#distance(KDPoint) distance(KDPoint p)}.
	 * @param p The query {@link KDPoint}.
	 * @return The solution to the nearest neighbor query. This method will return <tt>null</tt> if
	 * there are no points other than <tt>p</tt> in the tree.
	 */
	public KDPoint nearestNeighbor(KDPoint p){
		NNData<KDPoint> n = new NNData<KDPoint>(null, INFTY);
		if(root != null)
			n = root.nearestNeighbor(p, 0, n);
		return n.bestGuess;
	}

	/**
	 * Performs a k-nearest neighbors query on the <tt>KDTree</tt>. Returns the <em>k</em>
	 * {@link KDPoint}s which are nearest to <tt>p</tt>, as dictated by {@link utils.KDPoint#distance(KDPoint) distance(KDPoint p)}.
	 * The {@link KDPoint}s are sorted in ascending order of distance.
	 * @param k A positive integer denoting the amount of neighbors to return.
	 * @param p The query point.
	 * @return A {@link queues.BoundedPriorityQueue} containing the k-nearest neighbors of <tt>p</tt>.
	 * This queue will be empty if the tree contains only <tt>p</tt>.
	 * @throws RuntimeException If <tt>k&lt;=0</tt>.
	 */
	public BoundedPriorityQueue<KDPoint> kNearestNeighbors(int k, KDPoint p){
			/* I really don't have the time to collect all the points and
			 * put them in a PQ, so I will do it the naive way. Sorry.
			 */
		if(k <= 0)
			throw new RuntimeException("The value of k provided, " + k + ", is invalid: Please provide a positive integer.");
		BoundedPriorityQueue<KDPoint> queue = new BoundedPriorityQueue<KDPoint>(k);
		if(root != null)
			root.kNearestNeighbors(k, p, queue, 0);
		return queue; // Might be empty; that's not a problem.
	}

	/**
	 * Returns the height of the tree. By convention, the height of an empty tree is -1.
	 * @return The height of <tt>this</tt>.
	 */
	public int height(){
		return root == null ? -1 : root.height;
	}

	/**
	 * Reports whether the <tt>KDTree</tt> is empty, that is, it contains zero <tt>KDPoint</tt>s.
	 * @return <tt>true</tt> iff height() == -1.
	 */
	public boolean isEmpty(){
		return height() == -1;
	}

}
