import java.io.File;
import java.io.IOException;

public class LyricAIDriver {

	public static void main(String[] args) {
		// TODO Get There
		String s = "You used to call me on my, you used to, you used to You used to call me on my cell phone Late night when you need my love";
		
		
		POSProcessor fp = new POSProcessor();
		fp.resetLyric(s);
		fp.processLyric();
		System.out.println(fp.toJSON());
//		fp.processLine();
//		fp.processLine();
//		fp.processLine();
//		System.out.println(fp.frqMap.toJSON());
//		String[] top10 = fp.getTop10();
//		for(int i = 0; i < 10; i++){
//			System.out.println(top10[i]);
//		}
//		

		
		
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


