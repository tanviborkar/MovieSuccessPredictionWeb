package budget;

import java.io.BufferedReader;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.FileNotFoundException;
import java.sql.Statement;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ReadCreateData {
	Map<String, Map> actorCountMap = new HashMap<String, Map>();
	Map<String, Map> directorCountMap = new HashMap<String, Map>();
	Map<String, Map> genreCountMap = new HashMap<String, Map>();
	Integer[] totalActorCount = {0,0,0};
	Integer[] totalDirectorCount = {0,0,0};
	Integer[] totalGenreCount = {0,0,0};
	Double[] totalClassProb = {0.0,0.0,0.0};
	
	public void readCSVFileData(){
		String csvFile = "C:/Users/test/Practice_Programs/MovieSuccessPredictionJava/src/budget/movie_metadata.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
	            // use comma as separator
	            String[] infoArray = line.split(cvsSplitBy);
	            if((!(infoArray[1].equals("director_name"))) && (!infoArray[8].equals("")) && (!infoArray[22].equals(""))){
	            	System.out.println("Info: "+infoArray[1]);
	            	System.out.println(infoArray[22]);
	            	int label = classifyBudgetRatio(Double.parseDouble(infoArray[8]), Double.parseDouble(infoArray[22]));
	            	 
	            	if(infoArray[10]!="")
                        processActorData(infoArray[10], label);
                    if(infoArray[6]!="")
                        processActorData(infoArray[6], label);
                    if(infoArray[14]!="")
                        processActorData(infoArray[14], label);
                    if(infoArray[1]!="")
                        processDirectorData(infoArray[1], label);
                    if(infoArray[9]!="")
                        processGenreData(infoArray[9], label);
                    
                    totalClassProb[label] = totalClassProb[label] + 1;
                              
	            }
			}
			findTotal();    
            findFrequency() ; 
            calculateLabelTotal();
            performDatabaseOperations();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
	        if (br != null) {
	            try {
	                br.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
		}

	}
	
	public int classifyBudgetRatio(double earnings, double budget){
        double budgetRatio = (double)earnings/budget;
        if((budgetRatio>=0.0) && (budgetRatio<1.0)){
            return 0;
        }
        else if((budgetRatio>=1.0) && (budgetRatio<2.0)){
            return 1;
        }
        else if(budgetRatio>=2.0){
            return 2;
        }
        else{
            return -1;
        }
	}
	
	public void processActorData(String actorName, int label){
        if(actorName.contains("'"))
            actorName = actorName.replace("'","");
           
        if(actorCountMap.containsKey(actorName)){
        	Map<Integer, Double> actorValueMap = actorCountMap.get(actorName);
            double ratingCount = actorValueMap.get(label);
            actorValueMap.put(label, (ratingCount + 1));
            actorCountMap.put(actorName, actorValueMap);
        }
        else{
            Map<Integer, Double> actorValueMap = new HashMap<Integer, Double>();
            for(int i=0; i<3; i++){
            	if(i==label){
            		actorValueMap.put(label, 1.0);
            	}
            	else{
            		actorValueMap.put(i, 0.0);
            	}
            }
            actorCountMap.put(actorName, actorValueMap); 
        }
	}
	
	public void processDirectorData(String directorName, int label){
		if(directorName.contains("'"))
			directorName = directorName.replace("'","");
		
		if(directorCountMap.containsKey(directorName)){
        	Map<Integer, Double> directorValueMap = directorCountMap.get(directorName);
            double ratingCount = directorValueMap.get(label);
            directorValueMap.put(label, (ratingCount + 1));
            directorCountMap.put(directorName, directorValueMap);
        }
        else{
            Map<Integer, Double> directorValueMap = new HashMap<Integer, Double>();
            for(int i=0; i<3; i++){
            	if(i==label){
            		directorValueMap.put(label, 1.0);
            	}
            	else{
            		directorValueMap.put(i, 0.0);
            	}
            }
            directorCountMap.put(directorName, directorValueMap); 
        }
		
	}
	
	public void processGenreData(String genreName, int label){
        String[] genreList = genreName.split("\\|");
        for(String genre:genreList){
            if(genreCountMap.containsKey(genre)){
                Map<Integer, Double> genreValueMap = genreCountMap.get(genre);
                System.out.println("Label: "+label);
                double ratingCount = genreValueMap.get(label);
                genreValueMap.put(label, (ratingCount + 1));
                genreCountMap.put(genre, genreValueMap);
            }
            else{
            	Map<Integer, Double> genreValueMap = new HashMap<Integer, Double>();
                for(int i=0; i<3; i++){
                	if(i==label){
                		genreValueMap.put(label, 1.0);
                	}
                	else{
                		genreValueMap.put(i, 0.0);
                	}
                }
                genreCountMap.put(genre, genreValueMap); 
            }
        }
	}
	
	public void findTotal(){
		for(String actor:actorCountMap.keySet()){
            Map<Integer, Double> actorValueMap = actorCountMap.get(actor);
            Set<Integer> keyList = actorValueMap.keySet();
            int sumCount;
            for(int key:keyList){
                sumCount = totalActorCount[key];
                sumCount = sumCount + (int)Math.round(actorValueMap.get(key));
                totalActorCount[key] = sumCount;
            }
		}
                
        for(String director:directorCountMap.keySet()){
        	Map<Integer, Double> directorValueMap = directorCountMap.get(director);
        	Set<Integer> keyList = directorValueMap.keySet();
            int sumCount;
        	for(int key:keyList){
                sumCount = totalDirectorCount[key];
                sumCount = sumCount + (int)Math.round(directorValueMap.get(key));
                totalDirectorCount[key] = sumCount;
        	}
        }
        
        for(String genre:genreCountMap.keySet()){
        	Map<Integer, Double> genreValueMap = genreCountMap.get(genre);
        	Set<Integer> keyList = genreValueMap.keySet();
            int sumCount;
        	for(int key:keyList){
                sumCount = totalGenreCount[key];
                sumCount = sumCount + (int)Math.round(genreValueMap.get(key));
                totalGenreCount[key] = sumCount;
        	}
        }
	}
	
	public void findFrequency(){
        for(String actor:actorCountMap.keySet()){
        	Map<Integer, Double> actorValueMap = actorCountMap.get(actor);
        	Set<Integer> keyList = actorValueMap.keySet();
            for(int key:keyList){    
                double frequency = (Math.log(actorValueMap.get(key) + 1)) - (Math.log(totalActorCount[key] + 3));
                actorValueMap.put(key, frequency);
            }
        }
        for(String director:directorCountMap.keySet()){
        	Map<Integer, Double> directorValueMap = directorCountMap.get(director);
        	Set<Integer> keyList = directorValueMap.keySet();
        	for(int key:keyList){
        		 double frequency = (Math.log(directorValueMap.get(key) + 1)) - (Math.log(totalDirectorCount[key] + 3));
        		 directorValueMap.put(key, frequency);
        	}
        }
        for(String genre:genreCountMap.keySet()){
        	Map<Integer, Double> genreValueMap = genreCountMap.get(genre);
        	Set<Integer> keyList = genreValueMap.keySet();
        	for(int key:keyList){  
        		double frequency = (Math.log(genreValueMap.get(key) + 1)) - (Math.log(totalGenreCount[key] + 3));
        		genreValueMap.put(key, frequency);
        	}
        }
	}
                
    public void calculateLabelTotal(){ 
        double sumTotal = 0.0;
        for(int i=0; i<3; i++){
            sumTotal = sumTotal + totalClassProb[i];
        }  
        for(int i=0; i<3;i++){
            double frequency = (Math.log(totalClassProb[i] + 1))- (Math.log((sumTotal + 3)));
            totalClassProb[i] = frequency;          
        }  
    }
    
    public void performDatabaseOperations(){
        createDatabaseTables();
        insertActorBudgetFreq();
        insertDirectorBudgetFreq();
        insertGenreBudgetFreq();
        insertBudgetFreqData();
    }
    
    public void createDatabaseTables(){
        
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
        		stmt.executeUpdate("DROP TABLE IF EXISTS actor_budget");
        		stmt.executeUpdate("DROP TABLE IF EXISTS director_budget");
        		stmt.executeUpdate("DROP TABLE IF EXISTS genre_budget");
        		stmt.executeUpdate("DROP TABLE IF EXISTS budget");
        		String sqlQuery1 = "CREATE TABLE actor_budget (actor_name  CHAR(50) NOT NULL, budget_0 DECIMAL(25,20), budget_1 DECIMAL(25,20),budget_2 DECIMAL(25,20))";
        		stmt.executeUpdate(sqlQuery1);
        		String sqlQuery2 = "CREATE TABLE director_budget ("+
        		         "director_name  CHAR(50) NOT NULL,"+
        		         "budget_0 DECIMAL(25,20),"+  
        		         "budget_1 DECIMAL(25,20),"+
        		         "budget_2 DECIMAL(25,20))";
        		stmt.executeUpdate(sqlQuery2);
        		String sqlQuery3 = "CREATE TABLE genre_budget ("+
        		         "genre_name  CHAR(50) NOT NULL,"+
        		         "budget_0 DECIMAL(25,20),"+  
        		         "budget_1 DECIMAL(25,20),"+
        		         "budget_2 DECIMAL(25,20))";
        		stmt.executeUpdate(sqlQuery3);   
        		String sqlQuery4 = "CREATE TABLE budget ("+
        		         "budget_label INT NOT NULL,"+
        		         "budget_freq DECIMAL(25,20))";
        		stmt.executeUpdate(sqlQuery4);
        		
    		      } catch (SQLException e) {
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
    	else {
    		System.out.println("Failed to make connection!");
    	}
    	
    }
    
    public void insertActorBudgetFreq(){
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
    			for(String actor:actorCountMap.keySet()){
    				Map<Integer, Double> actorValueMap = actorCountMap.get(actor);
    				String sql = "INSERT INTO actor_budget(actor_name, budget_0, budget_1, budget_2) VALUES ("+actor+","+actorValueMap.get(0)+","+actorValueMap.get(1)+","+actorValueMap.get(2)+")";
    				stmt.executeUpdate(sql);
    			}
    		} catch (SQLException e) {
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
    
    public void insertDirectorBudgetFreq(){
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
    			for(String director:directorCountMap.keySet()){
    				Map<Integer, Double> directorValueMap = directorCountMap.get(director);
    				String sql = "INSERT INTO director_budget(director_name, budget_0, budget_1, budget_2) VALUES ("+director+","+directorValueMap.get(0)+","+directorValueMap.get(1)+","+directorValueMap.get(2)+")";
    				stmt.executeUpdate(sql);
    			}
    		} catch (SQLException e) {
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
        
    public void insertGenreBudgetFreq(){
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
    			for(String genre:genreCountMap.keySet()){
    				Map<Integer, Double> genreValueMap = genreCountMap.get(genre);
    				String sql = "INSERT INTO genre_budget(genre_name, budget_0, budget_1, budget_2) VALUES("+genre+","+genreValueMap.get(0)+","+genreValueMap.get(1)+","+genreValueMap.get(2)+")";
    				stmt.executeUpdate(sql);
    			}
    		} catch (SQLException e) {
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
        
    public void insertBudgetFreqData(){
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
    			for(int i=0; i<3; i++){
    				String sql = "INSERT INTO budget(budget_label, budget_freq) VALUES ("+i+","+totalClassProb[i]+")" ;
    	            stmt.executeUpdate(sql);
    			}
    		} catch (SQLException e) {
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
}
