package projects.pqueue.heaps; // ******* <---  DO NOT ERASE THIS LINE!!!! *******

/* *****************************************************************************************
 * THE FOLLOWING IMPORT IS NECESSARY FOR THE ITERATOR() METHOD'S SIGNATURE. FOR THIS
 * REASON, YOU SHOULD NOT ERASE IT! YOUR CODE WILL BE UNCOMPILABLE IF YOU DO!
 * ********************************************************************************** */

import java.util.Iterator;
/**
 * <p><tt>ArrayMinHeap</tt> is a {@link MinHeap} implemented using an internal array. Since projects.pqueue.heaps are <b>complete</b>
 * binary projects.pqueue.trees, using contiguous storage to store them is an excellent idea, since with such storage we avoid
 * wasting bytes per <tt>null</tt> pointer in a linked implementation.</p>
 *
 * <p>You <b>must</b> edit this class! To receive <b>any</b> credit for the unit tests related to this class,
 * your implementation <b>must</b> be a <b>contiguous storage</b> implementation based on a linear {@link java.util.Collection}
 * or a raw Java array.</p>
 *
 * @author -- YOUR NAME HERE ---
 *
 * @see MinHeap
 * @see ArrayMinHeap
  */
public class ArrayMinHeap<T extends Comparable<T>> implements MinHeap<T> { // *** <-- DO NOT CHANGE THIS LINE!!! ***

	private static final RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");

	/* *********************************************
	 * PLACE YOUR PRIVATE AND PROTECTED FIELDS HERE!
	 * YOU MIGHT ALSO WANT TO PUT PRIVATE METHODS AND/OR CLASSES HERE!
	 * THE DESIGN CHOICE IS YOURS ENTIRELY.
	 * ******************************************** */



	/* ***********************************************************************************
	 * YOU SHOULD IMPLEMENT THE FOLLOWING METHODS. BESIDES THE INTERFACE METHODS,
	 * THOSE INCLUDE CONSTRUCTORS (DEFAULT, NON-DEFAULT, COPY) AS WELL AS EQUALS().
	 * PLEASE MAKE SURE YOU RECALL HOW ONE SHOULD MAKE A CLASS-SAFE EQUALS() FROM EARLIER
	 * JAVA COURSES!
	 *
	 * YOU SHOULD NOT CHANGE *ANY* METHOD SIGNATURES! IF YOU DO, YOUR CODE WILL NOT RUN
	 * AGAINST OUR TESTS!
	 * ********************************************************************************** */


	/**
	 *  Default constructor.
	 */
	public ArrayMinHeap(){
		/* FILL THIS IN WITH YOUR IMPLEMENTATION OF A DEFAULT CONSTRUCTOR, IF ANY. */
	}

	/**
	 *  Second, non-default constructor.
	 *  @param rootElement the element to create the root with.
	 */
	public ArrayMinHeap(T rootElement){
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	/**
	 * Copy constructor initializes the current MinHeap as a carbon
	 * copy of the parameter.
	 *
	 * @param other The MinHeap to copy the elements from.
	 */
	public ArrayMinHeap(MinHeap<T> other){
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	/**
	 * Standard equals() method.
	 *
	 * @return true If the parameter Object and the current MinHeap
	 * are identical Objects.
	 */
	@Override
	public boolean equals(Object other){
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}


	@Override
	public boolean isEmpty() {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	@Override
	public int size() {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	@Override
	public void clear() {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}



	@Override
	public void insert(T element) {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	@Override
	public T getMin() throws EmptyHeapException {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	@Override
	public T deleteMin() throws EmptyHeapException {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}



	@Override
	public Iterator<T> iterator() {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}
}
