import java.io.File;
import java.io.IOException;

public class LyricAIDriver {

	public static void main(String[] args) {
		// TODO Get There
		
		MapSetList<Integer> m = new MapSetList<Integer>();
		m.insert(1);
		m.insert(2);
		m.insert(5);
		m.insert(0);
		m.insert(3);
		m.insert(1);
		m.print();
		
//		FrequencyProcessor fp = new FrequencyProcessor("hotline", 3);
//		fp.processLine();
//		fp.processLine();
//		System.out.println(fp.frqMap.toJSON());
		

		
		
		/*int[] x = new int[]{1,2};
		
		Integer a = x[0];
		Integer b = x[1];
		
		System.out.println(	m.comp.compare(a,b));*/
		
		
		
		/*	LyricProcessor posp = new POSProcessor("hello");
		String s = "hello,\n";
		System.out.println(s);
		s = posp.processWord(s);
		System.out.println(s);
		*/
		
		
		//posp.processLines();
		//System.out.println(posp.toJSON());
		//System.out.println(posp.leftovers);
		
		
		
		
		
		
		
		
		
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
	

	}

}


