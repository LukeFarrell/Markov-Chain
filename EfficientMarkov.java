import java.util.*;

public class EfficientMarkov implements MarkovInterface<String>{
		private String myText;
		private Random myRandom;
		private int myOrder;
		private HashMap<String,ArrayList<String>> trainingMap;
		
		private static String PSEUDO_EOS = "";
		private static int RANDOM_SEED = 1234;

// CONTRUCTOR & RANDOM SEED
		
		public EfficientMarkov(int order) {
			myRandom = new Random(RANDOM_SEED);
			myOrder = order;
		}
		
		public EfficientMarkov() {
			this(3);
		}

//SET TRAINING MAP
		
		public void setTraining(String text) {
			myText = text;
			HashMap<String,ArrayList<String>> trainingM = new HashMap<String, ArrayList<String>>();
			ArrayList<String> textList = new ArrayList<String>(Arrays.asList(myText.split("")));
			
			for(int i =0; i < myText.length()-myOrder+1; i++){
				StringBuilder gram = new StringBuilder();
				for(int k = i; k <= i+myOrder-1; k++){ 
					gram.append(textList.get(k));
				}
				String kgram = gram.toString();
				if(i + myOrder >= myText.length()){
					if(trainingM.containsKey(kgram)){
						ArrayList<String> a = trainingM.get(kgram);
						a.add("PSEUDO_EOS");
						trainingM.put(kgram, a);
						break;
					}
					else{
						break;
					}
				}
				else{
					if(trainingM.containsKey(kgram)){
						ArrayList<String> a =  trainingM.get(kgram);
						a.add(textList.get(i+myOrder));
						trainingM.put(kgram, a);
					}
					else{
						ArrayList<String> a = new ArrayList<String>();
						a.add(textList.get(i+myOrder));
						trainingM.put(kgram, a);
					}
				}
			}
			trainingMap = trainingM;
		}

// GET THE LENGTH OF TEXT
		
		public int size() {
			return myText.length();
		}

////GET RANDOM GENERATED MARKOV TEXT BY STRINGS
		
		public String getRandomText(int length) {
			StringBuilder sb = new StringBuilder();
			int index = myRandom.nextInt(myText.length() - myOrder);
			
			String current = myText.substring(index, index + myOrder);
			//System.out.printf("first random %d for '%s'\n",index,current);
			sb.append(current);
			for(int k=0; k < length-myOrder; k++){
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
					System.out.println("---STRING OCCURS AT END---");
					break;
				}
				sb.append(nextItem);
				current = current.substring(1)+ nextItem;
			}
			return sb.toString();
		}

// GET FOLLOWS LIST FROM THE  STRING KEY
		
		public ArrayList<String> getFollows(String key){
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
