<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>
<%--<%@page session="true"%>--%>
<%--<html>--%>
<%--<body>--%>
<%--<h1>Title : ${title}</h1>--%>
<%--<h1>Message : ${message}</h1>--%>

<%--<c:if test="${pageContext.request.userPrincipal.name != null}">--%>
    <%--<h2>Welcome : ${pageContext.request.userPrincipal.name}--%>
        <%--| <a href="<c:url value="/logout" />" > Logout</a></h2>--%>
<%--</c:if>--%>

<%--</body>--%>
<%--</html>--%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Data Analysis Of Social Networks | Post Form</title>
</head>
<!-- style setting buttons  -->
<style type="text/css">
    input {
        height: 50px;
        width: 100px;
        cursor: pointer;
        color: teal;
        font-size: 15px;
        font-family: Verdana;
        font-weight: bold;
    }
    .for_map_link {
        height: 40px;
        width: 70px;
        cursor: pointer;
        color: teal;
        font-size: 10px;
        font-family: Verdana;
        font-weight: bold;
    }
    .for_interest {
        height: 30px;
        width: 67px;
        cursor: pointer;
        color: teal;
        font-size: 9px;
        font-family: Verdana;
        font-weight: bold;
    }
    .center {
        width: 800px;
        margin: 0 auto;
    }
</style>

<body class="center">
<div id="fb-root"></div>
<!-- script out of the Facebook -->
<script src="http://connect.facebook.net/en_US/all.js"></script>
<script type="text/javascript">
    function newDoc() {
        window.location.assign("index")
    }
</script>
<br>
<br>
<br>
<br>


<!-- Get name current user -->

<div style="color: teal; font-size: 30px">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
    Welcome : ${pageContext.request.userPrincipal.name}
    | <input type="button" value="Logout" onclick="newDoc()">
    </c:if>
    </div>
<br>
<br>
<%--<input id="cb" type="checkbox" onchange="window.location.href='http://google.com/'">--%>
<div>
    <input type=button onClick="parent.location='ukraineMap?page=admin'" value='Statistic' class="for_map_link">
</div>

<!-- After saving the message is redirected /savePost -->

<c:url var="userMessage" value="savePost.html?userId=${userId}" />
<form:form id="messageForm" modelAttribute="post" method="post"
           action="${userMessage}">
    <table width="400px" height="150px">
        <tr>
            <td>
                <!-- a table that shows a limited number of records -->

                    <display:table id="row" name="post" requestURI="" style="width: 250%;text-align: left;word-wrap:break-word;" pagesize="10"
                                   export="true">
                        <display:column property="id" title="Id" sortable="true"
                                        style="border: 1px solid teal;  " />
                        <display:column property="userName" title="User Name" sortable="true"
                                        style="border: 1px solid teal;  " />
                        <display:column property="post" title="Post" sortable="true"
                                        style="border: 1px solid teal; " />
                        <display:column property="interest" title="Interest" sortable="true"
                                        style="border: 1px solid teal; " />
                        <display:column title="Set">
                            <div text-align="center" >

                                <input type=button onClick="parent.location='selectInterest.html?id=${row.id}&interest=1'" value='News' class="for_interest">
                                <input type=button onClick="parent.location='selectInterest.html?id=${row.id}&interest=2'" value='Politics' class="for_interest">
                                <input type=button onClick="parent.location='selectInterest.html?id=${row.id}&interest=3'" value='GAMES' class="for_interest">
                                <input type=button onClick="parent.location='selectInterest.html?id=${row.id}&interest=4'" value='MUSIC' class="for_interest">
                                <input type=button onClick="parent.location='selectInterest.html?id=${row.id}&interest=5'" value='SPORT' class="for_interest">
                                <input type=button onClick="parent.location='selectInterest.html?id=${row.id}&interest=6'" value='SCIENCE' class="for_interest">
                                <input type=button onClick="parent.location='selectInterest.html?id=${row.id}&interest=7'" value='BUSINESS' class="for_interest">
                                <input type=button onClick="parent.location='selectInterest.html?id=${row.id}&interest=8'" value='CINEMA' class="for_interest">
                                <input type=button onClick="parent.location='selectInterest.html?id=${row.id}&interest=9'" value='HUMOR' class="for_interest">
                                <input type=button onClick="parent.location='selectInterest.html?id=${row.id}&interest=10'" value='OTHER' class="for_interest">

                            </div>
                            <%--<a href="http://localhost:8080/selectInterest?id=${row.id}&interest=1" >News</a>--%>
                            <%--<a href="http://localhost:8080/selectInterest?id=${row.id}&interest=2">Politics</a>--%>
                            <%--<a href="http://localhost:8080/selectInterest?id=${row.id}&interest=2">GAMES</a>--%>
                            <%--<a href="http://localhost:8080/selectInterest?id=${row.id}&interest=2">MUSIC</a>--%>
                            <%--<a href="http://localhost:8080/selectInterest?id=${row.id}&interest=2">SPORT</a>--%>
                            <%--<a href="http://localhost:8080/selectInterest?id=${row.id}&interest=2">SCIENCE</a>--%>
                            <%--<a href="http://localhost:8080/selectInterest?id=${row.id}&interest=2">BUSINESS</a>--%>
                            <%--<a href="http://localhost:8080/selectInterest?id=${row.id}&interest=2">CINEMA</a>--%>
                            <%--<a href="http://localhost:8080/selectInterest?id=${row.id}&interest=2">HUMOR</a>--%>
                            <%--<a href="http://localhost:8080/selectInterest?id=${row.id}&interest=2">OTHER</a>--%>
                        </display:column>
                    </display:table>

               </td>
        </tr>
    </table>
</form:form>
</body>
</html>