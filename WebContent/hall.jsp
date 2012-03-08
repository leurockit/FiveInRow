<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.tao.entity.*"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
<script type="text/javascript">
var name;
var isBlack;
$(document).ready(function(){
	setInterval('checkPlayer()', 1000);
	<%
	Player player = (Player) request.getSession().getAttribute("player");
	String playerName = player.getName();
	%>
	name='<%=playerName%>';
	var isBlackFromServer = '<%=Hall.isPlayerBlack(player)%>';
	if('true'==isBlackFromServer){
		isBlack = true;
	}else{
		isBlack = false;
	}
});
function checkPlayer(){
	$.ajax({ 
	   type:"GET", 
	   url:'Hall', 
	   success: render}); 
}
function render(data){
	var names = data.split(',');
    for(var i=0;i<20;i++){
    	if(names[i]==''){
    		return;
    	}
    	$('#name'+i).text(names[i]);
    	if(names[i]==name){
    		if(isBlack){
    			if(names[i+1]==null||names[i+1]==''){
    				$('#start').hide();	
    			}else{
    				$('#start').show();
    			}
    		}else{
    			if(names[i-1]==null||names[i-1]==''){
    				$('#start').hide();	
    			}else{
    				$('#start').show();
    			}
    		}
    	}
    }
}
</script>

</head>
<body>
<form action="StartGame" method="get">
	<table border="5">
	<tr>
    <th>Black</th>
    <th></th>
    <th>White</th>
    </tr>
    <tr>
    <td id='name0'></td>
    <td>VS</td>
    <td id='name1'></td>
    </tr>
    <tr>
    <td id='name2'></td>
    <td>VS</td>
    <td id='name3'></td>
    </tr>
    <tr>
    <td id='name4'></td>
    <td>VS</td>
    <td id='name5'></td>
    </tr>
    <tr>
    <td id='name6'></td>
    <td>VS</td>
    <td id='name7'></td>
    </tr>
    <tr>
    <td id='name8'></td>
    <td>VS</td>
    <td id='name9'></td>
    </tr>
    <tr>
    <td id='name10'></td>
    <td>VS</td>
    <td id='name11'></td>
    </tr>
    <tr>
    <td id='name12'></td>
    <td>VS</td>
    <td id='name13'></td>
    </tr>
    <tr>
    <td id='name14'></td>
    <td>VS</td>
    <td id='name15'></td>
    </tr>
    <tr>
    <td id='name16'></td>
    <td>VS</td>
    <td id='name17'></td>
    </tr>
    <tr>
    <td id='name18'></td>
    <td>VS</td>
    <td id='name19'></td>
    </tr>
	</table>
	<input type="submit" value="Start" id="start" style="display: none" />
	</form>
</body>
</html>