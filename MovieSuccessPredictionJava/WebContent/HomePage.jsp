<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<%ResultSet resultset =null;%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Movie Success Predictor</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-ui-1.12.1.hotsneaks/jquery-ui.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
  <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/jquery-ui-1.12.1.hotsneaks/jquery-ui.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/movieprediction.js"></script>
  <script>
  $( function() {
  	function showMovieResults() {
      // get effect type from
      var selectedEffect = "blind";
       // Run the effect
      $( "#movie-tab" ).show();
      var movieName = $("#movie-input").val();
      $("#movie-name").html(movieName);
      var path="images/";
      $('#movie-image').attr('src',path+movieName.replace(/ /g, '')+'.jpg');
    };
    
    $( "#tabs" ).tabs();
    $( "#top10" ).selectmenu();
    $( "#movie-button" ).on( "click", function() {
    	getPredictedValues();
        showMovieResults();
      });
    $( "#movie-tab" ).hide();
    $( "#topInfo" ).on( "click", function() {
    	loadTopTenData();
      });
  } 
  );
  </script>
</head>
<body>
<h1>Movie Success Predictor</h1>
<div id="tabs">
  <ul>
    <li><a href="#tabs-1">Movie</a></li>
    <li><a href="#tabs-2">Actor</a></li>
    <li><a href="#tabs-3">Director</a></li>
    <li><a href="#tabs-4">Genre</a></li>
    <li id="topInfo"><a href="#tabs-5">Top 10</a></li>
  </ul>
  <div id="tabs-1">
    <p>
    	<div class="ui-widget">
	      	<label for="tags">Movie: </label>
	    		<select name="movie-input" id="movie-input">
	    		<%
    				try{
						Class.forName("com.mysql.jdbc.Driver").newInstance();
						Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdb?user=root&password=test123");
						
						PreparedStatement statement = connection.prepareStatement("select movie_title from upcoming_movies");
						resultset =statement.executeQuery();
				%>
			      <option disabled selected>Please pick one</option>
			      <%  while(resultset.next()){ %>
            		<option><%= resultset.getString("movie_title")%></option>
        		<% } %>
			    </select>
			    <%
				//**Should I input the codes here?**
        		}
		        catch(Exception e)
		        {
		             out.println("wrong entry"+e);
		        }
				%>
	      		<input class="ui-button ui-widget ui-corner-all" id="movie-button" type="submit" value="Search">
    	</div>
    </p>
    <div id="movie-tab">
  		<div id="effect" class="ui-widget-content ui-corner-all">
   			<h3 id="movie-name" class="ui-widget-header ui-corner-all"></h3>
 		    <p>
 		      <div class="flex-container movie-image">
				  <div class="flex-item"> <img id="movie-image" src="" width="200" height="300"></div>
				  <div class="flex-item" id="predBudget">Predicted Budget</div>
				  <div class="flex-item" id="predRating">Predicted Rating</div>  
			  </div>
  			</p>
 		</div>
	</div>
    
    <p>
    
    </p>
  </div>
  <div id="tabs-2">
    <p>
	  <div class="ui-widget">
      <label for="tags">Actor: </label>
      <input id="tags">
      <input class="ui-button ui-widget ui-corner-all" type="submit" value="Search">
      </div>
	</p>
  </div>
  <div id="tabs-3">
    <p>
    <div class="ui-widget">
      <label for="tags">Director: </label>
      <input id="tags">
      <input class="ui-button ui-widget ui-corner-all" type="submit" value="Search">
      </div>
    </p>
  </div>
  <div id="tabs-4">
    <p>
    <div class="ui-widget">
      <label for="tags">Genre: </label>
      <input id="tags">
      <input class="ui-button ui-widget ui-corner-all" type="submit" value="Search">
      </div>
    </p>
  </div>
  <div id="tabs-5">
    <p>
    <div class="demo">
		<label for="top10">Select a category</label>
		    <select name="top10" id="top10">
		      <option selected="selected">Movies</option>
		      <option>Actors</option>
		      <option>Directors</option>
		      <option>Genres</option>
		    </select>
		<table id="displayTen" style="display:none">
			<tr>
				<th>Rank</th>
				<th id="categoryName">Rank</th>
				<th>Predicted Rating</th>
				<th>Predicted Earnings</th>
			</tr>
			<tr>
				<td>1</td>
				<td id="row12"></td>
				<td id="row13"></td>
				<td id="row14"></td>
			</tr>
			<tr>
				<td>2</td>
				<td id="row22"></td>
				<td id="row23"></td>
				<td id="row24"></td>
			</tr>
			<tr>
				<td>3</td>
				<td id="row32"></td>
				<td id="row33"></td>
				<td id="row34"></td>
			</tr>
			<tr>
				<td>4</td>
				<td id="row42"></td>
				<td id="row43"></td>
				<td id="row44"></td>
			</tr>
			<tr>
				<td>5</td>
				<td id="row52"></td>
				<td id="row53"></td>
				<td id="row54"></td>
			</tr>
			<tr>
				<td>6</td>
				<td id="row62"></td>
				<td id="row63"></td>
				<td id="row64"></td>
			</tr>
			<tr>
				<td>7</td>
				<td id="row72"></td>
				<td id="row73"></td>
				<td id="row74"></td>
			</tr>
			<tr>
				<td>8</td>
				<td id="row82"></td>
				<td id="row83"></td>
				<td id="row84"></td>
			</tr>
			<tr>
				<td>9</td>
				<td id="row92"></td>
				<td id="row93"></td>
				<td id="row94"></td>
			</tr>
			<tr>
				<td>10</td>
				<td id="row102"></td>
				<td id="row103"></td>
				<td id="row104"></td>
			</tr>
		</table>
	</div>
    </p>
  </div>
</div>
</body>
</html>