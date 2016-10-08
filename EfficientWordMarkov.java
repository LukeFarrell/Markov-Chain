import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class EfficientWordMarkov implements MarkovInterface<WordGram>{
	private String myText;
	private Random myRandom;
	private int myOrder;
	private HashMap<WordGram,ArrayList<String>> trainingMap;
	
	private static String PSEUDO_EOS = "";
	private static int RANDOM_SEED = 1234;

//CONSTRUCT EFFICIENT MARKOV and RANDOM SEED
	
	public EfficientWordMarkov(int order) {
		myRandom = new Random(RANDOM_SEED);
		myOrder = order;
	}
	
	public EfficientWordMarkov() {
		this(3);
	}

//SET TRAINING METHOD USING WORDGRAM	

	public void setTraining(String text) {
		myText = text;
		HashMap<WordGram,ArrayList<String>> trainingM = new HashMap<WordGram, ArrayList<String>>();
		String [] source = myText.split("\\s+");
		
		for(int i = 0; i  < source.length-myOrder; i++){
			WordGram key = new WordGram(source, i, myOrder);
			if(i+myOrder == source.length){
				if(trainingM.containsKey(key)){
					ArrayList<String> a =  trainingM.get(key);
					a.add("PSEUDO_EOS");
					trainingM.put(key, a);
				}
				else{
					ArrayList<String> a = new ArrayList<String>();
					a.add("PSEUDO_EOS");
					trainingM.put(key, a);
				}
			}
			else{
				if(trainingM.containsKey(key)){
					ArrayList<String> a =  trainingM.get(key);
					a.add(source[i+myOrder]);
					trainingM.put(key, a);
				}
				else{
					ArrayList<String> a = new ArrayList<String>();
					a.add(source[i+myOrder]);
					trainingM.put(key, a);
				}
			}
			}
		trainingMap = trainingM;
	}
	
// GET SIZE OF TEXT METHOD
	
	public int size() {
		return myText.split("\\s+").length;
	}
	
//GET RANDOM GENERATED MARKOV TEXT BY WORD GRAM
	
	public String getRandomText(int length) {
		StringBuilder sb = new StringBuilder();
		int index = myRandom.nextInt(myText.split("\\s+").length - myOrder);
		
		WordGram current = new WordGram(myText.split("\\s+"), index, myOrder);
		sb.append(current);
		for(int k=0; k < length; k++){
			ArrayList<String> follows = new ArrayList<String>();
			if (trainingMap.containsKey(current)){
				follows = trainingMap.get(current);					
			}
			else{
				System.out.println("---KEY NOT PRESENT---");
				System.out.println(current);
				break;
			}
			index = myRandom.nextInt(follows.size());
			
			String nextItem = follows.get(index);
			if (nextItem.equals(PSEUDO_EOS)) {
				break;
			}
			sb.append(nextItem);
			sb.append(" ");
			current = current.shiftAdd(nextItem);
		}
		return sb.toString();
	}
	
// RETURN THE FOLLOW LIST OF THE WORDGRAM KEY
	
	public ArrayList<String> getFollows(WordGram key){
		ArrayList<String> follows = new ArrayList<String>(trainingMap.get(key));
	return follows;
	}

// GET ORDER OF MARKOV
	
	@Override
	public int getOrder() {
		return myOrder;
	}
	
}
//Final
