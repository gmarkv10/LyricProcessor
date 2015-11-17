import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class databaseMethods {

	//creates the initial database for our project (don't run this if lyricproject.db exists)
	public void create383ProjectDB() throws ClassNotFoundException, SQLException{
		 // load the sqlite-JDBC driver using the current class loader
	    Class.forName("org.sqlite.JDBC");
	    Connection connection = null;
	      // create a database connection
	      connection = DriverManager.getConnection("jdbc:sqlite:lyricproject.db");
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.
	      // create our tables
	      statement.executeUpdate("create table weeklyurls (week string, url string NOT NULL, CONSTRAINT week_pKey PRIMARY KEY (week))");
	      statement.executeUpdate("create table rankdata (week string, rank integer, song string NOT NULL, artist string NOT NULL,  CONSTRAINT song_data_pKey PRIMARY KEY (week, rank))");
	      statement.executeUpdate("create table lyrics (song string, artist string, lyrics TEXT,  CONSTRAINT lyrics_pKey PRIMARY KEY (song, artist))");
	}
	//this method parses all songs which the parser failed to find lyrics, and prints non-alphanumeric characters found in song/artist name
	public void findUnusualChars() throws ClassNotFoundException{
		 // load the sqlite-JDBC driver using the current class loader
	    Class.forName("org.sqlite.JDBC");

	    Connection connection = null;
	    try
	    {
	      // create a database connection
	      connection = DriverManager.getConnection("jdbc:sqlite:lyricproject.db");
	      Statement statement = connection.createStatement();
	      //PreparedStatement ps = connection.prepareStatement("UPDATE lyrics SET lyrics = ? WHERE song = ? AND artist = ?");
	     // PreparedStatement updateTries = connection.prepareStatement("UPDATE lyrics SET tries = ? WHERE song = ? AND artist = ?");
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.
	      
	      //all songs we do not have lyrics from
	     ResultSet rs = statement.executeQuery("SELECT * FROM lyrics WHERE lyrics IS NULL");
	    //  ResultSet rs = statement.executeQuery("select * from lyrics where lyrics IS NULL AND (song like '%''%' OR artist like '%''%')");
	      parser p = new parser();
	      ArrayList<String> songs = new ArrayList<String>();
	      ArrayList<String> artists = new ArrayList<String>();
	      while(rs.next())
	      {
	        // read the result set
	    	try {
	    		String artist = rs.getString("artist");
	    		String song = rs.getString("song");
	    		songs.add(song);
	    		artists.add(artist);
	    		
    			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	      p.unusualCharStats(songs, artists);
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
	    
	}
	//tries to pull lyrics for songs returned by a query (I change the query based on changes i make to the parser)
	public void pullLyrics() throws ClassNotFoundException{
		 // load the sqlite-JDBC driver using the current class loader
	    Class.forName("org.sqlite.JDBC");

	    Connection connection = null;
	    try
	    {
	      // create a database connection
	      connection = DriverManager.getConnection("jdbc:sqlite:lyricproject.db");
	      Statement statement = connection.createStatement();
	      PreparedStatement ps = connection.prepareStatement("UPDATE lyrics SET lyrics = ? WHERE song = ? AND artist = ?");
	      PreparedStatement updateTries = connection.prepareStatement("UPDATE lyrics SET tries = ? WHERE song = ? AND artist = ?");
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.
	      
	      //all songs we do not have lyrics from
	      ResultSet rs = statement.executeQuery("SELECT * FROM lyrics WHERE lyrics IS NULL AND (artist LIKE '%&%' OR artist LIKE '%and%' OR artist LIKE '%with%' OR artist LIKE '%+%')");
	      //ResultSet rs = statement.executeQuery("select * from lyrics where lyrics IS NULL AND (song like '%''%' OR artist like '%''%')");
	      parser p = new parser();
	      int count = 0;
	     
	      while(rs.next())
	      {
	        // read the result set
	    	try {
	    		String artist = rs.getString("artist");
	    		String song = rs.getString("song");
	    		if (rs.getString("tries") == null){
	    			System.out.println("finding nulls");
	    			updateTries.setInt(1, 0);
	    		}
	    		else{
	    			updateTries.setInt(1, rs.getInt("tries")+1);
	    		}
	    		updateTries.setString(2, song);
    			updateTries.setString(3, artist);
    			updateTries.executeUpdate();
    			String lyric1 = p.getLyrics123(artist, song);
    			if (lyric1 == null){
    				lyric1 = p.getLyricsGenius(artist, song);
    			}
    			if (lyric1 != null){
    				ps.setString(1, lyric1);
    				ps.setString(2, song);
    				ps.setString(3, artist);
    				ps.executeUpdate();
    			}
    			count++;
    			System.out.println("count = "+count);
    			
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
	}
	
	//populates our weeklyurls table in our database (don't run, already did this)
	public void populateWeeklyurls() throws IOException, ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");

	    Connection connection = null;
	    try
	    {
	      // create a database connection
	      connection = DriverManager.getConnection("jdbc:sqlite:lyricproject.db");
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.
	      parser p = new parser();
	      ArrayList<weekURL> weeklyURLS = p.getWeeklyURLS();
	      
	      
	      for (int i=0;i<weeklyURLS.size();i++){
	    	  statement.executeUpdate("insert into weeklyurls values('"+weeklyURLS.get(i).week+"', '"+weeklyURLS.get(i).url+"')");
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
	}
	//pulls top 50 song rank for weeks found in weeklyurls table but not rankData table (dont run if db already created)
	public void populateRankData() throws ClassNotFoundException{
		 // load the sqlite-JDBC driver using the current class loader
	    Class.forName("org.sqlite.JDBC");

	    Connection connection = null;
	    try
	    {
	      // create a database connection
	      connection = DriverManager.getConnection("jdbc:sqlite:lyricproject.db");
	      Statement statement = connection.createStatement();
	      PreparedStatement ps = connection.prepareStatement("insert into rankdata values(?,?,?,?)");
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.
	      
	      //weeks that have not been added to rankdata yet
	      ResultSet rs = statement.executeQuery("SELECT * FROM weeklyurls WHERE week NOT IN (SELECT week from rankdata)");
	      
	      parser p = new parser();
	      while(rs.next())
	      {
	        // read the result set
	    	try {
	    		String week = rs.getString("week");
	    		System.out.println("doing week: "+week);
				ArrayList<topSong> weeklyRankData = p.getTop50(rs.getString("url"));
				for (int i =0;i<weeklyRankData.size();i++){
						String song = weeklyRankData.get(i).song;
						String artist = weeklyRankData.get(i).artist;
						ps.setString(1, week);
						ps.setInt(2, weeklyRankData.get(i).rank);
						ps.setString(3, song);
						ps.setString(4, artist);
						ps.execute();
					 
				}
			} catch (IOException e) {
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
	}
	
	//pulled this example as a base for interacting with db using java, I use it as a template for the above methods
	public void createDB() throws ClassNotFoundException{
		 // load the sqlite-JDBC driver using the current class loader
	    Class.forName("org.sqlite.JDBC");

	    Connection connection = null;
	    try
	    {
	      // create a database connection
	      connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.

	     // statement.executeUpdate("drop table if exists person");
	      statement.executeUpdate("create table person (id integer, name string)");
	      statement.executeUpdate("insert into person values(1, 'leo')");
	      statement.executeUpdate("insert into person values(2, 'yui')");
	      
	      
	      ResultSet rs = statement.executeQuery("select * from person");
	      while(rs.next())
	      {
	        // read the result set
	        System.out.println("name = " + rs.getString("name"));
	        System.out.println("id = " + rs.getInt("id"));
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
	}
	
	
	
	
	
	
	
}
