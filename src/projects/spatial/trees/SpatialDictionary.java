package projects.spatial.trees;
import projects.spatial.kdpoint.KDPoint;
import projects.spatial.knnutils.BoundedPriorityQueue;

/**
 * <p>A <tt>SpatialDictionary</tt> is an abstraction over any data structure that could be used to allow efficient
 * insertion, deletion and search of {@link KDPoint}s.</p>
 *
 * @author <a href="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 *
 * @see KDPoint
 * @see BoundedPriorityQueue
 * @see SpatialQuerySolver
 */
public interface SpatialDictionary {
    /**
     * Inserts <tt>p</tt> into the <tt>SpatialDictionary</tt>.
     * @param p The {@link KDPoint} to insert into the tree.
     */
    public void insert(KDPoint p);

    /**
     * Deletes <tt>p</tt> from the <tt>SpatialDictionary</tt>. If <tt>p</tt> is not in the
     * tree, this method performs no changes to the tree.
     * @param p The {@link KDPoint} to delete from the tree.
     */
    public void delete(KDPoint p);

    /**
     * Searches the dictionary for <tt>p</tt> and reports if it found it.
     * @param p The {@link KDPoint} to look for in the tree.
     * @return <tt>true</tt> if <tt>p</tt> is in the tree, <tt>false</tt> otherwise.
     */
    public boolean search(KDPoint p);

    /**
     * Return the height of the<tt>SpatialDictionary</tt>. The height is defined similarly to
     * AVL trees, as follows:
     * <ol>
     *   <li>The height of a null tree (no nodes) is -1 (minus 1).</li>
     *   <li>The height of a tree that consists of a single nodes (a "stub" tree) is 0 (zero). </li>
     *   <li>The height of a tree that consists of four children is the maximum height of its children plus one.</li>
     *</ol>
     *
     * Note that the data field <tt>height</tt> is <tt>protected</tt>, so it's only accessible by derived classes.
     * This is a public method, so it can be called by callers outside the scope of this class (such as
     * {@link projects.spatial.trees.PRQuadTree})
     *
     * @return the height of the subtree rooted at the current nodes.
     * @see #height
     */
    public int height();

    /**
     * Reports whether the <tt>SpatialDictionary</tt> is empty, that is, it contains zero
     * <tt>KDPoint</tt>s.
     * @return <tt>true</tt> iff {@link #count()} == 0, false otherwise.
     */
    public boolean isEmpty();

    /** Returns the number of elements in the <tt>SpatialDictionary</tt>.
     *  @return The number of elements in the <tt>SpatialDictionary</tt>.
     */
    public int count();
}
