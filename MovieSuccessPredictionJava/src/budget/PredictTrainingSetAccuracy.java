package budget;

public class PredictTrainingSetAccuracy {
	int countCorrectPredictions = 0;
	int countIncorrectPredictions = 0;
	PredictMovieBudgetNB predictObj; 
	
	public PredictTrainingSetAccuracy() {
		// TODO Auto-generated constructor stub
		predictObj = new PredictMovieBudgetNB();
	}

	public void readTraingData(){
		        with open('budget/movie_metadata.csv', 'rt') as csvfile:
		            columnNames = ['color', 'director_name', 'num_critic_for_reviews', 'duration', 'director_facebook_likes', 'actor_3_facebook_likes', 'actor_2_name', 'actor_1_facebook_likes', 'gross', 'genres', 'actor_1_name', 'movie_title', 'num_voted_users', 'cast_total_facebook_likes', 'actor_3_name', 'facenumber_in_poster', 'plot_keywords', 'movie_imdb_link', 'num_user_for_reviews', 'language', 'country', 'content_rating', 'budget', 'title_year', 'actor_2_facebook_likes', 'imdb_score', 'aspect_ratio', 'movie_facebook_likes']
		            reader = csv.DictReader(csvfile, columnNames)
		            for row in reader:
		                #code for processing actor data
		                if((row['actor_1_name'] != 'actor_1_name') & (row['gross'] !='') & (row['budget'] !='')):
		                    actor1Name = row['actor_1_name']
		                    actor2Name = row['actor_2_name']
		                    actor3Name = row['actor_3_name']
		                    directorName = row['director_name']
		                    genres = row['genres']
		                    earnings = row['gross']
		                    budget = row['budget']
		                    
		                    predictedLabels = self.predictObj.calculateRating(actor1Name, actor2Name, actor3Name, directorName, genres)
		                    actualLabel = self.classifyBudgetRatio(earnings, budget)
		                    print(predictedLabels)
		                    
		                    if(actualLabel in predictedLabels):
		                        self.countCorrectPredictions = self.countCorrectPredictions + 1
		                    else:
		                        self.countIncorrectPredictions = self.countIncorrectPredictions + 1
		                            
		        accuracy = self.countCorrectPredictions/(self.countCorrectPredictions + self.countIncorrectPredictions)
		        print('Accuracy of Naive Bayes '+str(accuracy))
		         
		        public int classifyBudgetRatio(double earnings, double budget){
			        double budgetRatio = earnings/budget;
			        if((budgetRatio>=0.0) && (budgetRatio<1.0))
			            return 0;
			        else if((budgetRatio>=1.0) & (budgetRatio<2.0))
			            return 1;
			        else if(budgetRatio>=2.0)
			            return 2;
			        else
			            return -1; 
		      }
}
