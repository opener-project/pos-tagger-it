package it.synthema.opener.postagger.it;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * Refactoring this class:
 * The name of the lemmatization dictionary is hardcoded here.
 * This is not a best practice, but the name is not likely to change, and for sake of simplicity I let so for now.
 * 
 * As the dictionary is in the classpath, it can be easily loaded.
 * It should be loaded only once, a lazzy initialization when the Lemmatizer is instantiated for the first time.
 * 
 * The rest of the code remains mostly unchanged (only some minor changes)
 * @author agarciap
 *
 */

public class Lemmatizer {

	private static final String filename="/lemmatizer.dict";
	private static Hashtable<String, String> wordToLemma;

	public Lemmatizer() {
		super();
		if(wordToLemma==null){
			wordToLemma = new Hashtable<String, String>();
			createDictionary();
		}
		
	}
	
	public String getLemma(String word, String pos){
		if(word == null || word.isEmpty())
			return null;
		
		if(pos == null || pos.isEmpty())
			return null;
		
		String key = word + "<@>" + pos;
		if(wordToLemma.containsKey(key))
			return wordToLemma.get(key);
		else
			return null;
		
	}

	public void exportDictionary(String fileout) {
		if (wordToLemma == null || wordToLemma.isEmpty()) {
			System.out.println("Dictionary is empty or null");
			return;
		}

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(fileout));
			
			Set<String> keyset = wordToLemma.keySet();
			List<String> keylist = new ArrayList<String>();
			keylist.addAll(keyset);
			
			Collections.sort(keylist);
			
			for(String wordpos : keylist){
				String lemma = wordToLemma.get(wordpos);
				String word = wordpos.split("<@>")[0];
				String pos = wordpos.split("<@>")[1];
				
				if (word != null && !word.isEmpty() && lemma != null && !lemma.isEmpty() && pos != null && !pos.isEmpty())
					out.write(word + "\t" + pos + "\t" + lemma + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected boolean createDictionary() {
		BufferedReader in = null;
		String line = null;
		try {
			in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));

			while ((line = in.readLine()) != null) {
				String[] infos = line.split("\t");
				if (infos.length == 3) {
					String word = infos[0];
					String pos = infos[1];
					String lemma = infos[2];
					
					if (pos.contains("~")){
						pos = pos.split("~")[0];
					}
					
					boolean ok = insertLemma(word, pos, lemma);
					if (!ok) {
						if (word.endsWith("è") || word.endsWith("à") || word.endsWith("ò") || word.endsWith("ì") || word.endsWith("ù")){
							word.replaceAll("è", "é");
							word.replaceAll("à", "á");
							word.replaceAll("ì", "í");
							word.replaceAll("ò", "ó");
							word.replaceAll("ù", "ú");
						}
						if (lemma.endsWith("è") || lemma.endsWith("à") || lemma.endsWith("ò") || lemma.endsWith("ì") || lemma.endsWith("ù")){
							lemma.replaceAll("è", "é");
							lemma.replaceAll("à", "á");
							lemma.replaceAll("ì", "í");
							lemma.replaceAll("ò", "ó");
							lemma.replaceAll("ù", "ú");
						}
						ok = insertLemma(word, pos, lemma);
					}
					if (!ok) {
					//System.err.println("Error: " + line);
					}
				} else {
					System.err.println("Skipping... " + line);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean insertLemma(String word, String pos, String lemma) {
		if (word == null || pos == null || lemma == null)
			return false;

		if (word.isEmpty() || pos.isEmpty() || lemma.isEmpty())
			return false;

		String key = word + "<@>" + pos;
		if (wordToLemma.containsKey(key))
			return false;

		wordToLemma.put(key, lemma);
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: ...");
			System.exit(-1);
		}
		Lemmatizer lemmatizer = new Lemmatizer();
		lemmatizer.createDictionary();
		System.out.println("abate NOU " + lemmatizer.getLemma("abate", "NOU"));
		lemmatizer.exportDictionary("lemmatizer.dict");
	}

}
