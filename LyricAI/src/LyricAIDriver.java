

public class LyricAIDriver {

	public static void main(String[] args) {
		// TODO Get There
		
		String d = "1999-07-24";
		String s = d.substring(0, 3) + "0s";
		System.out.println(getYearClass(d));
		
		
/*		String s = "You used to call me on my, you used to, you used to";
		String s1 = "You used to call me on my cell phone Late night when you need my love";
		String s3 = "You used to call me on my, you used to, you used to You used to call me on my cell phone Late night when you need my love";
		
		FrequencyProcessor fp = new FrequencyProcessor();
		FrequencyProcessor fp1 = new FrequencyProcessor();
		FrequencyProcessor fp3 = new FrequencyProcessor();
		fp.resetLyric(s);
		fp1.resetLyric(s1);
		fp3.resetLyric(s3);
		fp.processLyric();
		fp1.processLyric();
		fp3.processLyric();
		
		MyMap[] a = {fp.getMap(), null, fp1.getMap() };
		System.out.println(MyMap.merge(a).toJSON());
		System.out.println(fp3.toJSon());*/
		
		
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
	
	private static String getYearClass(String week){
		String ret;
		String decade = week.substring(0, 3) + "0s"; //get the decade
		int decYear = Integer.parseInt(week.substring(3, 4)); //get the year in the decade
		if(decYear >= 0 && decYear < 4 ){
			ret = "early";
		}
		else if( decYear >= 4 && decYear < 7){
			ret = "mid";
		}
		else{
			ret = "late";
		}
		
		return ret + decade;
	}

}


