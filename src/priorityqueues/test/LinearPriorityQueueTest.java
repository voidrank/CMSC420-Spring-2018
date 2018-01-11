package priorityqueues.test;

import fifoqueues.EmptyFIFOQueueException;
import org.junit.Test;
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

	private PriorityQueue<String> greekPublicSectorQueue = 
			new LinearPriorityQueue<String>();

	@Test
	public void testLinearPQSimpleConstructorAndSize(){
		assertTrue(greekPublicSectorQueue.isEmpty());
		assertEquals(greekPublicSectorQueue.size(), 0);
		greekPublicSectorQueue.enqueue("ASD", 10);
		assertEquals(greekPublicSectorQueue.size(), 1);
		greekPublicSectorQueue.clear();
		assertTrue(greekPublicSectorQueue.isEmpty());
		assertEquals(greekPublicSectorQueue.size(), 0);
	}

	@Test
	public void testLinearPQOrderOfInsertedElements(){
		greekPublicSectorQueue.enqueue("Filippou", 2);
		greekPublicSectorQueue.enqueue("Vasilakopoulos", 2);
		assertEquals(greekPublicSectorQueue.size(), 2);
		try {
			assertEquals(greekPublicSectorQueue.getFirst(), "Filippou");
		} catch (EmptyFIFOQueueException e) {
			fail("EmptyFIFOQueueException should not've been thrown.");
		}
		assertEquals(greekPublicSectorQueue.size(), 2);
		try {
			assertEquals(greekPublicSectorQueue.dequeue(), "Filippou");
		} catch (EmptyFIFOQueueException e) {
			fail("EmptyFIFOQueueException should not've been thrown.");
		}
		assertEquals(greekPublicSectorQueue.size(), 1);
		try {
			assertEquals(greekPublicSectorQueue.getFirst(), "Vasilakopoulos");
		} catch (EmptyFIFOQueueException e) {
			fail("EmptyFIFOQueueException should not've been thrown.");
		}
		greekPublicSectorQueue.enqueue("Papandreou", 1);
		greekPublicSectorQueue.enqueue("Mitsotakis", 1);
		try {
			assertNotEquals(greekPublicSectorQueue.getFirst(), "Vasilakopoulos"); // No longer the getFirst
			assertEquals(greekPublicSectorQueue.dequeue(), "Papandreou");
			assertEquals(greekPublicSectorQueue.dequeue(), "Mitsotakis");
			assertEquals(greekPublicSectorQueue.dequeue(), "Vasilakopoulos");
		}catch(EmptyFIFOQueueException e){
			fail("EmptyFIFOQueueException should not've been thrown.");
		}
		assertTrue(greekPublicSectorQueue.isEmpty());
		greekPublicSectorQueue.clear();
	}

	@Test
	public void testLinearPQIterator(){
		String[] strings = {"Karathodori", "Stergiou", "Tasou", "Pipinis", "Papandreou", "Mitsotakis"};
		for(int i = 0; i < strings.length; i++)
			greekPublicSectorQueue.enqueue(strings[i], strings.length - 1 - i);
		Iterator<String> it = greekPublicSectorQueue.iterator();
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
		it = greekPublicSectorQueue.iterator(); // reset iterator
		it.next();
		try {
			it.remove();
		} catch(IllegalStateException ile){
			fail("An IllegalStateException should not have been thrown by this call.");
		}
		for(int i = strings.length - 2; i > -1; i--) // Above zero, since we removed one element
			assertEquals(strings[i], it.next());
		greekPublicSectorQueue.clear();
		
		// Now we will also check iterations over a queue that has 
		// non-singleton FIFO fifoqueues in it
		
		// Give the getFirst 4 people in that array a priority of 2, and the last
		// 2 people a priority of 1:
		
		for(int i = 0; i < strings.length; i++)
			if(i < 4)
				greekPublicSectorQueue.enqueue(strings[i], 2);
			else
				greekPublicSectorQueue.enqueue(strings[i], 1);
		it = greekPublicSectorQueue.iterator();
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
		greekPublicSectorQueue.clear();
	}
}
