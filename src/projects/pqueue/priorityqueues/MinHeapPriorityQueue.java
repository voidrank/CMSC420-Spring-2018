package projects.pqueue.priorityqueues; // ******* <---  DO NOT ERASE THIS LINE!!!! *******


/* *****************************************************************************************
 * THE FOLLOWING IMPORTS WILL BE NEEDED BY YOUR CODE, BECAUSE WE REQUIRE THAT YOU USE
 * ANY ONE OF YOUR EXISTING MINHEAP IMPLEMENTATIONS TO IMPLEMENT THIS CLASS. TO ACCESS
 * YOUR MINHEAP'S METHODS YOU NEED THEIR SIGNATURES, WHICH ARE DECLARED IN THE MINHEAP
 * INTERFACE. ALSO, SINCE THE PRIORITYQUEUE INTERFACE THAT YOU EXTEND IS ITERABLE, THE IMPORT OF ITERATOR
 * IS NEEDED IN ORDER TO MAKE YOUR CODE COMPILABLE.
 ** ********************************************************************************** */

import projects.pqueue.InvalidPriorityException;
import projects.pqueue.heaps.MinHeap;

import java.util.Iterator;

/**
 * <p><tt>MinHeapPriorityQueue</tt> is a {@link PriorityQueue} implemented using a {@link MinHeap}.</p>
 *
 * <p>You  <b>must</b> implement the methods in this file! To receive <b>any credit</b> for the unit tests related to this class, your implementation <b>must</b>
 * use <b>whichever</b> {@link MinHeap} implementation among the two that you should have implemented you choose!</p>
 *
 * @author  ---- YOUR NAME HERE ----
 *
 * @param <T> The Type held by the container.
 *
 * @see LinearPriorityQueue
 * @see MinHeap
 */
public class MinHeapPriorityQueue<T> implements PriorityQueue<T>{ // *** <-- DO NOT CHANGE THIS LINE!!! ***

	private static final RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");

	/* *********************************************
	 * PLACE YOUR PRIVATE AND PROTECTED FIELDS HERE!
	 * YOU MIGHT ALSO WANT TO PUT PRIVATE METHODS AND/OR CLASSES HERE!
	 * THE DESIGN CHOICE IS YOURS ENTIRELY.
	 * ******************************************** */



	/* ***********************************************************************************
	 * YOU SHOULD IMPLEMENT THE FOLLOWING METHODS. BESIDES THE INTERFACE METHODS,
	 * THOSE INCLUDE A SIMPLE DEFAULT CONSTRUCTOR. FOR THE PRIORITY QUEUE CLASSES, YOU
	 * WILL *NOT* NEED TO IMPLEMENT COPY CONSTRUCTORS AND EQUALS(), BUT IF YOU WOULD LIKE TO
	 * IMPLEMENT THOSE TO FACILITATE YOUR OWN TESTS AND CLIENT CODE, PLEASE FEEL FREE TO DO SO!
	 * THE CHOICE IS YOURS.
	 *
	 * YOU SHOULD NOT CHANGE *ANY* METHOD SIGNATURES! IF YOU DO, YOUR CODE WILL NOT RUN
	 * AGAINST OUR TESTS!
	 * ********************************************************************************** */

	/**
	 * Simple default constructor.
	 */
	public MinHeapPriorityQueue(){
		/* FILL THIS IN WITH YOUR IMPLEMENTATION OF A DEFAULT CONSTRUCTOR, IF ANY. */
	}

	@Override
	public void enqueue(T element, int priority) throws InvalidPriorityException{
			throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}


	@Override
	public T dequeue() throws EmptyPriorityQueueException {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	@Override
	public T getFirst() throws EmptyPriorityQueueException {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	@Override
	public Iterator<T> iterator() {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	@Override
	public int size() {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	@Override
	public boolean isEmpty() {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	@Override
	public void clear() {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}
	

}
