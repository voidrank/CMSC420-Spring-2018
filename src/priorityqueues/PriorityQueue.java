package priorityqueues;

import fifoqueues.EmptyQueueException;

/** A <tt>PriorityQueue</tt> is an extension of a classic FIFO Queue. Instead of traditional
 * FIFO processing, a priorityqueues.PriorityQueue inserts elements with a higher priority
 * first, where "higher" is typically interpreted as "lower" in the arithmetic
 * sense, e.g 1 is "higher" priority than 2. Elements with the same priority
 * are inserted in a FIFO fashion.
 *
 * @author Jason Filippou (jasonfil@cs.umd.edu)
 *
 */
public interface PriorityQueue<T> extends Iterable<T> {

	/**
	 * Enqueue the element in the priorityqueues.PriorityQueue.
	 * @param element The element to enqueue.
	 * @param priority The priority of the element that will be enqueued.
	 */
	public void enqueue(T element, int priority);
	
	/**
	 * Removes and returns an element of the priorityqueues.PriorityQueue.
	 * @return The element at the top of the priorityqueues.PriorityQueue.
	 * @throws EmptyQueueException If the priorityqueues.PriorityQueue is empty.
	 */
	public T dequeue() throws EmptyQueueException;
	
	/**
	 * Returns but does not delete the first element from the top of the priorityqueues.PriorityQueue.
	 * @return The element at the top of the priorityqueues.PriorityQueue
	 * @throws EmptyQueueException If the priorityqueues.PriorityQueue is empty.
	 */
	public T first() throws EmptyQueueException;
	
	/**
	 * Returns the number of elements currently in the priorityqueues.PriorityQueue.
	 * @return The number of elements currently in the priorityqueues.PriorityQueue.
	 */
	public int size();
	
	/**
	 * Queries the ProrityQueue for emptiness.
	 * @return true if the priorityqueues.PriorityQueue is empty.
	 */
	public boolean isEmpty();
	
	/**
	 * Clears the priorityqueues.PriorityQueue.
	 */
	public void clear();
}
