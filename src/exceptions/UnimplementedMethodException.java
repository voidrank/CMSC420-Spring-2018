package exceptions;

/** <p>An {@link Exception} used by the skeleton code we provide for you to notify you of methods that you have not
 * yet implemented and have not yet done so.</p>
 *
 * <p>You should <b>not</b> edit this class! It is given to you as a resource for your project.</p>
 *
 * @author  <a href="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>

 * @see InvalidCapacityException
 *
 */
public class UnimplementedMethodException extends Exception {
    public UnimplementedMethodException(String msg){
        super(msg);
    }
}
