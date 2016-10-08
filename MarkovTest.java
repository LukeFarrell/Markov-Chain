import static org.junit.Assert.*;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class MarkovTest {
	// TODO: change to EfficientMarkov

	private MarkovInterface<String> getModel(int order) {
		// TODO: change to EfficientMarkov
		return new EfficientMarkov(order);
	}
	

	/**
	 * This test checks if MarkovModel makes a correct " Ngram using a simple
	 * source
	 */
	@Test(timeout = 10000)
	public void testMapMakeNgram() {
		MarkovInterface<String> markov = getModel(2);
		markov.setTraining("aabbaabbaabbaabbaabbaabbaabbaabbaabbaabba"
				+ "abbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabb");
		String output = "";
		while (output.length() < 8) {
			output = markov.getRandomText(100);
		}
		assertFalse("This test checks if MarkovModel makes a correct " +
					"Ngram using a simple source",
					output.contains("aaa"));
		assertFalse("This test checks if MarkovModel makes a correct " +
					"Ngram using a simple source",
					output.contains("bbb"));
		assertFalse("This test checks if MarkovModel makes a correct " + 
					"Ngram using a simple source",
					output.contains("aba"));
		assertFalse("This test checks if MarkovModel makes a correct " + 
					"Ngram using a simple source",
					output.contains("bab"));
		assertTrue("This test checks if MarkovModel makes a correct " + 
				   "Ngram using a simple source",
				   output.contains("aab"));
		assertTrue("This test checks if MarkovModel makes a correct " + 
				   "Ngram using a simple source",
				   output.contains("baa"));
		assertTrue("This test checks if MarkovModel makes a correct " + 
				   "Ngram using a simple source",
				   output.contains("abb"));
		assertTrue("This test checks if MarkovModel makes a correct " + "Ngram using a simple source",
					output.contains("bba"));

	}

	/**
	 * This test checks if MarkovModel makes a correct " Ngram when the source
	 * contains only one distinct letter
	 */
	@Test(timeout = 10000)
	public void testMapAllRepeats() {
		String testString = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		MarkovInterface<String> markov = getModel(1);
		markov.setTraining(testString);
		String output = "";
		while (output.length() < 8) {
			output = markov.getRandomText(testString.length());
		}
		assertEquals("This test checks if MarkovModel makes a correct " + 
					 "Ngram when the source contains only one distinct letter",
					 output, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".substring(0, output.length()));
	}

	/**
	 * This test checks if MarkovModel makes a correct Ngram when the source
	 * contains no repeat letters
	 */
	@Test(timeout = 10000)
	public void testMapNoRepeats() {
		String testString = "qwertyuiopasdfghjklzxcvbnm";
		MarkovInterface<String> markov = getModel(1);
		markov.setTraining(testString);
		String output = markov.getRandomText(100);
		assertTrue(
				"This test checks if MarkovModel makes a correct " + 
				"Ngram when the source contains no repeat letters",
				testString.contains(output));
	}

}
//Final
