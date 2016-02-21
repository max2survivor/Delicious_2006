/**
 * 
 */
package collection.query;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author ould
 *
 */
public class QueryGeneration {

	public static void main(String[] args) throws IOException {
		System.out.println("1");
		String path_file_users_topic = "id_users.txt";
		ArrayList<String> liste_users = new ArrayList<String>();
		Scanner scanner = new Scanner(new File(path_file_users_topic));
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			//System.out.println(line );
			liste_users.add(line);		
		}
		scanner.close();
		System.out.println("2" );
		ArrayList<String> liste_users_selected = new ArrayList<String>();
		FileWriter pw = new FileWriter("user_selected.txt");
		for (int i = 0; i < liste_users.size(); i++) {					
			final Path path = Paths.get("/home/data/collection/social_network/Delicious_2006/users_files/"+liste_users.get(i)+".txt");
			final long lineCount = Files.lines(path).count();
			//System.out.println(lineCount);
			if(lineCount>50){
				liste_users_selected.add(liste_users.get(i));
				pw.write(liste_users.get(i)+"\n");
			}
		}
		pw.close();
		
		System.out.println(liste_users +" "+ liste_users_selected );
		
		/*
		ArrayList<String> user_tag_matrix = new ArrayList<String>();
		for (int i = 0; i < liste_users.size(); i++) {
			Scanner scanner1 = new Scanner(new File("y:/social_network/Delicious_2006/users_files/"+liste_users.get(i)+".txt"));
			while (scanner1.hasNextLine()) {				
				String line = scanner1.nextLine();	
				System.out.println(line);
				String [] vect = line.split("\t");
				System.err.println(vect.length);
				if(vect.length==5){

					user_tag_matrix.add(vect[2]+" "+vect[4]);
					System.out.println(vect[2]+" "+vect[4]);
				}	
			}
			scanner1.close();
		}
		
		System.out.println(user_tag_matrix.size());
		
		int valeurMin = 0;
		int valeurMax = user_tag_matrix.size()-1;
		Random r = new Random();		
		
		FileWriter pw = new FileWriter("topics_d.xml");
		pw.write("<TOPS> \n");
		for (int i = 0; i < 1000; i++) {
			int valeur = valeurMin + r.nextInt(valeurMax - valeurMin);
			String selected = "";
			selected = user_tag_matrix.get(valeur);
			String [] s = selected.split(" ");
			pw.write("	<TOP> \n");
			pw.write("		<NUM>"+i+"</NUM>\n");
			pw.write("		<USER>"+s[0]+"</USER>\n");
			pw.write("		<TITLE>"+s[1]+"</TITLE>\n");
			pw.write("	</TOP>\n");
			
		}
		pw.write("</TOPS> \n");
		pw.close();
		*/

	}
}
