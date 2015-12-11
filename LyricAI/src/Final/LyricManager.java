package Final;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import Final.queries;

public class LyricManager {
	int folds = -1;
	BufferedWriter writer;
	
	//databaseQueries will let us perform the queries on our database, specifically to initialize songsAndArtists
	//songsAndArtists stores all 7801 song, artist pairs in a multidimensional array from queries.songsANDartists()
	//lyricWeeks is a helper structure to store lyric data, function described in lyricWeeksPopulate()
	//processedLyricWeeks is the structure that stores the lyric and weekCount objects, which is created with lyricWeeks in populateProcessedWeeklyCount()
	public final String[][] songsAndArtists;
	public final queries databaseQueries;
	public final Helpers helperFunctions;
	public HashMap<String, ArrayList<String>> lyricWeeks = new HashMap<String, ArrayList<String>>();
	public HashMap<String, ArrayList<weekCount>> processedLyricWeeks = new HashMap<String, ArrayList<weekCount>>();
	
	public LyricManager(int folds) throws ClassNotFoundException{
		helperFunctions = new Helpers();
		databaseQueries = new queries();
		songsAndArtists = databaseQueries.songsANDartists();
	}
	public void makeFoldFile(int fold) throws Exception{
		writer = new BufferedWriter(new FileWriter("Data/trainingData"+fold+".csv"));
	}
	
	public void makeFoldFiles(int folds) throws Exception{ //replaces writeDataFile
		for(int i = 0; i < folds; i++){
			makeFoldFile(i);
		}
		
	}
	/* lyricWeeksPopulate() is the first step that in getting lyric data from the DB by populating the lyricWeeks hashMap
	 * lyricWeeks is a intermediate helper structure that will read in lyrics from every one of our 7801 songs, 
	 * lyricWeeks Keys are all of the unique lyrics that appear in all of our songs 
	 * lyricWeeks Value for each Key is an ArrayList containing all weeks that a song containing the unique lyric was in the top 50
	 * This implies that each Value may contain duplicate weeks, meaning that the respective Key word occured in multiple songs for that week.
	 * 
	 * -------lyricWeeks will be empty after 
	 */
	public void populateLyricWeeks() throws ClassNotFoundException{
		for (int i = 0;i<songsAndArtists.length;i++){
			String lyrics = databaseQueries.getLyrics(songsAndArtists[i][0], songsAndArtists[i][1]);
			ArrayList<String> weeks = databaseQueries.allWeeks(songsAndArtists[i][0], songsAndArtists[i][1]);
			Set<String> uniqueLyrics = helperFunctions.findUniqueWords(lyrics);
			for (String aLyric : uniqueLyrics){
				if (lyricWeeks.get(aLyric)==null){
					lyricWeeks.put(aLyric, new ArrayList<String>());
				}
				for (int j=0;j<weeks.size();j++){
					lyricWeeks.get(aLyric).add(weeks.get(j));
				}
			}
			if (i % 100 == 0){
				System.out.println("lyricWeeksPopulate(): "+i+" / "+songsAndArtists.length);
			}
		}
	}
	//this method populates processedWeeklyCount from lyric weeks
	public void populateProcecessedWeeklyCount(){
		Iterator<Entry<String, ArrayList<String>>> it = lyricWeeks.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, ArrayList<String>> pair = (Map.Entry<String, ArrayList<String>>)it.next();
	        ArrayList<String> weeks = pair.getValue(); //list of all weeks for a lyric (a Value from lyricWeeks)
	        ArrayList<weekCount> convertedWeeks = convertWeekList(weeks); //list of weekCount (week, count) objects for a lyric
	        processedLyricWeeks.put(pair.getKey(), convertedWeeks); //store the lyric as a key, and the weekCount object as a value
	       // System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException //will remove entries from lyricWeeks as it goes
	    }
	}
	/* converts a list of weeks into weekCount objects, with each distinct week, and a count for how many of that week were in the list
	 * this is the main helper function for populateProcessedWeeklyCount(), called once on each Value in lyricWeeks
	*/
	public ArrayList<weekCount> convertWeekList(ArrayList<String> weeklyList){
		ArrayList<weekCount> result = new ArrayList<weekCount>();
		Collections.sort(weeklyList);
		String currentWeek = "";
		String previousWeek = null;
		int count=0;
		for (int i=0;i<weeklyList.size();i++){
			currentWeek = weeklyList.get(i);
			if (currentWeek.equals(previousWeek)){
				count++;
			}
			else{
				if (previousWeek!=null){
					result.add(new weekCount(previousWeek, count));
				}
				count = 1;
				previousWeek = currentWeek;
			}
		}
		result.add(new weekCount(currentWeek, count));
		return result;
	}

}