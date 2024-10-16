<%--
  Created by IntelliJ IDEA.
  User: hamza
  Date: 12/10/2024
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Job Offer</title>
    <style>
        body {
            font-family: sans-serif;
            background-color: #f4f4f4;
        }

        .container {
            max-width: 600px;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            color: #333;
            text-align: center;
        }

        input[type="text"], input[type="email"], input[type="tel"], input[type="date"], input[type="number"], select {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .error {
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Create New Job Offer</h2>
    <form action="${pageContext.request.contextPath}/recruiter/createJobOffer" method="post">
        <label for="title">Title:</label><br>
        <input type="text" id="title" name="title" required><br><br>

        <label for="description">Description:</label><br>
        <textarea id="description" name="description" rows="5" cols="40" required></textarea><br><br>

        <label for="department">Department:</label><br>
        <select id="department" name="departmentId" required>
            <c:forEach items="${departments}" var="department">
                <option value="${department.id}">${department.name}</option>
            </c:forEach>
        </select><br><br>

        <label for="jobTitle">Job Title:</label><br>
        <select id="jobTitle" name="jobTitleId" required>
            <c:forEach items="${jobTitles}" var="jobTitle">
                <option value="${jobTitle.id}">${jobTitle.title}</option>
            </c:forEach>
        </select><br><br>

        <label for="status">Status:</label><br>
        <select id="status" name="status" required>
            <c:forEach items="${jobOfferStatuses}" var="jobOfferStatus">
                <option value="${jobOfferStatus}">${jobOfferStatus}</option>
            </c:forEach>
        </select><br><br>

        <label for="publicationDate">Publication Date:</label><br>
        <input type="date" id="publicationDate" name="publicationDate" value="${today}" required><br><br>

        <label for="validityDurationDays">Validity Duration (Days):</label><br>
        <input type="number" id="validityDurationDays" name="validityDurationDays" min="1" required><br><br>

        <input type="submit" value="Create Job Offer" class="btn">
    </form>
</div>
</body>
</html>
