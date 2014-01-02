/**
 * 
 */
package codemining.math.probability;

import com.google.common.base.Optional;

/**
 * A conditional probability that can also be sampled.
 * 
 * @author Miltos Allamanis
 * 
 */
public interface ISamplableConditionalProbability<A extends Comparable<A>, B extends Comparable<B>>
		extends IConditionalProbability<A, B> {

	/**
	 * Returns a random sample from the conditional probability.
	 * 
	 * @param given
	 * @return
	 */
	public Optional<A> getRandomSample(B given);
}
