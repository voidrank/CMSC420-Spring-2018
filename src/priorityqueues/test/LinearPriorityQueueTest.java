package priorityqueues.test;

import org.junit.Test;
import priorityqueues.EmptyPriorityQueueException;
import priorityqueues.LinearPriorityQueue;
import priorityqueues.PriorityQueue;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * <p>A testing framework for {@link LinearPriorityQueue}.</p>
 *
 * @author  <a href="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 *
 * @see MinHeapPriorityQueueTest
 */
public class LinearPriorityQueueTest {

	private PriorityQueue<String> greekNamesQueue =
			new LinearPriorityQueue<String>();
	private PriorityQueue<Double> doubles = new LinearPriorityQueue<Double>();

	@Test
	public void testLinearPQConstructorAndSize(){
		assertTrue("After construction, a LinearPriorityQueue should be empty.",
				greekNamesQueue.isEmpty());
		assertEquals("After construction, a LinearPriorityQueue's size should be 0.",0,
				greekNamesQueue.size());
	}

	@Test
	public void testLinearPQClear(){
		greekNamesQueue.enqueue("Alexandrou", 8);
		greekNamesQueue.clear();
		assertTrue("After clearing, a LinearPriorityQueue should be empty.", greekNamesQueue.isEmpty());
		assertEquals("After clearing, a LinearPriorityQueue's size should be 0.",0,  greekNamesQueue.size());
	}
	@Test
	public void testLinearPQEnqueueAndGetMinDifferentPriorities(){
		greekNamesQueue.enqueue("Filippou", 2);
		assertEquals("After inserting a single element, a LinearPriorityQueue should have a size of 1", 1,
				greekNamesQueue.size());

		greekNamesQueue.enqueue("Alexandrou", 10);
		assertEquals("After inserting 2 elements, the queue should have a size of 2.",2, greekNamesQueue.size());

		greekNamesQueue.enqueue("Vasilakopoulos", 5);
		assertEquals("After inserting 3 elements, the queue should have a size of 2.",2, greekNamesQueue.size());
		try {
			assertEquals("LinearPriorityQueue.getFirst() did not return the correct element.",
					"Filippou", greekNamesQueue.getFirst());
		} catch(AssertionError ae) {throw(ae);}
		catch (EmptyPriorityQueueException e) {
			fail("Since the queue was not empty upon call to LinearPriorityQueue.getFirst(), an " +
					"EmptyPriorityQueueException should not have been thrown.");
		}
		try {
			assertEquals("LinearPriorityQueue.dequeue() did not return the correct element.", greekNamesQueue.dequeue(), "Filippou");
		} catch (EmptyPriorityQueueException e) {
			fail("Since the queue was not empty upon call to LinearPriorityQueue.dequeue(), an " +
					"EmptyPriorityQueueException should not have been thrown.");
		}
		try {
			assertEquals(greekNamesQueue.getFirst(), "Vasilakopoulos");

		} catch(AssertionError ae) {throw(ae);}
		catch (EmptyPriorityQueueException e) {
			fail("Despite a prior dequeue-ing, the queue was still not empty upon call to " +
					"LinearPriorityQueue.getFirst(), so an EmptyPriorityQueueException should not have been thrown.");
		}
		greekNamesQueue.enqueue("Papandreou", 1);
		greekNamesQueue.enqueue("Mitsotakis", 2);
		try {
			assertNotEquals(greekNamesQueue.getFirst(), "Vasilakopoulos"); // No longer the first.
			assertEquals(greekNamesQueue.dequeue(), "Papandreou");
			assertEquals(greekNamesQueue.dequeue(), "Mitsotakis");
			assertEquals(greekNamesQueue.dequeue(), "Vasilakopoulos");
		}
		catch(AssertionError ae) {throw(ae);}
		catch(EmptyPriorityQueueException e){
			fail("EmptyPriorityQueueException should not've been thrown.");
		}

		assertEquals("After dequeue-ing every element, the LinearPriorityQueue should have a size of 0",
				0, greekNamesQueue.size());
		assertTrue("After dequeue-ing every element, the LinearPriorityQueue should be empty.",
				greekNamesQueue.isEmpty());

	}

	@Test
	public void testLinearPQManyEnqueues(){

	}

	@Test
	public void testLinearPQManyDequeues(){

	}

	@Test
	public void testLinearPQIterator(){
		String[] strings = {"Karathodori", "Stergiou", "Tasou", "Pipinis", "Papandreou", "Mitsotakis"};
		for(int i = 0; i < strings.length; i++)
			greekNamesQueue.enqueue(strings[i], strings.length - 1 - i);
		Iterator<String> it = greekNamesQueue.iterator();
		assertTrue(it.hasNext());
		try {
			it.remove();
		} catch(IllegalStateException ile){}
		catch(Throwable t){
			fail("Instead of an IllegalStateException, call threw a " + 
					t.getClass() + " with message: " + t.getMessage() + ".");
		}

		for(int i = strings.length - 1; i > -1; i--)
			assertEquals(it.next(), strings[i]);
		assertFalse(it.hasNext());
		it = greekNamesQueue.iterator(); // reset iterator
		it.next();
		try {
			it.remove();
		} catch(IllegalStateException ile){
			fail("An IllegalStateException should not have been thrown by this call.");
		}
		for(int i = strings.length - 2; i > -1; i--) // Above zero, since we removed one element
			assertEquals(strings[i], it.next());
		greekNamesQueue.clear();
		
		// Now we will also check iterations over a queue that has 
		// non-singleton FIFO fifoqueues in it
		
		// Give the getFirst 4 people in that array a priority of 2, and the last
		// 2 people a priority of 1:
		
		for(int i = 0; i < strings.length; i++)
			if(i < 4)
				greekNamesQueue.enqueue(strings[i], 2);
			else
				greekNamesQueue.enqueue(strings[i], 1);
		it = greekNamesQueue.iterator();
		try {
			it.remove();
			fail("Call should have thrown an IllegalStateException.");
		} catch(IllegalStateException ile){}
		catch(Throwable t){
			fail("Instead of an IllegalStateException, call threw a " 
					+ t.getClass() + " with a message of: " + t.getMessage() + ".");
		}
		assertTrue(it.hasNext());
		assertEquals(it.next(), "Papandreou");
		assertEquals(it.next(), "Mitsotakis");
		for(int i = 0; i < 4; i++)
			assertEquals(it.next(), strings[i]);
		assertFalse(it.hasNext());
		greekNamesQueue.clear();
	}
}
