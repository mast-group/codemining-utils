/**
 * 
 */
package codemining.util;

import java.util.Set;

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

}
