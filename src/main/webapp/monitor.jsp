<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Monitor</title>
    <style>
        .OK {
            background-color: lightgreen;
        }

        .WARNING {
            background-color: yellow;
        }

        .CRITICAL {
            background-color: red;
        }

        .PENDING {
            background-color: lightgray;
        }

        .UNKNOWN {
            background-color: orange;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h2>Monitor</h2>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>URL</th>
            <th>Status</th>
            <th>Last Check</th>
            <th>Status Information</th>
        </tr>
        </thead>
        <c:forEach items="${states}" var="state">
            <jsp:useBean id="state" scope="page" type="com.plynko.model.State"/>
            <tr class="${state.currentStatus.toString()}">
                <td>${state.url}</td>
                <td>${state.currentStatus}</td>
                <td>${state.dateTime}</td>
                <td>${state.information}</td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
