import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class InputReader {

	public static ArrayList<String> contents = new ArrayList<String>();
	
	public static void readFileInput(String[] args) {
		String readLine = "";
		try {
		      @SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
		      while ((readLine = br.readLine()) != null) { 
		        contents.add(readLine);
		      }
		    }
		    catch (IOException e) {
		      e.printStackTrace();
		    }

	}

}
