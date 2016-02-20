/**
 * 
 */
package corpus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author ould
 *
 */
public class Filtring {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		getActiveDoc(args[0]);

		/*
		Scanner sn = new Scanner(new File("d:/code/workspace/Delicious_2006/files/urls"));
		HashMap<String, Integer> map_url_id= new HashMap<String, Integer>();
		FileWriter wr = new FileWriter("d:/code/workspace/Delicious_2006/files/id_urls_uniq");

		Integer i = 1;
		while (sn.hasNextLine()) {
			String line  = sn.nextLine();
			if(!map_url_id.containsKey(line)){
				map_url_id.put(line, i);
				i++;
				wr.write(i+"\t"+line+"\n");
				wr.flush();
			}
		}
		sn.close();
		wr.close();

		 */
	}


	public static void getActiveDoc(String file) throws IOException{

		/*

		ArrayList<String> id_doc = new ArrayList<String>();
		Scanner s = new Scanner(new File("d:/code/workspace/Delicious_2006/files/id_doc_found"));

		System.out.println("1");
		while (s.hasNextLine()) {
			String line  = s.nextLine();
			//System.out.println(line);
			id_doc.add(line.substring(0, line.lastIndexOf(".")));			
		}
		s.close();


		System.out.println("2");
		HashMap<String, String> map_id_url = new HashMap<String, String>();
		Scanner s2 = new Scanner(new File("d:/code/workspace/Delicious_2006/files/id_url_uniq"));
		FileWriter w = new FileWriter("d:/code/workspace/Delicious_2006/files/id_url_found");
		while (s2.hasNextLine()) {
			String [] line  = s2.nextLine().split("\t");
			if(id_doc.contains(line[0])){
				map_id_url.put(line[0], line[1]);
				//System.out.println(line[0]+"\t"+ line[1]);
				w.write(line[0]+"\t"+ line[1]+"\n");
				w.flush();
			}
		}
		s2.close();
		w.close();
		 */



		ArrayList<String> urls = new ArrayList<String>();
		Scanner s = new Scanner(new File(file));
		HashMap<String, String> map_id_url = new HashMap<String, String>();

		System.out.println("1");
		while (s.hasNextLine()) {
			String [] line  = s.nextLine().split("\t");	
			urls.add(line[1]);
			map_id_url.put(line[1], line[0]);
		}
		s.close();
		System.err.println(urls.size()+" "+map_id_url.size());

		System.out.println(urls.get(0));




		System.out.println("3");
		FileWriter wr = new FileWriter(file+".txt");	

		BufferedReader in_topic  = new BufferedReader(new FileReader("deliciousdata"));
		String line = "";		
		while ((line = in_topic.readLine()) != null) {	
			String [] vect = line.split("\t");
			if(urls.contains(vect[2])){
				//	System.out.println(entry.getKey()+line);	
				wr.write(map_id_url.get(vect[2])+"\t"+ line+"\n");
				wr.flush();
			}
		}
		in_topic.close();


		wr.close();


	}

}
