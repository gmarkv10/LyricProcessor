package Final;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class LyricManager {
	int folds = -1;
	BufferedWriter writer;
	
	public LyricManager(int folds){
		
	}
	
	
	public void makeFoldFile(int fold) throws Exception{
		writer = new BufferedWriter(new FileWriter("Data/trainingData" + fold + ".csv"));
	}
	
	public void makeFoldFiles(int folds) throws Exception{ //replaces writeDataFile
		for(int i = 0; i < folds; i++){
			makeFoldFile(i);
		}
		
	}
	
	public Set<String> findUniqueWords(String lyrics){
		
		return null;
	}
	
	public void processWeeklyCount(){
		
	}
	
	public ArrayList<weekCount> convertWeekList(ArrayList<String> weeklyList){
		return null;
	}
	
	//for adding a lyric found in a given song to our hashmap based on the weeks that song was popular
	public void addLyricNWeek(String lyric, ArrayList<String> weeks){
			
	}
	
	

}
