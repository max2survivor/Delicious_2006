package corpus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UserDocExist {
	
	
	public static void main(String[] args) throws IOException {
		
		
		ArrayList<String> id_doc= new ArrayList<>();
		BufferedReader doc  = new BufferedReader(new FileReader("id"));		
		String d = "";	
		while ((d = doc.readLine()) != null) {	
			id_doc.add(d);
		}
		doc.close();
		
		
		BufferedReader user  = new BufferedReader(new FileReader("./files/users_id"));		
		String line = "";	
		while ((line = user.readLine()) != null) {	
			
			FileWriter f = new FileWriter("./corpus_users_doc_existe/"+line);
			BufferedReader user_file = new BufferedReader(new FileReader("./corpus_users/"+line));		
			String file = "";
			while ((file = user_file.readLine()) != null) {
				String [] vect = file.split("\t");
				if(id_doc.contains(vect[2])){
					f.write(file+"\n");
				}
			}
			user_file.close();
			f.close();
		}
		user.close();
	}
	
	
	

}
