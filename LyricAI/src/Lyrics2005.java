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
		for(int i = 300; i < 301; i++ ){
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
			int[] posArr = pos.getPOSFrqInArffOrder();
			
			//contruct a line for an aarf file
			String arffLine = constructARFFLine(song, totalWords, distinctWords, top10words, top10phrases2, top10phrases3, posArr, bestWeek, bestRank);
			System.out.println(arffLine);
			//write that line to an aarf file
		}
		  
          
          
          //int bestRank = q.bestRanking("Your Man", "Josh Turner");
          //String bestWeek = q.bestWeek("Your Man", "Josh Turner");
//          String lyrics =  q.getLyrics("Your Man", "Josh Turner");
//          oneWordFreq.resetLyric(lyrics);
//          int totalWords = oneWordFreq.processLyric();
//          double total = totalWords;
//          int distinctWords = oneWordFreq.frqMap.size();
//          double distinct = distinctWords;
//          double ratio = distinct/total;
//          System.out.println(totalWords + ":" + distinctWords + ":" + ratio);

	}
	
	private static String constructARFFLine(String song, int numWords,
			int distinctWords, String[] top10_1, String[] top10_2,
			String[] top10_3, int[] POS, String date, int rank
			){
		String arff = "";
		arff += ("\'" + song + "',");
		
		double words = numWords;
		double distinct = distinctWords;
		double ratio = distinct/words;
		arff += (words + "," + ratio + ",");
		
		int i = 0;
		for(i =  0; i < 10; i++){
			arff += "\'" + top10_1[i] + "',"; //add 1 word phrases
		}
		for(i =  0; i < 10; i++){
			arff += "\'" + top10_2[i].trim() + "',"; //add 2 word phrases
		}
		for(i =  0; i < 10; i++){
			arff += "\'" + top10_3[i].trim() + "',"; //add 3 word phrases
		}
		
		for(i = 0; i < 13; i ++){
			arff += POS[i] + ",";    //add parts of speech in order
		}
		
		arff += "\"" + date + "\"," + rank + "\n";
		
		return arff;
	}

}
