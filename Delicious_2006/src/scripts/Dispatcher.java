/**
 * 
 */
package scripts;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Dispatcher {


	public static void main(String[] args) throws IOException {
		String path_file_url = "./files/id_urls.txt";

		Scanner scanner = new Scanner(new File(path_file_url));
		FileWriter file_job_liste = new FileWriter("job_liste_download_url_delicious_2006_2.txt");
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String vec[] = line.split(" ");
			file_job_liste.write("time java -jar /home/ould/experimentations/Delicious_2006/corpus_download/url_document_download.jar"+ " " 
					+ "\""+vec[0]+" "+ vec[1]+"\"" + " /home/data/collection/social_network/Delicious_2006/document_xml/"
					+ " /home/data/collection/social_network/Delicious_2006/document_text/"+ "\n");

		}
		scanner.close();
		file_job_liste.close();
	}

}
