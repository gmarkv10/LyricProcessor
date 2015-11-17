import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class POSProcessor extends LyricProcessor {
	private String filename;
	private File   file;
	private BufferedReader reader;
	private BufferedWriter writer;
	MyMap<String, Integer> posMap;
	String word = null;
	
	//NLP Objects
	InputStream model;
	POSModel nModel;
	POSTaggerME tagger;
	String leftovers = "";
	
	public POSProcessor(String f){
		file = new File(".");
		this.filename = f; 
		try {
			//System.out.println(new File(file.getCanonicalPath() + "\\Lyrics\\" + f + ".lyr").exists());
			String net = file.getCanonicalPath()+"\\OpenNLP\\en-pos-maxent.bin";
			posMap = new MyMap();
			model = new FileInputStream(new File(net));
			nModel = new POSModel(model);
			String[] words = {"one","1","epr","they","the"};
			tagger = new POSTaggerME(nModel);
			reader = new BufferedReader(new FileReader(file.getCanonicalPath() + "\\Lyrics\\" + f + ".lyr"));
		} catch(IOException e){
			System.err.println("unable to process file ");
		}
	}
	
	enum SPEECH { VERB, CONJUNCTION,ADJECTIVE, PRONOUN , NUMBER, NOUN, 
		           TO, PREPOSITION, ADVERB, INTERJECTION, OTHER, FOREIGN,
		           INTERROGATIVE
		          }
	
	public String getSpeech(){
		System.out.println(SPEECH.ADJECTIVE.toString());
		return SPEECH.ADJECTIVE.toString();
	}
	
	public String readWords(){
		try {
			return reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("EXCEPTION while reading file");
			return "";
		}
	}
	

	
	public boolean processLine(){
	//	return false; //if EOF
		String line = readWords();
		if(line == null) return false;
		else{
			String[] words = line.split(" ");
			String[] posTags = tagger.tag(words); //give raw tags from https://javaextreme.wordpress.com/category/java-part-of-speech-tagging/
			String tag;
			for(int i = 0; i < posTags.length; i++){
				if(posTags[i].equals("WRB")){
					System.out.println("A WRB is: " + words[i]);
				}
				
				tag = this.simplifyPOS(posTags[i]).toString();
				Integer freq = posMap.get(tag);
				posMap.put(tag, (freq == null) ? 1 : freq + 1);
			}
			return true;
		}
		
	}
	
	public String toJSON(){
		return posMap.toJSON();
	}
	

	
	public SPEECH simplifyPOS(String s){
		switch(s){
		case "CC":
			return SPEECH.CONJUNCTION;
		case "CD":
			return SPEECH.NUMBER;
		case "DT":
			return SPEECH.OTHER;
		case "EX":
			return SPEECH.OTHER;
		case "FW":
			return SPEECH.FOREIGN;
		case "IN":
			return SPEECH.PREPOSITION;
		case "JJ":
			return SPEECH.ADJECTIVE;
		case "JJR":
			return SPEECH.ADJECTIVE;
		case "JJS":
			return SPEECH.ADJECTIVE;
		case "LS":
			return SPEECH.OTHER;
		case "MD":
			return SPEECH.OTHER;
		case "NN":
			return SPEECH.NOUN;
		case "NNP":
			return SPEECH.NOUN;
		case "NNS":
			return SPEECH.NOUN;
		case "NNPS":
			return SPEECH.NOUN;
		case "PDT": 
			return SPEECH.OTHER;
		case "POS":
			return SPEECH.OTHER;
		case "PRP":
			return SPEECH.PRONOUN;
		case "PRP$":
			return SPEECH.PRONOUN;
				
		case "RB":
			return SPEECH.ADVERB;
		case "RBR":
			return SPEECH.ADVERB;
		case "RBS":
			return SPEECH.ADVERB;
		case "RP":
			return SPEECH.OTHER;
		case "SYM":
			return SPEECH.OTHER;
		case "TO":
			return SPEECH.TO;
		case "UH":
			return SPEECH.INTERJECTION;
		case "VB":
			return SPEECH.VERB;
		case "VBD":
			return SPEECH.VERB;
		case "VBG":
			return SPEECH.VERB;
		case "VBN":
			return SPEECH.VERB;
		case "VBP":
			return SPEECH.VERB;
		case "VBZ":
			return SPEECH.VERB;
		case "WRB":
			return SPEECH.INTERROGATIVE;
		case "WDT":
			return SPEECH.INTERROGATIVE;
		case "WP":
			return SPEECH.INTERROGATIVE;
		case "WP$":
			return SPEECH.INTERROGATIVE;
		default:
			leftovers += s + " ";
			return SPEECH.OTHER;
		}

	}
	
	String getLeftovers(){
		return leftovers;
	}

	@Override
	public String getCurrentPath() {
		// TODO Auto-generated method stub
		try {
			return file.getCanonicalPath() + "\\Lyrics\\" + filename + ".lyr";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

}
