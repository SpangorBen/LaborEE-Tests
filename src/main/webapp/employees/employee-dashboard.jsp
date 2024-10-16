<%--
  Created by IntelliJ IDEA.
  User: hamza
  Date: 13/10/2024
  Time: 22:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employee Dashboard</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
        }

        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 30px;
            background-color: #fff;
            box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        h1, h2 {
            color: #219ebc;
            font-weight: 600;
        }

        h2 {
            margin-top: 20px;
        }

        form {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 5px;
        }

        input[type="date"],
        textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #007EA7;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #dee2e6;
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #219ebc;
            color: #fff;
            font-weight: 600;
        }

        .allowance-box {
            background-color: #edf2f4;
            border-radius: 8px;
            padding: 20px;
            margin-top: 20px;
            text-align: center;
        }

        .allowance-box p {
            margin-bottom: 0;
            font-size: 1.2em;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Welcome, ${employee.name}!</h1>

    <h2>Submit Leave Request</h2>
    <form action="${pageContext.request.contextPath}/employee/employee-dashboard?action=submitLeave" method="post">
        <label for="startDate">Start Date:</label>
        <input type="date" id="startDate" name="startDate" required><br><br>

        <label for="endDate">End Date:</label>
        <input type="date" id="endDate" name="endDate" required><br><br>

        <label for="reason">Reason:</label>
        <textarea id="reason" name="reason"></textarea><br><br>

        <input type="submit" value="Submit Request">
    </form>

    <h2>My Leave Requests</h2>
    <table>
        <thead>
        <tr>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${leaveRequests}" var="request">
            <tr>
                <td>${request.startDate}</td>
                <td>${request.endDate}</td>
                <td>${request.status}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h2>My Family Allowance</h2>
    <p>Your current family allowance: <b>${familyAllowance} DH</b></p>
</div>
</body>
</html>
