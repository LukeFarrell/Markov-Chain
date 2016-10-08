import java.io.File;
import java.util.Scanner;
import java.util.*;

public class MarkovDriver {
	public static void main(String[] args) {
		String filename = "data/trump-convention.txt";
		if (args.length > 0) {
			filename = args[1];
		}
		File f = new File(filename);
		String text = TextSource.textFromFile(f);
	
		double start = System.nanoTime();
		for(int k=1; k <= 5; k++) {
			MarkovInterface<String> markov = new EfficientMarkov(k);
			markov.setTraining(text);
			String random = markov.getRandomText(200);
			System.out.printf("%d markov model with %d chars\n", k,random.length());
			printNicely(random,60);
		}
		double end = System.nanoTime();
		System.out.printf("total time = %2.3f\n", (end-start)/1e9);
	}

	private static void printNicely(String random, int screenWidth) {
		String[] words = random.split("\\s+");
		int psize = 0;
		System.out.println("----------------------------------");
		for(int k=0; k < words.length; k++){
			System.out.print(words[k]+ " ");
			psize += words[k].length() + 1;
			if (psize > screenWidth) {
				System.out.println();
				psize = 0;
			}
		}
		System.out.println("\n----------------------------------");
	}
}
//Final
