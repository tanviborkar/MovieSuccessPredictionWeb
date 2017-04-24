import java.util.ArrayList;
import java.util.List;

import budget.PredictMovieBudgetNB;
import budget.ReadCreateData;
import rating.PredictRatingUsingIMDBScore;

public class MainClass {
	public static void main(String[] args) {
		//ReadCreateData obj = new ReadCreateData();
		//obj.readCSVFileData();
		PredictMovieBudgetNB obj1 = new PredictMovieBudgetNB();
		//obj1.extractBudgetFreq();
		//obj1.extractActorBudget("Johnny Depp", "Orlando Bloom", "Jack Davenport");
		//obj1.extractDirectorBudget("James Cameron");
		//String[] genreList = {"Adventure","Crime","Mystery"};
		//obj1.extractGenreBudget(genreList);
		List<String> genresList = new ArrayList<String>();
		genresList.add("Action");
		genresList.add("Adventure");
		genresList.add("Fantasy");
		genresList.add("Sci-Fi");
		int predictedValues = obj1.calculateRating("CCH Pounder", "Joel David Moore", "Wes Studi", "James Cameron", "Action|Adventure|Fantasy|Sci-Fi");
		System.out.println(predictedValues);
		PredictRatingUsingIMDBScore ratingObj = new PredictRatingUsingIMDBScore();
		ratingObj.computeForUpcomingMovies("", "CCH Pounder", "Joel David Moore", "Wes Studi", "James Cameron", genresList);
	}

}
