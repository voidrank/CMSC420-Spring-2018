package projects.spatial.knnutils;

import java.util.Iterator;


/**
 * <p>{@link BoundedPriorityQueue} is a priority queue whose number of elements
 * is bounded. Insertions are such that if the queue's provided capacity is surpassed,
 * its length is not expanded, but rather the maximum priority element is ejected
 * (which could be the element just attempted to be enqueued).</p>
 *
 * <p><b>YOU ***** MUST ***** IMPLEMENT THIS CLASS!</b></p>
 *
 * @author  ---- YOUR NAME HERE! -----
 *
 * @see PriorityQueue
 * @see PriorityQueueNode
 */
public class BoundedPriorityQueue<T> implements PriorityQueue<T>, Iterable<T>{

	private static RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");

	/* *************************************************************************
	 ************** PLACE YOUR PRIVATE METHODS AND FIELDS HERE: ****************
	 ***************************************************************************/



	/* ***************************************************************************** */
	/* ******************* PUBLIC (INTERFACE) METHODS ****************************** */
	/* ***************************************************************************** */

	/**
	 * Constructor that specifies the size of our queue.
	 * @param size The static size of the {@link BoundedPriorityQueue}. Has to be a positive integer.
	 * @throws RuntimeException if <tt>size</tt> is not a strictly positive integer.
	 */
	public BoundedPriorityQueue(int size){
		throw UNIMPL_METHOD; // Erase this after you implement the method!
	}

	/**
	 * <p>Enqueueing elements for<tt> BoundedPriorityQueue</tt>s works a little bit differently from general case
	 * PriorityQueues. If the queue is not at capacity, the <tt>element</tt> is inserted at its
	 * appropriate location in the sequence. On the other hand, if the object is at capacity, the element is
	 * inserted in its appropriate spot in the sequence (if such a spot exists, based on its <tt>priority</tt>) and
	 * the maximum priority element is ejected from the structure.</p>
	 * 
	 * @param element The element to insert in the queue.
	 * @param priority The priority of the element to insert in the queue.
	 */
	@Override
	public void enqueue(T element, double priority) {
		throw UNIMPL_METHOD; // Erase this after you implement the method!
	}

	@Override
	public T dequeue() {
		throw UNIMPL_METHOD; // Erase this after you implement the method!
	}

	@Override
	public T first() {
		throw UNIMPL_METHOD; // Erase this after you implement the method!
	}
	
	/**
	 * Returns the last element in the queue. Useful for cases where we want to 
	 * compare the priorities of a given quantity with the maximum priority of 
	 * our stored quantities. In a minheap-based implementation of any {@link PriorityQueue},
	 * this operation would scan O(n) nodes and O(nlogn) links. In an array-based implementation,
	 * it takes constant time.
	 * @return The maximum priority element in our queue, or <tt>null</tt> if the queue is empty.
	 */
	public T last() {
		throw UNIMPL_METHOD; // Erase this after you implement the method!
	}

	@Override
	public int size() {
		throw UNIMPL_METHOD; // Erase this after you implement the method!
	}

	@Override
	public boolean isEmpty() {
		throw UNIMPL_METHOD; // Erase this after you implement the method!
	}

	@Override
	public Iterator<T> iterator() {
		throw UNIMPL_METHOD; // Erase this after you implement the method!
	}
}
