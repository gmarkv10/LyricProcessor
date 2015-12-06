import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;



public class Lyrics3005 {

	public static void main(String[] args) throws Exception{
		
		queries q                        = new queries();
		FrequencyProcessor oneWordFreq   = new FrequencyProcessor();
		FrequencyProcessor twoWordFreq   = new FrequencyProcessor(2);
		FrequencyProcessor threeWordFreq = new FrequencyProcessor(3);
		POSProcessor       pos           = new POSProcessor();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(".").getCanonicalPath() + "\\Data\\POSandNarcScores.csv" ));
		
		//query the db for [Songname, artist] from 'lyrics' pair and store in an array
		String[][] songArtWeek = q.songANDartistANDbestweekANDbestrank();


		//for(int i = 0; i < songsnartists.length; i++){
		for(int i = 0; i < songArtWeek.length; i++){
			String song   = songArtWeek[i][0];
			String artist = songArtWeek[i][1];
			String year = songArtWeek[i][2].substring(0, 4);
			String lyr = q.getLyrics(song, artist);
			pos.resetLyric(lyr);
			pos.processLyric();
			writer.write(createCSVLine(song.replaceAll(",",""), year, pos.getPOSFrqInArffOrder(), pos.simpleNarcScore(), pos.getNarcissismScore()));
			writer.newLine();
			System.out.println("done with " + i);
		}
		
		System.out.println("DONE");
		writer.close();
		
		
		
	}
	
	public static String createCSVLine(String song, String year, int[] pos, double simpleNarc, double complexNarc){
		String ret = song + "," + year + ",";
		for(int i = 0; i < pos.length; i++){
			ret += pos[i] + ",";
		}
		ret += simpleNarc + "," + complexNarc;
		
		return ret;
	}

}
