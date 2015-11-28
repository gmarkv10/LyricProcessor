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
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(".").getCanonicalPath() + "\\Data\\globalwordfreq.merge.json" ));
		
		//query the db for [Songname, artist] from 'lyrics' pair and store in an array
		String[][] songsnartists = q.songsANDartists();
		MyMap[] maps = new MyMap[76];
		int mapsIdx = 0;

		for(int i = 0; i < songsnartists.length; i++){
			String song   = songsnartists[i][0];
			String artist = songsnartists[i][1];
			String lyr = q.getLyrics(song, artist);
			if(i == 0){
				oneWordFreq.resetLyric(lyr);
				oneWordFreq.processLyric();
			}
			else{
				if(i % 100 == 0){
					maps[mapsIdx++] = oneWordFreq.getMap();
					oneWordFreq.resetLyric(lyr);

				}
				else{
					oneWordFreq.processLyric();
				}

			}
			System.out.println("done with " + i);
		}
		MyMap totalFreqs = MyMap.merge(maps);
		writer.write(totalFreqs.toJSON());
		writer.close();

	}

}
