/**
 * 
 */
package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author ould
 *
 */
public class UsersFunctions {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String path_file_users_topic = "./files/users_id";
		ArrayList<String> liste_users = new ArrayList<String>();
		Scanner scanner = new Scanner(new File(path_file_users_topic));
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			liste_users.add(line.substring(0,line.lastIndexOf(".")));		
		}
		scanner.close();
		
		
		ArrayList<String> user_tag_matrix = new ArrayList<String>();
		for (int i = 0; i < liste_users.size(); i++) {
			Scanner scanner1 = new Scanner(new File("corpus_users/"+liste_users.get(i)+".txt"));
			while (scanner1.hasNextLine()) {
				String line = scanner1.nextLine();				
				String [] vect = line.split("\t");				
				user_tag_matrix.add(vect[0]+" "+vect[1]);	
			}
			scanner1.close();
		}
		
		System.out.println(user_tag_matrix.size());
		
		int valeurMin = 0;
		int valeurMax = user_tag_matrix.size()-1;
		Random r = new Random();		
		
		FileWriter pw = new FileWriter("topics_bibsonomy_new_200.xml");
		pw.write("<TOPS> \n");
		for (int i = 0; i < 200; i++) {
			int valeur = valeurMin + r.nextInt(valeurMax - valeurMin);
			String selected = "";
			selected = user_tag_matrix.get(valeur);
			String [] s = selected.split(" ");
			pw.write("	<TOP> \n");
			pw.write("		<USER>"+i+"</USER>\n");
			pw.write("		<USER>"+s[0]+"</USER>\n");
			pw.write("		<TITLE>"+s[1]+"</TITLE>\n");
			pw.write("	</TOP>\n");
			
		}
		pw.write("</TOPS> \n");
		pw.close();
		

	}

}
