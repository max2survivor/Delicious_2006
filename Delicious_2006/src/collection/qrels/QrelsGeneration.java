package collection.qrels;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import utils.StemmingTerm;


public class QrelsGeneration {
	static org.jdom2.Document document;
	static Element racine;
	
	/**
	 * cette classe permet de créer les jugements de pertinences pour les requetes utilisateurs
	 */
	
	public static void main(String[] args) throws FileNotFoundException {

		SAXBuilder sxb = new SAXBuilder();
		try
		{
			document = sxb.build(new File("topics_delicious_2006.xml"));
		}
		catch(Exception e){}

		racine = document.getRootElement();
		List<Element> listEtudiants = racine.getChildren("TOP");
		Iterator<Element> i = listEtudiants.iterator();		
		while(i.hasNext())
		{
			Element courant = i.next();
			StemmingTerm porterStemmer = new StemmingTerm();
			String terme_query_stem = porterStemmer.stripAffixes(courant.getChild("TITLE").getText()); // requete
			getBinaryRelevanceJUdgement(courant.getChild("NUM").getText(),terme_query_stem, courant.getChild("USER").getText());

		}

	}


	/**
	 * on donne en parametre la requete et l'utilisateur et on cherche les tags dans les triplets (user, bookmarks, tag) 
	 * si l'utisateur a utilisé les termes de la requetes pour un tagger un document alors ce document est pertinenet pour la requet 
	 * @throws FileNotFoundException 
	 * 
	 */



	public static void getBinaryRelevanceJUdgement(String id_query, String terme_query, String user_id) throws FileNotFoundException{
		StemmingTerm porterStemmer = new StemmingTerm();
		//System.out.println("./corpus_users/" + user_id +".txt");
		Scanner s = new Scanner(new File("./corpus_users/" + user_id +".txt"));
		ArrayList<String> id_bookmark = new ArrayList<String>();
		while (s.hasNextLine()) {
			String line = s.nextLine();
			//System.err.println(line);
			String [] vect = line.split("\t");			
			if(porterStemmer.stripAffixes(vect[1]).equalsIgnoreCase(porterStemmer.stripAffixes(terme_query))){
				if(!id_bookmark.contains(vect[2])){
					id_bookmark.add(vect[2]
							);
				}
			}
		}		
		s.close();


		for (int i = 1; i < id_bookmark.size(); i++) {

			System.out.println(id_query + " " + user_id + " " +  id_bookmark.get(i) + " " + "1");

		}




		/*
		Scanner sc = new Scanner(new File("./files_qrls/idDocumentIndex_idBookmarks.txt"));
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String [] vect = line.split(" ");

			for (int i = 1; i < vect.length; i++) {
				if(id_bookmark.contains(vect[i])){
					System.err.println(line);
					System.out.println(id_query + " " + user_id + " " +  vect[0] + " " + "1");

				}

			}	

		}
		sc.close();
		 */
	}
}
