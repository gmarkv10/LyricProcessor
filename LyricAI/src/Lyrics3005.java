import java.io.BufferedWriter;

public class Lyrics3005 {

	public static void main(String[] args) throws Exception{
		
		queries q                        = new queries();
		FrequencyProcessor oneWordFreq   = new FrequencyProcessor();
		FrequencyProcessor twoWordFreq   = new FrequencyProcessor(2);
		FrequencyProcessor threeWordFreq = new FrequencyProcessor(3);
		POSProcessor       pos           = new POSProcessor();
		
		BufferedWriter writer;
		
		//query the db for [Songname, artist] from 'lyrics' pair and store in an array
		String[][] songsnartists = q.songsANDartists();
		
		System.out.println(songsnartists.length);
		/*for(int i = 0; i < songsnartists.length; i++){
			
		}*/

	}

}
