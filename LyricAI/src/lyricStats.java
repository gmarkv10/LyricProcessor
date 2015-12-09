import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;


import java.util.Scanner;

public class lyricStats {
	public POSProcessor posp;
	
	public String LYRICS;
	public Set<String> UNIQUE_LYRICS;
	public Set<String> SWEAR_WORDS;
	
	public Integer NUM_WORDS;
	public Integer NUM_UNIQUE_WORDS;
	public Integer MAX_WORD_LENGTH;
	public Integer AVG_WORD_LENGTH;
	public String IS_EXPLICIT;
	
	//part of speach counts
	public Integer VERBS;
	public Integer CONJUNCTIONS;
	public Integer ADJECTIVES;
	public Integer PRONOUNS;
	public Integer NUMBERS;
	public Integer NOUNS;
	public Integer TOS;
	public Integer PREPOSITIONS;
	public Integer ADVERBS;
	public Integer INTERJECTIONS;
	public Integer OTHERS;
	public Integer FOREIGNS;
	public Integer INTERROGATIVES;
	
	//relative frequencies
	public float VERBS_FRQ;
	public float CONJUNCTIONS_FRQ;
	public float ADJECTIVES_FRQ;
	public float PRONOUNS_FRQ;
	public float NUMBERS_FRQ;
	public float NOUNS_FRQ;
	public float TOS_FRQ;
	public float PREPOSITIONS_FRQ;
	public float ADVERBS_FRQ;
	public float INTERJECTIONS_FRQ;
	public float OTHERS_FRQ;
	public float FOREIGNS_FRQ;
	public float INTERROGATIVES_FRQ;
	
	
	
	public ArrayList<Integer> numericalFields;
	public ArrayList<String>  numericalFieldLabels;
	public ArrayList<String> booleanFields;
	public ArrayList<String> booleanFieldLabels;
	public ArrayList<Float> posFrequencyFields;
	public ArrayList<String> posFrequencyLabels;
	public lyricStats(String lyrics){
		this.LYRICS = lyrics;
	}
	public void init() throws IOException{
		posp = new POSProcessor();
		calcNumUniqueWords();
		calcWordLength();
		loadSwears();
		checkSwears();
		loadPOS();
		numericalFields = new ArrayList<Integer>();
		numericalFieldLabels = new ArrayList<String>();
		booleanFields = new ArrayList<String>();
		booleanFieldLabels = new ArrayList<String>();
		posFrequencyFields = new ArrayList<Float>();
		posFrequencyLabels = new ArrayList<String>();
		booleanFields.add(IS_EXPLICIT);
		booleanFieldLabels.add("IS_EXPLICIT");
		numericalFields.add(NUM_WORDS);
		numericalFieldLabels.add("NUM_WORDS");
		numericalFields.add(NUM_UNIQUE_WORDS);
		numericalFieldLabels.add("NUM_UNIQUE_WORDS");
		numericalFields.add(MAX_WORD_LENGTH);
		numericalFieldLabels.add("MAX_WORD_LENGTH");
		numericalFields.add(AVG_WORD_LENGTH);
		numericalFieldLabels.add("AVG_WORD_LENGTH");
			
		posFrequencyFields.add(VERBS_FRQ);
		posFrequencyLabels.add("VERBS_FRQ");
		posFrequencyFields.add(CONJUNCTIONS_FRQ);
		posFrequencyLabels.add("CONJUNCTIONS_FRQ");
		posFrequencyFields.add(ADJECTIVES_FRQ);
		posFrequencyLabels.add("ADJECTIVES_FRQ");
		posFrequencyFields.add(PRONOUNS_FRQ);
		posFrequencyLabels.add("PRONOUNS_FRQ");
		posFrequencyFields.add(NUMBERS_FRQ);
		posFrequencyLabels.add("NUMBERS_FRQ");
		posFrequencyFields.add(NOUNS_FRQ);
		posFrequencyLabels.add("NOUNS_FRQ");
		posFrequencyFields.add(TOS_FRQ);
		posFrequencyLabels.add("TOS_FRQ");
		posFrequencyFields.add(PREPOSITIONS_FRQ);
		posFrequencyLabels.add("PREPOSITIONS_FRQ");
		posFrequencyFields.add(ADVERBS_FRQ);
		posFrequencyLabels.add("ADVERBS_FRQ");
		posFrequencyFields.add(INTERJECTIONS_FRQ);
		posFrequencyLabels.add("INTERJECTIONS_FRQ");
		posFrequencyFields.add(OTHERS_FRQ);
		posFrequencyLabels.add("OTHERS_FRQ");
		posFrequencyFields.add(FOREIGNS_FRQ);
		posFrequencyLabels.add("FOREIGNS_FRQ");
		posFrequencyFields.add(INTERROGATIVES_FRQ);
		posFrequencyLabels.add("INTERROGATIVES_FRQ");
		
	}
	private void loadPOS(){
		posp.resetLyric(LYRICS);
		posp.processLyric();
		int[] posCount = posp.getPOSFrqInArffOrder();
	    VERBS = posCount[0];
		CONJUNCTIONS = posCount[1];
		ADJECTIVES = posCount[2];
		PRONOUNS = posCount[3];
		NUMBERS = posCount[4];
	    NOUNS = posCount[5];
		TOS = posCount[6];
		PREPOSITIONS = posCount[7];
		ADVERBS = posCount[8];
		INTERJECTIONS = posCount[9];
		OTHERS = posCount[10];
		FOREIGNS = posCount[11];
		INTERROGATIVES = posCount[12];
		
		VERBS_FRQ = 100.0f*((float)VERBS)/((float)NUM_WORDS);
		CONJUNCTIONS_FRQ = 100.0f*((float)CONJUNCTIONS)/((float)NUM_WORDS);
		ADJECTIVES_FRQ = 100.0f*((float)ADJECTIVES)/((float)NUM_WORDS);
		PRONOUNS_FRQ = 100.0f*((float)PRONOUNS)/((float)NUM_WORDS);
		NUMBERS_FRQ = 100.0f*((float)NUMBERS)/((float)NUM_WORDS);
		NOUNS_FRQ = 100.0f*((float)NOUNS)/((float)NUM_WORDS);
		TOS_FRQ = 100.0f*((float)TOS)/((float)NUM_WORDS);
		PREPOSITIONS_FRQ = 100.0f*((float)PREPOSITIONS)/((float)NUM_WORDS);
		ADVERBS_FRQ = 100.0f*((float)ADVERBS)/((float)NUM_WORDS);
		INTERJECTIONS_FRQ = 100.0f*((float)INTERJECTIONS)/((float)NUM_WORDS);
		OTHERS_FRQ = 100.0f*((float)OTHERS)/((float)NUM_WORDS);
		FOREIGNS_FRQ = 100.0f*((float)FOREIGNS)/((float)NUM_WORDS);
		INTERROGATIVES_FRQ = 100.0f*((float)INTERROGATIVES)/((float)NUM_WORDS);
		
		
		
		/*Iterator<Entry<String, Integer>> it = posp.posMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)it.next();
	        System.out.println(pair.getKey()+" : "+pair.getValue().intValue());
	    }*/
	}
	private void loadSwears() throws IOException{
		SWEAR_WORDS = new HashSet<String>();
		File file = new File(".");
		File f = new File(file.getCanonicalPath()+"/Data/swearWords.txt");
		Scanner s = new Scanner(f);
		while (s.hasNextLine()){
			String curSwear = s.nextLine().trim();
			curSwear = curSwear.replaceAll("[^A-Za-z0-9 ]", "");
			SWEAR_WORDS.add(curSwear);
		}
	}
	private void checkSwears(){
		IS_EXPLICIT = "FALSE";
		for (String word : UNIQUE_LYRICS){
			if (SWEAR_WORDS.contains(word)){
				IS_EXPLICIT = "TRUE";
			//	System.out.println("song contains: "+word);
				break;
			}
		}
		
	}
	//max/avg word length
	private void calcWordLength(){
		int max = 0;
		int total = 0;
		String[] words = LYRICS.split(" ");
		for (String word : words){
			total += word.length();
			if (word.length() > max){
				max = word.length();
			}
		}
		if (words.length > 0){
			AVG_WORD_LENGTH = total / words.length;
		}
		else{
			AVG_WORD_LENGTH = 0;
		}
		MAX_WORD_LENGTH = max;
	}
	
	
	private void calcNumUniqueWords(){
		Set<String> uniques = new HashSet<String>();
		//removes non-alphanumeric characters, judgement call
		String mod_lyrics = LYRICS.replaceAll("[^A-Za-z0-9 ]", "");
		String[] words = mod_lyrics.split(" ");
		NUM_WORDS = words.length;
		for (String word : words){
			word = word.toLowerCase();
			uniques.add(word);
		}
		UNIQUE_LYRICS = uniques;
		NUM_UNIQUE_WORDS = UNIQUE_LYRICS.size();
	}
	
	private String uniquesToString(){
		String result = "";
		for (String word : UNIQUE_LYRICS){
			result += word+" ";
		}
		return result;
	}
	public String toString(){
		String result = "";
		result+= "LYRICS = "+LYRICS+"\n";
		result+= "UNIQUE_LYRICS = "+uniquesToString()+"\n";
		for (int i=0;i<booleanFields.size();i++){
			result+=booleanFieldLabels.get(i)+" = ";
			result+=booleanFields.get(i)+", ";
		}
		for (int i=0;i<numericalFields.size();i++){
			result+= numericalFieldLabels.get(i) + " = ";
			result+= numericalFields.get(i)+ ", ";
		}
		result +="\n";
		for (int i=0;i<posFrequencyFields.size();i++){
			result+=posFrequencyLabels.get(i)+" = ";
			result+=posFrequencyFields.get(i)+", ";
		}
		return result;
	}
}
