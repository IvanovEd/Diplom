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
<body>
<div style="color: teal; font-size: 30px"> Hello
    <c:if test="${!empty values}">

        <c:forEach items="${values}" var="val">

            <c:out value="${val.key}"/>
        </c:forEach>
    </c:if>

    <c:if test="${!empty valuesForPio}">
        <c:forEach items="${valuesForPio}" var="val">

            <c:out value="${val.value}"/>
        </c:forEach>
    </c:if>
    :)
</div>
<div id="chart_div_pio" style="width: 500px; height: 350px;"></div>
<div id="chart_div" style="width: 600px; height: 400px;"></div>
</body>
</html>