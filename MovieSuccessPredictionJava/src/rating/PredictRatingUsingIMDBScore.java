package rating;

import java.util.ArrayList;
import java.util.List;

public class PredictRatingUsingIMDBScore {
	PredictRatingUsingActor actorRating = new PredictRatingUsingActor();
	PredictRatingUsingDirector directorRating = new PredictRatingUsingDirector();
	PredictRatingUsingGenre genreRating = new PredictRatingUsingGenre();
	int correctPrediction = 0;
	int incorrectPrediction = 0;
	
	public double computeForUpcomingMovies(String actor1Name, String actor2Name, String actor3Name, String directorName, String genres){
        double actorIMDBScore = actorRating.calculateRating(actor1Name, actor2Name, actor3Name);
        double directorIMDBScore = directorRating.calculateRating(directorName);
        double genreIMDBScore = 0.0;
        if((genres!=null) && (!genres.equals(""))){
            String[] genreNameList = genres.split("\\|"); 
            List<String> genreList = new ArrayList<String>();
            for(int i=0;i<genreNameList.length;i++){
            	genreList.add(genreNameList[i]);
            }
            genreIMDBScore = genreRating.calculateRating(genreList);
        }
         
        double combinedIMDBScore = ((4 * directorIMDBScore) + (3 * actorIMDBScore) + (3 * genreIMDBScore)) / 10;
        return combinedIMDBScore;
	}
}
