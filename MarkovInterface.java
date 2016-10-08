import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface for Compsci 201 Markov Assignment. Note that the order
 * of the Markov model would be typically set via a constructor
 * @author ola
 *
 * @param <Type> should be String or WordGram
 */

public interface MarkovInterface<Type> {
	
	/**
	 * Set the training text for subsequent random text generation.
	 * @param text is the training text
	 * @return 
	 */
	public void setTraining(String text);
	
	/**
	 * Get randomly generated text based on the training text
	 * and order.
	 * @param length is the number of characters or words generated, this is a maximum
	 * since if EOS encountered possibly fewer items than length will be generated
	 * @return randomly generated text 
	 */
	public String getRandomText(int length);
	
	/**
	 * Really a helper method, but must be public to be part of interface. Used
	 * to get all the characters or strings that follow a key. Returns an
	 * ArrayList of the following items. 
	 * @param key is key being searched for in training text
	 * @return a list of items that follow key: single-character strings
	 * if <Type> is String and Strings if <Type> is WordGram
	 */
	public ArrayList<String> getFollows(Type key);
	
	/**
	 * returns the order of this Markov Model, typically set at construction 
	 * @return the order of this model
	 */
	public int getOrder();
}
//Final
