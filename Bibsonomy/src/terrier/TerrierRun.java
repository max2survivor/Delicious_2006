/**
 * 
 */
package terrier;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.model.DocumentRelevance;
import org.terrier.applications.InteractiveQuerying;
import org.terrier.applications.batchquerying.TRECQuerying;
import org.terrier.matching.ResultSet;
import org.terrier.matching.models.WeightingModel;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.querying.parser.TerrierQueryParser;
import org.terrier.structures.Index;
import org.terrier.structures.IndexOnDisk;
import org.terrier.structures.MetaIndex;
import org.terrier.utility.ApplicationSetup;

import utils.StemmingTerm;

/**
 * @author ould
 *
 */
public class TerrierRun {

	/**
	 * @param args
	 */
	


	protected static Index index;
	protected static final Logger logger = Logger.getLogger(InteractiveQuerying.class);
	protected static String managerName = ApplicationSetup.getProperty("interactive.manager", "Manager");
	protected static Manager queryingManager;
	protected static PrintWriter resultFile = new PrintWriter(System.out);
	
	

	static org.jdom2.Document document;
	static Element racine;
	

	public static boolean ASC = true;
	public static boolean DESC = false;
	protected static int number_result = 1000;
	public static FileWriter file_result;
	public static String Model = "DirLM";

	public static void main(String[] args) throws IOException {
		String path_index = "z:/experimentations/bibsonomy/bibsonomy/index/index_documents/terrier-4.0/var/index/";
		loadIndex(path_index);
		createManager();	
		
		SAXBuilder sxb = new SAXBuilder();
		try
		{
			document = sxb.build(new File("topics_bibsonomy_new_160_trainning.xml"));
		}
		catch(Exception e){}
		
		
		
		
		TRECQuerying tq = new TRECQuerying();
		for (double mu = 100; mu <= 2500; mu+=100) {
			file_result = new FileWriter("./Resultats/"+Model+"_mu_"+mu+".txt");
			racine = document.getRootElement();
			List<Element> listQuery = racine.getChildren("TOP");
			Iterator<Element> i = listQuery.iterator();
			
			while(i.hasNext())		
			{
				tq.processQueries(mu, true); 
				Element courant = i.next();
				String query = courant.getChild("TITLE").getText();
				System.out.println("statc" + index.getCollectionStatistics());
				SearchRequest srq = queryingManager.newSearchRequest("queryID0", query);
				srq.addMatchingModel("Matching", "DirichletLM");			
				queryingManager.runPreProcessing(srq);
				queryingManager.runMatching(srq);
				queryingManager.runPostProcessing(srq);
				queryingManager.runPostFilters(srq);
				printResults(courant.getChild("NUM").getText(), courant.getChild("TITLE").getText(),resultFile, srq);
				
				

			}
			
			file_result.close();
		}
		
	}
	
	/**
	 * 
	 * @param path_index
	 */

	protected static void loadIndex(String path_index){
		long startLoading = System.currentTimeMillis();
		index = IndexOnDisk.createIndex();
		if(index == null)
		{
			logger.fatal("Failed to load index. Perhaps index files are missing");
		}
		long endLoading = System.currentTimeMillis();
		if (logger.isInfoEnabled())
			logger.info("time to intialise index : " + ((endLoading-startLoading)/1000.0D));
	}

	protected static void createManager(){
		try{
			if (managerName.indexOf('.') == -1)
				managerName = "org.terrier.querying."+managerName;
			queryingManager = (Manager) (Class.forName(managerName)
					.getConstructor(new Class[]{Index.class})
					.newInstance(new Object[]{index}));
		} catch (Exception e) {
			logger.error("Problem loading Manager ("+managerName+"winking smiley: ",e);
		}
	}
	
	public static void printResults(String query_id, String user_id, PrintWriter pw, SearchRequest q) throws IOException {
		ResultSet set = q.getResultSet();	
		final MetaIndex metaIndex = index.getMetaIndex();
		int[] docids = set.getDocids();
		double[] scores = set.getScores();				
		for(int i=0; i<docids.length; i++) {			
			System.out.println(query_id+" "+user_id+" "+metaIndex.getItem("docno", docids[i])+" "+i+" " + scores[i]);
		}
	}

}
