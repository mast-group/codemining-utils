/**
 * 
 */
package codemining.util;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * @author Miltos Allamanis <m.allamanis@ed.ac.uk>
 * 
 */
public class StatsUtilTest {

	@Test
	public void testLogSumExpSet() {
		final List<Double> listOfNumbers = Lists.newArrayList();

		listOfNumbers.add(-10.);
		listOfNumbers.add(-10.);
		assertEquals(StatsUtil.logSumOfExponentials(listOfNumbers), -9, 10E-100);

		listOfNumbers.add(-1000.);
		assertEquals(StatsUtil.logSumOfExponentials(listOfNumbers), -9, 10E-100);
	}

	@Test
	public void testLogSumExpSimple() {
		final double log2prob = -1000;
		assertEquals(StatsUtil.logSumOfExponentials(log2prob, log2prob), -999,
				10E-100);

		final double log2prob2 = -10000;
		assertEquals(StatsUtil.logSumOfExponentials(log2prob2, log2prob2),
				-9999, 10E-100);

		assertEquals(StatsUtil.logSumOfExponentials(log2prob, log2prob2),
				-1000, 10E-10);
	}

	@Test
	public void testNanBehaviour() {
		final List<Double> listOfNumbers = Lists.newArrayList();

		listOfNumbers.add(1.);
		listOfNumbers.add(2.);
		listOfNumbers.add(5.);
		listOfNumbers.add(10.);
		listOfNumbers.add(10.);
		listOfNumbers.add(Double.NaN);

		assertEquals(StatsUtil.max(listOfNumbers), 10, 10E-100);
		assertEquals(StatsUtil.min(listOfNumbers), 1, 10E-100);
		assertEquals(StatsUtil.mode(listOfNumbers), 10, 10E-100);
		assertEquals(StatsUtil.median(listOfNumbers), 7.5, 10E-100);
		assertEquals(StatsUtil.sum(listOfNumbers), Double.NaN, 10E-100);
		assertEquals(StatsUtil.mean(listOfNumbers), Double.NaN, 10E-10);
	}

	@Test
	public void testSimpleFunctions() {
		final List<Double> listOfNumbers = Lists.newArrayList();

		listOfNumbers.add(-1.);
		listOfNumbers.add(1.);
		listOfNumbers.add(2.);
		listOfNumbers.add(5.);
		listOfNumbers.add(10.);
		listOfNumbers.add(10.);
		listOfNumbers.add(100.);

		assertEquals(StatsUtil.max(listOfNumbers), 100, 10E-100);
		assertEquals(StatsUtil.min(listOfNumbers), -1, 10E-100);
		assertEquals(StatsUtil.mode(listOfNumbers), 10, 10E-100);
		assertEquals(StatsUtil.median(listOfNumbers), 5, 10E-100);
		assertEquals(StatsUtil.sum(listOfNumbers), 127, 10E-100);
		assertEquals(StatsUtil.mean(listOfNumbers), 127. / 7, 10E-10);
	}

}
