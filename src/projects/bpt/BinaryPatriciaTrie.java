package projects.bpt;

import java.util.Iterator;

/**
 * <p>BinaryPatriciaTrie is a Patricia Trie over the binary alphabet 0, 1. By restricting themselves
 * to this small but terrifically useful alphabet, Binary Patricia Tries combine all the positive
 * aspects of Patricia Tries while shedding the storage cost typically associated with Tries that
 * deal with huge alphabets.</p>
 *
 * @author <a href = "mailto:jasonfil@cs.umd.edu">Jason Filippou</a> &amp; <a href ="mailto:fyang623@gmail.com">Fan Yang</a>
 */
public class BinaryPatriciaTrie {

    private static RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");

    /* *************************************************************************
     ************** PLACE YOUR PRIVATE METHODS AND FIELDS HERE: ****************
     ***************************************************************************/




    /* *********************************************************************
     ************************* PUBLIC (INTERFACE) METHODS *******************
     **********************************************************************/


    /**
     * Simple constructor that will initialize the internals of <tt>this</tt>.
     */
    BinaryPatriciaTrie() {
        throw UNIMPL_METHOD;

    }

    /**
     * Searches the trie for a given <tt>key</tt>.
     *
     * @param key The input String key.
     * @return true if and only if key is in the trie, false otherwise.
     */
    public boolean search(String key) {
        throw UNIMPL_METHOD;
    }


    /**
     * Inserts <tt>key</tt> into the trie.
     *
     * @param key The input String key.
     * @return true if and only if the key was not already in the trie, false otherwise.
     */
    public boolean insert(String key) {
        throw UNIMPL_METHOD;
    }



    /**
     * Deletes <tt>key</tt> from the trie.
     *
     * @param key The String key to be deleted.
     * @return True if and only if key was contained by the trie before we attempted deletion, false otherwise.
     */
    public boolean delete(String key) {
        throw UNIMPL_METHOD;
    }


    /**
     * Queries the trie for emptiness.
     *
     * @return true if and only if {@link #getSize()} == 0, false otherwise.
     */
    public boolean isEmpty() {
        throw UNIMPL_METHOD;
    }

    /**
     * Returns the number of keys in the tree.
     *
     * @return The number of keys in the tree.
     */
    public int getSize() {
        throw UNIMPL_METHOD;
    }

    /**
     * <p>Performs an <i>inorder (symmetric) traversal</i> of the Binary Patricia Trie. Remember from lecture that inorder
     * traversal in tries is NOT sorted traversal, unless all the stored keys have the same length. This
     * is of course not required by your implementation, so you should make sure that in your tests you
     * are not expecting this method to return keys in lexicographic order. We put this method in the
     * interface because it helps us test your submission thoroughly and it helps you debug your code! </p>
     *
     * <p>We <b>neither require nor test </b> whether the {@link Iterator} returned by this method is fail-safe or fail-fast.
     * This means that you  do <b>not</b> need to test for thrown {@link java.util.ConcurrentModificationException}s and we do
     * <b>not</b> test your code for the possible occurrence of concurrent modifications.</p>
     *
     * <p>We also assume that the {@link Iterator} is <em>immutable</em>, i,e we do <b>not</b> test for the behavior
     * of {@link Iterator#remove()}. You can handle it any way you want for your own application, yet <b>we</b> will
     * <b>not</b> test for it.</p>
     *
     * @return An {@link Iterator} over the {@link String} keys stored in the trie, exposing the elements in <i>symmetric
     * order</i>.
     */
    public Iterator<String> inorderTraversal() {
        throw UNIMPL_METHOD;
    }


    /**
     * Finds the longest {@link String} stored in the Binary Patricia Trie.
     *
     * @return <p>The longest {@link String} stored in this. If the trie is empty, the empty string "" should be
     * returned. Careful: the empty string "" is <b>not</b> the same string as " "; the latter is a string
     * consisting of a single <b>space character</b>! It is also <b>not</b> the same as a <tt>null</tt> reference.</p>
     * <p>Ties should be broken in terms of <b>value</b> of the bit string. For example, if our trie contained
     * only the binary strings 01 and 11, <b>11</b> would be the longest string. If our trie contained
     * only 001 and 010, <b>010</b> would be the longest string.</p>
     */
    public String getLongest() {
        throw UNIMPL_METHOD;
    }
}