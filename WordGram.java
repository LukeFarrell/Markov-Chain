import java.util.*;

public class WordGram {
	private String[] myWords;
	private int myHash;
	
//CONSTRUCTOR
	
	public WordGram(String[] source, int start, int size){
		ArrayList<String> temp =  new ArrayList<String>();
		for(int i = start; i < start+ size; i++){
			temp.add(source[i]);
		}
		myWords = temp.toArray(new String[size]);
	    myHash = this.hashCode();
	}
	
//HASH CODE METHOD
	
	public int hashCode(){
		int hash = 0;
		double pos = 1;
		for(String word : myWords){
			pos++;
			double value = word.hashCode();
			hash += (value/pos);
			}
		myHash= hash;
		return myHash;
	}

//TO STRING METHOD
	
	public String toString(){
		StringBuilder printable = new StringBuilder();
		printable.append("{");
		int pos = 0;
		for(String word : myWords){
			pos++;
			printable.append(word);
			if(pos < myWords.length){
				printable.append(",");
			}
		}
		printable.append("}");
		return printable.toString();
		
	}
	
//EQUALS METHOD
	
	public boolean equals(Object other){
		if (! (other instanceof WordGram)) return false;
		WordGram otherW = (WordGram) other;
		if(this.compareTo(otherW) == 0){
			return true;
		}
		else{
			return false;
		}
		
	}
	
//COMPARE TO METHOD 
	
	int compareTo(WordGram other){
		WordGram otherW = (WordGram) other;
		int difference = myHash - otherW.hashCode();
		return difference;
	}
	
//SHIFT ADD METHOD
	
	public WordGram shiftAdd(String last){
		ArrayList<String> myNewWords = new ArrayList<String>(Arrays.asList(myWords));
		myNewWords.remove(myWords[0]);
		myNewWords.add(last);
		String [] wordArray = myNewWords.toArray(new String[myWords.length]);
		WordGram shifted = (WordGram) new WordGram(wordArray, 0, myWords.length);
		return shifted;
	}
	
//LENGTH METHOD
	public int length(){
		return myWords.length;
	}
	
//Final
}
