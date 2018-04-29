package projects.spatial.trees;
import projects.spatial.kdpoint.KDPoint;
import projects.spatial.knnutils.*;
import projects.spatial.nodes.KDTreeNode;
import java.util.Collection;
import java.util.LinkedList;

/**
 * <p><tt>KDTree</tt> implements <em>K</em>-D Trees. <em>K</em> is a positive integer.
 *  By default, <em>k=2</em>. <tt>KDTree</tt> supports standard insertion, deletion and
 *  search routines, and also allows for range searching and nearest neighbor queries.</p>
 *
 * <p>KD-Trees alternate dimensions with every increasing level. At any given level,
 * a KD-Tree acts as a Binary Search Tree over the relevant dimension. Refer to the course
 * slides and the textbook for exact algorithms of insertion, deletion and range / kNN
 * queries. </p>
 *
 * @author <a href ="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 */
public class KDTree implements SpatialTree {


	/* ************************************************************************** */
	/* ************************* PRIVATE FIELDS ********************************* */
	/* ************************************************************************** */

	private KDTreeNode root;
	private int dims;
	private static final double INFTY = -1; // Encoding infinity with a negative number is safer than Double.MAX_VALUE for our purposes.
	private static final int DEFAULT_DIMS = 2;
	private int count;


	/* ************************************************************************** */
	/* ************************* PUBLIC METHOD ********************************* */
	/* ************************* IMPLEMENTATION ******************************** */
	/* ************************************************************************** */

	/**
	 * Default constructor constructs <tt>this</tt> with <em>k=2</em>.
	 */
	public KDTree(){
		this(DEFAULT_DIMS);
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
		count = 0;
	}

	@Override
	public void insert(KDPoint p){
		if(root == null)
			root = new KDTreeNode(p);
		else
			root.insert(p, 0, dims);
		count++;
	}

	@Override
	public void delete(KDPoint p){
		if(root != null) {
			root = root.delete(p, 0, dims);
			count--;
		}
	}

	@Override
	public boolean search(KDPoint p){
		return (root != null) && root.search(p, 0, dims);
	}

	/**
	 * Returns the root nodes of the <tt>KDTree</tt>.
	 * @return The {@link KDPoint} located at the root of the tree, or <tt>null</tt>
	 * if the tree is empty.
	 */
	public KDPoint getRoot(){
		return root == null ? null : new KDPoint(root.getPoint());
	}

	@Override
	public Collection<KDPoint> range(KDPoint p, double range){
		LinkedList<KDPoint> pts = new LinkedList<KDPoint>();
		if(root == null)
			return pts; // empty
		else
			root.range(p, pts, range, 0, dims);
		return pts;
	}

	@Override
	public KDPoint nearestNeighbor(KDPoint p){
		NNData<KDPoint> n = new NNData<KDPoint>(null, INFTY);
		if(root != null)
			n = root.nearestNeighbor(p, 0, n, dims);
		return n.bestGuess;
	}

	@Override
	public BoundedPriorityQueue<KDPoint> kNearestNeighbors(int k, KDPoint p){
		if(k <= 0)
			throw new RuntimeException("The value of k provided, " + k + ", is invalid: Please provide a positive integer.");
		BoundedPriorityQueue<KDPoint> queue = new BoundedPriorityQueue<KDPoint>(k);
		if(root != null)
			root.kNearestNeighbors(k, p, queue, 0, dims);
		return queue; // Might be empty; that's not a problem.
	}
	@Override
	public int height(){
		return root == null ? -1 : root.height();
	}

	@Override
	public boolean isEmpty(){
		return height() == -1;
	}

	@Override
	public int count(){
		return count;
	}
}
