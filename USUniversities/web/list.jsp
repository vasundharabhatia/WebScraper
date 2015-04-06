<%-- 
    Document   : list
    Created on : Mar 18, 2015, 3:47:21 AM
    Author     : vasundharabhatia
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="cialfo.model.UniversityModel"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
         <!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
        <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
        <script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
        <script src="/scripts/jquery/jquery.session.js"></script>
        <script>
            function getResult() {
                $('#me').empty();
                var listItems = "";
                $.ajax({
                    url: 'http://localhost:8080/USUniversities/University/getSearched',
                    dataType: 'JSON',
                    data: {key: $("#id").val(),numberLow: $("#low").val(),numberHigh: $("#high").val()},
                    type: 'Post',
                    cache: false,
                    success: function (data)
                    {
                        var array = JSON.parse(JSON.stringify(data));
                        //alert(array);
                        listItems="<table class="+"table table-condensed"+">"
                        listItems+="<tr><td>"+"University"+"<"+"/td>"+
                        "<td>Tuition</td></tr>"
                        for (var i = 0; i < array.length; i++) {
                            listItems+="<tr>";
                            listItems += "<td>" + array[i].Name + "</td>";
                            listItems += "<td>" + array[i].Tuition + "</td>";
                            listItems+="</tr>";

                        }
                        listItems+="</table>"
                        $('#LIST').empty();
                        $('#me').append(listItems);
                    },
                    error: function(){
                    alert("error!");
                    }
                    });
                    }
            
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <h1>US Universities</h1>
        
        <br>
        <h3>Filter Search:</h3>
        <div class="form-group">
         Enter University Name: <input type="text" name="id" id="id">
         &nbsp &nbsp &nbsp &nbsp Fee Range From: <input type="text" name="low" id="low">
         -To: <input type="text" name="high" id="high">
         
         <input type="button" value="Search" onclick="getResult();" class="btn btn-success"/><br>
        </div>
         <a href="http://localhost:8080/USUniversities/University">Go to Complete list</a>

<div id="me"></div>
        <h1></h1>
        <table border="4">
     
                    <table id="LIST" name="univlist" style="border: 1px;" class="table table-condensed" >
                        <tr><td>University</td>
                        <td>Tuition</td></tr>
<%
List<UniversityModel> list = (List<UniversityModel>) request.getAttribute("arrayList");

for(UniversityModel university : list) {
%>

    <tr value="<%=university.getName()%>">
        <td>
            <%=university.getName() %></td>
        <td>
            <%=university.getTuition() %></td>
    </tr>

<%
}
%>

</table>
     </div>
</table>
    </body>
</html>
