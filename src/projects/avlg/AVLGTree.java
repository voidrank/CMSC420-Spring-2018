/* DO NOT ERASE THESE THREE LINES OR YOUR CODE WON'T COMPILE! */
package projects.avlg;
import projects.avlg.exceptions.EmptyTreeException;
import projects.avlg.exceptions.InvalidBalanceException;


/** <p>An <tt>AVL-G Tree</tt> is an AVL Tree with a relaxed balance condition. Its constructor receives a strictly
 * positive parameter which controls the <b>maximum</b> imbalance allowed on any subtree of the tree which
 * it creates. So, for example:</p>
 *  <ul>
 *      <li>An AVL-1 tree is a classic AVL tree, which only allows for perfectly balanced binary
 *      subtrees (imbalance of 0 everywhere), or subtrees with a maximum imbalance of 1 (somewhere). </li>
 *      <li>An AVL-2 tree relaxes the criteria of AVL-1 trees, by also allowing for subtrees
 *      that have an imbalance of 2.</li>
 *      <li>AVL-3 trees allow an imbalance of 3.</li>
 *      <li>...</li>
 *  </ul>
 *
 *  <p>The idea behind AVL-G trees is that rotations cost time, so maybe we would be willing to
 *  accept bad search performance now and then if it would mean less rotations.</p>
 *
 * <p>You <b>must</b> implement this class! You should <b>not</b> move this class' file to
 * a different position in the code tree. You <b>are</b> allowed to add any classes, enums, interfaces,
 * abstract classes, packages, etc. that you think you might need.</p>
 *
 * @author YOUR NAME HERE!
 */
public class AVLGTree<T extends Comparable<T>> {

    private static final RuntimeException UNIMPL_METHOD =
            new RuntimeException("Implement this method!");


    /* ********************************************************************
     ****************** YOUR PRIVATE FIELDS AND METHODS HERE: **************
     **********************************************************************/



    /* *********************************************************************
     ************************* PUBLIC (INTERFACE) METHODS *******************
     **********************************************************************/

    /**
     * The class constructor provides the tree with its maximum imbalance allowed.
     * @param maxImbalance The maximum imbalance allowed by the AVL-G Tree.
     * @throws InvalidBalanceException if <tt>maxImbalance</tt> is a value smaller than 1.
     */
    public AVLGTree(int maxImbalance) throws InvalidBalanceException {
        throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
    }

    /**
     * Insert <tt>key</tt> in the tree.
     * @param key The key to insert in the tree.
     */
    public void insert(T key) {
        throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
    }

    /**
     * Delete the key from the data structure and return it to the caller.
     * @param key The key to delete from the structure.
     * @return The key that was removed, or <tt>null</tt> if the key was not found.
     * @throws EmptyTreeException if the tree is empty.
     */
    public T delete(T key) throws EmptyTreeException {
        throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
    }

    /**
     * <p>Search for <tt>key</tt> in the tree. Return a reference to it if it's in there,
     * or <tt>null</tt> otherwise.</p>
     * @param key The key to search for.
     * @return <tt>key</tt> if <tt>key</tt> is in the tree, or <tt>null</tt> otherwise.
     * @throws EmptyTreeException if the tree is empty.
     */
    public T search(T key) throws EmptyTreeException {
        throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
    }


    /**
     * <p>Return the height of the tree. The height of the tree is defined as the length of the
     * longest path between the root and the leaf level. By definition of path length, a
     * stub tree has a height of 0, and we define an empty tree to have a height of -1.</p>
     * @return The height of the tree.
     */
    public int height() {
        throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
    }

    /**
     * Query the tree for emptiness. A tree is empty iff it has zero keys stored.
     * @return <tt>true</tt> if the tree is empty, <tt>false</tt> otherwise.
     */
    public boolean isEmpty() {
        throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
    }

    /**
     * Return the key at the tree's root node.
     * @return The key at the tree's root node.
     */
    public T getRoot() {
        throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
    }


    /**
     * <p>Establishes whether the AVL-G tree <em>globally</em> satisfies the BST condition.
     * This method is <b>terrifically useful for testing!</b></p>
     * @return <tt>true</tt> if the tree satisfies the Binary Search Tree property,
     * <tt>false</tt> otherwise.
     */
    public boolean isBST() {
        throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
    }


    /**
     * <p>Establishes whether the AVL-G tree <em>globally</em> satisfies the AVL-G condition.
     * This method is <b>terrifically useful for testing!</b></p>
     * @return <tt>true</tt> if the tree satisfies the Binary Search Tree property,
     * <tt>false</tt> otherwise.
     */
    public boolean isAVLGBalanced() {
        throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
    }

    /**
     * <p>Empties the <tt>AVLGTree</tt> of all its elements. After a call to this method, the
     * tree should have <b>0</b> elements.</p>
     */
    public void clear(){
        throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
    }


    /**
     * <p>Return the number of elements in the tree.</p>
     * @return  The number of elements in the tree.
     */
    public int getCount(){
        throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
    }
}
