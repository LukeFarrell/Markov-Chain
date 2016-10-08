import java.io.File;

public class TextAnalyzer {
	
	
	public static String[] getSentences(String text) {
		// Replace newlines with whitespace
		text = text.replaceAll("\\n", " ");
		
		// first regex of (?<= from this site: http://stackoverflow.com/questions/19951850/
		// second part is any sentence terminator followed by a space character
		
		String[] sentences = text.split("(?<=[.?!]\\s)");
		return sentences;
	}
	
	public static String[] getWords(String text) {
		// Replace newlines with whitespace
		text = text.replaceAll("\\n", " ");
		
		// return words separated by one or more white-space chars
		return text.split("\\s+");
	}
	
	public static void analyze(String text) {
		
		String[] words = getWords(text);
		String[] sentences = getSentences(text);
		
		int wlength = 0;
		int slength = 0;
		for(String w : words) {
			wlength += w.length();
		}
		for(String s : sentences) {
			slength += s.split("\\s+").length;
		}
		double wavg = wlength*1.0/words.length;
		double savg = slength*1.0/sentences.length;
		
		System.out.printf("%2.3f,%2.3f\n",wavg,savg);
	}
	
	public static void main(String[] args) {
		String text = TextSource.textFromFile(new File("data/romeo.txt"));
		System.out.println("order,word average,sentence average");
		System.out.print("0,");
		analyze(text);
		for(int k=1; k < 6; k++) {
			System.out.printf("%d,",k);
			BruteMarkov bm = new BruteMarkov(k);
			bm.setTraining(text);
			String randomText = bm.getRandomText(500);
			
			analyze(randomText);
			//System.out.println(randomText);
		}
	}
}
