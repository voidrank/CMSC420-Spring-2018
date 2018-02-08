package demos.traversals;

import java.util.Stack;

/**<p><tt>JLinkedList</tt> is a Linked List which offers three ways to compute its getCount:</p>
 * <ol>
 *     <li>Purely iteratively.</li>
 *     <li>"Recursion" with a custom stack</li>
 *     <li>Recursion.</li>
 * </ol>
 * @author jason
 * @since 1.2
 */
public class JLinkedList {

    // Dummy node without any data fields even.
    private class Node {
        Node next;
    }

    private Node root;

    /**
     * <tt>pushBack()</tt> adds another node at the end of the list. It doesn't even need a data element.
     */
    public void pushBack(){
        if(root == null)
            root = new Node();
        Node curr = root;
        while(curr.next != null)
            curr = curr.next;
        curr.next = new Node();
    }

    /* Counts */

    public int countIter(){
        int count = -1;
        Node curr = root;
        while(curr != null) {
            curr = curr.next;
            count++;
        }
        return count;
    }

    public int countRec(){
        return countRec(root);
    }

    private int countRec(Node n){
        if(n == null)
            return -1;
        else
            return 1 + countRec(n.next);
    }

    public int countStack(){
        Stack<Node> s = new Stack();
        Node curr = root;
        int count = -1;
        while(curr != null){
            s.push(curr);
            curr = curr.next;
            count++;
        }
        while(!s.isEmpty()) // If you're going to emulate recursion, you need to emulate explicit popping.
            s.pop();
        return count;
    }
}
