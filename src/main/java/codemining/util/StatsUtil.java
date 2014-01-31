/**
 * Various useful statistics utilities
 * 
 * @author Jaroslav Fowkes
 */
package codemining.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.math.DoubleMath;

public class StatsUtil {

	/**
	 * Code ported from LingPipe This method returns the log of the sum of the
	 * natural exponentiated values in the specified array. Mathematically, the
	 * result is
	 * 
	 * <blockquote>
	 * 
	 * <pre>
	 * logSumOfExponentials(xs) = log <big><big>( &Sigma;</big></big><sub>i</sub> exp(xs[i]) <big><big>)</big></big>
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * But the result is not calculated directly. Instead, the calculation
	 * performed is:
	 * 
	 * <blockquote>
	 * 
	 * <pre>
	 * logSumOfExponentials(xs) = max(xs) + log <big><big>( &Sigma;</big></big><sub>i</sub> exp(xs[i] - max(xs)) <big><big>)</big></big>
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * which produces the same result, but is much more arithmetically stable,
	 * because the largest value for which <code>exp()</code> is calculated is
	 * 0.0.
	 * 
	 * <p>
	 * Values of {@code Double.NEGATIVE_INFINITY} are treated as having
	 * exponentials of 0 and logs of negative infinity. That is, they are
	 * ignored for the purposes of this computation.
	 * 
	 * @param xs
	 *            Array of values.
	 * @return The log of the sum of the exponentiated values in the array.
	 */
	public static double logSumOfExponentials(final Collection<Double> xs) {
		if (xs.size() == 1) {
			return xs.iterator().next();
		}
		final double max = max(xs);
		double sum = 0.0;
		for (final double value : xs) {
			if (value != Double.NEGATIVE_INFINITY) {
				sum += Math.pow(2, value - max);
			}
		}
		return max + DoubleMath.log2(sum);
	}

	public static double logSumOfExponentials(final double elem1,
			final double elem2) {
		final double max = Math.max(elem1, elem2);
		final double sum = Math.pow(2, elem1 - max) + Math.pow(2, elem2 - max);
		return max + DoubleMath.log2(sum);
	}

	/**
	 * Retrieve the max element
	 * 
	 * @param xs
	 * @return
	 */
	public static double max(final Collection<Double> xs) {
		double max = Double.NEGATIVE_INFINITY;
		for (final double value : xs) {
			if (max < value) {
				max = value;
			}
		}
		return max;
	}

	/**
	 * Retrieve the min element
	 * 
	 * @param xs
	 * @return
	 */
	public static double min(final Collection<Double> xs) {
		double min = Double.POSITIVE_INFINITY;
		for (final double value : xs) {
			if (min > value) {
				min = value;
			}
		}
		return min;
	}

	/**
	 * Calculates the mean of a Collection
	 */
	public static double mean(final Collection<Double> array) {
		return sum(array) / array.size();
	}

	/**
	 * Calculates the median of a List
	 */
	public static double median(final List<Double> array) {

		Collections.sort(array);

		final int middle = array.size() / 2;
		if (array.size() % 2 == 1) {
			return array.get(middle);
		} else {
			return (array.get(middle - 1) + array.get(middle)) / 2.0;
		}

	}

	/**
	 * Calculates the mode of a Collection
	 */
	public static double mode(final Collection<Double> array) {

		double maxValue = 0;
		int maxCount = 0;

		for (final Double elementA : array) {
			int count = 0;
			for (final Double elementB : array) {
				if (elementB.equals(elementA)) {
					++count;
				}
			}
			if (count > maxCount) {
				maxCount = count;
				maxValue = elementA;
			}
		}

		return maxValue;
	}

	/**
	 * Calculates the sum of a Collection
	 */
	public static double sum(final Iterable<Double> array) {
		double sum = 0;
		for (final Double element : array) {
			sum += element;
		}
		return sum;
	}

	/**
	 * Calculates the sum of an Array
	 */
	public static double sum(final double[] array) {
		double sum = 0;
		for (final double element : array) {
			sum += element;
		}
		return sum;
	}

}
