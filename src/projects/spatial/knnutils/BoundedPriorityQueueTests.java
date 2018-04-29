package projects.spatial.knnutils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * <p>Some tests for {@link BoundedPriorityQueue}s.</p>
 * @author <a href = "mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 */
public class BoundedPriorityQueueTests {

	private BoundedPriorityQueue<String> people;
	private Random r;
	private static final int DEFAULT_CAPACITY = 10;
	private static final int DEFAULT_SEED = 47;
	private static final int DEFAULT_ITER = 500;
	private static final int MAX_PRIORITY = 1000;
	private static final int SCALE = 100;

	@Before
	public void setUp(){
		try {
			people = new BoundedPriorityQueue<String>(DEFAULT_CAPACITY);
		}catch(Throwable t){
			fail("During setUp(), we caught a " + t.getClass() + 
					" with message: " + t.getMessage() + " when creating a BPQ with the default capacity of:" + 
					DEFAULT_CAPACITY + ".");
		}
		r = new Random(DEFAULT_SEED);
	}

	@After
	public void tearDown()  {
		people = null;
		r = null;
		System.gc();
	}

	@Test
	public void testBoundedPriorityQueue() {

		// First, test that for a bunch of invalid priorities,
		// an exception is thrown.
		for(int i = 0; i < DEFAULT_ITER; i++){
			int randNegInt = -r.nextInt(MAX_PRIORITY); // 0, -1, ..., -MAX_PRIORITY + 1
			try {
				new BoundedPriorityQueue<Object>(randNegInt);
			} catch(RuntimeException exc){
			} catch(Throwable t){
				fail("When creating BPQ # " + i + " with priority " + randNegInt
						+ ", we should've caught a RuntimeException. Instead, we caught a " 
						+ t.getClass() + " with message: " + t.getMessage() + ".");
			}
		}

		// Then, check that for a bunch of valid priorities, everything goes well.
		for(int i = 0; i < DEFAULT_ITER; i++){
			int randInt = r.nextInt(MAX_PRIORITY); // 0, 1, ..., MAX_PRIORITY - 1
			try {
				new BoundedPriorityQueue<Object>(randInt);
			}
			catch(Throwable t){
				fail("When creating BPQ # " + i + " with priority " + randInt + 
						", we caught a " + t.getClass() + " with message: " + 
						t.getMessage() + ".");
			}
		}
	}

	@Test
	public void testEnqueue() {

		// (1) Enqueue exactly DEFAULT_CAPACITY many elements. Assure not empty
		// after each enqueueing.
		assertTrue("BPQ should be empty", people.isEmpty());
		for(int i = 0; i < DEFAULT_CAPACITY; i++){
			int priority = r.nextInt(MAX_PRIORITY);
			try {
				people.enqueue("Person " + i, priority);
			} catch(Throwable t){
				fail("After inserting person #" + i + " with priority " + priority + " in our BPQ, we caught a " + 
						t.getClass() + " with error message: " + t.getMessage() + ".");
			}
			assertFalse("After enqueuing the person #" + i + " with priority " + priority + 
					", the BPQ was determined to be empty.", people.isEmpty());
		}
		
		
		// (2) Enqueue another one, check that size() is the same since this is a BPQ.
		int priority = r.nextInt(MAX_PRIORITY);
		try {
			people.enqueue("Person " + DEFAULT_CAPACITY, priority);
		}catch(Throwable t){
			fail("Caught a " + t.getClass() + " with message: " + t.getMessage() + 
					" when inserting element \"Person " + DEFAULT_CAPACITY + "\"" + 
					" with priority " + priority + ", which is one beyond the queue's capacity.");
		}
		assertEquals("Inserting an element beyond a BPQ's capacity should not expand the queue, "
				+ "which is what happened when inserting element \"Person " + DEFAULT_CAPACITY + "\"" + 
				" with priority " +	priority + ".",	DEFAULT_CAPACITY, people.size());

		// (3) Keep on doing that DEFAULT_ITER - many times, always making sure the size 
		// remains the same.
		for(int i = 0; i < DEFAULT_ITER; i++){
			priority = r.nextInt(MAX_PRIORITY);
			try {
				people.enqueue("Person " + new Integer(DEFAULT_CAPACITY + i), priority);
			} catch(Throwable t){
				fail("After inserting person #" + new Integer(DEFAULT_CAPACITY + i) + 
						" with priority " + priority + " in our BPQ, we caught a " 
						+ t.getClass() + " with error message: " + t.getMessage() + ".");
			}
			assertEquals("Inserting elements beyond a BPQ's capacity should not expand the queue, "
					+ "which is what happened when inserting element \"Person " + 
					new Integer(DEFAULT_CAPACITY + i) + "\"" + " with priority " +	
					priority + ".",	people.size(), DEFAULT_CAPACITY);
		}
	}

	@Test
	public void testDequeue() {
		
		// (1) An easy test.
		people.enqueue("Jim", r.nextInt(MAX_PRIORITY));
		assertEquals("Jim", people.dequeue());
		assertTrue("BPQ should be empty after dequeueing its only element.", people.isEmpty());
		
		// (2) Make sure that two elements with equal priorities
		// get processed in a FIFO manner.
		people.enqueue("Jim", 11.3);
		people.enqueue("Jill", 11.3);
		people.enqueue("Mary",9.1); // precedes the other two!
		assertEquals("It appears that this BPQ does not provide for proper ordering.", 
				"Mary", people.dequeue());
		assertEquals("Elements with the same priority should be treated in a FIFO manner", 
				"Jim", people.dequeue());
		assertEquals("Elements with the same priority should be treated in a FIFO manner", 
				"Jill", people.dequeue());
		assertEquals("Dequeueing an empty queue should return null to the caller.", 
				null, people.first());
		
		assertTrue("BPQ should be empty after dequeueing all of its elements.", people.isEmpty());
		
		// (3) A rather contrived test.
		Double[] priorities = new Double[DEFAULT_CAPACITY];
		for(int i = 0; i < priorities.length; i++){
			double priority = SCALE * r.nextDouble(); 
			priorities[i] = priority;
			people.enqueue("Person " + priority, priority);
		}
		
		Arrays.sort(priorities);
		int size = DEFAULT_CAPACITY;
		for(Double pr : priorities){
			assertEquals("It appears that this BPQ does not provide for proper ordering.", 
					"Person " + pr, people.dequeue());
			assertEquals("Dequeuing a BPQ should result in a size decrease by 1.", --size, people.size());
		}
		assertTrue("BPQ should be empty after dequeueing all of its elements.", people.isEmpty());
		
	}

	@Test
	public void testFirst() {
		
		// Similar tests to testDequeue(), with the obvious caveat
		// that just peeking into the BPQ should not alter its size...
		
		// (1) An easy test.
		people.enqueue("Jim", r.nextInt(MAX_PRIORITY));
		assertEquals("Jim", people.first());
		assertFalse("A singleton BPQ should not be empty after peeking its top element.", 
				people.isEmpty());
		people = new BoundedPriorityQueue<String>(DEFAULT_CAPACITY);
		// (2) Make sure that two elements with equal priorities
		// get processed in a FIFO manner.
		people.enqueue("Jim", 11.3);
		people.enqueue("Jill", 11.3);
		assertEquals("Elements with the same priority should be treated in a FIFO manner", 
				"Jim", people.first());
		people.dequeue();
		assertEquals("Elements with the same priority should be treated in a FIFO manner", 
				"Jill", people.dequeue()); // Attention, this is dequeue()!
		// So, now, peeking should be null.
		assertEquals("Peeking into an empty queue should provide us with null.", 
				null, people.first());
		
		// (3) A rather contrived test.
		Double[] priorities = new Double[DEFAULT_CAPACITY];
		for(int i = 0; i < priorities.length; i++){
			double priority = SCALE * r.nextDouble(); 
			priorities[i] = priority;
			people.enqueue("Person " + priority, priority);
		}
		Arrays.sort(priorities);
		assertEquals("It appears that this BPQ does not provide for proper ordering.", 
				"Person " + priorities[0], people.first());
		
		// (4) Finally, test semantics. Make sure that insertions after the queue is full
		// obey the priorities.
		people.enqueue("New Min Person", priorities[0] - 1.0);
		assertEquals("Inserting a minimum priority element after the queue is full should not alter its size.", 
				DEFAULT_CAPACITY, people.size());
		assertEquals("Inserting a minimum priority element after the queue is full should bubble it up front.", 
				"New Min Person", people.first());
		people.enqueue("Dude who should be second", priorities[0] - 0.5);
		assertEquals("Inserting a minimum priority element after the queue is full should not alter its size.", 
				DEFAULT_CAPACITY, people.size());
		assertNotEquals("Encountered an error with the prioritization of elements inserted after the queue is full.", 
				"Dude who should be second", people.first());
		people.dequeue();
		assertEquals("Inserting a minimum priority element after the queue is full should bubble it up front.", 
				"Dude who should be second", people.first());
	}
	
	@Test
	public void testLast(){
		
		// Similar to testFirst().
		assertEquals("Querying an empty BPQ for its last element should return null.", 
				null, people.last());
		
		// (1) An easy test.
		people.enqueue("Jim", r.nextInt(MAX_PRIORITY));
		assertEquals("Jim", people.last());
		assertFalse("A singleton BPQ should not be empty after peeking its last element.", 
				people.isEmpty());
		people = new BoundedPriorityQueue<String>(DEFAULT_CAPACITY);
		// (2) Make sure that two elements with equal priorities
		// get processed in a FIFO manner.
		people.enqueue("Jim", 11.3);
		people.enqueue("Jill", 11.3);
		assertEquals("Elements with the same priority should be treated in a FIFO manner", 
				"Jill", people.last());
		people.dequeue(); people.dequeue();
		// So, now, peeking should be null.
		assertEquals("Querying an empty BPQ for its last element should return null.",
				null, people.last());
		
		// (3) A rather contrived test.
		Double[] priorities = new Double[DEFAULT_CAPACITY];
		for(int i = 0; i < priorities.length; i++){
			double priority = SCALE * r.nextDouble(); 
			priorities[i] = priority;
			people.enqueue("Person " + priority, priority);
		}
		Arrays.sort(priorities);
		assertEquals("It appears that this BPQ does not provide for proper ordering.", 
				"Person " + priorities[priorities.length - 1], people.last());
		
		// (4) And test semantics.
		people.enqueue("New Max Person", priorities[priorities.length - 1] + 1.0);
		assertNotEquals("Prioritization of enqueuing a past-max-priority element past capacity problematic.", 
				"New Max Person", people.last());
		people.enqueue("Barely New Max Person", priorities[priorities.length - 1]);
		assertNotEquals("Prioritization of enqueuing a past-max-priority element past capacity problematic.", 
				"Barely New Max Person", people.last());
		
		// After we insert an element in the queue (that does not have the max priority),
		// we should pop out the last element of the queue.
		people.enqueue("New Min Person", priorities[1]);
		assertNotEquals("Should be ejecting the maximum priority element when inserting a "
				+ "non-maximum priority element in a BPQ that's already full.", "New Max Person", people.last());
		assertEquals("Did not detect the expected new maximum priority element after inserting a " + 
				"non-maximum priority element in a BPQ that's already full.", 
				"Person " + priorities[priorities.length - 2], people.last());
	}
	
	@Test
	public void testIterator(){
		
		// Insert some peeps.
		Double[] priorities = new Double[DEFAULT_CAPACITY];
		for(int i = 0; i < DEFAULT_CAPACITY; i++)
			priorities[i] = SCALE * r.nextDouble();
		for(Double pr: priorities)
			people.enqueue("Person " + pr, pr);
		Arrays.sort(priorities);
		
		// (1) Check proper use of iterator through both calls to next() and remove().
		Iterator<String> personIt = people.iterator();
		assertTrue(personIt.hasNext());
		for(int i = 0; i < priorities.length; i++)
			assertEquals("Failed in our fetching of person #" + i + " from our Iterator.",
					"Person " + priorities[i], personIt.next());
		assertFalse(personIt.hasNext());
		
		personIt = people.iterator(); // reset
		for(int i = 0; i < priorities.length; i++){
			personIt.next();
			try {
				personIt.remove();
			} catch(Throwable t){
				fail("Caught a " + t.getClass() + " with message " + t.getMessage() + 
						" when deleting element #" + i + " with priority " + priorities[i] + ".");
			}
		}
			
		// (2) Since BoundedPriorityQueues are now Iterables, the
		// for-each loop should operate primo, right?
		int i = 0;
		for(String p : people)
			assertEquals("Failed in our fetching of person #" + i + " in our for-each loop.",
					"Person " + priorities[i++], p);
		
		// (3) Check that appropriate exceptions are thrown.
		
		// IllegalStateException
		try {
			people.iterator().remove();
		}catch(IllegalStateException isexc){
			// Good
		} catch(Throwable t){
			fail("Caught a " + t.getClass() + " with message: " + t.getMessage() + 
					" instead of an IllegalStateException when removing an element through a "
					+ "freshly created Iterator on which we hadn't yet made a call to \"next()\".");
		}
		
		//ConcurrentModificationException
		for(Double pr: priorities)
			people.enqueue("Person " + pr, pr);
		Iterator<String> it = people.iterator();
		
		it.next();
		people.dequeue();
		try {
			it.next();
		}catch(ConcurrentModificationException cme){
			// Good
		} catch(Throwable t){
			fail("Caught a " + t.getClass() + " with message: " + t.getMessage() + 
					" instead of a ConcurrentModificationException when calling next() on an Iterator"
					+ " over a BPQ that was modified in between creation of the Iterator and the call to next().");
		}
		
		try {
			it.remove();
		}catch(ConcurrentModificationException cme){
			// Good
		} catch(Throwable t){
			fail("Caught a " + t.getClass() + " with message: " + t.getMessage() + 
					" instead of a ConcurrentModificationException when calling remove() on an Iterator"
					+ " over a BPQ that was modified in between creation of the Iterator and the call to next().");
		}
	}

	/*@Test
	public void testSize() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEmpty() {
		fail("Not yet implemented");
	}

	@Test
	public void testClear() {
		fail("Not yet implemented");
	}*/

}
