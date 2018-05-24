package projects.spatial.kdpoint;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static projects.spatial.kdpoint.KDPoint.distance;

/**
 * <p><tt>KDPointTests</tt> is a unit testing class for {@link KDPoint}. It has next to nothing
 * to do with your project and is just here to provide some confidence about the fact that
 * {@link KDPoint}s should work as advertised. </p>
 *
 * <p>It might be of interest to you to see how the method {@link Assert#assertEquals(double, double, double)}
 * can be used when comparing doubles in Java. It turns out that comparing doubles in Java is not particularly safe
 * because of precision issues. In fact, it is recommended that one uses {@link java.math.BigDecimal} instead,
 * which offers arbitrary long precision.</p>
 *
 * @author <a href = "mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 */
public class KDPointTests {

	private KDPoint origin2D, origin3D;
	private Random r;
	private static final int SEED = 47;
	private static final int SCALE = 10;
	private static final double EPSILON = Double.MIN_VALUE;
	private static final int MAX_ITER = 100000;
	private static final int MAX_DIM = 1000;

	@Before
	public void setUp() throws Exception {
		origin2D = new KDPoint();
		origin3D = new KDPoint(3);
		r = new Random(SEED); // Re-producible results via static seed.
	}

	@After
	public void tearDown() throws Exception {
		origin2D = origin3D = null;
		r = null;
	}

	@Test
	public void testKDPointInt() {
		KDPoint hopefullyOrigin2D = new KDPoint(2),
				hopefullyOrigin3D = new KDPoint(3);
		assertEquals(hopefullyOrigin2D, origin2D);
		assertEquals(hopefullyOrigin3D, origin3D);

		// Is the appropriate exception properly thrown?
		for(int i = 0; i < MAX_ITER; i++){
			int currDim = -r.nextInt(1000); // Negative integers or zero.
			try {
			   new KDPoint(currDim); 
			} catch(RuntimeException re){
				// Good
			} catch(Throwable t){
				fail("Should've caught a RuntimeException when creating a KDPoint of dimensionality " +currDim + " . Instead we caught a " 
						+ t.getClass() + " with message: " + t.getMessage() + ".");
			}
		}
	}

	@Test
	public void testKDPointDoubleArray() {
		KDPoint point = new KDPoint(2.12, -9.23, 0.01, -34);
		assertEquals(4, point.coords.length);
		assertEquals(2.12, point.coords[0], EPSILON);
		assertEquals(-9.23, point.coords[1], EPSILON);
		assertEquals(0.01, point.coords[2], EPSILON);
		assertEquals(-34, point.coords[3], EPSILON);
	}

	@Test
	public void testKDPointKDPoint() {
		assertEquals(new KDPoint(origin2D), origin2D);
		assertEquals(new KDPoint(origin3D), origin3D);
		KDPoint fourDPoint = new KDPoint(-20.45, 6.78, 0.56, -9.76);
		assertEquals(new KDPoint(fourDPoint),fourDPoint);
	}

	@Test
	public void testDistanceKDPoint() {

		// Trivial zero distances
		assertEquals(0, origin2D.distance(origin2D), EPSILON);
		assertEquals(0, origin3D.distance(origin3D), EPSILON);
		for(int i = 0; i < MAX_ITER; i++){
			KDPoint p = new KDPoint(-SCALE * r.nextDouble(), SCALE * r.nextDouble());
			assertEquals(0, p.distance(p), EPSILON);
		}

		// Let's also check if some exceptions are properly thrown.
		try {
			origin2D.distance(origin3D);
		} catch(RuntimeException rexc){
			// Good
		} catch(Throwable t){
			fail("Should've caught a RuntimeException when computing the distance between two KDPoints of different dimensionalities. Instead, " + 
					"we caught a " + t.getClass() + " with message: " + t.getMessage() + ".");
		}

		try {
			origin3D.distance(origin2D);
		} catch(RuntimeException rexc){
			// Good
		} catch(Throwable t){
			fail("Should've caught a RuntimeException when computing the distance between two KDPoints of different dimensionalities. Instead, " + 
					"we caught a " + t.getClass() + " with message: " + t.getMessage() + ".");
		}
		
		for(int i = 0; i < MAX_ITER; i++){
			int currDim = r.nextInt(MAX_DIM);
			try {
				new KDPoint(currDim).distance(new KDPoint(currDim + 1));
			} catch(RuntimeException rexc){	
			} 
			  catch(Throwable t){
				  fail("Should've caught a RuntimeException when computing the distance between two KDPoints of dimensionalities + " +
				  			 currDim + " and " + currDim + 1 + ". Instead, " + 
							"we caught a " + t.getClass() + " with message: " + t.getMessage() + ".");
			  }
		}

		// Simple stuff first, 1-D points!
		KDPoint one = new KDPoint(3.0), two = new KDPoint(0.0);
		assertEquals(0, one.distance(one), EPSILON);
		assertEquals(0, two.distance(two), EPSILON);
		assertEquals(9, one.distance(two), EPSILON);
		assertEquals(9, two.distance(one), EPSILON);
		KDPoint three = new KDPoint(-3.0);
		assertEquals(9, two.distance(three), EPSILON);
		assertEquals(9, three.distance(two), EPSILON);

		// Classic.
		KDPoint oneOne = new KDPoint(1, 1);
		assertEquals(2, new KDPoint().distance(oneOne), EPSILON);
		KDPoint minusOneOne = new KDPoint(1, -1);
		assertEquals(2, new KDPoint().distance(minusOneOne), EPSILON);
		KDPoint oneMinusOne = new KDPoint(1, -1);
		assertEquals(2, new KDPoint().distance(oneMinusOne), EPSILON);
		KDPoint minusOneminusOne = new KDPoint(-1, -1);
		assertEquals(2, new KDPoint().distance(minusOneminusOne), EPSILON);

		// And a not so trivial one
		KDPoint complexPointOne = new KDPoint(3.5, 2.1, -10.9);
		KDPoint complexPointTwo = new KDPoint(-1.4, 2.8, -0.0007);
		assertEquals(143.29474049, complexPointOne.distance(complexPointTwo), EPSILON); // Computed with Google's calculator
		assertEquals(143.29474049, complexPointTwo.distance(complexPointOne), EPSILON);
	}

	@Test
	public void testDistanceKDPointKDPoint() {
		// Some trivial ones
		assertEquals(0, distance(origin2D, origin2D), EPSILON); // Recall that the static method has been statically imported, so this works.
		assertEquals(0, distance(origin3D, origin3D), EPSILON);	
		for(int i = 0; i < MAX_ITER; i++){
			KDPoint p = new KDPoint(-SCALE * r.nextDouble(), SCALE * r.nextDouble());
			assertEquals(0, distance(p, p), EPSILON);
		}

		// The complex example from the previous test:
		KDPoint complexPointOne = new KDPoint(3.5, 2.1, -10.9);
		KDPoint complexPointTwo = new KDPoint(-1.4, 2.8, -0.0007);
		assertEquals(143.29474049, distance(complexPointOne, complexPointTwo), EPSILON);
		assertEquals(143.29474049, distance(complexPointTwo, complexPointOne), EPSILON);
		
		// And, finally, proper exceptions thrown when comparing objects of different
		// dimensionalities:
		for(int i = 0; i < MAX_ITER; i++){
			int currDim = r.nextInt(MAX_DIM);
			try {
				distance(new KDPoint(currDim), new KDPoint(currDim + 1));
			} catch(RuntimeException rexc){	} 
			  catch(Throwable t){
				  fail("Should've caught a RuntimeException when computing the distance between two KDPoints of dimensionalities + " +
				  			 currDim + " and " + currDim + 1 + ". Instead, " + 
							"we caught a " + t.getClass() + " with message: " + t.getMessage() + ".");
			  }
		}
	}
	
	@Test
	public void testToString(){
		
		// (1) 1D KDPoints
		for(int i = 0; i < MAX_ITER; i++){
			double randNum = r.nextDouble();
			KDPoint p = new KDPoint(randNum);
			assertEquals("We failed to generate a proper String-ified representation for "
					+ "the 1D point  #" + i, "A KDPoint with coordinates: ("+randNum+")",
					p.toString());
			p = new KDPoint(SCALE*randNum);
			assertEquals("We failed to generate a proper String-ified representation for "
					+ "the 1D point  #" + i, "A KDPoint with coordinates: ("+SCALE*randNum+")",
					p.toString());
			p = new KDPoint(-randNum);
			assertEquals("We failed to generate a proper String-ified representation for "
					+ "the 1D point  #" + i, "A KDPoint with coordinates: ("+ -randNum+")",
					p.toString());
			p = new KDPoint(-SCALE*randNum);
			assertEquals("We failed to generate a proper String-ified representation for "
					+ "the 1D point  #" + i, "A KDPoint with coordinates: ("+ -SCALE*randNum+")",
					p.toString());
		} 
		
		// (2) 2D KDPoints		
		for(int i = 0; i < MAX_ITER; i++){
			double[] randNums = {r.nextDouble(), r.nextDouble()};
			KDPoint p = new KDPoint(randNums);
			assertEquals("We failed to generate a proper String-ified representation for "
					+ "the 1D point  #" + i, "A KDPoint with coordinates: ("+randNums[0]+", " 
							+ randNums[1] + ")",p.toString());
			double[] scaledRandNums = {SCALE*randNums[0], SCALE*randNums[1]};
			p = new KDPoint(scaledRandNums);
			assertEquals("We failed to generate a proper String-ified representation for "
					+ "the 1D point  #" + i, "A KDPoint with coordinates: ("+scaledRandNums[0]+", " 
							+ scaledRandNums[1] + ")",p.toString());
			double[] minusRandNums = {-randNums[0], -randNums[1]};
			p = new KDPoint(minusRandNums);
			assertEquals("We failed to generate a proper String-ified representation for "
					+ "the 1D point  #" + i, "A KDPoint with coordinates: ("+minusRandNums[0]+", " 
							+ minusRandNums[1] + ")",p.toString());
			double[] minusScaledRandNums = {-scaledRandNums[0], -scaledRandNums[1]};
			p = new KDPoint(minusScaledRandNums);
			assertEquals("We failed to generate a proper String-ified representation for "
					+ "the 1D point  #" + i, "A KDPoint with coordinates: ("+minusScaledRandNums[0]+", " 
							+ minusScaledRandNums[1] + ")",p.toString());
		} 
		
		// Could add tests for more dimensions, but it's not like we will be using toString()
		// for anything other than debugging information...
	}

}
