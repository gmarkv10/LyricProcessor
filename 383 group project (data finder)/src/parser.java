import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class parser {
	
/*	Code for itunes top 100
 * public String getTop100() throws IOException{
		String result = "";
		Document doc = Jsoup.connect("http://www.apple.com/au/itunes/charts/songs/").get();  
		Elements songs = doc.select("section.chart-grid").select("div.section-content").select("li");
		for (Element song : songs){
			int songRank = Integer.parseInt(song.select("strong").text().replace(".", ""));
			String songName = song.select("a").first().nextElementSibling().text();
			String artistName = song.select("a").first().nextElementSibling().nextElementSibling().text();
			result+=songRank+","+songName+","+artistName+"\n";
		}
		result = result.substring(0, result.length()-1);
		System.out.print(result);
		return result;
	}
	
	public void writeFileTop100() throws IOException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 	Date date = new Date();
		String fileName = dateFormat.format(date);//YYYY-MM-DD
		PrintWriter writer = new PrintWriter(new File(fileName), "UTF-8");
		writer.print(getTop100());
		writer.close();
	}*/
	
	//attempts to get lyrics from genius.com
	public String getLyricsGenius(String artistName, String songName) throws Exception{
		String result = "";
		
		String modifiedArtist = trimArtist(artistName);
		modifiedArtist = removeApos(modifiedArtist);
		modifiedArtist = modifiedArtist.replaceAll(" ", "-");
		String modifiedSong = songName;
		modifiedSong = removeApos(modifiedSong);
		modifiedSong = modifiedSong.replaceAll(" ", "-");
		//System.out.println("modartist = "+modifiedArtist+" modsong = "+modifiedSong);
		String url = "http://www.genius.com/"+modifiedArtist+"-"+modifiedSong+"-"+"lyrics";
		Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
			      .referrer("http://www.google.com").get();  
		if (doc.select("title").text().contains("Burr!")){
			System.out.println("not on genius.com");
			return null;
		}
		else{
			System.out.println("genius    - found song...jackpot");
			Elements lyrics = doc.select("div.lyrics_container").select("div.lyrics");
			for (Element partialLyric : lyrics){
				result += partialLyric.text();
			}
			//System.out.println(result);
			result = trimGeniusBrackets(result);
		//	System.out.println(result);
			return result;
		}
	}
	// gets rid of all the [Hook] type text included with genius.com lyrics
	public String trimGeniusBrackets(String input){
		ArrayList<Integer> startIndexes = new ArrayList<Integer>();
		ArrayList<Integer> endIndexes = new ArrayList<Integer>();
		for (int i=0;i<input.length();i++){
			if (input.charAt(i) == '['){
				startIndexes.add(i);
			}
			else if (input.charAt(i) == ']'){
				endIndexes.add(i);
			}
		}
		int offset = 0;
		for (int i =0;i<startIndexes.size();i++){
			//System.out.println(input);
			String firstHalf = "";
			if(startIndexes.get(i)!=0){ 
				firstHalf = input.substring(0, startIndexes.get(i)-offset);
			}
			input = firstHalf + input.substring(endIndexes.get(i)-offset+1, input.length());
			offset+= endIndexes.get(i)-startIndexes.get(i)+1;
		}
		return input;
	}
	//trys to get lyrics from lyrics123.net, returns null if fail
	public String getLyrics123(String artistName, String songName) throws Exception{
		
		String modifiedArtist = trimArtist(artistName);
		modifiedArtist = removeApos(modifiedArtist);
		modifiedArtist = modifiedArtist.replaceAll(" ", "-");
		String modifiedSong = songName;
		modifiedSong = removeApos(modifiedSong);
		modifiedSong = modifiedSong.replaceAll(" ", "-");
		//System.out.println("modartist = "+modifiedArtist+" modsong = "+modifiedSong);
		
		
		String url = "http://www.lyrics123.net/"+modifiedArtist+"/"+modifiedSong+"/";
		Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
			      .referrer("http://www.google.com").get();  
		if (doc.select("div[id=b]").select("h1:eq(0)").text().equals("Searchable Song Lyrics Database")){
			System.out.println("not on lyrics123.net");
			return null;
		}
		else{
		System.out.println("lyrics123 - found song...jackpot");
		String lyrics = doc.select("div[id=b]").select("p:eq(3)").text();
		//System.out.println(lyrics);
		return lyrics;
		}
	}
	public String removeApos(String input) throws Exception{
		//System.out.println("original song: "+input);
		String output = input;
		//output = output.replaceAll("'", "");
		//output = output.replaceAll(",", "");
		//output = output.replaceAll("  ", " ");
		//output = output.replaceAll("-", "");
		//output = output.replaceAll("?", "");
		//output = output.replaceAll("!", "");
		//output = output.replaceAll("\\.", "");
		output = trimParens(output);
		output = output.replaceAll("\\s+", " ").trim();
		output = output.replaceAll("[^A-Za-z0-9 ]", "");
		//output = output.replaceAll("( )+", " ");
		//System.out.println("output = "+output);
		output = output.trim();
		output = output.replaceAll("\\s+", " ").trim();
		//System.out.println("output = "+output);
		return output;
	}
	
	public String trimParens(String input) throws Exception{
		ArrayList<Integer> startIndexes = new ArrayList<Integer>();
		ArrayList<Integer> endIndexes = new ArrayList<Integer>();
		for (int i=0;i<input.length();i++){
			if (input.charAt(i) == '('){
				startIndexes.add(i);
			}
			else if (input.charAt(i) == ')'){
				endIndexes.add(i);
			}
		}
		int offset = 0;
		for (int i =0;i<startIndexes.size();i++){
			//System.out.println(input);
			String firstHalf = "";
			if(startIndexes.get(i)!=0){ 
				firstHalf = input.substring(0, startIndexes.get(i)-offset);
			}
			input = firstHalf + input.substring(endIndexes.get(i)-offset+1, input.length());
			offset+= endIndexes.get(i)-startIndexes.get(i)+1;
		}
		return input;
	}
	// gets rid of featuring artists, they are rarely included in lyric site URLS
	public String trimArtist(String artistName){
		String modName = artistName.toLowerCase();
		if (modName.contains("featuring")){
			int startIndex = modName.indexOf("featuring");
			modName = modName.substring(0, startIndex-1);
		}
		if (modName.contains("&")){
			int startIndex = modName.indexOf("&");
			modName = modName.substring(0, startIndex-1);
		}
		if (modName.contains(",")){
			int startIndex = modName.indexOf(",");
			modName = modName.substring(0, startIndex-1);
		}
		if (modName.contains("and")){
			int startIndex = modName.indexOf("and");
			modName = modName.substring(0, startIndex-1);
		}
		if (modName.contains("with")){
			int startIndex = modName.indexOf("with");
			modName = modName.substring(0, startIndex-1);
		}
		if (modName.contains("+")){
			int startIndex = modName.indexOf("+");
			modName = modName.substring(0, startIndex-1);
		}
		return modName;
	}
	
	//pull URLS to weekly top 50 rankings
	public ArrayList<weekURL> getWeeklyURLS() throws IOException{
		String baseURL = "http://musicchartsarchive.com/singles-chart/";
		String one = baseURL + "2010s";
		String two = baseURL + "2000s";
		String three = baseURL + "1990s";
		String four = baseURL + "1980s";
		
		String[] decadeURLS = {one, two, three, four};
		/*ArrayList<String> dates = new ArrayList<String>();
		ArrayList<String> weeklyRankURLs = new ArrayList<String>();*/
		ArrayList<weekURL> weeklyURLs = new ArrayList<weekURL>();
		for (int i=0;i<decadeURLS.length;i++){
			Document doc = Jsoup.connect(decadeURLS[i]).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				      .referrer("http://www.google.com").get();  
			Elements weeklyRanks = doc.select("table.decade-table").select("a");
			for (Element weeklyRank : weeklyRanks){
				/*dates.add(weeklyRank.text());
				weeklyRankURLs.add(weeklyRank.attr("abs:href"));*/
				String week = weeklyRank.text();
				String url = weeklyRank.attr("abs:href");
				weeklyURLs.add(new weekURL(week, url));
			}
		}
		return weeklyURLs;
		/*for (int i =0;i<dates.size();i++){
			System.out.println("Week: "+dates.get(i)+", URL: "+weeklyRankURLs.get(i));
		}*/
	}
	//Pulls top 50 songs from a URL to the weekly top 50
	public ArrayList<topSong> getTop50(String weekURL) throws IOException{
		ArrayList<topSong> ranks = new ArrayList<topSong>();
		Document doc = Jsoup.connect(weekURL).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
			      .referrer("http://www.google.com").get();  
		
		Elements songData = doc.select("div.view-content").select("tr");
		
		for (Element song : songData){
			Elements cols = song.select("td");
			String rank = cols.get(0).text();
			String songName = cols.get(1).text();
			String artistName = cols.get(2).text();
			ranks.add(new topSong(Integer.parseInt(rank), songName, artistName));
		}
		
		for (int i=0;i<ranks.size();i++){
			System.out.println(ranks.get(i).toString());
		}
		
		return ranks;
	}
	
	
	
	
	public void unusualCharStats(ArrayList<String> songNames, ArrayList<String> artistNames){
		ArrayList<Character> unusualChars = new ArrayList<Character>();
		ArrayList<Integer> count = new ArrayList<Integer>();
		//parse the songs
		for (int i=0;i<songNames.size();i++){
			String song = songNames.get(i).toLowerCase();
			for (int j =0;j<song.length();j++){
				if (song.charAt(j) < 'a' || song.charAt(j) > 'z' && song.charAt(j)!=' '){
					if (unusualChars.contains(song.charAt(j))){
						count.set(unusualChars.indexOf(song.charAt(j)), count.get(unusualChars.indexOf(song.charAt(j))) + 1);
					}
					else{
						unusualChars.add(song.charAt(j));
						count.add(1);
					}
				}
			}
		}
		//parse the artists
		for (int i=0;i<artistNames.size();i++){
			String artist = artistNames.get(i).toLowerCase();
			for (int j =0;j<artist.length();j++){
				if (artist.charAt(j) < 'a' || artist.charAt(j) > 'z' && artist.charAt(j)!=' '){
					if (unusualChars.contains(artist.charAt(j))){
						count.set(unusualChars.indexOf(artist.charAt(j)), count.get(unusualChars.indexOf(artist.charAt(j))) + 1);
					}
					else{
						unusualChars.add(artist.charAt(j));
						count.add(1);
					}
				}
			}
		}
		
		for (int i =0;i<unusualChars.size();i++){
			if (!(unusualChars.get(i) >= 48 && unusualChars.get(i) <= 57)){
			System.out.println("CH: "+unusualChars.get(i)+", #: "+count.get(i));
			}
		}
	}
	
}


