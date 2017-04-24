package entities;

public class MovieInfo implements Comparable<MovieInfo> {

	String movieName = "";
	String actor1Name;
	String actor2Name;
	String actor3Name;
	String directorName;
	String genres;
	int predictedBudget=-1;
	double predictedRating=0.0;
	double score;
	
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getActor1Name() {
		return actor1Name;
	}
	public void setActor1Name(String actor1Name) {
		this.actor1Name = actor1Name;
	}
	public String getActor2Name() {
		return actor2Name;
	}
	public void setActor2Name(String actor2Name) {
		this.actor2Name = actor2Name;
	}
	public String getActor3Name() {
		return actor3Name;
	}
	public void setActor3Name(String actor3Name) {
		this.actor3Name = actor3Name;
	}
	public String getDirectorName() {
		return directorName;
	}
	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}
	public String getGenres() {
		return genres;
	}
	public void setGenres(String genres) {
		this.genres = genres;
	}
	public int getPredictedBudget() {
		return predictedBudget;
	}
	public void setPredictedBudget(int predictedBudget) {
		this.predictedBudget = predictedBudget;
	}
	public double getPredictedRating() {
		return predictedRating;
	}
	public void setPredictedRating(double predictedRating) {
		this.predictedRating = predictedRating;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	@Override
	public int compareTo(MovieInfo obj) {
		// TODO Auto-generated method stub
		return Double.compare(obj.getScore(), this.getScore());
	}
}
