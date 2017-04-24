package movie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import budget.PredictMovieBudgetNB;
import entities.Actor;
import entities.MovieInfo;
import rating.PredictRatingUsingIMDBScore;

public class PredictTopTenInformation {
	List<MovieInfo> moviesList = new ArrayList<MovieInfo>();
	
	public PredictTopTenInformation(){
		loadUpcomingMovies();
		predictValuesForMovies();
	}
	
	public void loadUpcomingMovies(){
		try {
    		Class.forName("com.mysql.jdbc.Driver");
    		System.out.println("MySQL JDBC Driver Registered!");
    	} catch (ClassNotFoundException e) {
    		System.out.println("Where is your MySQL JDBC Driver?");
    		e.printStackTrace();
    		return;
    	}
    	Connection connection = null;
    	PreparedStatement statement = null;
    	try {
    		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdb","root", "test123");
    	} catch (SQLException e) {
    		System.out.println("Connection Failed! Check output console");
    		e.printStackTrace();
    		return;
    	}

    	if (connection != null) {
    		try{
	    		statement = connection.prepareStatement("select * from upcoming_movies");
	    		ResultSet resultset =statement.executeQuery();
	    		while(resultset.next()){
					MovieInfo movieInfoObj = new MovieInfo();
					movieInfoObj.setMovieName(resultset.getString("movie_title"));
					movieInfoObj.setActor1Name(resultset.getString("actor_1_name"));
					movieInfoObj.setActor2Name(resultset.getString("actor_2_name"));
					movieInfoObj.setActor3Name(resultset.getString("actor_3_name"));
					movieInfoObj.setDirectorName(resultset.getString("director_name"));
					movieInfoObj.setGenres(resultset.getString("genres"));
					moviesList.add(movieInfoObj);
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
	}
	
	public void predictValuesForMovies(){
		PredictMovieBudgetNB budgetObj = new PredictMovieBudgetNB();
		PredictRatingUsingIMDBScore ratingObj = new PredictRatingUsingIMDBScore();
		for(MovieInfo movie:moviesList){
			int predictedValue = budgetObj.calculateRating(movie.getActor1Name(), movie.getActor2Name(), movie.getActor3Name(), movie.getDirectorName(), movie.getGenres());
			double ratingValue = ratingObj.computeForUpcomingMovies(movie.getActor1Name(), movie.getActor2Name(), movie.getActor3Name(), movie.getDirectorName(), movie.getGenres());
			movie.setPredictedBudget(predictedValue);
			movie.setPredictedRating(ratingValue);
			movie.setScore(ratingValue + predictedValue); 
		}
		
	}
	
	public List<MovieInfo> fetchTopTenMovies(){
		Collections.sort(moviesList);
		return moviesList;
	}
	
	public List<Actor> fetchTopTenActors(){
		Map<String, List<Double>> actorMap = new HashMap<String, List<Double>>();
		List<Actor> topTenActors = new ArrayList<Actor>();
		
		for(MovieInfo movie:moviesList){
			if(actorMap.containsKey(movie.getActor1Name())){
				actorMap.get(movie.getActor1Name()).add(movie.getScore());
			}
			else{
				List<Double> actorScore = new ArrayList<Double>();
				actorScore.add(movie.getScore());
				actorMap.put(movie.getActor1Name(), actorScore);
			}
			if(actorMap.containsKey(movie.getActor2Name())){
				actorMap.get(movie.getActor2Name()).add(movie.getScore());
			}
			else{
				List<Double> actorScore = new ArrayList<Double>();
				actorScore.add(movie.getScore());
				actorMap.put(movie.getActor2Name(), actorScore);
			}
			if(actorMap.containsKey(movie.getActor3Name())){
				actorMap.get(movie.getActor3Name()).add(movie.getScore());
			}
			else{
				List<Double> actorScore = new ArrayList<Double>();
				actorScore.add(movie.getScore());
				actorMap.put(movie.getActor3Name(), actorScore);
			}
		}
		
		for(String actorName:actorMap.keySet()){
			List<Double> valueList = actorMap.get(actorName);
			double median = 0.0;
			for(Double value:valueList){
				median = median + value;
			}
			median = median/valueList.size();
			Actor actor = new Actor();
			actor.setActorName(actorName);
			actor.setScore(median);
			topTenActors.add(actor);
		}
		
		Collections.sort(topTenActors);
		return topTenActors;
	}
}
