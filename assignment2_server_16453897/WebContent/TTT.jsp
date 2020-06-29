<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- getting ajax. -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<!-- a nice css file to make it look better. 
https://github.com/kognise/water.css
MIT license
-->
<link rel='stylesheet' href='./dist/dark.css'>

<meta charset="UTF-8">
<title>Tic-Tac-Toe</title>
</head>

<script type="text/javascript">
//if the game is won
var done=false;
//new game
function newGame(ptype) {
	done=false;
	document.getElementById("won").innerHTML="";
	//reset the wining title
	if(ptype==1){
		//if ai starts
		$.post('./ttt/ustart', {}, function(data){ 
			updateScreen();
		})
	}
	if(ptype==0){
		//if user start
		$.post('./ttt/istart', {}, function(data){ 
			updateScreen();
		})
	}
	}
//update the table with what is on the server
function updateScreen() {

	$.get('./ttt/state?format=txt', {}, function(data){ 
		data=data.split("\n");
		for (i = 0; i < 3; i++) { 
			for (e = 0; e < 3; e++) { 
				id=""+(i+1)+(e+1)
				if(data[i][e]=="_"){
					//button that sends its cords to post when pressed
					document.getElementById(id).innerHTML="<button onClick=\"post("+(i+1)+","+(e+1)+")\" align=\"center\")\">.</button>".trim();
					console.log(id)
				}else{
					//button that does nothing for the x's / 0's
					document.getElementById(id).innerHTML="<button><b>"+ data[i][e] +"</b></button>".trim();
				}
			}
			}
	})
	}
//sending a move to the game server
function post(x,y) {
	if(!done){
	console.log(x);
	console.log(y);
	cords=""+x+","+y;
	
	$.get('./ttt/possiblemoves', {}, function(data){ 
		data=data.split("\n");
		console.log(data);
		//checking it is a valid move
		if(data.includes(cords)){
			//formating the url
			uri="x"+x+"y"+y;
			$.post('./ttt/move/'+uri, {}, function(data){ 
				//updating the game board
				updateScreen();
				$.post('./ttt/won', {}, function(data){ 
					//checking to see if anyone won
					console.log(data);
					if(data!="none"){
						//updating winner part of screen 
						document.getElementById("won").innerHTML=data;
						done=true;
					}
				})
			})
		}
	})
	}
}

function load() {
	updateScreen();
	
	$.post('./ttt/won', {}, function(data){ 
		//checking to see if anyone won
		console.log(data);
		if(data!="none"){
			//updating winner part of screen 
			document.getElementById("won").innerHTML=data;
			done=true;
		}
	});
}

window.onload=load;
</script>

<body align="center">
<h1>XOX</h1>

<h2 id="won"></h2>
<!-- where the game is placed -->
<div id="board" align="center">
<table>
<!-- makes a 3 x 3 table with IDs being there coordinates in the table -->

<%
for(int x=1;x<4;x++)
{
%>
<tr>
<%for(int y=1;y<4;y++)
{ %>
<td id='<%=x%><%=y%>'>
<% }%>
</tr>
<%	
}%>
</table>
</div>

<!-- start/reset game controls -->
<div id="controls">
<button onclick="newGame(1)">new game computer start</button>
<button onclick="newGame(0)">new game user start</button>
</div>
</body>
</html>