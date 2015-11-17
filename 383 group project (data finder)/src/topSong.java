
public class topSong {

	public int rank;
	public String song;
	public String artist;
	
	public topSong(int rank, String song, String artist){
		this.rank = rank;
		this.song = song;
		this.artist = artist;
	}
	
	public String toString(){
		return "rank: "+rank+", song: "+song+", artist: "+artist;
	}
	
}
