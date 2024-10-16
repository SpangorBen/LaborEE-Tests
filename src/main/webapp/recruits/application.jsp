<%--
  Created by IntelliJ IDEA.
  User: hamza
  Date: 12/10/2024
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Apply for ${jobOffer.title}</title>
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
    <h2>Apply for ${jobOffer.title}</h2>

    <form action="applyForJob" method="post" enctype="multipart/form-data">
        <input type="hidden" name="jobOfferId" value="${jobOffer.jobOfferId}">

        <label for="applicantName">Your Name:</label><br>
        <input type="text" id="applicantName" name="applicantName" required><br><br>

        <label for="email">Your Email:</label><br>
        <input type="email" id="email" name="email" required><br><br>

        <label for="skills">Your Skills (comma-separated):</label><br>
        <input type="text" id="skills" name="skills"><br><br>

        <label for="resume">Resume (PDF or DOCX):</label><br>
        <input type="file" id="resume" name="resume" accept=".pdf,.docx" required><br><br>

        <label for="coverLetter">Cover Letter (optional):</label><br>
        <textarea id="coverLetter" name="coverLetter" rows="5" cols="40"></textarea><br><br>

        <input type="submit" value="Submit Application" class="btn">
    </form>
</div>
</body>
</html>
