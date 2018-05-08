package projects.spatial.kdpoint;

/** <p>{@link KDPoint} is a class that represents a k-dimensional point in Euclidean
 * space, where <em>k</em> is a positive integer. It provides methods for initialization,
 * copy construction, equality checks and distance calculations. The precision of {@link KDPoint}s
 * is <tt>double</tt>.</p>
 * 
 * <p><b>YOU SHOULD NOT EDIT THIS CLASS'S SOURCE CODE! IF YOU DO, YOU RISK FAILING OUR UNIT TESTS!</b></p>
 *
 * @author <a href="mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 */
public class KDPoint {
	
	/** To make matters simple for client code, we will allow the {@link KDPoint}'s
	 * coordinates to be publicly accessible. This makes {@link KDPoint}s <b>mutable</b>,
	 * so deep copies will be required wherever we copy {@link KDPoint}s.
	 */
	public double[] coords;
	
	
	/**
	 * Default constructor initializes <tt>this</tt> as a 2D {@link KDPoint} describing
	 * the Cartesian origin.
	 */
	public KDPoint(){
		this(2);
	}

	/**
	 * Initialize a <em>k</em>-dimensional {@link KDPoint} at the origin of the axes.
	 * @param k The dimensionality of the {@link KDPoint}.
	 * @throws RuntimeException if the provided dimensionality is &lt; 1.
	 */
	public KDPoint(int k) {
		if(k <= 0)
			throw new RuntimeException("All KDPoints need to have a positive dimensionality.");
		coords = new double[k];
	}
	
	/**
	 * Initialize a {@link KDPoint} with some values. Implicitly sets the {@link KDPoint}'s
	 * dimensionality.
	 * @param vals The values with which to initialize the {@link KDPoint}.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 */
	public KDPoint(double... vals){
		coords = new double[vals.length];
		System.arraycopy(vals, 0, coords, 0, vals.length);
	}
	
	/**
	 * Initialize a {@link KDPoint} based on an already existing {@link KDPoint}. Since {@link KDPoint} is a
	 * <b>mutable</b> class, <b>all new {@link KDPoint} instances</b> should be created this copy-constructor!
	 * @param p The {@link KDPoint} on which we will base the creation of <tt>this</tt>.
	 */
	public KDPoint(KDPoint p){
		this(p.coords);
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null)
			return false;
		if(o.getClass() != getClass()) // TODO: Make sure that it's ok to do a reference equality check here.
			return false;
		KDPoint oCasted = (KDPoint)o; // No ClassCastExceptions here because of above check
		if(oCasted.coords.length != coords.length)
			return false;
		for(int i = 0; i < coords.length; i++)
			if(coords[i] != oCasted.coords[i]) // TODO: Make sure that this is correct up to numerical accuracy.
				return false;
		return true;
	}
	
	/**
	 * Calculate the <b><u>squared</u> Euclidean distance</b> between <tt>this</tt> and <tt>p</tt>.
	 * @param p The {@link KDPoint} to calculate the distance to.
	 * @return The <b><u>squared</u> Euclidean distance</b> between the two {@link KDPoint}s.
	 * @throws RuntimeException if the dimensionality of the two KDPoints is different.
	 */
	public double distance(KDPoint p) throws RuntimeException{
		if(coords.length != p.coords.length)
			throw new RuntimeException("Cannot calculate the Euclidean Distance between KDPoints of different dimensionalities.");
		double sum = 0.0;
		for(int i = 0; i < coords.length; i++)
			sum += Math.pow(coords[i] - p.coords[i], 2);
		return sum;
	}
	
	/**
	 * A static version of distance calculations. Since the Squared Euclidean distance is symmetric,
	 * it's somewhat awkward to have to specify a start and end point, as {@link #distance(KDPoint) distance} does,
	 * so we provide this option as well.
	 * @param p1 One of the two {@link KDPoint}s to calculate the distance of.
	 * @param p2 One of the two {@link KDPoint}s to calculate the distance of.
	 * @return The Euclidean distance between <tt>p1</tt> and <tt>p2</tt>.
	 */
	public static double distance(KDPoint p1, KDPoint p2){
		return p1.distance(p2);
	}
	
	@Override
	public String toString(){
		String retVal = "A KDPoint with coordinates: (";
		for(int i = 0; i < coords.length; i++){
			retVal += coords[i];
			if(i < coords.length - 1) 
				retVal += ", ";
		}
		return retVal +").";
	}
}
