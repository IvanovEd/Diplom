<%--
  Created by IntelliJ IDEA.
  User: Ivanov
  Date: 04.11.15
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
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
<head>
    <script type='text/javascript' src='https://www.google.com/jsapi'></script>
    <script type="text/javascript">
        google.load("visualization", "1", {packages: ["corechart"]});
        google.setOnLoadCallback(drawChart);
        function drawChart() {
            var NEWS,POLITIC,GAMES,MUSIC,SPORT,SCIENCE,BUSINESS,CINEMA,HUMOR,OTHER;
            <c:if test="${!empty valuesForPio}">
            NEWS = ${valuesForPio["NEWS"]},
                    POLITIC = ${valuesForPio["POLITIC"]},
                    MUSIC = ${valuesForPio["MUSIC"]},
                    SPORT = ${valuesForPio["SPORT"]},
                    SCIENCE = ${valuesForPio["SCIENCE"]},
                    BUSINESS = ${valuesForPio["BUSINESS"]},
                    CINEMA = ${valuesForPio["CINEMA"]},
                    OTHER = ${valuesForPio["OTHER"]},
                    HUMOR = ${valuesForPio["HUMOR"]},
                    GAMES = ${valuesForPio["GAMES"]}
            </c:if>;
            var data = google.visualization.arrayToDataTable([
                ['Task', 'Hours per Day'],
                ['NEWS', NEWS],
                ['POLITIC', POLITIC],
                ['GAMES', GAMES],
                ['MUSIC', MUSIC],
                ['SPORT', SPORT],
                ['SCIENCE', CINEMA],
                ['BUSINESS', BUSINESS],
                ['CINEMA', POLITIC],
                ['HUMOR', HUMOR],
                ['OTHER', OTHER]
            ]);

            var options = {
                title: 'Statistics'
            };

            var chart = new google.visualization.PieChart(document.getElementById('chart_div_pio'));

            chart.draw(data, options);
        }
    </script>
    <script type='text/javascript'>
        google.load('visualization', '1', {'packages': ['geochart']});
        google.setOnLoadCallback(drawRegionsMap);


        function drawRegionsMap() {
            var kyivska, krym, zakarpatska, odesska, vinnitska, lvivska, ivanofrankivska, zhitomerska, harkiv, sumska, donetska, luhanska, mikolaivska, volinska, chernigivska, cherkaska, zakarpatska, zaporizhska, kirovogradska, ternopilska, hmelnitska, dnipropetrovska, poltavska, rivnenska, hersonska, chernivetska;
            <c:if test="${!empty valuesMap}">
            kyivska = ${valuesMap["kuyivska"]},
                    odesska = ${valuesMap["odesska"]},
                    vinnitska = ${valuesMap["vinnitska"]},
                    lvivska = ${valuesMap["lvivska"]},
                    ivanofrankivska = ${valuesMap["ivanofrankivska"]},
                    zhitomerska = ${valuesMap["zhitomerska"]},
                    harkiv = ${valuesMap["harkiv"]},
                    sumska = ${valuesMap["sumska"]},
                    donetska = ${valuesMap["donetska"]},
                    luhanska = ${valuesMap["luhanska"]},
                    mikolaivska = ${valuesMap["mikolaivska"]},
                    volinska = ${valuesMap["volinska"]},
                    chernigivska = ${valuesMap["chernigivska"]},
                    cherkaska = ${valuesMap["cherkaska"]},
                    zakarpatska = ${valuesMap["zakarpatska"]},
                    zaporizhska = ${valuesMap["zaporizhska"]},
                    kirovogradska = ${valuesMap["kirovogradska"]},
                    ternopilska = ${valuesMap["ternopilska"]},
                    hmelnitska = ${valuesMap["hmelnitska"]},
                    dnipropetrovska = ${valuesMap["dnipropetrovska"]},
                    poltavska = ${valuesMap["poltavska"]},
                    rivnenska = ${valuesMap["rivnenska"]},
                    hersonska = ${valuesMap["hersonska"]},
                    krym = ${valuesMap["krym"]},
                    chernivetska = ${valuesMap["chernivetska"]};
            </c:if>
            var data = google.visualization.arrayToDataTable([
                ['City', 'Site'],
                ['khersons\'ka oblast\'', hersonska],
                ['zakarpats\'ka oblast\'', zakarpatska],
                ['respublika krym', krym],
                ['odes \'ka oblast\'', odesska],
                ['sums \'ka oblast\'', sumska],
                ['rivnens\'ka oblast\'', rivnenska],
                ['khmel\'nyts\'ka oblast\'', hmelnitska],
                ['ivano-frankivs\'ka oblast\'', ivanofrankivska],
                ['poltavs\'ka oblast\'', poltavska],
                ['kharkivs\'ka oblast\'', harkiv],
                ['zhytomyrs\'ka oblast\'', zhitomerska],
                ['ternopil\'s\'ka oblast\'', ternopilska],
                ['cherkas\'ka oblast\'', cherkaska],
                ['zaporiz\'ka oblast\'', zaporizhska],
                ['luhans\'ka oblast\'', luhanska],
                ['vinnyts\'ka oblast\'', vinnitska],
                ['donets\'ka oblast\'', donetska],
                ['l\'vivs\'ka oblast\'', lvivska],
                ['volyns\'ka oblast\'', volinska],
                ['dnipropetrovs\'ka oblast\'', dnipropetrovska],
                ['mykola\u00efvs\'ka oblast\'', mikolaivska],
                ['kirovohrads\'ka oblast\'', kirovogradska],
                ['ky\u00efvs\'ka oblast\'', kyivska],
                ['chernivets\'ka oblast\'', chernivetska],
                ['chernihivs\'ka oblast\'', chernigivska],
                ['ky\u00efv', 0]
            ]);
            var options = {region: 'UA', displayMode: 'region', resolution: 'provinces', showZoomOut: true, colors: ['#0066ff'], showTip: true, magnifyingGlass: {enable: true, zoomFactor: 7.5}};

            var chart = new google.visualization.GeoChart(document.getElementById('chart_div'));
            chart.draw(data, options);
        }
    </script>

</head>
<body class="center">
<div style="color: teal; font-size: 30px">

    <!-- check values, uncommented for test -->
    <%--<c:if test="${!empty values}">--%>

        <%--<c:forEach items="${values}" var="val">--%>

            <%--<c:out value="${val.key}"/>--%>
        <%--</c:forEach>--%>
    <%--</c:if>--%>

    <%--<c:if test="${!empty valuesForPio}">--%>
        <%--<c:forEach items="${valuesForPio}" var="val">--%>

            <%--<c:out value="${val.value}"/>--%>
        <%--</c:forEach>--%>
    <%--</c:if>--%>
    <input type=button onClick="parent.location='ukraineMap.html?interest=1'" value='News' class="input">
</div>

<div id="chart_div_pio" style="width: 500px; height: 350px;"></div>
<div >
    <input type=button onClick="parent.location='ukraineMap.html?interest=1'" value='News' class="for_interest">
    <input type=button onClick="parent.location='ukraineMap.html?interest=2'" value='Politics' class="for_interest">
    <input type=button onClick="parent.location='ukraineMap.html?interest=3'" value='GAMES' class="for_interest">
    <input type=button onClick="parent.location='ukraineMap.html?interest=4'" value='MUSIC' class="for_interest">
    <input type=button onClick="parent.location='ukraineMap.html?interest=5'" value='SPORT' class="for_interest">
    <input type=button onClick="parent.location='ukraineMap.html?interest=6'" value='SCIENCE' class="for_interest">
    <input type=button onClick="parent.location='ukraineMap.html?interest=7'" value='BUSINESS' class="for_interest">
    <input type=button onClick="parent.location='ukraineMap.html?interest=8'" value='CINEMA' class="for_interest">
    <input type=button onClick="parent.location='ukraineMap.html?interest=9'" value='HUMOR' class="for_interest">
    <input type=button onClick="parent.location='ukraineMap.html?interest=10'" value='OTHER' class="for_interest">
</div>
<div id="chart_div" style="width: 600px; height: 400px;"></div>
</body>
</html>