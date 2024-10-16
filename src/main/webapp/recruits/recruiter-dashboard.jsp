<%--
  Created by IntelliJ IDEA.
  User: hamza
  Date: 12/10/2024
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Recruiter Dashboard</title>
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
            color: #219ebc; /* Your primary brand color */
            font-weight: 600;
        }

        h2 {
            margin-top: 20px;
        }

        /* Job Offer Card Styling */
        .job-offers-container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            margin-bottom: 1rem;
        }

        .job-offer-card {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: calc(33.33% - 20px);
            box-sizing: border-box;
            color: #333;
        }

        .job-offer-card h3 {
            color: #219ebc; /* Your primary brand color */
            margin-top: 0;
        }

        .job-offer-card .actions {
            margin-top: 15px;
        }

        .job-offer-card .actions a {
            display: inline-block;
            margin-right: 10px;
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            font-weight: 500;
            cursor: pointer;
        }

        .job-offer-card .actions .edit-btn {
            background-color: #007EA7; /* Your secondary brand color */
            color: white;
        }

        .job-offer-card .actions .delete-btn {
            background-color: #f44336; /* Red for delete */
            color: white;
        }

        /* Application Table Styling */
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
            background-color: #219ebc; /* Your primary brand color */
            color: #fff;
            font-weight: 600;
        }

        .btn {
            display: inline-block;
            padding: 8px 16px;
            background-color: #007EA7; /* Your secondary brand color */
            color: white;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            cursor: pointer;
        }

        .btn:hover {
            /*background-color: darken(#f0b42a, 10%); !* Slightly darken on hover *!*/
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Welcome, Recruiter!</h1>

    <h2>Your Job Offers:</h2>
    <div class="job-offers-container">
        <c:forEach items="${jobOffers}" var="jobOffer">
            <div class="job-offer-card">
                <h3>${jobOffer.title}</h3>
                <p>Department: ${jobOffer.department.name}</p>
                <p>Status: ${jobOffer.status}</p>
                <a href="<c:url value='/recruiter/viewApplications?jobOfferId=${jobOffer.jobOfferId}'/>" class="btn">View Applications (${jobOffer.applications.size()})</a>
                <div class="actions">
                    <a href="<c:url value='/recruiter/editJobOffer?id=${jobOffer.jobOfferId}'/>" class="edit-btn">Edit</a>
                    <a href="<c:url value='/recruiter/deleteJobOffer?id=${jobOffer.jobOfferId}'/>" class="delete-btn" onclick="return confirm('Are you sure you want to delete this job offer?')">Delete</a>
                </div>
            </div>
        </c:forEach>
    </div>

    <a href="<c:url value='/recruiter/createJobOffer'/>" class="btn">Create New Job Offer</a>

    <c:if test="${not empty applications}">
        <h2>Applications:</h2>
        <table>
            <thead>
            <tr>
                <th>Applicant Name</th>
                <th>Application Date</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${applications}" var="application">
                <tr>
                    <td>${application.applicantName}</td>
                    <td>${application.applicationDate}</td>
                    <td>${application.status}</td>
                    <td>
                        <a href="viewApplicationDetails?id=${application.applicationId}">View Details</a>

                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty applications}">
        <h2>No applications found.</h2>
    </c:if>
</div>
</body>
</html>