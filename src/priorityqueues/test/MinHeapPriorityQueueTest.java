package priorityqueues.test;

import org.junit.Test;
import priorityqueues.EmptyPriorityQueueException;
import priorityqueues.MinHeapPriorityQueue;
import priorityqueues.PriorityQueue;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * <p>A testing framework for {@link MinHeapPriorityQueue}.</p>
 *
 * @author  <a href="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 *
 * @see LinearPriorityQueueTest
 */
public class MinHeapPriorityQueueTest {


	private PriorityQueue<String> greekNamesQueue =
			new MinHeapPriorityQueue<String>();
	private PriorityQueue<Double> doubles = new MinHeapPriorityQueue<Double>();

	private static final int MAX_PRIORITY = 10;
	private static final long SEED = 47;
	private Random r = new Random(SEED);

	@Test
	public void testLinearPQConstructorAndSize(){
		assertTrue("After construction, a HeapPriorityQueue should be empty.",
				greekNamesQueue.isEmpty());
		assertEquals("After construction, a HeapPriorityQueue's size should be 0.",0,
				greekNamesQueue.size());
	}

	@Test
	public void testLinearPQClear(){
		greekNamesQueue.enqueue("Alexandrou", 8);
		greekNamesQueue.clear();
		assertTrue("After clearing, a HeapPriorityQueue should be empty.", greekNamesQueue.isEmpty());
		assertEquals("After clearing, a HeapPriorityQueue's size should be 0.",0,  greekNamesQueue.size());
	}

	@Test
	public void testLinearPQSimpleEnqueueDifferentPriorities(){
		greekNamesQueue.enqueue("Filippou", 2);
		greekNamesQueue.enqueue("Alexandrou", 3);
		greekNamesQueue.enqueue("Costakis", 1);
		try {
			assertEquals("HeapPriorityQueue.getFirst() did not return the correct element.",
					"Costakis", greekNamesQueue.getFirst());
		} catch(EmptyPriorityQueueException ignored){
			fail("Since the queue was not empty upon call to HeapPriorityQueue.getFirst(), an " +
					"EmptyPriorityQueueException should not have been thrown.");
		}
	}

	@Test
	public void testLinearPQSimpleEnqueueSamePriorities(){
		greekNamesQueue.enqueue("Filippou", 1);
		greekNamesQueue.enqueue("Alexandrou", 1);
		greekNamesQueue.enqueue("Costakis", 1);
		try {
			assertEquals("HeapPriorityQueue.getFirst() did not return the correct element. " +
					"Are you treating same priorities correctly?","Filippou", greekNamesQueue.getFirst());
		} catch(EmptyPriorityQueueException ignored){
			fail("Since the queue was not empty upon call to HeapPriorityQueue.getFirst(), an " +
					"EmptyPriorityQueueException should not have been thrown.");
		}
	}

	@Test
	public void testLinearPQComplexEnqueuesAndDequeues(){
		greekNamesQueue.enqueue("Filippou", 2);
		assertEquals("After inserting a single element, a HeapPriorityQueue should have a size of 1", 1,
				greekNamesQueue.size());

		greekNamesQueue.enqueue("Alexandrou", 10);
		assertEquals("After enqueueing 2 elements, the queue should have a size of 2.",2, greekNamesQueue.size());

		greekNamesQueue.enqueue("Vasilakopoulos", 5);
		assertEquals("After enqueueing 3 elements, the queue should have a size of 3.",3, greekNamesQueue.size());
		try {
			assertEquals("HeapPriorityQueue.getFirst() did not return the correct element.",
					"Filippou", greekNamesQueue.getFirst());
		} catch(AssertionError ae) {throw(ae);}
		catch (EmptyPriorityQueueException ignored) {
			fail("Since the queue was not empty upon call to HeapPriorityQueue.getFirst(), an " +
					"EmptyPriorityQueueException should not have been thrown.");
		}
		try {
			assertEquals("HeapPriorityQueue.dequeue() did not return the correct element.",
					"Filippou", greekNamesQueue.dequeue());
		} catch (EmptyPriorityQueueException ignored) {
			fail("Since the queue was not empty upon call to HeapPriorityQueue.dequeue(), an " +
					"EmptyPriorityQueueException should not have been thrown.");
		}
		try {
			assertEquals( " After a prior dequeueing, HeapPriorityQueue.getFirst() did not return the " +
					"correct element.", "Vasilakopoulos", greekNamesQueue.getFirst());

		} catch(AssertionError ae) {throw(ae);}
		catch (EmptyPriorityQueueException ignored) {
			fail("Despite a prior dequeue-ing, the queue was still not empty upon call to " +
					"HeapPriorityQueue.getFirst(), so an EmptyPriorityQueueException should not have been thrown.");
		}
		greekNamesQueue.enqueue("Papandreou", 1);
		greekNamesQueue.enqueue("Mitsotakis", 1);

		assertEquals("After 3 enqueueings, 1 dequeueing and 2 enqueueings, the size of the HeapPriorityQueue should be 4.",
				4, greekNamesQueue.size() );
		try {
			assertNotEquals("HeapPriorityQueue.dequeue() returned an element that used to be the top element, " +
					"but is not any more after some recent enqueueings.", greekNamesQueue.dequeue(), "Vasilakopoulos"); // No longer the first.
			assertEquals("HeapPriorityQueue.dequeue() did not return the correct element. Are you treating same priorities correctly?", greekNamesQueue.dequeue(), "Mitsotakis");
			assertEquals("HeapPriorityQueue.dequeue() did not return the correct element. Are you treating same priorities correctly?", greekNamesQueue.dequeue(), "Vasilakopoulos");
			assertEquals("HeapPriorityQueue.dequeue() did not return the correct element. Are you treating same priorities correctly?", greekNamesQueue.dequeue(), "Alexandrou");
		}
		catch(AssertionError ae) {throw(ae);}
		catch(EmptyPriorityQueueException ignored){
			fail("Despite a prior dequeue-ing, the queue was still not empty upon call to HeapPriorityQueue.dequeue(), " +
					"so an EmptyPriorityQueueException should not have been thrown.");
		}

		assertEquals("After dequeue-ing every element, the HeapPriorityQueue should have a size of 0",
				0, greekNamesQueue.size());
		assertTrue("After dequeue-ing every element, the HeapPriorityQueue should be empty.",
				greekNamesQueue.isEmpty());

	}


	@Test
	public void testManyEnqueues() {
		List<Integer> priorities = IntStream.range(0, MAX_PRIORITY).boxed().collect(Collectors.toList());
		Collections.shuffle(priorities, r);
		for (int cnt = 0; cnt < MAX_PRIORITY; cnt++) {
			try {
				doubles.enqueue(r.nextDouble(), priorities.get(cnt));
			} catch (Throwable t) {
				fail("During the enqueueing of element #" + cnt + ", we caught a " + t.getClass().getSimpleName()
						+ " with message " + t.getMessage());
			}
		}
		assertEquals("After enqueueing " + MAX_PRIORITY + " elements, the size of the HeapPriorityQueue " +
				"should be " + MAX_PRIORITY + ".", MAX_PRIORITY, doubles.size());
	}


	@Test
	public void testManyDequeues(){
		List<Integer> priorities = IntStream.range(0, MAX_PRIORITY).boxed().collect(Collectors.toList());
		Collections.shuffle(priorities, r);
		priorities.forEach(pr->doubles.enqueue(r.nextDouble(), pr)); // Insert a bunch of doubles with randomly shuffled priorities
		List<Integer> prioritiesSorted = IntStream.of(0, MAX_PRIORITY).boxed().collect(Collectors.toList());
		for (int cnt = 0; cnt < MAX_PRIORITY; cnt++) {
			try {
				doubles.dequeue();
			} catch (Throwable t) {
				fail("During the dequeueing of element #" + cnt + ", we caught a " + t.getClass().getSimpleName()
						+ " with message " + t.getMessage());
			}
		}
		assertEquals("After dequeueing all the elements of the HeapPriorityQueue, its size should be 0.",
				0, doubles.size());
		assertTrue("After dequeueing all the elements of the HeapPriorityQueue, its size should be 0.",
				doubles.isEmpty());
	}



	@Test
	public void testLinearPQIteratorAndConcurrentModifications(){
		String[] strings = {"Karathodori", "Stergiou", "Tasou", "Pipinis", "Papandreou", "Mitsotakis"};
		for(int i = 0; i < strings.length; i++)
			greekNamesQueue.enqueue(strings[i], strings.length - 1 - i);
		Iterator<String> it = greekNamesQueue.iterator();
		assertTrue("Since we have some elements in the HeapPriorityQueue, the iterator's hasNext()" +
				" method should return true.", it.hasNext());


		for(int i = strings.length - 1; i > -1; i--)
			assertEquals("The iterator's next() method did not return the expected element.", strings[i], it.next());
		assertFalse("After looping through all the elements with next(), the iterator's hasNext() method" +
				" should return false.", it.hasNext());
		it = greekNamesQueue.iterator(); // reset iterator

		it.next();
		it.remove();

		for(int i = strings.length - 2; i > -1; i--) // Above zero, since we removed one element
			assertEquals("The iterator's next() method did not return the expected element.", strings[i], it.next());
		greekNamesQueue.clear();

		// Now we will also check iterations over a queue that has
		// non-singleton FIFO fifoqueues in it

		// Give the getFirst 4 people in that array a priority of 2, and the last
		// 2 people a priority of 1:

		for(int i = 0; i < strings.length; i++) {
			if (i < 4)
				greekNamesQueue.enqueue(strings[i], 2);
			else
				greekNamesQueue.enqueue(strings[i], 1);
		}
		it = greekNamesQueue.iterator();

		assertTrue("Before looping through the HeapPriorityQueue, its iterator's hasNext() method should " +
				" return true.", it.hasNext());
		assertEquals("The iterator's next() method did not return the expected element.",  "Papandreou", it.next());
		assertEquals("The iterator's next() method did not return the expected element.", "Mitsotakis", it.next());
		for(int i = 0; i < 4; i++)
			assertEquals("The iterator's next() method did not return the expected element.", it.next(), strings[i]);
		assertFalse("After looping through all the elements with next(), the iterator's hasNext() method" +
				" should return false.", it.hasNext());

		// Finally, check the proper throwing of a ConcurrentModificationException

		it = greekNamesQueue.iterator();
		it.next();
		greekNamesQueue.enqueue("Stamatopoulos", 9);
		try {
			it.next();
		} catch(ConcurrentModificationException ignored){
			// ok
		} catch(Throwable t){
			fail("Instead of a ConcurrentModificationException, we were thrown a " + t.getClass().getSimpleName() +
					" with message: " + t.getMessage() + ".");
		}
	}

}
