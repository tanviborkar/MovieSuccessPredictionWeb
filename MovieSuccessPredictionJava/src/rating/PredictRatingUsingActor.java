package rating;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Actor;

public class PredictRatingUsingActor {
	List<Actor> actorList;
	
	public void loadActorData(String actor1Name, String actor2Name, String actor3Name){
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
    			if(!actor1Name.equals("")){
    				if(actor1Name.contains("'"))
    					actor1Name = actor1Name.replace("'","");
    				String sql1 = "SELECT * FROM actor_rating WHERE actor_name=?";
    				stmt = connection.prepareStatement(sql1);
    				stmt.setString(1, actor1Name);
    				ResultSet rs = stmt.executeQuery();
    				while(rs.next()){
    					Actor actorObj = new Actor();
    					actorObj.setActorName(rs.getString("actor_name"));
    					actorObj.setActorIMDBScore(rs.getDouble("actor_imdb"));
    					actorList.add(actorObj);
    				}
        			stmt.close();
    			}
    			
    			if(!actor2Name.equals("")){
    				if(actor2Name.contains("'"))
    					actor2Name = actor2Name.replace("'","");
    				String sql1 = "SELECT * FROM actor_rating WHERE actor_name =?";
    				stmt = connection.prepareStatement(sql1);
    				stmt.setString(1, actor2Name);
    				ResultSet rs = stmt.executeQuery();
    				while(rs.next()){
    					Actor actorObj = new Actor();
    					actorObj.setActorName(rs.getString("actor_name"));
    					actorObj.setActorIMDBScore(rs.getDouble("actor_imdb"));
    					actorList.add(actorObj);
    				}
        			stmt.close();
    			}
    			
    			if(!actor3Name.equals("")){
    				if(actor3Name.contains("'"))
    					actor3Name = actor3Name.replace("'","");
    				String sql1 = "SELECT * FROM actor_rating WHERE actor_name =?";
    				stmt = connection.prepareStatement(sql1);
    				stmt.setString(1, actor3Name);
    				ResultSet rs = stmt.executeQuery();
    				while(rs.next()){
    					Actor actorObj = new Actor();
    					actorObj.setActorName(rs.getString("actor_name"));
    					actorObj.setActorIMDBScore(rs.getDouble("actor_imdb"));
    					actorList.add(actorObj);
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
    	
	public double calculateRating(String actor1Name, String actor2Name, String actor3Name){
		actorList = new ArrayList<Actor>();
		loadActorData(actor1Name, actor2Name, actor3Name);
		double sumIMDBScore = 0.0;
		for(Actor actor:actorList){
			if(actor.getActorName().equals(actor1Name)){
				sumIMDBScore = sumIMDBScore + (6 * actor.getActorIMDBScore());
			}
			else if(actor.getActorName().equals(actor2Name)){
				sumIMDBScore = sumIMDBScore + (3 * actor.getActorIMDBScore());
			}    
			else{
				sumIMDBScore = sumIMDBScore + actor.getActorIMDBScore();
			}
		}
		sumIMDBScore = sumIMDBScore / 10;
		return sumIMDBScore;
	}
}
