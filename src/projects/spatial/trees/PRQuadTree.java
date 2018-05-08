package projects.spatial.trees;

import projects.spatial.kdpoint.KDPoint;
import projects.spatial.nodes.PRQuadBlackNode;
import projects.spatial.nodes.PRQuadNode;

/**
 * <p><tt>PRQuadTree</tt> implements Point-Region (P-R) QuadTrees. Those are trie-based
 * decompositions of 2D space which, unlike {@link KDTree}s or Point QuadTrees, is not generated by the data points,
 * but is decided in advance based on decomposing a 2^n &#42; 2^n space at successively smaller powers of 2.</p>
 *
 * <p>Because PR-QuadTrees are 4-ary instead of binary trees, they can offer better search
 * efficiency than KD-Trees. On the other hand, points very close to each other can cause many recursive decompositions
 * for just two points. This can negatively impact search locally in the tree. </p>
 *
 * <p>PR-QuadTrees are also not particularly easy to generalize to other dimensions, because the fan-out of every nodes is
 * 2^d, for dimensionality d of the space that is indexed. For just 10 dimensions, we already have more than 1000 children
 * subtrees per nodes. The course staff has come around some papers with applications of oct-trees, which are extensions of PR-QuadTrees) in 3 *
 * dimensions. Beyond 3, it seems that the idea of such exponential fanout trie-based quadtrees fades.</p>
 *
 * @author <a href="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 */
public class PRQuadTree implements SpatialDictionary {


    /**
     * Our root is a {@link PRQuadNode}. If null, it is assumed to be a white nodes.
     */
    private PRQuadNode root;

    /**
     * The bucketing parameter which globally controls how many {@link KDPoint}s
     */
    private int maxPoints;


    /**
     * n defines the area spanned by the root: 2^n &#42; 2^n, with the origin (0,0) assumed to be the bottom left corner.
     * This means that the centroid has coordinates (2^(n-1), 2^(n-1))
     */
    private int n;

    /**
     * The number of {@link KDPoint}s held by the <tt>PRQuadTree</tt>. Note that, unlike KD-Trees, in PR-QuadTrees, the
     * number of nodes is not (necessarily) equal to the number of points stored.
     */
    private int count;

    /**
     * Constructor for <tt>PRQuadTree</tt> objects.
     * @param n The exponent of 2 that defines the area assumed to be spanned by the root nodes.
     * @param bucketingParam The "bucketing" parameter, which controls how many {@link KDPoint}s a
     * {@link PRQuadBlackNode} of this tree can hold before having to split.
     * @see #n
     * @see #maxPoints
     */
    public PRQuadTree(int n, int bucketingParam){
        this.n = n;
        this.maxPoints = bucketingParam;
        count = 0;
    }


    @Override
    public void insert(KDPoint p) {
        if(root == null) { // white nodes, first point stored
            root = new PRQuadBlackNode(maxPoints, p);

        }
        else // black or gray nodes
            root.insert(p); // will adjust height accordingly.
        count++;
    }

    @Override
    public void delete(KDPoint p) {
        if(root != null) {
            root = root.delete(p);
            count--;
        }
    }

    @Override
    public boolean search(KDPoint p) {
        /* Short-circuiting makes the following line safe,
         * but it could be harder to parse when compared to an if-else statement
         * or the use of the ternary operator. Food for thought: Do I save lines of code
         * or do I try to make my code more readable? The answer is not always straightforward.
         */
        return (root != null) && root.search(p);
    }

    @Override
    public int height() {
        return (root == null) ? -1 : root.height();
    }

    @Override
    public boolean isEmpty(){
        return (count() == 0);
    }

    @Override
    public int count() {
        return count;
    }
}
