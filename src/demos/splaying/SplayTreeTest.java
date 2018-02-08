package demos.splaying;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.Random;

import static org.junit.Assert.*;

public class SplayTreeTest {

	private SplayTree<Integer> spTree;
	private Random r;
	private final static long SEED=47;
	private final static String EMPTY_MSG = "Tree is not empty; should not have thrown an EmptyTreeException at this point.";

	@Before
	public void setUp() throws Exception {
		spTree = new SplayTree<Integer>();
		r = new Random(SEED);
	}

	@After
	public void tearDown() throws Exception{
		spTree = null;
		r = null;
	}

	@Test
	public void testEmptinessAndSize(){
		assertTrue(spTree.isEmpty());
		assertEquals(0, spTree.getCount());
		spTree.insert(1);
		assertFalse(spTree.isEmpty());
		assertEquals(1, spTree.getCount());
		spTree.delete(1);
		assertTrue(spTree.isEmpty());
		assertEquals(0, spTree.getCount());
	}

	@Test
	public void testInsertionsSimple() throws Exception{
		Integer[] ints = new Integer[]{-10, 10, 2};
		for(Integer i: ints)
			spTree.insert(i);
		/* The tree should now look like this:
		 * 
		 * 				2
		 * 			   / \
		 *           -10 10
		 */

			Iterator<Integer> it = spTree.preOrder();
			assertTrue(checkExpectedOrder(it, new Integer[]{2, -10, 10}));

	}

	@Test
	public void testInsertionsComplex() throws Exception{
		int i = 0, randNum = 0;
		try {
			for(i = 0; i < 1000; i++){
				randNum = r.nextInt();
				spTree.insert(randNum);
				assertEquals(spTree.getRoot(), new Integer(randNum));
		}
			} catch(Throwable t){
			fail("Iteration " + i + " with random number " + randNum + ": threw a " + t.getClass() + " with message: " + t.getMessage());
		}
		
	}

	@Test
	public void testSeekingSimple()  throws Exception{
		Integer[] ints = new Integer[]{-10, 10, 2};
		for(Integer i : ints)
			spTree.insert(i);
		/* The tree now looks like this:
		 * 
		 * 				2
		 * 			  /   \
		 * 			-10		10
		 */

			assertTrue(seekElements(spTree, new Integer[]{2, -10, 10})); // All those elements are in the tree and should be found at the root after seeking
			// We re-insert the elements for the subsequent tests
			// because seekElements() will've re-organized them.
			
			for(Integer i : ints)
				spTree.insert(i);
			assertEquals(null, spTree.search(-11));

			/* Despite the unsuccessful search, the underlying splaying of the tree should make
			 * it unbalanced, like so:
			 * 
			 * 					-10
			 *					   \
			 *						2
			 *						  \	
			 *							10 				
			 */
			Iterator<Integer> postOrder = spTree.postOrder();
			assertTrue(checkExpectedOrder(postOrder, new Integer[]{10, 2, -10}));

		
	}

	@Test
	public void testSeekingComplex() throws Exception{
		int i = 0, j = 0;
		try{
			for(i = 0; i < 1000; i++)
				spTree.insert(i);
			for(j = 0; j < 1000; j++){
				assertEquals(new Integer(j), spTree.search(j)); // The last value of i generated is the immediate predecessor of all new additions.
				assertEquals(new Integer(j), spTree.getRoot()); // Which should also be at the root
			}
		} catch(Throwable t){
			fail("After " + i + " insertions and " + j + " searches, we caught a " + t.getClass() + " with message: " + t.getMessage() + ".");
		}
		
	}

	@Test
	public void testDeletionSimple()  throws Exception{
		Integer[] ints = new Integer[]{-10, 10, 2};
		for(Integer i : ints)
			spTree.insert(i);

		/* The tree now looks like this:
		 * 
		 * 				2
		 * 			  /   \
		 * 			-10		10
		 * 
		 * Trying to delete -11 should not be successful in deleting any nodes, but it should
		 * splay the tree, bringing -10 to the root for the unbalanced result:
		 * 
		 * 					-10
		 *					   \
		 *						2
		 *						  \	
		 *							10 				
		 */
		assertEquals(3, spTree.getCount());
		spTree.delete(-11);
		assertEquals(3, spTree.getCount());

			Iterator<Integer> postOrder = spTree.postOrder();
			assertTrue(checkExpectedOrder(postOrder, new Integer[]{10, 2, -10}));
			while(!spTree.isEmpty())
				spTree.delete(spTree.getRoot());
			assertTrue(spTree.isEmpty());

	}

	@Test
	public void testDeletionComplex()  throws Exception{
		int i = 0, j = 0;
		try {
			for(i = 0; i < 1000; i++)
				spTree.insert(i);
			for(j = 999; j > -1; j--){
				assertEquals(new Integer(j), spTree.delete(j)); // All elements removed are actually in the tree
				if(j > 0)
					assertEquals(new Integer(j - 1), spTree.getRoot()); // When 999 is removed, 998 will be the new root, and so on until 0.
				else
					assertTrue(spTree.isEmpty());
			}
		} catch(Throwable t){
			fail("After " + i + " insertions and " + j + " deletions, we caught a " + t.getClass() + " with message: " + t.getMessage() );
		}
	}


	/* Some helper subroutines for the tests above, */

	private boolean seekElement(SplayTree<Integer> spTree, Integer element){
		Integer retVal = spTree.search(element);
		if(!retVal.equals(element))
			return false;
		if(!spTree.getRoot().equals(retVal)) // Sought elements, when found, should ascend to the root in a splay tree
			return false;
		return true;
	}



	private boolean seekElements(SplayTree<Integer> spTree, Integer[] elements){
		for(Integer i : elements)
			if(!seekElement(spTree, i))
				return false;
		return true;
	}


	private boolean checkExpectedOrder(Iterator<Integer> it, Integer[] array){
		if(!it.hasNext())
			return false;
		for(Integer i: array)
			if(!it.next().equals(i))
				return false;
		if(it.hasNext())
			return false;
		return true;
	}

}
