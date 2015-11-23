import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class queries {
	
	
	public String path = "DataDB/lyricproject.db";
	
	//returns multidimentional array of song, artist strings for all songs which lyrics are not null (lyrics we've found)
	public String[][] songsANDartists() throws ClassNotFoundException{
		 	Class.forName("org.sqlite.JDBC");
		 	
		 	String[][] songArtists = null;
		 	
		    Connection connection = null;
		    try
		    {
		      // create a database connection
		      connection = DriverManager.getConnection("jdbc:sqlite:"+path);
		      Statement statement = connection.createStatement();
		      statement.setQueryTimeout(30);  // set timeout to 30 sec.
		      
		      
		      ResultSet count = statement.executeQuery("SELECT count(*) AS count FROM lyrics WHERE lyrics NOT NULL");
		      count.next();
		      int numSongs = count.getInt("count");
		      songArtists = new String[numSongs][2];
		      
		      //all songs we have lyrics from
		      ResultSet rs = statement.executeQuery("SELECT * FROM lyrics WHERE lyrics NOT NULL");

		      //ArrayList<String> songs = new ArrayList<String>();
		      //ArrayList<String> artists = new ArrayList<String>();
		      int index = 0;
		      while(rs.next())
		      {
		        // read the result set
		    	try {
		    		String artist = rs.getString("artist");
		    		String song = rs.getString("song");
		    		songArtists[index][0] = song;
		    		songArtists[index][1] = artist;
		    		index++;
	    			
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      }
		    }
		    catch(SQLException e)
		    {
		      // if the error message is "out of memory", 
		      // it probably means no database file is found
		      System.err.println(e.getMessage());
		    }
		    finally
		    {
		      try
		      {
		        if(connection != null)
		          connection.close();
		      }
		      catch(SQLException e)
		      {
		        // connection close failed.
		        System.err.println(e);
		      }
		    }
		    for (int i =0;i<songArtists.length;i++){
		    	System.out.println("Song: "+songArtists[i][0]+" || Artist: "+songArtists[i][1]);
		    }
		    System.out.println("returned "+songArtists.length+" distinct songs");
		    return songArtists;
	}
	//returns lowest ranking 1-50 given song and artist, case sensitive
	public int bestRanking(String song, String artist) throws ClassNotFoundException{
		 Class.forName("org.sqlite.JDBC");
		 	int rank = -1;
		    Connection connection = null;
		    try
		    {
		      // create a database connection
		    	connection = DriverManager.getConnection("jdbc:sqlite:"+path);

		      PreparedStatement ps = connection.prepareStatement("SELECT week, min(rank) as rank, song, artist from rankdata where song = ? and artist = ? group by song, artist");
		      ps.setString(1, song);
		      ps.setString(2, artist);
		      ResultSet rs = ps.executeQuery();
		     
		      if (rs.next())
		      {
		        // read the result set
		    	try {
		    		rank = rs.getInt("rank");
		    		System.out.println(rank);
		    		
	    			
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      }
		    }
		    catch(SQLException e)
		    {
		      // if the error message is "out of memory", 
		      // it probably means no database file is found
		      System.err.println(e.getMessage());
		    }
		    finally
		    {
		      try
		      {
		        if(connection != null)
		          connection.close();
		      }
		      catch(SQLException e)
		      {
		        // connection close failed.
		        System.err.println(e);
		      }
		    }
		    return rank;
	}
	//returns best week (week of lowest rank) given song and artist, case sensitive
	public String bestWeek(String song, String artist) throws ClassNotFoundException{
		 Class.forName("org.sqlite.JDBC");
		 	String week = null;
		    Connection connection = null;
		    try
		    {
		      // create a database connection
		    	connection = DriverManager.getConnection("jdbc:sqlite:"+path);

		      PreparedStatement ps = connection.prepareStatement("SELECT week, min(rank) as rank, song, artist from rankdata where song = ? and artist = ? group by song, artist");
		      ps.setString(1, song);
		      ps.setString(2, artist);
		      ResultSet rs = ps.executeQuery();
		     
		      if (rs.next())
		      {
		        // read the result set
		    	try {
		    		week = rs.getString("week");
		    		System.out.println(week);
		    		
	    			
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      }
		    }
		    catch(SQLException e)
		    {
		      // if the error message is "out of memory", 
		      // it probably means no database file is found
		      System.err.println(e.getMessage());
		    }
		    finally
		    {
		      try
		      {
		        if(connection != null)
		          connection.close();
		      }
		      catch(SQLException e)
		      {
		        // connection close failed.
		        System.err.println(e);
		      }
		    }
		    return week;
	}
	//returns lyrics for a given song/artist, case sensitive
	public String getLyrics(String song, String artist) throws ClassNotFoundException{
		 Class.forName("org.sqlite.JDBC");
		 	String lyrics = null;
		    Connection connection = null;
		    try
		    {
		      // create a database connection
		    	connection = DriverManager.getConnection("jdbc:sqlite:"+path);

		      PreparedStatement ps = connection.prepareStatement("SELECT lyrics AS lyrics from lyrics where song = ? and artist = ?");
		      ps.setString(1, song);
		      ps.setString(2, artist);
		      ResultSet rs = ps.executeQuery();
		     
		      if (rs.next())
		      {
		        // read the result set
		    	try {
		    		lyrics = rs.getString("lyrics");
		    		System.out.println(lyrics);
		    		
	    			
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      }
		    }
		    catch(SQLException e)
		    {
		      // if the error message is "out of memory", 
		      // it probably means no database file is found
		      System.err.println(e.getMessage());
		    }
		    finally
		    {
		      try
		      {
		        if(connection != null)
		          connection.close();
		      }
		      catch(SQLException e)
		      {
		        // connection close failed.
		        System.err.println(e);
		      }
		    }
		    return lyrics;
	}
	
	
	
}
