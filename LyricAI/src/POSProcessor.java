
public class POSProcessor extends LyricProcessor {
	
	enum SPEECH { VERB, CONJUNCTION,ADJECTIVE, PRONOUN , NUMBER, NOUN, 
		           TO, PREPOSITION, ADVERB, INTERJECTION, OTHER, FOREIGN
		          }
	
	

	@Override
	public String getCurrentPath() {
		// TODO Auto-generated method stub
		return null;
	}
	public SPEECH getSpeech(){
		System.out.println(SPEECH.ADJECTIVE);
		return SPEECH.ADJECTIVE;
	}
	

	
	public boolean processLine(){
		return false; //if EOF
//		String line = readWords();
//		if(line == null) return false;
//		else{
//			String[] words = line.split(" ");
//			String word;
//			String phrase; //for if we're using a CircList
//			for(int i = 0; i < words.length; i++){
//				word = FrequencyProcessor.processWord(words[i]);
//				if(window > 1){ //then we're using a CircList
//					cList.insert(word);
//					phrase = cList.getPhrase();
//					Integer freq = frqMap.get(phrase);
//					frqMap.put(phrase, (freq == null) ? 1 : freq + 1);
//				}
//				else{
//					Integer freq = frqMap.get(word);
//					frqMap.put(word, (freq == null) ? 1 : freq + 1);
//				}
//			}
//			return true;
//		}
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
		case "NNPS":
			return SPEECH.NOUN;
		case "PDT": 
			return SPEECH.OTHER;
		case "POS":
			return SPEECH.OTHER;
		case "PRP":
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
		default:
			return SPEECH.OTHER;
		}

	}


}
