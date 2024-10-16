<%--
  Created by IntelliJ IDEA.
  User: hamza
  Date: 11/10/2024
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Employee</title>
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
    <h2>Create New Employee</h2>
    <form action="${pageContext.request.contextPath}/admin/createEmployee" method="post">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required><br><br>

        <label for="birthDate">Birth Date:</label>
        <input type="date" id="birthDate" name="birthDate" required><br><br>

        <label for="socialSecurityNumber">Social Security Number:</label>
        <input type="text" id="socialSecurityNumber" name="socialSecurityNumber" required><br><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>

        <label for="phone">Phone:</label>
        <input type="tel" id="phone" name="phone" required><br><br>

        <label for="hireDate">Hire Date:</label>
        <input type="date" id="hireDate" name="hireDate" required><br><br>

        <label for="numOfChildren">Number of Children:</label>
        <input type="number" id="numOfChildren" name="numOfChildren" min="0" required><br><br>

        <label for="salary">Salary:</label>
        <input type="number" id="salary" name="salary" min="0" step="0.01" required><br><br>

        <label for="street">Street:</label>
        <input type="text" id="street" name="street" required><br><br>

        <label for="city">City:</label>
        <input type="text" id="city" name="city" required><br><br>

        <label for="postalCode">Postal Code:</label>
        <input type="text" id="postalCode" name="postalCode" required><br><br>

        <label for="country">Country:</label>
        <input type="text" id="country" name="country" required><br><br>

        <label for="department">Department:</label>
        <select id="department" name="departmentId" required>
            <c:forEach items="${departments}" var="department">
                <option value="${department.id}">${department.name}</option>
            </c:forEach>
        </select><br><br>

        <label for="jobTitle">Job Title:</label>
        <select id="jobTitle" name="jobTitleId" required>
            <c:forEach items="${jobTitles}" var="jobTitle">
                <option value="${jobTitle.id}">${jobTitle.title}</option>
            </c:forEach>
        </select><br><br>

        <input type="submit" value="Create Employee">
    </form>
</div>
</body>
</html>
