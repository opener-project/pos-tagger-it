package it.synthema.opener.postagger.it;

import java.util.ArrayList;
import java.util.List;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.TimeZone;
import java.util.Date;

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
    boolean fixedTimestamp = false;
		for(String arg:args){
			if(arg.equalsIgnoreCase("-t")){
				fixedTimestamp=true;
			}
		}

    CLI cli = new CLI();
    cli.process(System.in, System.out, fixedTimestamp);
  }

  public void process(){
    process(System.in, System.out, false);
  }

  public void process(InputStream inputStream, OutputStream outputStream){
    process(inputStream, outputStream, false);
  }

  public String processToString(InputStream inputStream){
    return processToString(inputStream, false);
  }

  public String processToString(InputStream inputStream, Boolean fixedTimestamp){
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    process(inputStream, baos, fixedTimestamp);
    return baos.toString();
  }

  public void process(InputStream inputStream, OutputStream outputStream, Boolean fixedTimestamp){
		KafSaxParser saxparser = new KafSaxParser();
		saxparser.setXMLValidate(false);


		// parse file
		saxparser.parseFile(inputStream);

		// wordform
		// System.out.println("Read wordforms...");
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

		KafMetadata metadata = saxparser.getMetadata();

    String timestamp;

    if(fixedTimestamp == false){
      timestamp = getTimestamp();
    }else{
      timestamp = "0000-00-00T00:00Z";
    }
		metadata.addLayer("terms", "opennlp-it-pos", "1.0", timestamp);
		// KAF WRITER
		saxparser.writeKafToStream(outputStream, false);
	}

	private void printErrorMessage(String error) {
		System.err.println(error);
	}

  private String getTimestamp(){
    TimeZone tz = TimeZone.getTimeZone("UTC");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
    df.setTimeZone(tz);
    String nowAsISO = df.format(new Date());
    return nowAsISO;
  }
}
