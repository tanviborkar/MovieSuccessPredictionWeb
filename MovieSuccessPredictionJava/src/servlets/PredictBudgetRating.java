package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import budget.PredictMovieBudgetNB;
import entities.MovieInfo;
import movie.ExtractMovieInformation;
import rating.PredictRatingUsingIMDBScore;

/**
 * Servlet implementation class PredictBudgetRating
 */
@WebServlet("/PredictBudgetRating")
public class PredictBudgetRating extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PredictBudgetRating() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String movieTitle = request.getParameter("movieTitle");
		ExtractMovieInformation movieObj = new ExtractMovieInformation();
		MovieInfo movieInfo = movieObj.getMovieInformation(movieTitle);
		response.setContentType("text/plain");
		if(movieInfo != null){
			PredictMovieBudgetNB budgetObj = new PredictMovieBudgetNB();
			int predictedValue = budgetObj.calculateRating(movieInfo.getActor1Name(), movieInfo.getActor2Name(), movieInfo.getActor3Name(), movieInfo.getDirectorName(), movieInfo.getGenres());
			PredictRatingUsingIMDBScore ratingObj = new PredictRatingUsingIMDBScore();
			double ratingValue = ratingObj.computeForUpcomingMovies(movieInfo.getActor1Name(), movieInfo.getActor2Name(), movieInfo.getActor3Name(), movieInfo.getDirectorName(), movieInfo.getGenres());
			String output = predictedValue + "@#@"+ ratingValue;
			response.getWriter().write(output);
		}
		else{
			response.getWriter().write("");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
