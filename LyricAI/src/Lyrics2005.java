import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Lyrics2005 {

	public static void main(String[] args) throws Exception{
		String path = "thousandEntries.arff";
		File file = new File(".");
		queries q                        = new queries();
		FrequencyProcessor oneWordFreq   = new FrequencyProcessor();
		FrequencyProcessor twoWordFreq   = new FrequencyProcessor(2);
		FrequencyProcessor threeWordFreq = new FrequencyProcessor(3);
		POSProcessor       pos           = new POSProcessor();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(file.getCanonicalPath() + "\\Data\\"+path ));
		
		//query the db for [Songname, artist] from 'lyrics' pair and store in an array
		//String[][] songsnartists = q.songsANDartists();
		
		//combined best week, best rank, and song/artists into 1 query to increase speed
		String[][] songnartistsnweeknrank = q.songANDartistANDbestweekANDbestrank();
		
		//for([Songname, artist] in that object):
		for(int i = 0; i < 1000; i++ ){
			//	song = songname
			//String song = songsnartists[i][0];
		
			// 	art = artist
			//String artist = songsnartists[i][1];
			
			//  week = select [Songname, artist] order anscending, take the first
			//String bestWeek = q.bestWeek(song, artist);
			
			//	rank
			//int bestRank = q.bestRanking(song, artist);
			
			//combined the 3 queries into 1 query to increase speed
			String song = songnartistsnweeknrank[i][0];
			String artist = songnartistsnweeknrank[i][1];
			String bestWeek = songnartistsnweeknrank[i][2];
			int bestRank = Integer.parseInt(songnartistsnweeknrank[i][3]);
			
			
			String rankClass = rankClass(bestRank);
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
			String arffLine = constructARFFLine(song, totalWords, distinctWords, top10words, top10phrases2, top10phrases3, posArr, bestWeek, rankClass);
			writer.write(arffLine);
			writer.newLine();
			System.out.println(song + " " + i + "/1000" );
			//write that line to an aarf file
		}
		writer.close();

	}
	
	private static String constructARFFLine(String song, int numWords,
			int distinctWords, String[] top10_1, String[] top10_2,
			String[] top10_3, int[] POS, String date, String rank
			){
		String arff = "";
		arff += ("\"" + song + "\",");
		
		double words = numWords;
		double distinct = distinctWords;
		double ratio = distinct/words;
		arff += (words + "," + ratio + ",");
		
		int i = 0;
		for(i =  0; i < 10; i++){
			arff += "\"" + top10_1[i] + "\","; //add 1 word phrases
		}
		for(i =  0; i < 10; i++){
			arff += "\"" + top10_2[i].trim() + "\","; //add 2 word phrases
		}
		for(i =  0; i < 10; i++){
			arff += "\"" + top10_3[i].trim() + "\","; //add 3 word phrases
		}
		
		for(i = 0; i < 13; i ++){
			arff += POS[i] + ",";    //add parts of speech in order
		}
		
		arff += "\"" + date + "\"," + rank;
		
		return arff;
	}
	
	private static String rankClass(int rank){
		if(rank <= 10){
			return "top10";
		}
		if(rank <= 20){
			return "top20";
		}
		if(rank <= 30){
			return "top30";
		}
		if(rank <= 40){
			return "top40";
		}
		if(rank <= 50){
			return "top50";
		}
		return "notTop";
		
	}

}
