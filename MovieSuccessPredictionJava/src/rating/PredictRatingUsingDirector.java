package rating;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Director;

public class PredictRatingUsingDirector {
		
	public Director loadDirectorData(String directorName){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return null;
		}
		System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javadb","root", "test123");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}
	
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			try {
				if(!directorName.equals("")){
					if(directorName.contains("'"))
						directorName = directorName.replace("'","");
					String sql1 = "SELECT * FROM director_rating WHERE director_name=?";
    				stmt = connection.prepareStatement(sql1);
    				stmt.setString(1, directorName);
    				ResultSet rs = stmt.executeQuery();
    				Director directorObj=null;
    				while(rs.next()){
    					directorObj = new Director();
    					directorObj.setDirectorName(rs.getString("director_name"));
    					directorObj.setDirectorIMDBScore(rs.getDouble("director_imdb"));
    				}
    				return directorObj;
				}
			}
			catch (SQLException e) {
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
		return null;
	}
	
	public double calculateRating(String directorName){
		Director directorObj = loadDirectorData(directorName);
		if(directorObj != null){
			return directorObj.getDirectorIMDBScore();
		}
		else{
			return 0.0;
		}
	}
}
