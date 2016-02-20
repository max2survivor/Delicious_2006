package scripts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Functions {
	
	public static void main(String[] args) throws IOException {
		String path_file_url = "uuu";

		ArrayList<String> li =  new ArrayList<String>();
		
		int i = 1;
		Scanner scanner = new Scanner(new File(path_file_url));
		FileWriter file_job_liste = new FileWriter("id_url_uniq.txt");
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(!li.contains(line)){
				li.add(line);
				file_job_liste.write(i+ " "+line+ " \n");
				i++;
			}			
		}
		scanner.close();
		file_job_liste.close();

	}
	
}
