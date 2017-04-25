import java.util.ArrayList;

public class SentimentAnalysis {

	public static void main(String[] args) {
		String topic = "pirates of the caribbean movie";
		ArrayList<String> tweets = TweetManager.getTweets(topic);
		NLP.init();
		int count = 20;
		double sum = 0;
		double average = 0;
		int temp=0;
		for(String tweet : tweets) {
			if(count>0) {
				temp = NLP.findSentiment(tweet);
				sum = sum + temp;
				System.out.println(tweet+" : "+temp );
				count--;
			}
			else {
				sum = sum + NLP.findSentiment(tweet);
			}
			
		}
		average = sum/tweets.size();
		System.out.println("Number of tweets: "+tweets.size());
		System.out.println("Total sum is :"+sum);
		System.out.println("The average sentiment for "+topic+" is "+average );
	}
}
