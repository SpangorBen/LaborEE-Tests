<%--
  Created by IntelliJ IDEA.
  User: hamza
  Date: 11/10/2024
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
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

        .leave-requests-container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
        }

        .leave-request-card {
            background-color: #edf2f4;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: calc(33.33% - 20px); /* Adjust width as needed */
            box-sizing: border-box;
            color: #333;
        }

        .leave-request-card h3 {
            color: #219ebc;
            margin-top: 0;
        }

        .leave-request-card .actions {
            margin-top: 15px;
        }

        .leave-request-card .actions a {
            display: inline-block;
            margin-right: 10px;
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            font-weight: 500;
            cursor: pointer;
        }

        .leave-request-card .actions .approve-btn {
            background-color: #4CAF50;
            color: white;
        }

        .leave-request-card .actions .reject-btn {
            background-color: #f44336;
            color: white;
        }

        a {
            color: #007EA7;
            text-decoration: none;
            font-weight: 500;
        }

        a:hover {
            text-decoration: underline;
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


        .btn {
            display: inline-block;
            padding: 8px 16px;
            background-color: #007EA7;
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
    <h1>Welcome, Admin!</h1>

    <h2>Pending Leave Requests:</h2>
<%--    <ul>--%>
<%--        <c:forEach items="${pendingLeaveRequests}" var="request">--%>
<%--            <li>--%>
<%--                Employee: ${request.user.name} <br/>--%>
<%--                Start Date: ${request.startDate} <br/>--%>
<%--                End Date: ${request.endDate} <br/>--%>
<%--                Reason: ${request.reason} <br/>--%>
<%--                <a href="<c:url value='/admin/approveLeave?id=${request.leaveRequestId}'/>">Approve</a>--%>
<%--                <a href="<c:url value='/admin/rejectLeave?id=${request.leaveRequestId}' />">Reject</a>--%>
<%--            </li>--%>
<%--        </c:forEach>--%>
<%--    </ul>--%>

    <div class="leave-requests-container"> <%-- Container for the cards --%>
        <c:forEach items="${pendingLeaveRequests}" var="request">
            <div class="leave-request-card"> <%-- Card for each request --%>
                <h3>Employee: ${request.user.name}</h3>
                <p>Start Date: ${request.startDate}</p>
                <p>End Date: ${request.endDate}</p>
                <p>Reason: ${request.reason}</p>
                <div class="actions">
                    <a href="<c:url value='/admin/approveLeave?id=${request.leaveRequestId}'/>" class="approve-btn">Approve</a>
                    <a href="<c:url value='/admin/rejectLeave?id=${request.leaveRequestId}' />" class="reject-btn">Reject</a>
                </div>
            </div>
        </c:forEach>
    </div>

    <h2>Employee Management</h2>
    <a href="<c:url value='/admin/createEmployee' />">Create New Employee</a> <br/>

    <h2>All Employees:</h2>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>SSN</th>
            <th>Salary</th>
            <th>Department</th>
            <th>Job Title</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${employees}" var="employee">
            <tr>
                <td>#</td>
                <td>${employee.name}</td>
                <td>${employee.email}</td>
                <td>${employee.phone}</td>
                <td>${employee.socialSecurityNumber}</td>
                <td>${employee.salary}</td>
                <td>${employee.department.name}</td>
                <td>${employee.jobTitle.title}</td>
                <td>
                    <a href="<c:url value='/admin/editEmployee?id=${employee.id}'/>">Edit</a> |
                    <a href="<c:url value='/admin/deleteEmployee?id=${employee.id}'/>" onclick="return confirm('Are you sure you want to delete this employee?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
</body>
</html>