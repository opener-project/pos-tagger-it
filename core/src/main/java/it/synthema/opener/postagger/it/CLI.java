package it.synthema.opener.postagger.it;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import eu.openerproject.kaf.layers.KafMetadata;
import eu.openerproject.kaf.layers.KafTarget;
import eu.openerproject.kaf.layers.KafTerm;
import eu.openerproject.kaf.layers.KafWordForm;
import eu.openerproject.kaf.reader.KafSaxParser;

public class CLI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		KafSaxParser saxparser = new KafSaxParser();

		// set internal xml validation (it works only via xsd schema)
		saxparser.setXMLValidate(false);

		boolean fixedTimestamp=false;
		for(String arg:args){
			if(arg.equalsIgnoreCase("-t")){
				fixedTimestamp=true;
			}
		}
		
		// parse file
		saxparser.parseFile(System.in);

		// wordform
		//System.out.println("Read wordforms...");
		List<KafWordForm> wordforms = saxparser.getWordList();
		String[] words = new String[wordforms.size()];
		for (int i = 0; i < wordforms.size(); i++) {
			String wordform = wordforms.get(i).getWordform();
			if (wordform == null || wordform.isEmpty()) {
				printErrorMessage("current wordform " + wordforms.get(i).getWid() + " is null");
				System.exit(-1);
			}
			words[i] = wordform;
		}

		// tagging and lemmatizer
		Lemmatizer lemmatizer = new Lemmatizer();
		

		POSTagger postagger = new POSTagger();
		List<KafTerm> terms = new ArrayList<KafTerm>();
		String[] tags = postagger.tag(words);
		int tid_counter = 0;
		//int wid_counter = 0;
		for (int i = 0; i < words.length; i++) {
			//if (words[i].matches("[.,:;-_?!\"\'<>]"))
			//	continue;
			tid_counter++;
			KafTerm term = new KafTerm();
			String kaf_pos = PTB2KAF.convert(tags[i]);
			String lemma = "unknown";
			if (kaf_pos.equals("O"))
				lemma = words[i].toLowerCase();
			if (kaf_pos.equals("R"))
				lemma = words[i].toLowerCase();
			if (lemmatizer != null) {
				String pos_lemma = tags[i];
				if (pos_lemma.contains("~"))
					pos_lemma = pos_lemma.split("~")[0];
				String lemma_extracted = lemmatizer.getLemma(words[i].toLowerCase(), pos_lemma);
				if (lemma_extracted != null)
					lemma = lemma_extracted;
				else
					lemma = words[i].toLowerCase();
			}
			term.setLemma(lemma);
			//term.setComment(words[i]);
			
			// span
			List<KafTarget> span = new ArrayList<KafTarget>();
			span.add(new KafTarget("w" + (i + 1)));
			term.setSpan(span);
			term.setTid("t" + tid_counter);
			term.setPos(kaf_pos);
			term.setMorphofeat(tags[i]);
			terms.add(term);
			//System.out.println(words[i] + " " + tags[i]);
		}
		saxparser.setTermList(terms);

		String timestamp = getCurrentDate();
		KafMetadata metadata = saxparser.getMetadata();
		metadata.addLayer("terms", "opennlp-it-pos", "1.0", fixedTimestamp?timestamp.replaceAll("[0-9]", "0"):timestamp);

		// KAF WRITER
		saxparser.writeKafToStream(System.out, false);

	}

	private static void printErrorMessage(String error) {
		System.err.println(error);
	}

	public static String getCurrentDate() {
		Calendar c = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR);
		int minutes = c.get(Calendar.MINUTE);
		int seconds = c.get(Calendar.SECOND);
		StringBuffer timestamp_sb = new StringBuffer();
		timestamp_sb.append(year);
		timestamp_sb.append("-");
		if (month < 10)
			timestamp_sb.append("0");
		timestamp_sb.append(month);
		timestamp_sb.append("-");
		if (day < 10)
			timestamp_sb.append("0");
		timestamp_sb.append(day);
		timestamp_sb.append("-");
		timestamp_sb.append("T");
		if (hour < 10)
			timestamp_sb.append("0");
		timestamp_sb.append(hour);
		timestamp_sb.append(":");
		if (minutes < 10)
			timestamp_sb.append("0");
		timestamp_sb.append(minutes);
		timestamp_sb.append(":");
		if (seconds < 10)
			timestamp_sb.append("0");
		timestamp_sb.append(seconds);
		timestamp_sb.append("Z");
		return timestamp_sb.toString();
	}

}
