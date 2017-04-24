package rating;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Genre;

public class PredictRatingUsingGenre {
	List<Genre> genreList = new ArrayList<Genre>();
	
	public void loadGenreData(List<String> movieGenreList){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}
		System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javadb","root", "test123");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			try {
				if(!movieGenreList.isEmpty()){
					for(String genre:movieGenreList){
						String sql1 = "SELECT * FROM genre_rating WHERE genre_name=?";
	    				stmt = connection.prepareStatement(sql1);
	    				stmt.setString(1, genre);
	    				ResultSet rs = stmt.executeQuery();
	    				while(rs.next()){
	    					Genre genreObj = new Genre();
	    					genreObj.setGenreName(rs.getString("genre_name"));
	    					genreObj.setGenreIMDBScore(rs.getDouble("genre_imdb"));
	    					genreList.add(genreObj);
	    				}
	    				stmt.close();
					}
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
			        stmt.close();
			        connection.close();        
			      } catch (SQLException e) {
			        e.printStackTrace();
			      }
			    }
	    	}
		else{
			System.out.println("Operation Failed");
		}
	}
	
	public double calculateRating(List<String> movieGenreList){
        genreList.clear();
        loadGenreData(movieGenreList);
        double sumIMDBScore = 0.0;
        if(!genreList.isEmpty()){
        	for(Genre genre:genreList){
        		sumIMDBScore = sumIMDBScore + genre.getGenreIMDBScore();
        	}
        	return (sumIMDBScore / genreList.size());
        }
        else{
        	return 0.0;
        }
	}
}
