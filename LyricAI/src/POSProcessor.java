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
	MyMap<String, Integer> posMap;
	String lyric = null;
	
	//Narcissism scores
	private int fSing = 0; //first person singular
	private int sSing = 0; //second person singular
	private int tSing = 0; //third person singular
	private int fPlur = 0;
	//private int sPlur = 0; //not used
	private int tPlur = 0;
	int totalPronouns = 0;
	
	//NLP Objects
	InputStream model;
	POSModel nModel;
	POSTaggerME tagger;
	String leftovers = "";
	
	public POSProcessor(){
		file = new File("."); 
		try {
			//System.out.println(new File(file.getCanonicalPath() + "\\Lyrics\\" + f + ".lyr").exists());
			//String net = file.getCanonicalPath()+"\\OpenNLP\\en-pos-maxent.bin"; //gabe's path
			String net = file.getCanonicalPath()+"/OpenNLP/en-pos-maxent.bin"; //tony path
			posMap = new MyMap();
			model = new FileInputStream(new File(net));
			nModel = new POSModel(model);
			tagger = new POSTaggerME(nModel);
		} catch(IOException e){
			System.err.println("unable to process file ");
		}
	}
	
	static enum SPEECH { VERB, CONJUNCTION,ADJECTIVE, PRONOUN , NUMBER, NOUN, 
		           TO, PREPOSITION, ADVERB, INTERJECTION, OTHER, FOREIGN,
		           INTERROGATIVE
		          }
	
	

	@Override 
	public void resetLyric(String l){
		lyric = l;
		posMap = new MyMap<String, Integer>();
		resetNarcScore();
	}
	
	@Override
	public int processLyric(){
	//	return false; //if EOF
		if(lyric == null) return -1;
		else{
			String[] words = lyric.split(" ");
			for(String s: words){
				s = processWord(s); //take away those commas, periods, newlines, etc 
			}
			String[] posTags = tagger.tag(words); //give raw tags from https://javaextreme.wordpress.com/category/java-part-of-speech-tagging/
			String tag;
			for(int i = 0; i < posTags.length; i++){
				tag = this.simplifyPOS(posTags[i]).toString();
				if(tag.equals("PRONOUN")){
					classifyPronoun(words[i]); //update narcissism score
				}
				Integer freq = posMap.get(tag);
				posMap.put(tag, (freq == null) ? 1 : freq + 1);
			}
			return words.length;
		}
		
	}
	
	public int[] getPOSFrqInArffOrder(){
		int[] ret = new int[13];
		//see arff header.txt in repo for why the go in this order
		ret[0] = getPOSinMap(SPEECH.VERB);
		ret[1] = getPOSinMap(SPEECH.CONJUNCTION);
		ret[2] = getPOSinMap(SPEECH.ADJECTIVE);
		ret[3] = getPOSinMap(SPEECH.PRONOUN);
		ret[4] = getPOSinMap(SPEECH.NUMBER);
		ret[5] = getPOSinMap(SPEECH.NOUN);
		ret[6] = getPOSinMap(SPEECH.TO);
		ret[7] = getPOSinMap(SPEECH.PREPOSITION);
		ret[8] = getPOSinMap(SPEECH.ADVERB);
		ret[9] = getPOSinMap(SPEECH.INTERJECTION);
		ret[10] = getPOSinMap(SPEECH.OTHER);
		ret[11] = getPOSinMap(SPEECH.FOREIGN);
		ret[12] = getPOSinMap(SPEECH.INTERROGATIVE);
		return ret;
	}
	
	public int getPOSinMap(SPEECH pos){
		try{
			return posMap.get(pos.toString());
		} catch(NullPointerException e){
			return 0;
		}
	}
	
	public double getNarcissismScore(){
		double spread = fSing - fPlur; //straight up, is it narcissistic or communal?
		System.out.print("SPREAD: " + spread);
		double scale = Math.max(sSing, tPlur); //these two lines
		scale = Math.max(scale, tSing);        //find the max of the other pronouns
		scale = scale/totalPronouns;           //then this one scales it by the total number of pronouns used
		
		if(spread > 0){ //we are leaning towards being narc
			return spread * (1- scale); //so we scale back if theres a mix of pron's
		}
		else{
			return spread * (1 + scale); //otherwise were communal anyway so we scale up if there was a good mix
		}
	}
	
	public void classifyPronoun(String s){
		totalPronouns++;
		if(s.equals("i") || s.equals("i'm") || s.equals("me") || s.equals("my") || s.equals("mine") || s.equals("myself")) {
			fSing++; return;
		}
		if(s.equals("you") ||s.equals("you're") ||s.equals("yours") ||s.equals("yourself") ||s.equals("yours") || s.equals("yourselves")){
			sSing++; return; //well include yourselves even though its plural, judgement call
		}
		if(s.equals("we") ||s.equals("us") ||s.equals("our") ||s.equals("ours") || s.equals("ourselves")){
			fPlur++; return;
		}
		if(s.equals("they") ||s.equals("they're") ||s.equals("them") ||s.equals("their") ||s.equals("theirs") ||s.equals("themselves")){
			tPlur++; return;
		}
		tSing++;
		
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
	
	private void resetNarcScore(){
		fSing = 0; 
		sSing = 0; 
		tSing = 0; 
		fPlur = 0;
		tPlur = 0;
		totalPronouns = 0;
		
	}
	
	String getLeftovers(){
		return leftovers;
	}



}
