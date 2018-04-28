package projects.spatial.trees;
import projects.spatial.kdpoint.KDPoint;
import projects.spatial.knnutils.BoundedPriorityQueue;

import java.util.Collection;

public interface SpatialTree {
    /**
     * Inserts <tt>p</tt> into the <tt>KDTree</tt>.
     * @param p The {@link KDPoint} to insert into the tree.
     */
    public void insert(KDPoint p);

    /**
     * Deletes <tt>p</tt> from the <tt>KDTree</tt>. If <tt>p</tt> is not in the
     * tree, this method performs no changes to the tree.
     * @param p The {@link KDPoint} to delete from the tree.
     */
    public void delete(KDPoint p);

    /**
     * Searches the tree for <tt>p</tt> and reports if it found it.
     * @param p The {@link KDPoint} to look for in the tree.
     * @return <tt>true</tt> iff <tt>p</tt> is in the tree.
     */
    public boolean search(KDPoint p);


    public KDPoint getRoot();


    public Collection<KDPoint> range(KDPoint p, double range);

    public KDPoint nearestNeighbor(KDPoint p);

    /**
     * <p>Return the k-nearest neighbors of a given {@link KDPoint}.</p>
     */
    public BoundedPriorityQueue<KDPoint> kNearestNeighbors(int k, KDPoint p);

    /**
     * Returns the height of the tree. By convention, the height of an empty tree is -1.
     * @return The height of <tt>this</tt>.
     */
    public int height();

    /**
     * Reports whether the <tt>KDTree</tt> is empty, that is, it contains zero <tt>KDPoint</tt>s.
     * @return <tt>true</tt> iff height() == -1.
     */
    public boolean isEmpty();

}
