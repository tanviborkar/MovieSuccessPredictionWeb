package entities;

public class Actor implements Comparable<Actor> {
	String actorName = null;
	double actorIMDBScore = 0.0;
	double score;
	
	public String getActorName() {
		return actorName;
	}
	public void setActorName(String actorName) {
		this.actorName = actorName;
	}
	public double getActorIMDBScore() {
		return actorIMDBScore;
	}
	public void setActorIMDBScore(double actorIMDBScore) {
		this.actorIMDBScore = actorIMDBScore;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	@Override
	public int compareTo(Actor obj) {
		// TODO Auto-generated method stub
		return Double.compare(obj.getScore(), this.getScore());
	}	
}
