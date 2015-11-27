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
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(".").getCanonicalPath() + "\\Data\\globalwordfreq.json" ));
		
		//query the db for [Songname, artist] from 'lyrics' pair and store in an array
		String[][] songsnartists = q.songsANDartists();
		
		
		for(int i = 0; i < songsnartists.length; i++){
			String song   = songsnartists[i][0];
			String artist = songsnartists[i][1];
			String lyr = q.getLyrics(song, artist); 
			if(i == 0){
				oneWordFreq.resetLyric(lyr);
			}
			else{
				oneWordFreq.setLyric(lyr);
			}
			if(i % 100 == 0){
				
			}
			else{
				
			}
			oneWordFreq.processLyric();
			System.out.println("done with " + i);
		}
		writer.write(oneWordFreq.toJSon());
		writer.close();

	}

}
