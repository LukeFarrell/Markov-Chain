import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class TextSource {
	public static String textFromURL(String url){
		BufferedInputStream source;
		Scanner scan = null;
		try {
			source = new BufferedInputStream(new URL(url).openStream());
			source.mark(Integer.MAX_VALUE);
			scan = new Scanner(source);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		scan.useDelimiter("\\Z");
		return scan.next();
	}
	public static String textFromFile(File f){
		BufferedInputStream source;
		Scanner scan = null;
		try {
			source = new BufferedInputStream(new FileInputStream(f));
			source.mark(Integer.MAX_VALUE);
			scan = new Scanner(source);
		} catch (IOException e) {
			e.printStackTrace();
		}
		scan.useDelimiter("\\Z");
		return scan.next();
	}
}
