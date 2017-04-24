package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Actor;
import entities.MovieInfo;
import movie.PredictTopTenInformation;

/**
 * Servlet implementation class PredictTopTen
 */
@WebServlet("/PredictTopTen")
public class PredictTopTen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PredictTopTen() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String category = request.getParameter("category");
		response.setContentType("text/plain");
		String output = "";		
		if(category!=null){
			PredictTopTenInformation predictObj = new PredictTopTenInformation();
			if(category.equals("Movies")){
				List<MovieInfo> moviesList = predictObj.fetchTopTenMovies();
				for(int i=0;i<10;i++){
					MovieInfo movie = moviesList.get(i);
					if(output.equals("")){
						output = output+movie.getMovieName()+"@#@"+movie.getPredictedRating()+"@#@"+movie.getPredictedBudget();
					}
					else{
						output = output+"@$@$@"+movie.getMovieName()+"@#@"+movie.getPredictedRating()+"@#@"+movie.getPredictedBudget();
					}
				}
			}
			else if(category.equals("Actors")){
				List<Actor> topTenActors = predictObj.fetchTopTenActors();
				
			}
		}
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
