/**
 * 
 */
package codemining.util;

import java.util.Set;
import java.util.SortedSet;

import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Multisets;
import com.google.common.collect.Sets;

/**
 * A utility class containing collection-related utilities.
 * 
 * @author Miltos Allamanis <m.allamanis@ed.ac.uk>
 * 
 */
public class CollectionUtil {

	/**
	 * Return the elements that have been seen at least nSeen times.
	 * 
	 * @param nSeen
	 * @param baseMultiset
	 * @return
	 */
	public static <T> Set<T> getElementsUpToCount(final int nSeen,
			final Multiset<T> baseMultiset) {
		final Set<T> toKeep = Sets.newHashSet();
		for (final Entry<T> entry : Multisets.copyHighestCountFirst(
				baseMultiset).entrySet()) {
			if (entry.getCount() < nSeen) {
				break;
			}
			toKeep.add(entry.getElement());
		}
		return toKeep;
	}

	/**
	 * Return the top elements of a sorted set.
	 * 
	 * @param originalSet
	 * @param nTopElements
	 * @return
	 */
	public static <T extends Comparable<T>> SortedSet<T> getTopElements(
			final SortedSet<T> originalSet, final int nTopElements) {
		final SortedSet<T> filteredElements = Sets.newTreeSet();
		int i = 0;
		for (final T element : originalSet) {
			if (i > nTopElements) {
				break;
			}
			filteredElements.add(element);
			i++;
		}
		return filteredElements;
	}

}
