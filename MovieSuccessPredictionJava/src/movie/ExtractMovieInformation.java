package movie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.MovieInfo;

public class ExtractMovieInformation {

	public MovieInfo getMovieInformation(String movieTitle){
		try {
    		Class.forName("com.mysql.jdbc.Driver");
    		System.out.println("MySQL JDBC Driver Registered!");
    	} catch (ClassNotFoundException e) {
    		System.out.println("Where is your MySQL JDBC Driver?");
    		e.printStackTrace();
    		return null;
    	}
    	Connection connection = null;
    	PreparedStatement statement = null;
    	try {
    		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdb","root", "test123");
    	} catch (SQLException e) {
    		System.out.println("Connection Failed! Check output console");
    		e.printStackTrace();
    		return null;
    	}

    	if (connection != null) {
    		try{
	    		statement = connection.prepareStatement("select * from upcoming_movies where movie_title=?");
	    		statement.setString(1, movieTitle);
	    		ResultSet resultset =statement.executeQuery();
	    		while(resultset.next()){
					MovieInfo movieInfoObj = new MovieInfo();
					movieInfoObj.setActor1Name(resultset.getString("actor_1_name"));
					movieInfoObj.setActor2Name(resultset.getString("actor_2_name"));
					movieInfoObj.setActor3Name(resultset.getString("actor_3_name"));
					movieInfoObj.setDirectorName(resultset.getString("director_name"));
					movieInfoObj.setGenres(resultset.getString("genres"));
					return movieInfoObj;
	    		}
    	}catch (SQLException e) {
		      System.out.println("error: failed to create a connection object.");
		      e.printStackTrace();
		    } catch (Exception e) {
		      System.out.println("other error:");
		      e.printStackTrace();
		    }
  		finally {
		      try {
		    	  statement.close();
		        connection.close();        
		      } catch (SQLException e) {
		        e.printStackTrace();
		      }
		    }
	  	}
		else{
				System.out.println("Operation Failed");
		}
    	return null;
	}
}
