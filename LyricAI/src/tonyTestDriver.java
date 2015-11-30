import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class tonyTestDriver {

	
	public static void main(String[] args) throws ClassNotFoundException, IOException{
		queries q = new queries();
		timePrediction tp = new timePrediction();
		tp.init();
		//String[][] alldata = q.songANDartistANDbestweekANDbestrank();
		/*String lyrics = q.getLyrics("Hotline Bling", "Drake");
		System.out.println(lyrics);
		Set<String> uni = tp.findUniqueWords(lyrics);
		String result = "";
		for (String word : uni){
			result+= word+" ";
		}
		System.out.println(result);
		
		
		ArrayList<String> weeks = q.allWeeks("Hotline Bling", "Drake");
		for (int i=0;i<weeks.size();i++){
			System.out.println(weeks.get(i));
		}
		
		//tp.populateMap();
		
		ArrayList<String> sampleWeeks = new ArrayList<String>();
		sampleWeeks.add("2015-22-1");
		sampleWeeks.add("2015-22-1");
		sampleWeeks.add("2015-22-1");
		sampleWeeks.add("2015-0-1");
		sampleWeeks.add("2015-22-1");
		sampleWeeks.add("2015-32-1");
		sampleWeeks.add("2015-22-1");
		sampleWeeks.add("2015-32-1");
		
		tp.convertWeekList(sampleWeeks);*/
		
		//tp.writeDataFile();
		//tp.loadData();
		String weekdat = "1983-20-10";
		//System.out.println(weekdat.substring(0,4));
		
		//int year = tp.predictYear(q.getLyrics("Hotline Bling", "Drake"));
		//System.out.println(year);
		//tp.testAllData();
		
		/*HashMap<String, Integer> distWeeks = q.everyWeek();
		System.out.println(distWeeks.size());*/
		
	
		String testLyrics = "";
		File f = new File("Lyrics\\hello.lyr");
		System.err.println(f.exists());
		Scanner sc = new Scanner(f);
		while (sc.hasNextLine()){
			testLyrics+=sc.nextLine()+" ";
		}
		sc.close();
		System.out.println("predicting testLyrics are from year: "+tp.predictYear(testLyrics));
		tp.testAllData();
	}
	
	
}
