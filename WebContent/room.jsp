<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.tao.entity.Room" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
<script type="text/javascript">
	var can;
	var canOffset;
	var ctx;
	var unitSize = 33;
	var boardOffset = [ 20, 20 ];
	var unitNumber = 15;//don't change this is the chess standard.
	var pieceSize = unitSize / 3;
	var rows;
	var isBlack;
	var isBlackTurn;
	var isVictory;

	function init() {
		can = document.getElementById('can');
		canOffset = [ parseInt(can.style.left), parseInt(can.style.top) ];
		ctx = can.getContext('2d');
		var isBlackFromServer = '<%=request.getSession().getAttribute("isBlack")%>';
		if('true'==isBlackFromServer){
			isBlack = true;
		}else{
			isBlack = false;
		}
		isBlackTurn = true;//black should be first in turn
		isVictory = false;
		drawBG();
		if(isBlack!=isBlackTurn){
			get();
		}
	}
	
	function reset(){
		if(isVictory){
			$.ajax({
				type : "POST",
				url : 'Game',
				data : 'restart=0',
				success : function(data){
					isBlackTurn = true;
					isVictory = false;
					drawBG();
					if(isBlack!=isBlackTurn){
						get();
					}
				}
			});
		}	
	}
	
	function drawBG() {
		var img = new Image();
		img.onload = function() {
			ctx.drawImage(img, 0, 0, 500, 500);
			initGrid();
			//drawGrid();
		};
		img.src = 'img/board.png';
	}

	function initGrid() {
		var x = boardOffset[0];
		var y = boardOffset[1];
		rows = new Array(unitNumber);
		for ( var i = 0; i < unitNumber; i++) {
			var row = new Array(unitNumber);
			for ( var j = 0; j < unitNumber; j++) {
				row[j] = [ x, y ];
				x += unitSize;
			}
			rows[i] = row;
			//reset X and increase Y to start a new row
			x = boardOffset[0];
			y += unitSize;
		}
	}
	
	//ajax get
	function get(){
		$.ajax({
			type : "POST",
			url : 'Game',
			success : function(data){//call local draw point
				var point = data.split(',');
				drawPiece(point[0],point[1],point[2]);
				if(point[3]==0){
					isVictory = true;
					alert('You lose.');
					return;
				}
				isBlackTurn=!isBlackTurn;
			}
		});
	}
	
	function put(event) {
		if(isVictory){
			alert('Game Over');
			return;
		}
		if(isBlack!=isBlackTurn){
			alert('wait for adversary!');
			return;
		}
		var putPoint = [ event.x - canOffset[0], event.y - canOffset[1] ];
		//check which is the nearest
		for ( var i = 0; i < unitNumber; i++) {
			var row = rows[i];
			for ( var j = 0; j < unitNumber; j++) {
				var rsX = Math.abs(putPoint[0] - row[j][0]);
				var rsY = Math.abs(putPoint[1] - row[j][1]);
				if (rsX < pieceSize && rsY < pieceSize) {
					if (row[j][2] != null) {//can't cover
						return;
					}
					row[j][2] = isBlack;
					//ajax put
					var param = 'x='+i+'&y='+j;
					$.ajax({
						type : "GET",
						url : 'Game',
						data : param,
						async: false,
						success : function(data){//call local draw point
							if(''==data||null==data){
								return;
							}
							var point= data.split(',');
							drawPiece(point[0],point[1],point[2]);
							if(point[3]==0){
								isVictory = true;
								alert('You win!');
								return;
							}
							isBlackTurn=!isBlackTurn;
							get();
							return;
						}
					});
				}
			}
		}
	}
	
	function drawPiece(x,y,colr) {
		if (colr==0) {
			ctx.fillStyle = 'black';
		}
		if(colr==1){
			ctx.fillStyle = 'white';
		} 
		ctx.beginPath();
		ctx.arc(rows[x][y][0], rows[x][y][1], pieceSize, 0, Math.PI * 2, true);
		ctx.closePath();
		ctx.fill();
	}

</script>
</head>
<body onload="init()">
<%
Room room = (Room)request.getSession().getAttribute("room");
%>
<table>
<tr>
<td>Black: <%=room.getBlackName()%></td>
<td></td>
<td>White: <%=room.getWhiteName()%></td>
</tr>
</table>
<canvas id="can" onclick="put(event)" width="500" height="500"
		style="position: absolute; left: 200px; top: 200px; margin: 0px; padding: 0px;"></canvas>
<input type="button" value="restart" onclick="reset();" />
</body>
</html>