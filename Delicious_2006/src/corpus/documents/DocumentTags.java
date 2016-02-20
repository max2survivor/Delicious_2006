package corpus.documents;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DocumentTags {



	public static String  path_documents_collection_xml;
	public static String  path_documents_collection_text;

	public static void main(String[] args) throws IOException {
		
		path_documents_collection_xml = args[0];
		path_documents_collection_text = args[1];
		String path_liste_doc = args[2];
		ArrayList<String> liste_users = new ArrayList<String>();
		BufferedReader user  = new BufferedReader(new FileReader(path_liste_doc));
		String line = "";		
		while ((line = user.readLine()) != null) {	
			liste_users.add(line);
		}
		user.close();

		for (int i = 0; i < liste_users.size(); i++) {
			System.out.println(liste_users.get(i));
			BufferedReader data  = new BufferedReader(new FileReader("deliciousdata_found_sorted_by_doc"));
			String line2 = "";
			boolean trouve = false;
			String text ="";
			while ((line2 = data.readLine()) != null) {	
				String [] vect = line2.split("\t");
				if(vect[2].equals(liste_users.get(i))){
					trouve = true;
					if (vect.length==5) {
						String[] tokens = vect[4].split("^\\W.|\\_|\\-|/|\\+|\\,|\\:");
						if (tokens.length!=0) {
							for (int j = 0; j < tokens.length; j++) {
								text = text + tokens[j] + " ";	
							}	
						}
						else {
							text = text + vect[4] + " ";	
						}
					}

				}
				else {
					if(trouve==true)
						break;
				}
			}
			data.close();
			fileDocSave(liste_users.get(i), text);
			fileTextSave(liste_users.get(i), text);
		}

	}

	public static void fileDocSave(String num_doc, String text){
		try
		{

			FileWriter pw = new FileWriter(path_documents_collection_xml+String.valueOf(num_doc)+".xml", true);
			pw.write("<doc>"+"\n");
			pw.write("<docno>" + num_doc + "</docno>" +"\n");
			pw.write("<tags>" + "\n");
			pw.write(text + "\n");
			pw.write("</tags>" + "\n");
			pw.write("</doc>");
			pw.close();
		}
		catch (IOException exception)
		{
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	}

	public static void fileTextSave(String user_id,String text){
		try
		{
			FileWriter pw = new FileWriter(path_documents_collection_text+String.valueOf(user_id)+".txt", true);
			pw.write(text);
			pw.close();
		}
		catch (IOException exception)
		{
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	} 

}