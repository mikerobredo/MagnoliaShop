function setIdAndShowSummary(id, restUrl, nexPage){
	console.log("hola");
    $.ajax({
      type: "GET",
      url: restUrl ,
      data: {'idredsys' : id},
      success: function(result) {      
        location.href= nexPage ;
      }
    });
  }

function sendRest(restUrl, nexPage){
	console.log("adios");
    $.ajax({
      type: "GET",
      url: restUrl ,
      data: {'proceed' : true},
      success: function(result) {      
        location.href= nexPage ;
      }
    });
  }

/*
 * function messageEventListener(restUrl, ml){
 * 	window.addEventListener("message", function receiveMessage(event) {
      storeIdOper(event,"token");        
      setIdAndShowSummary(event.data.idOper, restUrl,ml);      
  });
 * }
 * 
 */