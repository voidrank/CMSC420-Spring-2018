package projects.spatial.knnutils;

import projects.spatial.kdpoint.KDPoint;

import java.util.Iterator;

/**
 * <p>{@link BoundedPriorityQueue} is an {@link Iterable} priority queue whose number of elements
 * is bounded above. Insertions are such that if the queue's provided capacity is surpassed,
 * its length is not expanded, but rather the maximum priority element is ejected
 * (which could be the element just attempted to be enqueued).</p>
 *
 * <p><b>YOU ***** MUST ***** IMPLEMENT THIS CLASS!</b></p>
 *
 * @author  ---- YOUR NAME HERE! -----
 *
 */
public class BoundedPriorityQueue<T> implements Iterable<T>{

	private static RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");

	/* *************************************************************************
	 ************** PLACE YOUR PRIVATE METHODS AND FIELDS HERE: ****************
	 ***************************************************************************/



	/* ***************************************************************************** */
	/* ******************* PUBLIC (INTERFACE) METHODS ****************************** */
	/* ***************************************************************************** */


	/**
	 * Standard constructor. Creates a {@link BoundedPriorityQueue} of the provided size.
	 * @param size The number of elements that the {@link BoundedPriorityQueue} instance is allowed to store.
	 * @throws RuntimeException if <tt>size</tt> &lt; 1.
	 */
	public BoundedPriorityQueue(int size){
		throw UNIMPL_METHOD; // Erase this after you implement the method!
	}

	/**
	 * <p>Insert <tt>element</tt> in the Priority Queue, according to its <tt>priority</tt>.
	 * <b>Lower is better.</b> We allow for <b>non-integer priorities</b> such that the Priority Queue
	 * can be used for orderings where the prioritization is <b>not</b> rounded to integer quantities, such as
	 * Euclidean Distances in KNN queries. </p>
	 *
	 * @param element The element to insert in the queue.
	 * @param priority The priority of the element.
	 *
	 * @see projects.spatial.kdpoint.KDPoint#distance(KDPoint)
	 */
	public void enqueue(T element, double priority) {
		throw UNIMPL_METHOD; // Erase this after you implement the method!
	}

	/**
	 * Return the <b>minimum priority element</b> in the queue, <b>simultaneously removing it</b> from the structure.
	 * @return The minimum priority element in the queue, or <tt>null</tt> if the queue is empty.
	 */
	public T dequeue() {
		throw UNIMPL_METHOD; // Erase this after you implement the method!
	}

	/**
	 * Return, <b>but don't remove</b>, the <b>minimum priority element</b> from the queue.
	 * @return The minimum priority element of the queue, or <tt>null</tt> if the queue is empty.
	 */
	public T first() {
		throw UNIMPL_METHOD; // Erase this after you implement the method!
	}


	/**
	 * <p>Return, <b>but don't remove</b>, the <b>maximum priority element</b> from the queue. This operation is inefficient
	 * in MinHeap - based Priority Queues. That's fine for the purposes of our project; you should feel free to
	 * implement your priority queue in any way provides correctness and elementary efficiency of operations.</p>
	 * @return The maximum priority element of the queue, or <tt>null</tt> if the queue is empty.
	 */
	public T last() {
		throw UNIMPL_METHOD; // Erase this after you implement the method!
	}

	/**
	 * Query the queue about its size. <b>Empty queues have a size of 0.</b>
	 * @return The size of the queue. Returns 0 if the queue is empty.
	 */
	public int size() {
		throw UNIMPL_METHOD; // Erase this after you implement the method!
	}

	/**
	 * Query the queue about emptiness. A queue is empty <b>iff</b> it contains <b>0 (zero)</b> elements.
	 * @return <tt>true</tt> iff the queue contains <b>0 (zero)</b> elements.
	 */
	public boolean isEmpty() {
		throw UNIMPL_METHOD; // Erase this after you implement the method!
	}

	@Override
	public Iterator<T> iterator() {
		throw UNIMPL_METHOD; // Erase this after you implement the method!
	}
}
