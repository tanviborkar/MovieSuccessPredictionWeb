package entities;

public class Genre {
	String genreName = null;
	double genreIMDBScore = 0.0;
	
	public String getGenreName() {
		return genreName;
	}
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	public double getGenreIMDBScore() {
		return genreIMDBScore;
	}
	public void setGenreIMDBScore(double genreIMDBScore) {
		this.genreIMDBScore = genreIMDBScore;
	}
}
