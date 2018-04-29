package projects.spatial.knnutils;

/**
 * <p><tt>PriorityQueue</tt> is an interface describing PriorityQueues, that is, queues that instead
 * of operating in a FIFO manner, insert elements according to a given priority, where lower is better.</p>
 * .
 * @author Jason Filippou (jasonfil@cs.umd.edu)
 *
 * @since March 2015
 */

public interface PriorityQueue<T> {
	
	/**
	 * Insert <tt>element</tt> in the PriorityQueue according to its <tt>priority</tt>. Lower
	 * is better. We allow for non-integer priorities such that the PriorityQueue can be used
	 * for orderings where the prioritization is not rounded to integer quantities, such as 
	 * Euclidean Distances in KNN queries.
	 * @param element The element to insert in the queue.
	 * @param priority The priority of the element.
	 */
	public void enqueue(T element, double priority);
	
	/**
	 * Return the minimum priority element in the queue, simultaneously removing it from the structure.
	 * @return The minimum priority element in the queue, or <tt>null</tt> if the queue is empty.
	 */
	public T dequeue();
	
	/**
	 * Return, but don't remove, the minimum priority element from the queue.
	 * @return The minimum priority element of the queue, or <tt>null</tt> if the queue is empty.
	 */
	public T first();	
	
	/**
	 * Query the queue about its size. Empty queues have a size of 0.
	 * @return The size of the queue. Returns 0 if the queue is empty.
	 */
	public int size();
	
	/**
	 * Query the queue about emptiness. A queue is empty iff it contains zero elements.
	 * @return <tt>true</tt> iff the queue contains zero elements.
	 */
	public boolean isEmpty();

}

