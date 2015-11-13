<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spilna Sprava | Post Form</title>
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
</style>

<body>
	<div id="fb-root"></div>
	<!-- script out of the Facebook -->
	<script src="http://connect.facebook.net/en_US/all.js"></script>
	<script type="text/javascript">
	function logout() {
		
		FB.init({
		    appId      : '913574692064006',
		    status     : true,
		    xfbml      : true
		  });
		 FB.getLoginStatus(function(resp) {
	            if (resp.authResponse) {
	                FB.logout(function(resp) {
	                    // Now that they're logged out of Facebook, redirect them
	                    // to our logout page.
	                    redirect();
	                });
	            } else {
	                // They're not actually logged in after all, but we still need
	                // to redirect.
	                redirect();
	            }
	        });
	    }
	 
	    function redirect() {
	        // This is the logout URL you want to be redirected to after the user
	        // logs out of Facebook, if they've logged in with Facebook.
	        window.location.href = "/signin";
	    }

    function newDoc() {
        window.location.assign("http://localhost:8080/ukrainMap")
    }
    function Redirect(id, interes) {
        window.location="http://localhost:8080/selectInterest?id="+id;
    }
	
  
</script>
	<center>
		<br>
		<br>
		<br>
		<br>

		
				<!-- Get name current user -->
				
		<div style="color: teal; font-size: 30px"> Hello 
		<c:if test="${!empty user}">

			<c:forEach items="${user}" var="user">

				<c:out value="${user.name}" />
			</c:forEach>
		</c:if>
		 :)</div>
		<br>
		<br>
        <%--<input id="cb" type="checkbox" onchange="window.location.href='http://google.com/'">--%>
        <div>
            <input type=button onClick="parent.location='ukraineMap.html?interest=1'" value='Politic' class="for_map_link">
            <input type=button onClick="parent.location='ukraineMap.html?interest=2'" value='Music' class="for_map_link">
        </div>

		<!-- After saving the message is redirected /savePost -->
		
		<c:url var="userMessage" value="savePost.html" />
		<form:form id="messageForm" modelAttribute="post" method="post"
			action="${userMessage}">
			<table width="400px" height="150px">
				<tr>
					<td><form>
							<div style="color: teal; font-size: 30px">enter your post</div>
							<!-- Text block -->
							<textarea name="post" rows=7 cols=50></textarea>
							<!-- Button -->
							<input  type="submit" value="Send"> 
							<input  type="reset">
							
						</form> <br>
						<!-- a table that shows a limited number of records -->
						<center>
							<display:table id="row" name="post" requestURI="" pagesize="10"
								export="true">
								<display:column property="id" title="Id" sortable="true"
									style="border: 1px solid teal;  " />
								<display:column property="post" title="Post" sortable="true"
									style="border: 1px solid teal; " />
                                <display:column property="interest" title="Interest" sortable="true"
                                                style="border: 1px solid teal; " />
								<display:column title="Set">
									<a href="http://localhost:8080/selectInterest?id=${row.id}&interest=1" >Politics</a>
                                    <a href="http://localhost:8080/selectInterest?id=${row.id}&interest=2">Music</a>
								</display:column>
							</display:table>

						</center></td>
				</tr>
			</table>

			<p>
				<input type="button" value="Logout" onclick="logout()">
                <input type="button" value="Load new document" onclick="newDoc()">
			</p>
		</form:form>
	</center>
</body>
</html>