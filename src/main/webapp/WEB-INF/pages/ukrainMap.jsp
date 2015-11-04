<%--
  Created by IntelliJ IDEA.
  User: Ivanov
  Date: 04.11.15
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<html>
<head>
    <script type='text/javascript' src='https://www.google.com/jsapi'></script>
    <script type='text/javascript'>
        google.load('visualization', '1', {'packages': ['geochart']});
        google.setOnLoadCallback(drawRegionsMap);


        function drawRegionsMap() {
            var sumskaVal;
            <c:if test="${!empty values}">
            sumskaVal = ${values["kuyiv"]}
            <%--<c:forEach items="${values}" var="val">--%>

            <%--sumskaVal = ${val["kuyiv"]}--%>
            <%--</c:forEach>--%>
            </c:if>
            var data = google.visualization.arrayToDataTable([
                ['City', 'Site'],
                ['sums\'ka oblast\'', sumskaVal],
                ['khersons\'ka oblast\'', 1]
            ]);

            var options = {region: 'UA',  displayMode: 'regions', resolution: 'provinces', showZoomOut: true, colors: ['#00FFFF'], showTip: true ,magnifyingGlass:{enable: true, zoomFactor: 7.5}};

            var chart = new google.visualization.GeoChart(document.getElementById('chart_div'));
            chart.draw(data, options);
        };
    </script>
</head>
<body>
<div style="color: teal; font-size: 30px"> Hello
    <c:if test="${!empty values}">

        <c:forEach items="${values}" var="val">

            <c:out value="${val.key}" />
        </c:forEach>
    </c:if>
    :)</div>

<div id="chart_div" style="width: 600px; height: 400px;"></div>
</body>
</html>