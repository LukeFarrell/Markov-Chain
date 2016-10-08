
import java.io.*;
import java.util.*;
/**
 * Modified from Spring 2016 offering of Compsci 201
 * @author ola
 * @version 2.0
 */
public class BenchMark {
	
	private static final int TRIALS = 10; // number of trials for each run
	private static final int MAX_ORDER = 15;
	private static final int MIN_ORDER = 1;
	
	/**
	 * Returns the MarkovModel object used to benchmark, e.g., BruteMarkov
	 * or EfficientMarkov. Isolates dependency on the model to this single method.
	 * @param order of the markov model returned
	 * @return a model that implements the proper interface
	 */
	private static MarkovInterface<String> getMarkov(int order) {
		return new BruteMarkov(order);
		//return new EfficientMarkov(order);
	}
	
	/**
	 * Runs model based on parameters passed, returns mean and standard deviation
	 * in an array of two double values with mean in ret[0] and sigma in ret[1]
	 * @param textLength is size of random text generated
	 * @param k is order of markov model
	 * @param source is string used for training
	 * @return mean and sigma/standard deviation in that order in double[]
	 * @throws Exception thrown if thread issues occur
	 */
	private static double[] benchmark(int textLength, int k, String source) throws Exception {
		
		double[] times = new double[TRIALS];
		for (int i = 0; i < TRIALS; i++) {			
			double start = System.nanoTime();
			MarkovInterface<String> model = getMarkov(k);
			model.setTraining(source);
			Thread thread = new Thread(() -> {
				String dummy = model.getRandomText(textLength);
			});
			thread.run();
			thread.join();
			times[i] = (System.nanoTime() - start) / 1.0e9;
		}
		
		double mean = 0;
		for (int i = 0; i < TRIALS; i++) {
			mean += times[i];
		}
		mean /= TRIALS;
		
		double stddev = 0;
		for (int i = 0; i < TRIALS; i++) {
			stddev += Math.pow(times[i] - mean, 2);
		}
		stddev /= TRIALS - 1;
		
		return new double[] {mean, stddev};
	}
	
	/**
	 * Returns number of unique k-grams in a text.
	 * @param text is characters being analyzed
	 * @param order is order of k-gram being found
	 * @return number of unique k-grams in text
	 */
	public static int uniqueKeys(String text, int order) {
		HashSet<String> set = new HashSet<>();
		for(int k=0; k < text.length() - order + 1; k++) {
			String s = text.substring(k, k+order);
			set.add(s);
		}
		return set.size();
	}
	
	public static void varykfixed(String source,int randomLength) throws Exception {
		double[] data;
		
		System.out.printf("order\trandom\tsource size\tmean\tsigma\n");
		
		for (int i = MIN_ORDER; i <= MAX_ORDER; i++) {
			data = benchmark(randomLength, i, source);
			System.out.printf("%d\t%d\t%d\t%f\t%f\n", i, randomLength,source.length(),data[0], data[1]);
		}
		
		System.out.println();
	}
	
	
	public static void main(String[] args) throws Exception {
		System.out.println("Starting tests\n");
		
		String fileName = "alice.txt";
		File file = new File("data/"+fileName);
		double[] data;
		String source = TextSource.textFromFile(file);
		int[] sizes = {100,200,400,800,1600};
		
		for(int size : sizes) {
			System.out.printf("Varying order, text length %d, source size %d\n",size,source.length());
			varykfixed(source,size);
		}
		System.out.println();
		
		System.out.printf("Varying text length, and order using file length %d (%s)\n", source.length(),fileName);
		System.out.printf("\norder\trandom\tsource size\n");
		int[] orders = {1,5,10};
		for(int order : orders) {
			for (int i = 200; i <= 1600; i *= 2) {
				data = benchmark(i, order, source);
				System.out.printf("%d\t%d\t%d\t%f\t%f\n", order, i, source.length(),data[0], data[1]);
			}
		}
		
		System.out.println();
		
		String[] fileNames = {"alice.txt", "hawthorne.txt", "kjv10.txt", "poe.txt", "melville.txt", "romeo.txt"};
		ArrayList<File> files = new ArrayList<File>();
		for(String fname : fileNames) {
			files.add(new File("data/"+fname));
		}
		Collections.sort(files, Comparator.comparing(File::length));
		
		int order = 5;
		int randomLength = 400;
		System.out.printf("Varying file length, using k %d and text length %d\n", 5, randomLength);
		System.out.println("\nunique\tsize\tname\trandom\tmean\tsigma\n");
		for (File f : files) {
			String text = TextSource.textFromFile(f);
			int uniqueKeys = uniqueKeys(text,order);
			data = benchmark(randomLength, order, text);
			System.out.printf("%d\t%d\t%s\t%d\t%f\t%f\n", uniqueKeys, f.length(),f.getName(),randomLength,data[0], data[1]);
		}
		
		System.out.println();
		System.out.println("Finished tests");
	}
}
//Final
