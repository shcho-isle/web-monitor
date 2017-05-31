<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Monitor</title>

    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body class="body">
<section>
    <h2><a href="index.html">Home</a></h2>

    <table class="infoBox" border=1 cellpadding=0 cellspacing=0>
        <tr>
            <td class="infoBox">
                <div class="infoBoxTitle">Info</div>
                Last Updated: ${lastUpdated}<br>
                Updated every ${period} seconds<br>
                Pavlo Plynko - <a href="https://github.com/shcho-isle" target="_new">GitHub</a><br>
            </td>
        </tr>
    </table>

    <table border="0" cellpadding="8" cellspacing="2">
        <thead class="thead">
        <tr>
            <th>Entity</th>
            <th>Status</th>
            <th>Last Check</th>
            <th>Status Information</th>
        </tr>
        </thead>
        <tbody class="tbody">
        <c:forEach items="${states}" var="state">
            <jsp:useBean id="state" scope="page" type="com.plynko.model.State"/>
            <c:set var="status" scope="page" value="${state.status.toString()}"/>
            <tr class="${status}">
                <td>${state.name}</td>
                <td class="${status}">${state.status}</td>
                <td><fmt:formatDate value="${state.dateTime}" pattern="MM-dd-yyyy hh:mm:ss"/></td>
                <td>${state.information}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>
</body>
</html>
