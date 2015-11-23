import java.io.BufferedWriter;

public class Lyrics2005 {

	public static void main(String[] args) throws Exception{
		
		queries q                        = new queries();
		FrequencyProcessor oneWordFreq   = new FrequencyProcessor();
		FrequencyProcessor twoWordFreq   = new FrequencyProcessor(2);
		FrequencyProcessor threeWordFreq = new FrequencyProcessor(3);
		POSProcessor       pos           = new POSProcessor();
		
		BufferedWriter writer;
		
		//query the db for [Songname, artist] from 'lyrics' pair and store in an array
		String[][] songsnartists = q.songsANDartists();
		
		//for([Songname, artist] in that object):
		for(int i = 300; i < 305; i++ ){
			//	song = songname
			String song = songsnartists[i][0];
			// 	art = artist
			String artist = songsnartists[i][1];
			//  week = select [Songname, artist] order anscending, take the first
			String bestWeek = q.bestWeek(song, artist);
			//	rank
			int bestRank = q.bestRanking(song, artist);
			//	lyric = query the db by [Songname, artist] for lyrics store in a string
			String lyrics =  q.getLyrics(song, artist);
			oneWordFreq.resetLyric(lyrics);   
			int totalWords = oneWordFreq.processLyric();
			int distinctWords = oneWordFreq.frqMap.size();
			twoWordFreq.resetLyric(lyrics);   twoWordFreq.processLyric();
			threeWordFreq.resetLyric(lyrics); threeWordFreq.processLyric();
			//  top10[] = frequencyProcessor.read(lyric);
			String[] top10words = oneWordFreq.getTop10();
			String[] top10phrases2 = twoWordFreq.getTop10();
			String[] top10phrases3 = threeWordFreq.getTop10();
			//    ... and so on for 2 and 3
	
			//  speech[] = POSProcesor.read(lyric)
			pos.resetLyric(lyrics);
			pos.processLyric();
			//	rank = select [Songname, artist] order anscending, take the first
			
			//contruct a line for an aarf file
			//write that line to an aarf file
		}
		  
          
          
          //int bestRank = q.bestRanking("Your Man", "Josh Turner");
          //String bestWeek = q.bestWeek("Your Man", "Josh Turner");
          //String lyrics =  q.getLyrics("Your Man", "Josh Turner");

	}

}
