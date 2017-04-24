/**
 * 
 */
function getPredictedValues(){
	$.ajax({
		url : 'PredictBudgetRating',
		data : {
			movieTitle : $('#movie-input').val()
		},
		success : function(responseText) {
			if(responseText!=''){
				var splitValues = responseText.split('@#@');
				if(splitValues[0] == '-1'){
					$("#predBudget").text('Insufficient Information for making Earnings Predictions');
				}
				else{
					if(splitValues[0] == '0'){
						$("#predBudget").text('Earnings Prediction: Loss');
					}
					else if(splitValues[0] == '1'){
						$("#predBudget").text('Earnings Prediction: Average');
					}
					else{
						$("#predBudget").text('Earnings Prediction: Profit');
					}
				}
				$("#predRating").text('Predicted Ratings: '+parseFloat(splitValues[1]).toFixed(2));
			}
			else{
				$("#predBudget").text('Insufficient Information for making Predictions');
				$("#predRating").text('Insufficient Information for making Predictions');
			}
		}
	});	
}

function loadTopTenData(){
	var category = $('#top10').val();
	$.ajax({
		url : 'PredictTopTen',
		data : {
			category : category
		},
		success : function(responseText) {
			if(responseText!=''){
				var	movieList = responseText.split("@$@$@");
				if(category=='Movies'){
					$("#categoryName").text('Movie Name')
				}
				for(i=0;i<movieList.length;i++){
					var movieAttributes = movieList[i].split("@#@");
					for(j=0;j<movieAttributes.length;j++){
						var elementId = 'row'+(i+1)+(j+2)
						if(j==1){
							$("#"+elementId).text(parseFloat(movieAttributes[j]).toFixed(2));
						}
						else if(j==2){
							$("#"+elementId).text(predictLabel(movieAttributes[j]));
						}
						else{
							$("#"+elementId).text(movieAttributes[j]);
						}
					}
				}
				$("#displayTen").show();
			}
		}
	});	
}

function predictLabel(value){
	if(value == '0'){
		return 'Loss';
	}
	else if(value == '1'){
		return 'Average';
	}
	else if(value == '2'){
		return 'Profit';
	}
	else{
		return 'Insufficient Data';
	}
}