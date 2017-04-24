package entities;

public class Director {

	String directorName = null;
	double directorIMDBScore = 0.0;
	
	public String getDirectorName() {
		return directorName;
	}
	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}
	public double getDirectorIMDBScore() {
		return directorIMDBScore;
	}
	public void setDirectorIMDBScore(double directorIMDBScore) {
		this.directorIMDBScore = directorIMDBScore;
	}	
}
