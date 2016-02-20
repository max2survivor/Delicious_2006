/**
 * 
 */
package corpus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.tika.language.LanguageIdentifier;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author ould
 *
 */
public class DocumentUrlsDownload {

	/**
	 * @param args
	 */



	//public static FileWriter pw_correct ;
	public static String path_file_url ="";
	public static String path_documents_collection_xml ="";
	public static String path_documents_collection_text ="";

	public static void main(String[] args) throws IOException {		 
		path_file_url = args[0];
		path_documents_collection_xml = args[1];
		path_documents_collection_text = args[2];
		Scanner sn = new Scanner(new File(path_file_url));
		while (sn.hasNextLine()) {			
			String vect[] = sn.nextLine().split("\t"); 
			//pw_correct = new FileWriter("/home/data/collection/social_network/TREC_MICROBLOG_2015/files_urls/"+vect[0]+"_url_correct.txt", true);
			getTextUrl(Integer.parseInt(vect[0]), vect[1]);	
		}
		sn.close();
		//pw_correct.close();
	}



	public static void getTextUrl(int id ,String url) throws IOException{
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
			String title = doc.title();
			LanguageIdentifier identifier = new LanguageIdentifier(doc.text());
			String language = identifier.getLanguage();
			if(language.equals("en")){
				fileDocSave(id, title, doc.text(), url);
				fileTextSave(id, title, doc.text());
			}

		} catch (Exception e) {
		}
	}


	public static void fileDocSave(int id, String title, String text, String url){
		try
		{
			//pw_correct.write(id+ " " + url+"\n");
			//pw_correct.flush();
			FileWriter pw = new FileWriter(path_documents_collection_xml+String.valueOf(id)+".xml", true);
			pw.write("<doc>"+"\n");
			pw.write("<docno>" + id + "</docno>" +"\n");
			pw.write("<url>" + url + "</url>" +"\n");
			pw.write("<title>" + title + "</title>" +"\n");
			pw.write("<text>" + "\n");
			pw.write(text + "\n");
			pw.write("</text>" + "\n");
			pw.write("</doc>");
			pw.close();
		}
		catch (IOException exception)
		{
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	}

	public static void fileTextSave(int id, String title, String text){
		try
		{
			FileWriter pw = new FileWriter(path_documents_collection_text+String.valueOf(id)+".txt", true);
			pw.write(title+" "+text);
			pw.close();
		}
		catch (IOException exception)
		{
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	} 
}
