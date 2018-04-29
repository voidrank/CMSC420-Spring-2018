package projects.spatial.trees;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import projects.spatial.knnutils.*;
import projects.spatial.kdpoint.*;
import java.util.*;
import static org.junit.Assert.*;
import static projects.spatial.kdpoint.KDPoint.distance;

/**
 * A testing framework for {@link KDTree}.
 * @author Jason Filippou (jasonfil@cs.umd.edu)
 */
public class KDTreeTests {

	private static final int MAX_ITER = 200;
	private KDTree twoDTree, threeDTree, fourDTree, mount2DTree, jason2DTree, jason3DTree;
	private static final int MAX_DIM = 30;
	private Random r;
	private static final int SEED = 47, SCALE = 10;
	private static final double EPSILON = Math.pow(10,  -8);

	/* Private methods */

	/* Prepares David Mount's 2D-tree, taken from his 420 notes, page 76.
	 * IMPORTANT NOTE: DAVID MOUNT'S MADE A MISTAKE ON THAT TREE OF HIS.
	 * Namely, the leaf node (60, 10) should be its parent's left child.
	 * To make the example work, I changed that particular node to (75, 10).
	 * 
	 */
	private void prepMountTree(){
		KDPoint[] points = {
				new KDPoint(35, 60), new KDPoint(20, 45),
				new KDPoint(10, 35), new KDPoint(20, 20),
				new KDPoint(60, 80), new KDPoint(80, 40),
				new KDPoint(50, 30), new KDPoint(70, 20),
				new KDPoint(75, 10),new KDPoint(90, 60)
		};
		for(KDPoint p: points)
			mount2DTree.insert(p);

		/*The tree should now have the following structure:
		 * 
		 * 
		 *								   (35,60)
		 * 									/    \
		 * 								   /      \
		 * 								  /        \
		 * 							  (20,45)     (60,80)
		 * 								/		    /
		 * 							   /      	   /
		 * 						   (10,35)	    (80,40)
		 * 							   \		  /  \
		 * 								\		 /    \
		 * 							  (20,20) (50,30) (90,60)
		 * 									   /
		 * 									  /
		 * 								   (70,20)
		 * 									  \
		 * 									   \
		 * 									 (75,10)
		 */
	}

	/* Prepares a custom 2D-tree. This tree will be lop-sided, to create
	 * a deletion special case which we can check via getRoot(). 
	 */
	private void prepJason2DTree(){
		KDPoint[] points = {
				new KDPoint(2, 3), new KDPoint(-1, 6),
				new KDPoint(0, 5), new KDPoint(-1, 0),
				new KDPoint(-10, -3), new KDPoint(-4,-3),
				new KDPoint(-6,-6), new KDPoint(-4,-1),
				new KDPoint(1,3)
		};
		for(KDPoint p: points)
			jason2DTree.insert(p);

		/* This is what the tree should look like:
		 * 
		 * 					(2, 3)
		 * 					/
		 * 				   /
		 * 				(-1,6)
		 * 				 /
		 * 		   		/
		 * 			 (0,5)
		 * 			  /  \
		 * 		     /    \
		 * 	   	   (-1,0) (1,3)
		 * 		   /
		 * 		  /
		 * 	  (-10,-3)
		 * 		  \
		 * 		   \
		 * 		  (-4,-3)
		 * 			/  \
		 * 		   /    \
		 * 		(-6,-6) (-4,-1)
		 */
	}

	/* Prepares a custom 3D-tree. */
	private void prepJason3DTree(){
		KDPoint[] points = {
				new KDPoint(2,3,-5), new KDPoint(-1, 2, 3), new KDPoint(-1,-9,2),
				new KDPoint(-4,1,0), new KDPoint(-3,-8,-3), new KDPoint(-3,-10,1),
				new KDPoint(-3,-8,0), new KDPoint(-6,4,8), new KDPoint(-5,6,8),
				new KDPoint(6,0,-1), new KDPoint(8,-2,6), new KDPoint(8,-2,7),
				new KDPoint(8,-1,9), new KDPoint(4,1,-2)
		};
		for(KDPoint p: points)
			jason3DTree.insert(p);

		/*
		 *	This is what the tree should look like:
		 *
		 *							  (2,3,-5)
		 *						      / 	  \
		 *						     /	  	   \
		 *						    /		    \
		 *			            (-1,2,3)        (6,0,-1)
		 *						  /	   \           /   \
		 *					     /		\         /     \
		 *                  (-1,-9,2) (-6,4,8) (8,-2,6) (4,1,-2)
		 *                    /			  \		   \
		 *                   /			   \		\
		 * 				 (-4,1,0)		(-5,6,8)   (8,-2,7)
		 * 					\						  \
		 * 					 \						   \
		 * 					(-3,-8,-3)				  (8,-1,9)
		 * 				     /		\
		 * 					/		 \
		 * 				(-3,-10,1)	(-3,-8,0)
		 * 
		 * Measure 233 from (8,-2,7)...
		 * 
		 * Nodes within: 
		 * 		[2, 3, -5]
		 *		[-1, 2, 3]
		 *		[6, 0, -1]
		 *		[-1, -9, 2]
		 *		[-6, 4, 8]
		 *		[8, -2, 6]
		 *		[4, 1, -2]
		 *		[-4, 1, 0]
		 *		[8, -2, 7] <---- The point itself...
		 *		[8, -1, 9]
		 *		[-3, -10, 1]
		 *		[-3, -8, 0]
		 *
		 */
	}

	/**
	 * Return {+1, -1} uniformly
	 * @return Either 1 or -1 from a uniform distribution.
	 */
	private int getRandomSign(){
		return 2 * r.nextInt(2) - 1; 
	}

	/**
	 * Check whether a range query on a {@link KDTree} is satisfied.
	 * @param tree A populated {@link KDTree}.
	 * @param origin The {@link KDPoint} used as the center of the range query.
	 * @param range The maximum {@link KDPoint#distance(KDPoint, KDPoint) that any {@link KDPoint} can have
	 * from <tt>origin</tt> in order to satisfy our query. 
	 * @param candidates A varargs array containing the ground truth answers to our range query.
	 * @return <tt>true</tt> iff all the {@link KDPoint}s in <tt>candidates</tt> are at a {link {@link KDPoint#distance(KDPoint, KDPoint)}
	 * of at most <tt>range</tt> from <tt>origin</tt>.
	 */
	private boolean checkRangeQuery(KDTree tree, KDPoint origin, double range, KDPoint... candidates){
		Collection<KDPoint> rangeQueryResults = tree.range(origin, range);
		List<KDPoint> candidateList = Arrays.asList(candidates);
		return rangeQueryResults.containsAll(candidateList); // Order not important in range queries: only containment.
	}

	/**
	 * Check whether a K-Nearest Neighbors query is satisfied by a {@link KDTree}.
	 * @param tree A populated {@link KDTree}.
	 * @param origin The {@link KDPoint} to calculate the nearest neighbors of.
	 * @param k The number of nearest neighbors to compute.
	 * @param neighbors A {@link List} containing the ground truth response to the query.
	 * @return <tt>true</tt> iff the {@link KDPoint}s in <tt>candidates</tt> are the <tt>k</tt> nearest
	 * points to <tt>origin</tt>, as dictated by {@link KDPoint#distance(KDPoint)}.
	 */
	private boolean checkKNNQuery(KDTree tree, KDPoint origin, int k, List<KDPoint> neighbors){
		assert neighbors.size() == k : "Mismatch between requested neighbors and number of expected neighbors.";
		BoundedPriorityQueue<KDPoint> results = tree.kNearestNeighbors(k, origin);
		Iterator<KDPoint> resultsIt = results.iterator();
		for(KDPoint n : neighbors)
			if(!resultsIt.hasNext() || !resultsIt.next().equals(n))
				return false;
		return true;	
	}

	/**
	 * Check whether a K-Nearest Neighbors query is satisfied by a {@link KDTree}. Different
	 * from {@link #checkKNNQuery(KDTree, KDPoint, int, List)} in the type of last argument.
	 * @param tree A populated {@link KDTree}.
	 * @param origin The {@link KDPoint} to calculate the nearest neighbors of.
	 * @param k The number of nearest neighbors to compute.
	 * @param neighbors A varargs array containing the ground truth response to the query.
	 * @return <tt>true</tt> iff the {@link KDPoint}s in <tt>candidates</tt> are the <tt>k</tt> nearest
	 * points to <tt>origin</tt>, as dictated by {@link KDPoint#distance(KDPoint)}.
	 */
	private boolean checkKNNQuery(KDTree tree, KDPoint origin, int k, KDPoint... neighbors){
		assert neighbors.length == k : "Mismatch between requested neighbors and number of expected neighbors.";
		BoundedPriorityQueue<KDPoint> results = tree.kNearestNeighbors(k, origin);
		Iterator<KDPoint> resultsIt = results.iterator();
		for(KDPoint n : neighbors)
			if(!resultsIt.hasNext() || !resultsIt.next().equals(n))
				return false;
		return true;	
	}
	
	private double[] getRandomDoubleCoords(int num){
		double[] coords = new double[num];
		for(int i = 0; i < num; i++)
			coords[i] = getRandomSign() * SCALE * r.nextDouble();
		return coords;
	}
	
	private KDPoint getRandomPoint(int dim){
		return new KDPoint(getRandomDoubleCoords(dim));
	}

	/* Setup and tear-down methods */

	/**
	 * A setup method that runs before every one of our jUnit testing methods.
	 * @throws Exception For multiple different reasons we can't possibly track.
	 */
	@Before
	public void setUp() throws Exception{
		twoDTree = new KDTree(2); // Using the default constructor should also be fine.
		threeDTree = new KDTree(3);
		fourDTree = new KDTree(4);
		mount2DTree = new KDTree(2);
		jason2DTree = new KDTree(2);
		jason3DTree = new KDTree(3);
		r = new Random(SEED);
	}


	/** 
	 * A cleanup method that runs after every one of our jUnit testing methods.
	 * @throws Exception For multiple different reasons we can't possibly track.
	 */
	@After
	public void tearDown() throws Exception{
		twoDTree = threeDTree = fourDTree = mount2DTree = jason2DTree = jason3DTree = null;
		r = null;
	}

	/* jUnit tests */

	/**
	 * Test method for {@link KDTree#KDTree()}.
	 */
	@Test
	public void testKDTree() {
		for(int i = 0; i < MAX_ITER; i++){
			try {
				new KDTree();
			} catch(Throwable t){
				fail("Caught a " + t.getClass() + " with message: " + t.getMessage() + 
						" when constructing a KD-Tree with the default constructor in loop " + i + ".");
			}
		}
	}

	/**
	 * Test method for {@link KDTree#KDTree(int)}.
	 */
	@Test
	public void testKDTreeInt() {
		/* First, track any exceptions thrown for valid values of 
		 * the dimensionality parameter.
		 */
		for(int i = 0; i < MAX_ITER; i++){
			int randDim = r.nextInt(MAX_DIM) + 1; // (Candidate set: {1, 2, ..., MAX_DIM}
			try {
				new KDTree(randDim);
			} catch(Throwable t){
				fail("Caught a " + t.getClass() + " with message: " + t.getMessage() + 
						" when constructing a KD-Tree of dimensionality " + randDim + 
						" in loop " + i + ".");
			}
		}

		/* Second, make sure that a good set of invalid values of the
		 * dimensionality parameter throws the expected exception.
		 */

		for(int i = 0; i < MAX_ITER; i++){
			int randDim = r.nextInt(MAX_DIM); // (Candidate set: {0, 1, ..., MAX_DIM - 1}
			try {
				new KDTree(-randDim); // 0 is also not acceptable by constructor.
			} catch(RuntimeException rexc) {
				// Good
			} catch(Throwable t){ 
				// Not good
				fail("While expecting a RuntimeException, we instead caught a " + 
						t.getClass() + " with message: " + t.getMessage() + 
						" when constructing a KD-Tree of dimensionality " + randDim + 
						" in loop " + i + ".");
			}
		}
	}


	/**
	 * Test method for {@link KDTree#insert(KDPoint)}.
	 */
	@Test
	public void testInsert() {

		/* (1) Create the trees using the private methods
		 * and look for exceptions. 
		 */
		try {
			prepMountTree();
		} catch(Throwable t){
			fail("Caught a " + t.getClass() + " with message: " 
					+ t.getMessage() + " when creating our first 2D tree.");
		}

		try {
			prepJason2DTree();
		} catch(Throwable t){
			fail("Caught a " + t.getClass() + " with message: " 
					+ t.getMessage() + " when creating our second 2D tree.");
		}

		try {
			prepJason3DTree();
		} catch(Throwable t){
			fail("Caught a " + t.getClass() + " with message: " 
					+ t.getMessage() + " when creating our 3D tree.");
		}

		/*
		 * (2) Stress tests for arbitrary dimensional trees.
		 */

		for(int dim = 1; dim < MAX_DIM + 1; dim++){ // For all possible dimensions that we consider...
			KDTree tempTree = new KDTree(dim);
			for(int i = 0; i < MAX_ITER; i++){ // Bunch of points
				KDPoint tempPt = getRandomPoint(dim);
				try {
					tempTree.insert(tempPt);
				} catch(Throwable t){
					fail("For a " + dim + "-D tree, we attempted to insert " + tempPt + 
							", which was point #" + i + " in the sequence, and received a " 
							+ t.getClass() +" with message: " + t.getMessage() + ".");
				}
			}

		}
	}

	/**
	 * Test method for {@link KDTree#delete(KDPoint)}.
	 */
	@Test
	public void testDelete() {

		/* (1) Simple tests on Mount tree: deletions of both inner nodes and leaves, as 
		 * 	well as deletions of elements that are not in the tree.
		 */
		prepMountTree();
		int origMountTreeHeight = mount2DTree.height();
		// Mount's tree contains no negative coordinate points; let's make sure that no
		// matter how many such points we generate, deleting them has no impact on the tree
		// whatsoever.
		for(int i = 0; i < MAX_ITER; i++){
			double x = -SCALE*r.nextDouble(), y = -SCALE*r.nextDouble(); // Negative, scaled
			mount2DTree.delete(new KDPoint(x, y));
			assertEquals("Deleting elements not in the tree should have no impact on the " + 
					"tree structure whatsoever.", origMountTreeHeight, mount2DTree.height());
		}

		// The following deletion, however, should reduce the tree's height by 1.
		mount2DTree.delete(new KDPoint(75, 10));
		assertEquals("Are you adjusting the height correctly after leaf deletions?", origMountTreeHeight - 1, mount2DTree.height());

		// While this one should also change the root of the tree (obviously).
		mount2DTree.delete(mount2DTree.getRoot());
		assertEquals("Are you deleting inner nodes (or even the root) correctly?", 
				new KDPoint(50, 30), mount2DTree.getRoot());

		// It appears that Mount's tree also gives rise to a special case 
		// of deletion: let's check by performing a deletion and then a search()
		// which'll fail unless the client code takes care of both appropriately.

		mount2DTree.delete(new KDPoint(20, 45));
		assertTrue("After deletion of a node without a right child, a node that formerly existed " + 
				" in its left subtree could no longer be found in the tree.",mount2DTree.search(new KDPoint(20, 20)));

		/* (2) Tests on custom 2D tree: This tree gives rise to a deletion special case
		 * the consistency of which can be checked via getRoot().
		 */

		prepJason2DTree();
		int origJason2DTreeHeight = jason2DTree.height();
		// Delete the root, check expected root and expected height.
		try {
			jason2DTree.delete(jason2DTree.getRoot());
		}catch(Throwable t){
			fail("Caught a " + t.getClass() + " with message " + t.getMessage() + 
					" when deleting a root with no right subtree in a 2D tree.");
		}
		assertEquals("Check your deletion special cases for the root node.", 
				new KDPoint(-10,-3), jason2DTree.getRoot());
		assertEquals("Just deleted the root, but this particular deletion should not have affected " + 
				" the tree height.", origJason2DTreeHeight, jason2DTree.height());

		/* (3) A similar test, only for a 3D tree this time. We go ahead and 
		 * delete the root and its entire right subtree, checking for exceptions
		 * along the way. Then we are left with essentially the same tree as in (2), 
		 * only in three dimensions.	
		 */
		prepJason3DTree();
		KDPoint[] pointsToInitDelete = {
				new KDPoint(2,3,-5), new KDPoint(6,0,-1), 
				new KDPoint(8,-2,6), new KDPoint(8,-2,7),
				new KDPoint(8,-1,9)
		};
		for(KDPoint p: pointsToInitDelete){
			try {
				jason3DTree.delete(p);
			} catch(Throwable t){
				fail("Caught a " + t.getClass() + " with message: " + t.getMessage() + 
						" when deleting " + p + " from a 3D tree.");
			}
		}

		// Now, deleting the root should reflect the expected changes.
		int origJason3DTreeHeight = jason3DTree.height();
		try {
			jason3DTree.delete(jason3DTree.getRoot());
		}catch(Throwable t){
			fail("Caught a " + t.getClass() + " with message " + t.getMessage() + 
					" when deleting a root with no right subtree in a 3D tree.");
		}
		assertEquals("Incorrect root detected after a special case deletions!", 
				new KDPoint(-6,4,8), jason3DTree.getRoot());
		assertEquals("Incorrect tree height detected after a special case deletion which should " + 
				"not change the tree's height.", origJason3DTreeHeight, jason3DTree.height());

		/* (4) Stress test for deletion on arbitrary dimensional trees. 
		 *	We create MAX_DIM-many KD-trees, and for each tree we insert
		 *	MAX_ITER-many KDPoints of the appropriate dimensionality, while
		 *	also keeping a store for these points. We then shuffle this store,
		 *	and sequentially delete all the points in the store from the actual
		 *	tree. 
		 */

		for(int dim = 1; dim < MAX_DIM + 1; dim++){ // For MAX_DIM-many trees...
			KDTree tree = new KDTree(dim);
			LinkedList<KDPoint> pts = new LinkedList<KDPoint>();
			for(int i = 0; i < MAX_ITER; i++){ // For MAX_ITER-many points...
				KDPoint randPoint = getRandomPoint(dim);
				pts.add(randPoint); // Add a new point based on random coordinates
				tree.insert(randPoint);
			}
			// Now delete all the points and check for exceptions while you delete
			// them as well as tree consistency after they're all gone.
			Collections.shuffle(pts, r);
			for(KDPoint p : pts){
				try {
					tree.delete(p);
				}catch(Throwable t){
					fail("Caught a " + t.getClass() + " with message " + t.getMessage() + 
							" when deleting " + p + " from a " + dim + "-D tree.");
				}
			}
			assertTrue("After deleting all the elements inserted in a " + dim + 
					"-D tree, the tree was deemed to not be empty.", tree.isEmpty());
		}

	}

	/**
	 * Test method for {@link KDTree#search(KDPoint)}.
	 */
	@Test
	public void testSearch() {
		// Really the only thing we can do at this point is a stress test
		// for arbitrary-dimensional KD-trees.
		for(int dim = 1; dim < MAX_DIM + 1; dim++){ // For MAX_DIM-many trees...
			KDTree tree = new KDTree(dim);
			LinkedList<KDPoint> pts = new LinkedList<KDPoint>();
			for(int i = 0; i < MAX_ITER; i++){ // For MAX_ITER-many points...
				KDPoint randPoint = getRandomPoint(dim);
				pts.add(randPoint); // Add a new point based on random coordinates
				tree.insert(randPoint);
			}
			// Now search all the points and check for exceptions while you look
			// them all up. Make sure that nothing changes in the tree after every single search.
			Collections.shuffle(pts, r);
			int treeHeight = tree.height();
			KDPoint rootPt = tree.getRoot();
			for(KDPoint p : pts){
				try {
					assertTrue("Looking up " + p + " which is known to be in the tree failed.", 
							tree.search(p));
					assertEquals("Looking up a KDPoint in the tree shouldn't alter its height in any way.", 
							treeHeight, tree.height());
					assertEquals("Looking up a KDPoint in the tree shouldn't alter its root in any way.", 
							rootPt, tree.getRoot());
				}
				catch(AssertionError ae){} // Generated by possible failures of assertions; separate from other Throwables so that you don't report multiple errors in the catchblocks.
				catch(Throwable t){
					fail("Caught a " + t.getClass() + " with message " + t.getMessage() + 
							" when deleting " + p + " from a " + dim + "-D tree.");
				}
			}
		}
	}

	/**
	 * Test method for {@link KDTree#getRoot()}.
	 */
	@Test
	public void testGetRoot(){
		// Since we've covered the cases for emptiness in other
		// methods, we will just make a really simple test here.
		// getRoot() will be used in other tests, such as deletions, to
		// ensure that the proper root is maintained anyway.
		for(int dim = 1; dim <= MAX_DIM; dim++){
			KDTree tempTree = new KDTree(dim);				
			KDPoint p = getRandomPoint(dim);
			tempTree.insert(p);
			assertEquals("For a " + dim + "-D tree where we inserted only " + p + 
					", we did not find it in the root of the tree.", p, tempTree.getRoot());
		}
	}

	/**
	 * Test method for {@link KDTree#range(KDPoint, double)}.
	 */
	@Test
	public void testRange() {
		// (1) Start with very simple tests that reveal fundamental errors.
		// (a) Insert one point in an arbitrary dimensional KD-tree,
		// make sure that you can find it if you draw a circle from
		// the origin.
		for(int dim = 1; dim <= MAX_DIM; dim++){ // For MAX_DIM-many trees...
			for(int i = 0; i < MAX_ITER; i++){ // For MAX_ITER-many points...
				KDPoint originInDim = new KDPoint(dim);
				KDTree tree = new KDTree(dim);				
				KDPoint p = getRandomPoint(dim);
				tree.insert(p);
				assertTrue("Failed a range query for a " + dim + "-D tree which only contained " +
						p + ", KDPoint #" + i + ".", checkRangeQuery(tree, originInDim, p.distance(originInDim) + EPSILON, p));
			}
		}

		// (b) Make a 1-D KD-tree (simple BST). Test Range queries on that one. 
		KDPoint[] points = {
				new KDPoint(15.0), new KDPoint(-20.0),
				new KDPoint(-10.0), new KDPoint(30.0),
				new KDPoint(20.0), new KDPoint(30.0)
		};
		KDTree degenerate = new KDTree(1);
		for(KDPoint p: points)
			degenerate.insert(p);

		// A range of 1225 for the left child of the root should cover the root
		// and the last remaining child of the left subtree (points[2]).
		KDPoint root = points[0], leftChildOfRoot = points[1];		
		assertTrue("1-D Tree (Simple BST): Failed the range query for the left child of the root.",
				checkRangeQuery(degenerate, leftChildOfRoot, 1225 + EPSILON, root, points[2]));

		// Repeat the same logic on the root for a different range. This time, only the root's 
		// right subtree should be contained in the result of the query.
		assertTrue("1-D Tree (Simple BST): Failed the range query for the the root.", 
				checkRangeQuery(degenerate, root, 225 + EPSILON, points[3], points[4], points[5]));

		// (2) Test my own 2D Tree. 
		prepJason2DTree();

		// (a) For every single point in the tree, check to see if the range
		// query starting from the origin satisfies its squared 2-norm.
		KDPoint[] ptsInTree  = {
				new KDPoint(2, 3), new KDPoint(-1, 6),
				new KDPoint(0, 5), new KDPoint(-1, 0),
				new KDPoint(-10, -3), new KDPoint(-4,-3),
				new KDPoint(-6,-6), new KDPoint(-4,-1),
				new KDPoint(1,3)
		};

		KDPoint origin = new KDPoint(2);
		Collection<KDPoint> rQueryRes;
		for(KDPoint p: ptsInTree){
			rQueryRes = jason2DTree.range(origin, distance(p, origin) + EPSILON);
			assertTrue("2-D Tree: We checked whether " + p + 
					" satisfied a range query from the origin and the check failed.", 
					rQueryRes.contains(p));
		}
		// (b) Observe the tree and make some simple, static tests.
		// I've build the spatial decomposition on a piece of paper so I know
		// the following queries should work.
		assertTrue("2-D Tree: Range query failure at 1st quadrant for a KDPoint in the tree.", 
				checkRangeQuery(jason2DTree, ptsInTree[8], 100,
						ptsInTree[1], ptsInTree[2], ptsInTree[0]));
		assertTrue("2-D Tree: Range query failure at 3rd quadrant for a KDPoint in the tree.", 
				checkRangeQuery(jason2DTree, ptsInTree[6], 5184, ptsInTree[4],
						ptsInTree[7], ptsInTree[5], ptsInTree[3]));

		// Let's also make some tests where the query point is not a part of the tree.
		// I will make this simple, perturbing the previous query points by EPSILON
		// and making sure the previous origins are now contained in the answer.

		KDPoint newOrigin1 = new KDPoint(ptsInTree[8].coords[0] + EPSILON, ptsInTree[8].coords[1]),
				newOrigin2 = new KDPoint(ptsInTree[6].coords[0], ptsInTree[6].coords[1] + EPSILON);
		assertTrue("2-D Tree: Range query failure at 1st quadrant for a KDPoint *not* in the tree.", 
				checkRangeQuery(jason2DTree, newOrigin1, 100,
						ptsInTree[1], ptsInTree[2], ptsInTree[0], ptsInTree[8]));
		assertTrue("2-D Tree: Range query failure at 3rd quadrant for a KDPoint *not* in the tree.",
				checkRangeQuery(jason2DTree, newOrigin2, 5184,
						ptsInTree[4], ptsInTree[7], ptsInTree[5], ptsInTree[3], ptsInTree[6]));

		// (3) Test my own 3D Tree. Similar to (2), but in 3 dimensions.
		// I have precomputed this with Python.
		prepJason3DTree();
		assertTrue("We failed our \"point-in-tree\"range query for the 3D Tree.", 
				checkRangeQuery(jason3DTree, new KDPoint(8,-2,7), 233,
						new KDPoint(2,3,-5), new KDPoint(-1,2,3), new KDPoint(6,0,-1),
						new KDPoint(-1,-9,2), new KDPoint(-6,4,8), new KDPoint(8,-2,6),
						new KDPoint(4,1,-2), new KDPoint(-4,1,0), new KDPoint(8,-1,9),
						new KDPoint(-3,-10,1), new KDPoint(-3,-8,0)));
		assertTrue("We failed our \"point-not-in-tree\"range query for the 3D Tree.", 
				checkRangeQuery(jason3DTree, new KDPoint(8 + getRandomSign()*EPSILON,-2 + getRandomSign()*EPSILON, 
						7 + getRandomSign()*EPSILON), 233, new KDPoint(8, -2, 7), // Include previous anchor point
						new KDPoint(2,3,-5), new KDPoint(-1,2,3), new KDPoint(6,0,-1),
						new KDPoint(-1,-9,2), new KDPoint(-6,4,8), new KDPoint(8,-2,6),
						new KDPoint(4,1,-2), new KDPoint(-4,1,0), new KDPoint(8,-1,9),
						new KDPoint(-3,-10,1), new KDPoint(-3,-8,0)));


		// (4) Stress test. Arbitrary dimensions. Mechanically construct KDPoints,
		// Store their pairwise squared euclidean distances,
		// make sure that the range query from every point to another is satisfied.

		for(int dim = 1; dim <= MAX_DIM; dim++){
			KDTree tree = new KDTree(dim);
			HashMap<KDPoint[], Double> ptPairs = new HashMap<KDPoint[], Double>();
			for(int i = 0; i < MAX_ITER; i++){
				KDPoint p1 = getRandomPoint(dim), p2 = getRandomPoint(dim);
				ptPairs.put(new KDPoint[]{p1, p2}, distance(p1, p2));
				tree.insert(p1);
				tree.insert(p2);
			}
			for(KDPoint[] pair : ptPairs.keySet())
				assertTrue("In a " + dim + "-D tree, we failed a range query for: " 
						+ pair[0] + " and " + pair[1] + ".", 
						checkRangeQuery(tree, pair[0], distance(pair[0], pair[1]) + EPSILON, pair[1]));
		}
	}


	/**
	 * Test method for {@link KDTree#nearestNeighbor(KDPoint)}.
	 */
	@Test
	public void testNearestNeighbor() {
		
		// Test for both points in the tree as well as points not in the tree.

		// (1) 1-D KD-tree (simple BST). Insert 10 points in a BST with an incremental
		// gap between them, check nearest neighbor for every one. Repeat this MAX_ITER-many times.

		for(int i = 0; i <MAX_ITER; i++){
			LinkedList<KDPoint> randPts = new LinkedList<KDPoint>();
			KDTree tree = new KDTree(1);
			for(int j = 0; j < 10; j++){
				KDPoint randPt = getRandomPoint(1);
				randPts.add(randPt);
				tree.insert(randPt);
			}
			// Now, sort the points based on the distance to the first point.
			Collections.sort(randPts, new KNNComparator<KDPoint>(randPts.get(0)));
			// The KDPoint generated from KDTree.nearestNeighbor(KDPoint) should then
			// be equal to randPts.get(1), the closest point after the sorting that's not
			// the point itself.
			assertEquals("1-D Tree (Classic BST): Failed on a nearest neighbor query for iteration #" 
					+ i + " with anchor point " + randPts.get(0) + ".", randPts.get(1), 
					tree.nearestNeighbor(randPts.get(0)));

		}

		// (2) Dave Mount's tree
		// Points in tree mapped to their nearest neighbor.
		prepMountTree();
		assertEquals("First NN query for Mount tree failed.", 
				new KDPoint(75, 10), mount2DTree.nearestNeighbor(new KDPoint(70, 20)));
		assertEquals("Second NN query for Mount tree failed.", 
				new KDPoint(35,60), mount2DTree.nearestNeighbor(new KDPoint(60,80)));

		// Slightly perturbed previous anchor points now should map to
		// the previous anchor points in terms of nearest neighbor.
		KDPoint p1 = new KDPoint(75 + getRandomSign() * EPSILON, 10 + getRandomSign() * SCALE * EPSILON),
				p2 = new KDPoint(35 + getRandomSign() * EPSILON, 60 + getRandomSign() * SCALE * EPSILON);
		assertEquals("Third NN query for Mount tree failed.", new KDPoint(75.0, 10.0), mount2DTree.nearestNeighbor(p1));
		assertEquals("Fourth NN query for Mount tree failed.", new KDPoint(35.0, 60.0), mount2DTree.nearestNeighbor(p2));

		// (3) My 2D Tree
		prepJason2DTree();
		//Same deal. Two nearest neighbor queries for points in the tree,
		// and two for points not in the tree.
		assertEquals("First NN query for custom 2D tree failed.", new KDPoint(2, 3), 
				jason2DTree.nearestNeighbor(new KDPoint(1, 3)));
		assertEquals("Second NN query for custom 2D tree failed.", new KDPoint(-4, -1), 
				jason2DTree.nearestNeighbor(new KDPoint(-4, -3)));

		p1 = new KDPoint(1 + getRandomSign()*EPSILON, 3 + getRandomSign()*EPSILON);
		p2 = new KDPoint(); // 2D Origin
		assertEquals("Third NN query for custom 2D tree failed.", new KDPoint(1,3),
				jason2DTree.nearestNeighbor(p1));
		assertEquals("Fourth NN query for custom 2D tree failed.", new KDPoint(-1,0),
				jason2DTree.nearestNeighbor(p2));

		// (4) My 3D Tree
		prepJason3DTree();
		// Same deal.
		assertEquals("First NN query for custom 3D tree failed.", new KDPoint(8,-2,6),
				jason3DTree.nearestNeighbor(new KDPoint(8,-2,7)));
		assertEquals("Second NN query for custom 3D tree failed.", new KDPoint(-1,2,3),
				jason3DTree.nearestNeighbor(new KDPoint(-4,1,0)));	
		p1 = new KDPoint(8 + getRandomSign()*EPSILON, -2 + getRandomSign()*EPSILON, 7 + getRandomSign()*EPSILON);
		p2 = new KDPoint(-4 + getRandomSign()*EPSILON, 1+getRandomSign()*EPSILON, getRandomSign()*EPSILON );
		assertEquals("Third NN query for custom 3D tree failed.", new KDPoint(8,-2,7),
				jason3DTree.nearestNeighbor(p1));
		assertEquals("Fourth NN query for custom 3D tree failed.", new KDPoint(-4,1,0),
				jason3DTree.nearestNeighbor(p2));
	}
	
	/**
	 * Test method for {@link KDTree#kNearestNeighbors(int, KDPoint)}.
	 */
	@Test
	public void testKNearestNeighbors() {
		// Straightforward modification of testNearestNeighbor().

		// (1) 1-D Tree (Simple BST).
		for(int i = 0; i <MAX_ITER; i++){
			ArrayList<KDPoint> randPts = new ArrayList<KDPoint>();
			KDTree tree = new KDTree(1);
			for(int j = 0; j < 10; j++){
				KDPoint randPt = getRandomPoint(1);
				randPts.add(randPt);
				tree.insert(randPt);
			}
			
			// Now, sort the points based on the distance to the first point.
			Collections.sort(randPts, new KNNComparator<KDPoint>(randPts.get(0)));

			// And check whether all K-NN queries for K=2,3,...9 are satisfied.
			for(int k = 2; k <=9; k++)
				assertTrue("1-D Tree (Classic BST): In iteration " + i + ", we failed our " + k + "-NN query for " + 
						randPts.get(0) + ".", checkKNNQuery(tree, randPts.get(0), k, randPts.subList(1, k+1)));
		}

		// (2) Dave Mount's tree, point-in-tree and not-in-tree.
		prepMountTree();
		assertTrue("Failed 2-NN query for point-in-Mount tree.", // Point in tree
				checkKNNQuery(mount2DTree, new KDPoint(20,45), 2, new KDPoint(10,35), new KDPoint(35,60)));
		assertTrue("Failed 3-NN query for point-in-Mount tree.", // Point not in tree
				checkKNNQuery(mount2DTree, new KDPoint(20,45 + EPSILON), 3, 
						new KDPoint(20, 45), new KDPoint(10,35), new KDPoint(35,60)));

		// (3) My 2D Tree, point-in-tree and point-not-in-tree.
		prepJason2DTree();
		assertTrue("Failed 3-NN query for point-in-Jason's 2D tree.",checkKNNQuery(jason2DTree,
				new KDPoint(-4,-3), 3,	new KDPoint(-4,-1), new KDPoint(-6,-6), new KDPoint(-1,0)));
		assertTrue("Failed 4-NN query for point-not-in-Jason's 2D tree.",checkKNNQuery(jason2DTree, new KDPoint(-4 - EPSILON, -3), 4,
				new KDPoint(-4, -3), new KDPoint(-4,-1), new KDPoint(-6,-6), new KDPoint(-1,0)));

		// (4) My 3D Tree. same deal.
		prepJason3DTree();
		assertTrue("Failed 4-NN query for point-in-Jason's 3D tree.", checkKNNQuery(jason3DTree,
				new KDPoint(-3,-8,-3), 4, new KDPoint(-3,-8,0), new KDPoint(-3,-10,1), new KDPoint(-1,-9,2), new KDPoint(-4, 1, 0)));
 		assertTrue("Failed 5-NN query for point-not-in-Jason's 3D tree.", checkKNNQuery(jason3DTree,
				new KDPoint(-3,-8,-3 - EPSILON), 5, new KDPoint(-3,-8,-3), new KDPoint(-3,-8,0), 
				new KDPoint(-3,-10,1), new KDPoint(-1,-9,2), new KDPoint(-4, 1, 0)));

		// (5) Stress test for all dimensions, with intermediate neighbor sorting
		// based on custom comparator.
		for(int dim = 1; dim <= MAX_DIM; dim++){
			KDTree tree = new KDTree(dim);
			LinkedList<KDPoint> ptCollection = new LinkedList<KDPoint>();
			for(int i = 0; i < MAX_ITER; i++){
				KDPoint randPt = getRandomPoint(dim);
				ptCollection.add(randPt);
				tree.insert(randPt);
			}
			// We sort all the KDPoints based on their distances to the first one.
			Collections.sort(ptCollection, new KNNComparator<KDPoint>(ptCollection.get(0)));

			// And then we check if all possible k-nn queries, for k = 1 to 11, are satisfied.
			// This can be improved, so that the upper bound of k is also MAX_ITER!
			for(int k = 1; k < 12; k++)
				assertTrue("Stress test for " + dim + "-D tree: Failed a " + k +"-NN query " + 
						"when anchoring around " + ptCollection.get(0) + ".", 
						checkKNNQuery(tree, ptCollection.get(0), k, ptCollection.subList(1, k+1)));
		}

	}


	/**
	 * Test method for {@link KDTree#height()}.
	 */
	@Test
	public void testHeight() {
		
		/* Tests for height equal to -1 for empty trees have already
		 * occurred in testIsEmpty(), so we won't do that here. 
		 * Instead:
		 */
		
		/****** (1) Check if a stub tree has a height of 0.**********
		 ****** Do that with a bunch of valid dimensionality trees.*/

		for(int i = 0; i < MAX_ITER; i++) {
			int randDim = r.nextInt(MAX_DIM) + 1; // [1, ..., MAX_DIM]
			KDTree tempTree = new KDTree(randDim);
			tempTree.insert(new KDPoint(randDim));
			assertEquals("A KD-tree of dimensionality " + randDim + " with one element "
					+ " inside it was found to have a height of : " + tempTree.height() + 
					" instead of 0", 0, tempTree.height());
		}

		/***** (2) Three short trees. We will perform many random ********************
		 ***** tests, by discriminating on the x axis by the smallest amounts possible
		 ***** to make it so that we build appropriately shaped trees.****************/

		for(int i = 0; i < MAX_ITER; i++){

			// (a) Right-heavy.  	
			double xVal = r.nextDouble();
			twoDTree.insert(new KDPoint(xVal, r.nextDouble()));
			twoDTree.insert(new KDPoint(xVal + EPSILON, r.nextDouble())); // Barely larger on x!
			assertEquals("On iteration " + i + ", we created what should have been a right-heavy 2D-"
					+ "tree with height 1, but the height was: " + twoDTree.height() + ".",	1, twoDTree.height());

			// (b) Left-heavy.  
			threeDTree.insert(new KDPoint(xVal, r.nextDouble(), r.nextDouble()));
			threeDTree.insert(new KDPoint(xVal - EPSILON, r.nextDouble(), r.nextDouble())); // Barely smaller on x!
			assertEquals("On iteration " + i + ", we created what should have been a left-heavy 3D-"
					+ "tree with height 1, but the height was: " + threeDTree.height() + ".", 1, threeDTree.height());

			// (c) Balanced.  
			KDPoint first = new KDPoint(xVal, r.nextDouble(), r.nextDouble(), r.nextDouble());
			fourDTree.insert(first);
			KDPoint second = new KDPoint(xVal - 1000*EPSILON, r.nextDouble(), r.nextDouble(), r.nextDouble());
			assertNotEquals("Something's not right here", first.coords[0], second.coords[0]);
			fourDTree.insert(second);
			fourDTree.insert(new KDPoint(xVal + EPSILON, r.nextDouble(), r.nextDouble(), r.nextDouble()));
			assertEquals("On iteration " + i + ", we created what should have been a balanced 4D-"
					+ "tree with height 1, but the height was: " + fourDTree.height() + ".", 1, fourDTree.height());

			// (d) Right-heavy, but with a height of 2.
			fourDTree = new KDTree(4); // In the absence of a clear() method...
			double yVal = r.nextDouble();
			fourDTree.insert(new KDPoint(xVal, r.nextDouble(), r.nextDouble(), r.nextDouble()));
			fourDTree.insert(new KDPoint(xVal - EPSILON, r.nextDouble(), r.nextDouble(), r.nextDouble()));
			fourDTree.insert(new KDPoint(xVal + EPSILON, yVal, r.nextDouble(), r.nextDouble()));
			fourDTree.insert(new KDPoint(xVal + EPSILON, yVal + EPSILON, r.nextDouble(), r.nextDouble())); // Guarantees right-heaviness
			assertEquals("On iteration " + i + ", we created what should have been a right-heavy 4D-"
					+ "tree with height 2, but the height was: " + fourDTree.height() + ".", 2, fourDTree.height());

			// (e) Left-heavy, but with a height of 2.
			fourDTree = new KDTree(4); 
			fourDTree.insert(new KDPoint(xVal, r.nextDouble(), r.nextDouble(), r.nextDouble()));
			fourDTree.insert(new KDPoint(xVal - EPSILON, yVal, r.nextDouble(), r.nextDouble()));
			fourDTree.insert(new KDPoint(xVal + EPSILON, r.nextDouble(), r.nextDouble(), r.nextDouble()));
			fourDTree.insert(new KDPoint(xVal - EPSILON, yVal - EPSILON, r.nextDouble(), r.nextDouble())); // Guarantees left-heaviness
			assertEquals("On iteration " + i + ", we created what should have been a left-heavy 4D-"
					+ "tree with height 2, but the height was: " + fourDTree.height() + ".", 2, fourDTree.height());

			// (f) Balanced, but with a height of 2.
			double yVal2 = yVal + 3*EPSILON;
			fourDTree = new KDTree(4); 
			fourDTree.insert(new KDPoint(xVal, r.nextDouble(), r.nextDouble(), r.nextDouble()));
			fourDTree.insert(new KDPoint(xVal - EPSILON, yVal, r.nextDouble(), r.nextDouble()));
			fourDTree.insert(new KDPoint(xVal + EPSILON, yVal2, r.nextDouble(), r.nextDouble()));
			fourDTree.insert(new KDPoint(xVal - EPSILON, yVal - EPSILON, r.nextDouble(), r.nextDouble()));
			fourDTree.insert(new KDPoint(xVal - EPSILON, yVal + EPSILON, r.nextDouble(), r.nextDouble()));
			fourDTree.insert(new KDPoint(xVal + EPSILON, yVal2-EPSILON, r.nextDouble(), r.nextDouble()));
			fourDTree.insert(new KDPoint(xVal + EPSILON, yVal2+EPSILON, r.nextDouble(), r.nextDouble()));
			assertEquals("On iteration " + i + ", we created what should have been a balanced 4D-"
					+ "tree with height 2, but the height was: " + fourDTree.height() + ".", 2, fourDTree.height());
			
			// Reset trees.
			try {
				tearDown();
				setUp();
			}catch(Exception exc){
				fail("Caught an " + exc.getClass());
			}
		} // for i = 0... MAX_ITER - 1


		// Mount's tree's and Jason's trees' height checks:

		prepMountTree();
		prepJason2DTree();
		prepJason3DTree();
		assertEquals("Mismatch between expected and actual tree height for a 2D-tree.", 5, mount2DTree.height());
		assertEquals("Mismatch between expected and actual tree height for a 2D-tree.", 6, jason2DTree.height());
		assertEquals("Mismatch between expected and actual tree height for a 3D-tree.", 5, jason3DTree.height());
	}

	/**
	 * Test method for {@link  KDTree#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		// Check if all our freshly built trees are empty.
		assertTrue("Freshly created 2D tree found to not be empty.", twoDTree.isEmpty());
		assertTrue("Freshly created 3D tree found to not be empty.", threeDTree.isEmpty());
		assertTrue("Freshly created 4D tree found to not be empty.", fourDTree.isEmpty());

		// What about getRoot()? Is it behaving as expected?
		assertEquals("Root of an empty 2D tree was not null.", null, twoDTree.getRoot());
		assertEquals("Root of an empty 3D tree was not null.", null, threeDTree.getRoot());
		assertEquals("Root of an empty 4D tree was not null.", null, fourDTree.getRoot());

		// Check if all our freshly build trees also have a
		// height of -1. This is making sure that the definition
		// of isEmpty(), as mentioned in the docs, is satisfied.
		assertEquals("Freshly created 2D tree found to not have a height of -1.", 
				-1, twoDTree.height());
		assertEquals("Freshly created 3D tree found to not have a height of -1.", 
				-1,	threeDTree.height());
		assertEquals("Freshly created 4D tree to not have a height of -1.", 
				-1, fourDTree.height());

		// For a bunch of trees of valid dimensionalities, make sure the
		// invariants for isEmpty() are satisfied.
		for(int i = 0; i < MAX_ITER; i++){
			int randDim = r.nextInt(MAX_DIM) + 1; // [1,..., MAX_DIM]
			KDTree tempTree = new KDTree(randDim);
			assertTrue("Freshly created KDTree of dimensionality " + 
					randDim + " was found to not be empty.", tempTree.isEmpty());
			assertEquals("Freshly created KDTree of dimensionality " + 
					randDim + " was found to not have a height of -1.", -1, tempTree.height());
			assertEquals("Root of freshly created KDTree of dimensionality " + 
					randDim + " was found to not be null.", null, tempTree.getRoot());
		}

		// (4) Finally, make sure that trees with elements in them are not
		// considered empty. Try this with our three pre-existing trees.
		for(int i = 0; i < MAX_ITER; i++){
			twoDTree.insert(new KDPoint(r.nextInt(), r.nextInt()));
			assertFalse("2D tree with one KDPoint in it was found to be empty.", twoDTree.isEmpty());
			twoDTree.insert(new KDPoint(r.nextInt(), r.nextInt()));
			assertFalse("2D tree with two KDPoints in it was found to be empty.", twoDTree.isEmpty());

			threeDTree.insert(new KDPoint(r.nextInt(), r.nextInt(), r.nextInt()));
			assertFalse("3D tree with one KDPoint in it was found to be empty.", threeDTree.isEmpty());
			threeDTree.insert(new KDPoint(r.nextInt(), r.nextInt(), r.nextInt()));
			assertFalse("3D tree with two KDPoints in it was found to be empty.", threeDTree.isEmpty());

			fourDTree.insert(new KDPoint(r.nextInt(), r.nextInt(), r.nextInt(), r.nextInt()));
			assertFalse("4D tree with one KDPoint in it was found to be empty.", fourDTree.isEmpty());
			fourDTree.insert(new KDPoint(r.nextInt(), r.nextInt(), r.nextInt(), r.nextInt()));
			assertFalse("4D tree with two KDPoints in it was found to be empty.", fourDTree.isEmpty());

		}

	}

}
