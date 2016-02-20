package corpus.users;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SpliteUserFile {
	
	
	
	public static void main(String[] args) throws IOException {

		
		ArrayList<String> liste_users = new ArrayList<String>();
		BufferedReader user  = new BufferedReader(new FileReader(""));
		String line = "";		
		while ((line = user.readLine()) != null) {	
			liste_users.add(line);
		}
		user.close();
		
		
		
		
		
		
		
	}
	

}
