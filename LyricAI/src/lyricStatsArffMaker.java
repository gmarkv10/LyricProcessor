import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class lyricStatsArffMaker {

	private static queries q = new queries();
	private static String pathToDataFolder = "/Data/";
	
	public static void allSongsAllFields() throws ClassNotFoundException, IOException{
		String[][] bestRankData = q.songANDartistANDbestweekANDbestrank();
		lyricStats atributeFinder = new lyricStats("");
		atributeFinder.init();
		String result = "@relation allSongsAllFields\n\n";
		
		for (int k =0;k<atributeFinder.numericalFieldLabels.size();k++){
			result+="@attribute "+atributeFinder.numericalFieldLabels.get(k)+" real"+"\n";
		}
		result+="@attribute SONG_RANK real"+"\n";
		result+="\n";
		
		//data
		result += "@data\n";
		int count = 0;
		for (int i =0;i<bestRankData.length;i++){
			String lyrics = q.getLyrics(bestRankData[i][0], bestRankData[i][1]);
			lyricStats ls = new lyricStats(lyrics);
			ls.init();
			
			for (int j=0;j<ls.numericalFields.size();j++){
				result += ls.numericalFields.get(j) + ",";
			}
			result += bestRankData[i][3]+"\n";
			if (count % 100 == 0){
				System.out.println(count+" / "+bestRankData.length);
			}
			count++;
		}
		//System.out.println(result);
		result = result.substring(0, result.lastIndexOf("\n"));
		writeOutputFile("allSongsAllFields.arff",result);
	}
	
	
	public static void allSongsAllFieldsRankClass() throws ClassNotFoundException, IOException{
		String[][] bestRankData = q.songANDartistANDbestweekANDbestrank();
		lyricStats atributeFinder = new lyricStats("");
		atributeFinder.init();
		String result = "@relation allSongsAllFieldsRankClass\n\n";
		
		for (int k =0;k<atributeFinder.numericalFieldLabels.size();k++){
			result+="@attribute "+atributeFinder.numericalFieldLabels.get(k)+" real"+"\n";
		}
		result+="@attribute SONG_RANK_CLASS {1-10, 11-20, 21-30, 31-40, 41-50}"+"\n";
		result+="\n";
		
		//data
		result += "@data\n";
		int count = 0;
		for (int i =0;i<bestRankData.length;i++){
			String lyrics = q.getLyrics(bestRankData[i][0], bestRankData[i][1]);
			lyricStats ls = new lyricStats(lyrics);
			ls.init();
			
			for (int j=0;j<ls.numericalFields.size();j++){
				result += ls.numericalFields.get(j) + ",";
			}
			result += rankClass(Integer.parseInt(bestRankData[i][3]))+"\n";
			if (count % 100 == 0){
				System.out.println(count+" / "+bestRankData.length);
			}
			count++;
		}
		//System.out.println(result);
		result = result.substring(0, result.lastIndexOf("\n"));
		writeOutputFile("allSongsAllFieldsRankClass.arff",result);
	}
	
	public static void allSongsAllFieldsRankClassWithDate() throws ClassNotFoundException, IOException{
		String[][] bestRankData = q.songANDartistANDbestweekANDbestrank();
		lyricStats atributeFinder = new lyricStats("");
		atributeFinder.init();
		String result = "@relation allSongsAllFieldsRankClassWithDate\n\n";
		
		for (int h =0;h<atributeFinder.booleanFieldLabels.size();h++){
			result+="@attribute "+atributeFinder.booleanFieldLabels.get(h) + " {TRUE, FALSE}\n";
		}
		result +="@attribute WEEK date \"yyyy-MM-dd\"\n";
		for (int k =0;k<atributeFinder.numericalFieldLabels.size();k++){
			result+="@attribute "+atributeFinder.numericalFieldLabels.get(k)+" real"+"\n";
		}
		result+="@attribute SONG_RANK_CLASS {1-10, 11-20, 21-30, 31-40, 41-50}"+"\n";
		result+="\n";
		
		//data
		result += "@data\n";
		int count = 0;
		for (int i =0;i<bestRankData.length;i++){
			String lyrics = q.getLyrics(bestRankData[i][0], bestRankData[i][1]);
			lyricStats ls = new lyricStats(lyrics);
			ls.init();
			
			for (int k =0;k<ls.booleanFields.size();k++){
				result+=ls.booleanFields.get(k)+",";
			}
			result+="\""+bestRankData[i][2]+"\",";
			for (int j=0;j<ls.numericalFields.size();j++){
				result += ls.numericalFields.get(j) + ",";
			}
			result += rankClass(Integer.parseInt(bestRankData[i][3]))+"\n";
			if (count % 100 == 0){
				System.out.println(count+" / "+bestRankData.length);
			}
			count++;
		}
		//System.out.println(result);
		result = result.substring(0, result.lastIndexOf("\n"));
		writeOutputFile("allSongsAllFieldsRankClassWithDate.arff",result);
	}
	public static void allSongsAllFieldsRankClassWithDatePOS() throws ClassNotFoundException, IOException{
		String[][] bestRankData = q.songANDartistANDbestweekANDbestrank();
		lyricStats atributeFinder = new lyricStats("");
		atributeFinder.init();
		String result = "@relation allSongsAllFieldsRankClassWithDatePOS\n\n";
		
		for (int h =0;h<atributeFinder.booleanFieldLabels.size();h++){
			result+="@attribute "+atributeFinder.booleanFieldLabels.get(h) + " {TRUE, FALSE}\n";
		}
		result +="@attribute WEEK date \"yyyy-MM-dd\"\n";
		for (int k =0;k<atributeFinder.numericalFieldLabels.size();k++){
			result+="@attribute "+atributeFinder.numericalFieldLabels.get(k)+" real"+"\n";
		}
		for (int j = 0;j<atributeFinder.posFrequencyLabels.size();j++){
			result+="@attribute "+atributeFinder.posFrequencyLabels.get(j)+" real"+"\n";
		}
		result+="@attribute SONG_RANK_CLASS {1-10, 11-20, 21-30, 31-40, 41-50}"+"\n";
		result+="\n";
		
		//data
		result += "@data\n";
		int count = 0;
		for (int i =0;i<bestRankData.length;i++){
			String lyrics = q.getLyrics(bestRankData[i][0], bestRankData[i][1]);
			lyricStats ls = new lyricStats(lyrics);
			ls.init();
			
			for (int k =0;k<ls.booleanFields.size();k++){
				result+=ls.booleanFields.get(k)+",";
			}
			result+="\""+bestRankData[i][2]+"\",";
			for (int j=0;j<ls.numericalFields.size();j++){
				result += ls.numericalFields.get(j) + ",";
			}
			for (int j = 0;j<ls.posFrequencyFields.size();j++){
				result+="@attribute "+ls.posFrequencyFields.get(j)+" real"+"\n";
			}
			result += rankClass(Integer.parseInt(bestRankData[i][3]))+"\n";
			if (count % 1 == 0){
				System.out.println(count+" / "+bestRankData.length);
			}
			count++;
		}
		//System.out.println(result);
		result = result.substring(0, result.lastIndexOf("\n"));
		writeOutputFile("allSongsAllFieldsRankClassWithDatePOS.arff",result);
	}
	
	
	public static void allSongsAllFieldsRankClassWithDatePOS2() throws ClassNotFoundException, IOException{
		File file = new File("."); 
		String fileName = "allSongsAllFieldsRankClassWithDatePOS.arff";
		PrintWriter pw = new PrintWriter(new File(file.getCanonicalPath() + pathToDataFolder + fileName));
		String[][] bestRankData = q.songANDartistANDbestweekANDbestrank();
		lyricStats atributeFinder = new lyricStats("");
		atributeFinder.init();
		pw.write("@relation allSongsAllFieldsRankClassWithDatePOS\n\n");
		
		for (int h =0;h<atributeFinder.booleanFieldLabels.size();h++){
			pw.write("@attribute "+atributeFinder.booleanFieldLabels.get(h) + " {TRUE, FALSE}\n");
		}
		pw.write("@attribute WEEK date \"yyyy-MM-dd\"\n");
		for (int k =0;k<atributeFinder.numericalFieldLabels.size();k++){
			pw.write("@attribute "+atributeFinder.numericalFieldLabels.get(k)+" real"+"\n");
		}
		for (int j = 0;j<atributeFinder.posFrequencyLabels.size();j++){
			pw.write("@attribute "+atributeFinder.posFrequencyLabels.get(j)+" real"+"\n");
		}
		pw.write("@attribute SONG_RANK_CLASS {1-10, 11-20, 21-30, 31-40, 41-50}"+"\n");
		pw.write("\n");
		
		//data
		pw.write("@data\n");
		int count = 0;
		for (int i =0;i<bestRankData.length;i++){
			String lyrics = q.getLyrics(bestRankData[i][0], bestRankData[i][1]);
			lyricStats ls = new lyricStats(lyrics);
			ls.init();
			
			for (int k =0;k<ls.booleanFields.size();k++){
				pw.write(ls.booleanFields.get(k)+",");
			}
			pw.write("\""+bestRankData[i][2]+"\",");
			for (int j=0;j<ls.numericalFields.size();j++){
				pw.write(ls.numericalFields.get(j) + ",");
			}
			for (int j = 0;j<ls.posFrequencyFields.size();j++){
				pw.write(ls.posFrequencyFields.get(j)+", ");
			}
			pw.write(rankClass(Integer.parseInt(bestRankData[i][3]))+"\n");
			if (count % 1 == 0){
				System.out.println(count+" / "+bestRankData.length);
			}
			count++;
		}
		pw.close();
	}
	
	public static String rankClass(int rank){
		if (rank <= 10){
			return "1-10";
		}
		else if (rank <= 20){
			return "11-20";
		}
		else if (rank <= 30){
			return "21-30";
		}
		else if (rank <= 40){
			return "31-40";
		}
		else {
			return "41-50";
		}
	}
	
	
	
	
	public static void writeOutputFile(String fileName, String fileContents) throws IOException{
		File file = new File("."); 
		PrintWriter pw = new PrintWriter(new File(file.getCanonicalPath() + pathToDataFolder + fileName));
		pw.write(fileContents);
		pw.close();
	}
	
	
	
	
}
