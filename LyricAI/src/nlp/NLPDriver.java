package nlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class NLPDriver {

	public static void main(String[] args){
		InputStream model = null;
		try {
			File pwd = new File(".");
			String s = pwd.getCanonicalPath()+"\\OpenNLP\\en-pos-maxent.bin";
			model = new FileInputStream(new File(s));

			POSModel nModel = new POSModel(model);
			String[] words = {"one","1","epr","they","the"};
			POSTaggerME tagger = new POSTaggerME(nModel);
			String[]  tags = tagger.tag(words);
			for(String p : tags){
				System.out.println(p);
			}
			

			//modelIn = new FileInputStream("en-pos-maxent.bin");
			//POSModel model = new POSModel(modelIn);
		}
		catch (Exception e) {
			// Model loading failed, handle the error
			e.printStackTrace();
		}

	}

}



