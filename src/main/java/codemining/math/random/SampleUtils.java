/**
 * 
 */
package codemining.math.random;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.math.RandomUtils;

import codemining.util.StatsUtil;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

/**
 * A utility class for sampling from sets and multisets.
 * 
 * @author Miltos Allamanis <m.allamanis@ed.ac.uk>
 * 
 */
public class SampleUtils {

	/**
	 * Get a uniformly random element from a Multiset.
	 * 
	 * @param set
	 * @return
	 */
	public static <T> T getRandomElement(final Multiset<T> set) {
		final int randPos = RandomUtils.nextInt(checkNotNull(set).size());

		T selected = null;
		int i = 0;
		for (final Multiset.Entry<T> entry : set.entrySet()) {
			i += entry.getCount();
			if (i > randPos) {
				selected = entry.getElement();
				break;
			}
		}
		return selected;
	}

	/**
	 * Get a random index when selecting from the given unnormalized
	 * log2-probabilities.
	 * 
	 * @param log2ProbWeights
	 * @return
	 */
	public static int getRandomIndex(final double[] log2ProbWeights) {
		double max = Double.NEGATIVE_INFINITY;
		for (final double weight : log2ProbWeights) {
			if (max < weight) {
				max = weight;
			}
		}

		final double[] weights = new double[log2ProbWeights.length];
		double sum = 0;

		for (int i = 0; i < log2ProbWeights.length; i++) {
			final double prob = Math.pow(2, log2ProbWeights[i] - max);
			sum += prob;
			weights[i] = prob;
		}

		final double randomPoint = RandomUtils.nextDouble() * sum;
		double partialSum = 0;
		for (int i = 0; i < log2ProbWeights.length; i++) {
			partialSum += weights[i];
			if (partialSum >= randomPoint) {
				return i;
			}
		}
		throw new IllegalStateException("Should not have reached here.");
	}

	/**
	 * Return a random T where each T is associated with a double log2
	 * probability.
	 * 
	 * @param log2ProbWeights
	 * @return
	 */
	public static <T> T getRandomIndex(final Map<T, Double> log2ProbWeights) {
		final double max = StatsUtil.max(log2ProbWeights.values());

		final Map<T, Double> weights = Maps.newHashMap();
		double sum = 0;

		for (final Entry<T, Double> entry : log2ProbWeights.entrySet()) {
			final double prob = Math.pow(2, entry.getValue() - max);
			weights.put(entry.getKey(), prob);

			sum += prob;
		}

		final double randomPoint = RandomUtils.nextDouble() * sum;
		double partialSum = 0;
		for (final Entry<T, Double> entry : weights.entrySet()) {
			partialSum += entry.getValue();
			if (partialSum >= randomPoint) {
				return entry.getKey();
			}
		}
		throw new IllegalStateException("Should not have reached here.");
	}

	/**
	 * Partition the elements T in partition, whose relative size is given
	 * approximately by partitionWeights. Here we do a best effort to match the
	 * weights.
	 * 
	 * @param elementWeights
	 * @param partitionWeights
	 * @return
	 */
	public static <K, T> Multimap<K, T> randomPartition(
			final Map<T, Double> elementWeights,
			final Map<K, Double> partitionWeights) {
		final List<Entry<T, Double>> elements = Lists
				.newArrayList(elementWeights.entrySet());
		Collections.shuffle(elements);

		return randomPartitionGivenOrder(elementWeights, partitionWeights,
				elements);
	}

	/**
	 * @param elementWeights
	 * @param partitionWeights
	 * @param orderedElements
	 * @return
	 */
	public static <K, T> Multimap<K, T> randomPartitionGivenOrder(
			final Map<T, Double> elementWeights,
			final Map<K, Double> partitionWeights,
			final List<Entry<T, Double>> orderedElements) {
		final Multimap<K, T> partitions = HashMultimap.create();

		final double elementWeightSum = StatsUtil.sum(elementWeights.values());
		final double partitionWeightSum = StatsUtil.sum(partitionWeights
				.values());

		final List<Entry<K, Double>> partitionList = Lists
				.newArrayList(partitionWeights.entrySet());

		int currentPartitionIdx = 0;
		double currentElementSum = 0;
		double currentPartitionSum = 0;

		for (int currentElementIdx = 0; currentElementIdx < orderedElements
				.size(); currentElementIdx++) {
			double partitionRandomPoint = (currentPartitionSum + partitionList
					.get(currentPartitionIdx).getValue()) / partitionWeightSum;
			final double elementRandomPoint = currentElementSum
					/ elementWeightSum;
			currentElementSum += orderedElements.get(currentElementIdx)
					.getValue();

			while (partitionRandomPoint <= elementRandomPoint) {
				currentPartitionSum += partitionList.get(currentPartitionIdx)
						.getValue();
				currentPartitionIdx++;
				partitionRandomPoint = (currentPartitionSum + partitionList
						.get(currentPartitionIdx).getValue())
						/ partitionWeightSum;
			}
			partitions.put(partitionList.get(currentPartitionIdx).getKey(),
					orderedElements.get(currentElementIdx).getKey());
		}

		return partitions;
	}

	private SampleUtils() {
	}
}
