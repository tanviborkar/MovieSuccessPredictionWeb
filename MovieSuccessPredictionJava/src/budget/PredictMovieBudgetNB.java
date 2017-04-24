package budget;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PredictMovieBudgetNB {
	Map<String, Double[]> actorFreqMap = new HashMap<String, Double[]>();
	Map<String, Double[]> directorFreqMap = new HashMap<String, Double[]>();
	Map<String, Double[]> genreFreqMap = new HashMap<String, Double[]>();
    double[] budgetFreq = {0.0, 0.0, 0.0};
    double[] scoreBudget = {0.0, 0.0, 0.0};
    
    public PredictMovieBudgetNB(){
    	extractBudgetFreq();
    }
    
    public int calculateRating(String actor1Name, String actor2Name, String actor3Name, String directorName, String genres){
        extractActorBudget(actor1Name, actor2Name, actor3Name);
        
        if(!directorName.equals("")){
            extractDirectorBudget(directorName);
        }
        
        if(!genres.equals("")){
            String[] genreNameList = genres.split("\\|"); 
            extractGenreBudget(genreNameList);
        }  
        
        if((!actorFreqMap.isEmpty())||(!directorFreqMap.isEmpty())||(!genreFreqMap.isEmpty())){
        	computeScoreForRating();
        	List<Integer> predictedLabel = computeMaxScore();
        	reinitializeGlobalValues();
	        if(predictedLabel.size() == 1){
	        	return predictedLabel.get(0);
	        }
	        else if(predictedLabel.isEmpty()){
	        	return -1;
	        }
	        else{
	        	Collections.sort(predictedLabel);
	        	return predictedLabel.get(predictedLabel.size() - 1);
	        }
        }
        return -1;
    }
    
    public void extractActorBudget(String actor1Name, String actor2Name, String actor3Name){
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
    				String sql1 = "SELECT * FROM actor_budget WHERE actor_name=?";
    				stmt = connection.prepareStatement(sql1);
    				stmt.setString(1, actor1Name);
    				ResultSet rs = stmt.executeQuery();
    				Double[] actorArray = {0.0,0.0,0.0};
        			while(rs.next()){
        				actorArray[0] = rs.getDouble("budget_0");
        				actorArray[1] = rs.getDouble("budget_1");
        				actorArray[2] = rs.getDouble("budget_2");
        			}
        			actorFreqMap.put(actor1Name, actorArray);
        			stmt.close();
    			}
    			
    			if(!actor2Name.equals("")){
    				if(actor2Name.contains("'"))
    					actor2Name = actor2Name.replace("'","");
    				String sql1 = "SELECT * FROM actor_budget WHERE actor_name =?";
    				stmt = connection.prepareStatement(sql1);
    				stmt.setString(1, actor2Name);
    				ResultSet rs = stmt.executeQuery();
    				Double[] actorArray = {0.0,0.0,0.0};
        			while(rs.next()){
        				actorArray[0] = rs.getDouble("budget_0");
        				actorArray[1] = rs.getDouble("budget_1");
        				actorArray[2] = rs.getDouble("budget_2");
        			}
        			actorFreqMap.put(actor2Name, actorArray);
        			stmt.close();
    			}
    			
    			if(!actor3Name.equals("")){
    				if(actor3Name.contains("'"))
    					actor3Name = actor3Name.replace("'","");
    				String sql1 = "SELECT * FROM actor_budget WHERE actor_name =?";
    				stmt = connection.prepareStatement(sql1);
    				stmt.setString(1, actor3Name);
    				ResultSet rs = stmt.executeQuery();
    				Double[] actorArray = {0.0,0.0,0.0};
        			while(rs.next()){
        				actorArray[0] = rs.getDouble("budget_0");
        				actorArray[1] = rs.getDouble("budget_1");
        				actorArray[2] = rs.getDouble("budget_2");
        			}
        			actorFreqMap.put(actor3Name, actorArray);
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
    
    public void extractDirectorBudget(String directorName){
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
    			if(!directorName.equals("")){
    				if(directorName.contains("'"))
    					directorName = directorName.replace("'","");
    				String sql1 = "SELECT * FROM director_budget WHERE director_name=?";
    				stmt = connection.prepareStatement(sql1);
    				stmt.setString(1, directorName);
    				ResultSet rs = stmt.executeQuery();
    				Double[] directorArray = {0.0,0.0,0.0};
        			while(rs.next()){
        				directorArray[0] = rs.getDouble("budget_0");
        				directorArray[1] = rs.getDouble("budget_1");
        				directorArray[2] = rs.getDouble("budget_2");
        			}
        			directorFreqMap.put(directorName, directorArray);
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
    			
    public void extractGenreBudget(String[] genresList){
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
    			for(String genre:genresList){
    				String sql1 = "SELECT * FROM genre_budget WHERE genre_name=?";
    				stmt = connection.prepareStatement(sql1);
    				stmt.setString(1, genre);
    				ResultSet rs = stmt.executeQuery();
    				Double[] genreArray = {0.0,0.0,0.0};
        			while(rs.next()){
        				genreArray[0] = rs.getDouble("budget_0");
        				genreArray[1] = rs.getDouble("budget_1");
        				genreArray[2] = rs.getDouble("budget_2");
        			}
        			genreFreqMap.put(genre, genreArray);
        			stmt.close();
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
        
    public void extractBudgetFreq(){
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    	} catch (ClassNotFoundException e) {
    		System.out.println("Where is your MySQL JDBC Driver?");
    		e.printStackTrace();
    		return;
    	}
    	System.out.println("MySQL JDBC Driver Registered!");
    	Connection connection = null;
    	Statement stmt = null;
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
    			stmt = connection.createStatement();
    			String sql = "SELECT * FROM budget";
    			ResultSet rs = stmt.executeQuery(sql);
    			while(rs.next()){
    				budgetFreq[rs.getInt("budget_label")] = rs.getDouble("budget_freq");
    			}
    		for(int i=0; i<budgetFreq.length; i++){
    			System.out.println(budgetFreq[i]);
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
    
    public void computeScoreForRating(){
        for(String actor:actorFreqMap.keySet()){
            Double[] valueMap = actorFreqMap.get(actor);
            for(int i=0; i<3; i++){
            	scoreBudget[i] = valueMap[i] + scoreBudget[i];
            }
        }
        
        for(String director:directorFreqMap.keySet()){
            Double[] valueMap = directorFreqMap.get(director);
            for(int i=0; i<3; i++){
                scoreBudget[i] = valueMap[i] + scoreBudget[i];
            }
        }
        
        for(String genre:genreFreqMap.keySet()){
            Double[] valueMap = genreFreqMap.get(genre);
            for(int i=0; i<3; i++){
                scoreBudget[i] = valueMap[i] + scoreBudget[i];
            }
        }
            
        for(int i=0; i<3; i++){
            scoreBudget[i] = scoreBudget[i] + budgetFreq[i];
        }
    }
    
    public List<Integer> computeMaxScore(){
        double max = scoreBudget[0];
        List<Integer> indexList = new ArrayList<Integer>();
        
        for(int i=1;i<3;i++){
            if(max<scoreBudget[i])
                max = scoreBudget[i];
        }        
        for(int i=0; i<3; i++){
            if(max == scoreBudget[i])
                indexList.add(i);
        }       
        return indexList;
    }
    
    public void reinitializeGlobalValues(){
        actorFreqMap.clear();
        directorFreqMap.clear();
        genreFreqMap.clear();
        for(int i=0; i<3; i++){
            scoreBudget[i] = 0.0;
        }
    }
}
