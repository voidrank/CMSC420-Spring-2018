package projects.spatial.trees;
import projects.spatial.kdpoint.KDPoint;
import projects.spatial.knnutils.BoundedPriorityQueue;

/**
 * <p>{@link SpatialDictionary} is an abstraction over any data structure that could be used to allow efficient
 * insertion, deletion and search of {@link KDPoint}s.</p>. Since {@link KDPoint}s are
 *
 * <p>Minor detail: since {@link SpatialDictionary} is an <b>interface</b>, all of its methods are implicitly <tt>public</tt>, so the explicit
 * scope modifier is <b>not needed</b> in the source.</p>
 *
 * @author <a href="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 *
 * @see KDPoint
 * @see BoundedPriorityQueue
 * @see SpatialQuerySolver
 */
public interface SpatialDictionary {
    /**
     * Inserts <tt>p</tt> into the {@link SpatialDictionary}.
     * @param p The {@link KDPoint} to insert into the tree.
     */
    void insert(KDPoint p);

    /**
     * Deletes <tt>p</tt> from the {@link SpatialDictionary}. If <tt>p</tt> is not in the
     * tree, this method performs no changes to the tree.
     * @param p The {@link KDPoint} to delete from the tree.
     */
    void delete(KDPoint p);

    /**
     * Searches the dictionary for <tt>p</tt> and reports if it found it.
     * @param p The {@link KDPoint} to look for in the tree.
     * @return <tt>true</tt> if <tt>p</tt> is in the tree, <tt>false</tt> otherwise.
     */
    boolean search(KDPoint p);

    /**
     *<p>Return the height of the {@link SpatialDictionary}. The height is defined similarly to
     * AVL trees, as follows: </p>
     * <ol>
     *   <li>The height of a null tree (no nodes) is -1 (minus 1).</li>
     *   <li>The height of a tree that consists of a single nodes (a "stub" tree) is 0 (zero). </li>
     *   <li>The height of a tree that consists of four children is the maximum height of its children plus one.</li>
     *</ol>
     * @return the height of the subtree rooted at the current node.
     */
    int height();

    /**
     * Reports whether the {@link SpatialDictionary} is empty, that is, it contains <b>0 (zero)</b> {@link KDPoint}s.
     * @return <tt>true</tt> iff {@link #count()} == 0, <tt>false</tt> otherwise.
     */
    boolean isEmpty();

    /** Returns the number of elements in the {@link SpatialDictionary}.
     *  @return The number of elements in the {@link SpatialDictionary}.
     */
    int count();
}
