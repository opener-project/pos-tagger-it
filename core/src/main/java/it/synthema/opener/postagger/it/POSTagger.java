package it.synthema.opener.postagger.it;

import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class POSTagger {
	
	//lemmatization file name   lemma_pos.dict
	private static final String POSTAGGING_MODEL_PATH = "/it-pos-perceptron.bin";
	private static POSModel posModel;
	private POSTaggerME postagger;

	/**
	 * The PosTagger class uses an internal instance of OpenNLP POSTaggerME. The
	 * model is only loaded once, when the first instance is created, and is
	 * shared among all instances.
	 * NOTE: POSTaggerME is not thread safe, so (unlike the model) each instance must have its own variable.
	 */
	public POSTagger() {
		try {
			//Load pos model only if it has not been loaded before
			if (posModel == null) {
				InputStream modelIn = getClass()
						.getResourceAsStream(POSTAGGING_MODEL_PATH);
				posModel = new POSModel(modelIn);
				modelIn.close();
			}
			//set the instance for the tagging tool
			postagger = new POSTaggerME(posModel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String[] tag(String[] words) {
		String[] tags = postagger.tag(words);
		return tags;
	}

}
