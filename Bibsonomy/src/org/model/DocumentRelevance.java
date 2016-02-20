/**
 * 
 */
package org.model;
import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.terrier.matching.models.WeightingModelLibrary;
import org.terrier.structures.BitIndexPointer;
import org.terrier.structures.DocumentIndex;
import org.terrier.structures.Index;
import org.terrier.structures.IndexOnDisk;
import org.terrier.structures.Lexicon;
import org.terrier.structures.LexiconEntry;
import org.terrier.structures.MetaIndex;
import org.terrier.structures.bit.BitPostingIndex;
import org.terrier.structures.bit.DirectIndex;
import org.terrier.structures.postings.IterablePosting;
/**
 * @author ould
 */
@SuppressWarnings("deprecation")
public class DocumentRelevance {
	/**
	 * @param args
	 */
	protected Index  index;	
	protected double numberOfDoc = 0;
	protected double numberOftokens = 0;
	protected double mu = 2500;
	protected String path_index;
	protected DocumentIndex doi;
	protected DirectIndex di;
	protected MetaIndex meta;
	protected Lexicon<String> lex;
	protected static Logger logger = Logger.getRootLogger();
	protected HashMap<String, Double> result_liste_doc = new HashMap<String, Double>();

	public DocumentRelevance(String path_index, double mu) throws IOException{	
		init();
		this.path_index = path_index;
		this.mu =mu;
	}
	
	/**
	 *  intitialisation de la classe 
	 * @throws IOException
	 */
	public void init() throws IOException{
		loadIndex();
		meta = index.getMetaIndex();
		di = new DirectIndex( (IndexOnDisk) index, "direct");
		doi = index.getDocumentIndex();
		lex = index.getLexicon();
		numberOfDoc = index.getCollectionStatistics().getNumberOfTokens();
	}
	/**
	 * 
	 * @param tf_t : fréquence du terme dans le document
	 * @param dl : taille du document 
	 * @param tf_c : fréquence du terme dans la collection 
	 * @return score dirichlet du terme 
	 */
	public double dirichlet(double tf_t, double dl, double tf_c){
		String score = ""; 
		score = String.valueOf(WeightingModelLibrary.log(1+(tf_t/(mu * (tf_c/numberOftokens)))) + WeightingModelLibrary.log(mu/(dl + mu)));
		if(!score.equals("Infinity")){
			return Double.parseDouble(score);
		}
		else {
			return 0;
		}
	}
	/**
	 * 
	 * @param terme : le terme pour lequel on calcul son score de correspondance 
	 * @throws IOException
	 * 
	 * 
	 */
	public void getScore(String terme) throws IOException{				
		LexiconEntry le = lex.getLexiconEntry(terme);
		if (le != null){
			BitPostingIndex inv = (BitPostingIndex) index.getInvertedIndex();
			IterablePosting postings = inv.getPostings((BitIndexPointer) le);		
			while (postings.next() != IterablePosting.EOL) {
				String docno = meta.getItem("docno", postings.getId());
				IterablePosting postings_doc = di.getPostings((BitIndexPointer)doi.getDocumentEntry(postings.getId()));
				result_liste_doc.put(docno,dirichlet(postings.getFrequency(),postings_doc.getDocumentLength(),le.getFrequency()));
			}		
		}
	}	
	public HashMap<String, Double> getResult_liste_doc() {
		return result_liste_doc;
	}
	/**
	 * chargement de l'index des documents et des tags des documents 
	 */
	public void loadIndex(){
		long startLoadingDoc = System.currentTimeMillis();
		index = Index.createIndex("", "data");
		if(index == null)
		{
			logger.fatal("Failed to load index tags. Perhaps index files are missing");
		}
		long endLoadingDoc = System.currentTimeMillis();
		if (logger.isInfoEnabled())
			logger.info("time to intialise index tag: " + ((endLoadingDoc-startLoadingDoc)/1000.0D));
	}
}
