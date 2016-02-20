package paramstrain;

/**
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.model.DocumentRelevance;

import utils.StemmingTerm;






/**
 * @author ould
 *
 */
public class DirLMTrain {

	/**
	 * 
	 * Cette classe est chargé de lancer le modèle de langue avec differentes valeur de mu ( pour le trainning)
	 * @param args
	 * @throws FileNotFoundException 
	 */
	static org.jdom2.Document document;
	static Element racine;
	

	public static boolean ASC = true;
	public static boolean DESC = false;
	protected static int number_result = 1000;
	public static FileWriter file_result;
	public static String Model = "DirLM";

	public static void main(String[] args) throws IOException {
	

		SAXBuilder sxb = new SAXBuilder();
		try
		{
			document = sxb.build(new File("topics_bibsonomy_new_160_trainning.xml"));
		}
		catch(Exception e){}
		String path_index ="";
		for (double mu = 100; mu <= 2500; mu+=100) {
			file_result = new FileWriter("./Resultats/"+Model+"_mu_"+mu+".txt");
			DocumentRelevance doc_rel =  new DocumentRelevance(path_index, mu);
			racine = document.getRootElement();
			List<Element> listQuery = racine.getChildren("TOP");
			Iterator<Element> i = listQuery.iterator();
			
			while(i.hasNext())		
			{
				Element courant = i.next();
				StemmingTerm porterStemmer = new StemmingTerm();
				String terme_query_stem = porterStemmer.stripAffixes(courant.getChild("TITLE").getText()); // requete
				doc_rel.getScore(terme_query_stem);
				resultFile(courant.getChild("NUM").getText(),courant.getChild("TITLE").getText(),doc_rel.getResult_liste_doc(),"DirLM");	
				

			}
			
			file_result.close();
		}
		


	}
	
	public static void resultFile(String query_id, String user_id, HashMap<String, Double> list_result, String model) throws IOException{
		
		Map<String, Double> result_liste = new HashMap<String, Double>();
		result_liste= sortByComparator(list_result,DESC);
		int rank = 1; 	
		for (String mapKey : result_liste.keySet()) {
			if(rank <= number_result){
				file_result.write(query_id +  " " + user_id + " " + mapKey + " " + rank + " " + result_liste.get(mapKey) + " " + model + "\n");
				rank++;
			}
		}
	}
	
	private static Map<String, Double> sortByComparator(Map<String, Double> unsortMap, final boolean order)
	{
		List<Entry<String, Double>> list = new LinkedList<Entry<String, Double>>(unsortMap.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Double>>(){
			public int compare(Entry<String, Double> o1,Entry<String, Double> o2)
			{if (order){return o1.getValue().compareTo(o2.getValue());}
			else
			{return o2.getValue().compareTo(o1.getValue());	}
			}
		});

		Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
		for (Entry<String, Double> entry : list)
		{sortedMap.put(entry.getKey(), entry.getValue());}

		return sortedMap;
	}
}






