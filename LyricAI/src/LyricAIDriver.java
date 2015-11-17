import java.io.File;
import java.io.IOException;

public class LyricAIDriver {

	public static void main(String[] args) {
		// TODO Get There
		
	
		
		
		
		POSProcessor posp = new POSProcessor("hello");
		posp.processLines();
		System.out.println(posp.toJSON());
		System.out.println(posp.leftovers);
		
		
		
		
		
		
		
		
		
		/*	FrequencyProcessor fp;
		try {
			File lyrdir = new File((new File(".").getCanonicalPath()) + "\\Lyrics");
			String[] lyrfiles = lyrdir.list();
			for(int  i = 0; i < lyrfiles.length; i++){
				System.out.println("PROCESSING: " + lyrfiles[i]);
				for(int phraseLength = 1; phraseLength <= 3; phraseLength++){
					if(phraseLength == 1){
						fp = new FrequencyProcessor(lyrfiles[i]);
					}
					else{
						fp = new FrequencyProcessor(lyrfiles[i], phraseLength);
					}
					fp.processLines();
					fp.exportJSON();
					fp.close();
					fp = null;
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//FrequencyProcessor fp = new FrequencyProcessor("hello");
		//fp.processLines();
		
		//System.out.println(fp.frqMap.toJSON(1));
		
		//fp.exportJSON();
	
/*		MapSetList m = new MapSetList<String>();
		
		m.insert("One");
		m.insert("Two");
		m.insert("Three");
		m.insert("Four");
		
		m.insertAt(3, "Five");
		m.print();
	*/

	}

}


