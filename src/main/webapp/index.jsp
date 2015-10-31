<%@page import="ru.mephi.ugc.burndown.model.Employee" %>
<%@page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Delta Solutions employees</title>
</head>
<body>
<h1 align="center">Burndown diagram</h1>

<div align="center">
    <form method="POST" action="BurndownServlet" align="center" id="myForm">
        <table>
            <%
                try {
                    Employee emp = (Employee) request.getAttribute("employee");
                    if (!emp.equals(null)) {
            %>
            <tr>
                <td>Number: <input required type="text" name="id"
                                   value="<%=emp.getId()%>"/></td>
            </tr>
            <tr>
                <td>Name: <input type="text" name="name"
                                 value="<%=emp.getName()%>"/></td>
            </tr>
            <tr>
                <td>Salary: <input type="text" name="salary"
                                   value="<%=emp.getSalary()%>"/></td>
            </tr>
            <tr>
                <td>Position: <input type="text" name="position"
                                     value="<%=emp.getPosition()%>"/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" name="action" value="Add"/>
                    <input type="submit" name="action" value="Edit"/> <input
                            type="submit" name="action" value="Delete"/> <input
                            type="submit" name="action" value="Search"/></td>

            </tr>
            <%
                }
            } catch (NullPointerException e) {
                System.out.println("jib,rf");
            %>
            <p align="center"> Ошибка. Измените параметры.</p>
            <tr>
                <td>Number: <input required type="text" name="id" value=""/></td>
            </tr>
            <tr>
                <td>Name: <input type="text" name="name" value=""/></td>
            </tr>
            <tr>
                <td>Salary: <input type="text" name="salary" value=""/></td>
            </tr>
            <tr>
                <td>Position: <input type="text" name="position" value=""/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" name="action" value="Add"/>
                    <input type="submit" name="action" value="Edit"/> <input
                            type="submit" name="action" value="Delete"/> <input
                            type="submit" name="action" value="Search"/></td>

            </tr>
            <%
                }
            %>
        </table>

    </form>
</div>
<table border="1" width="303" align="center">
    <tr>
        <td width="119"><b>Id</b></td>
        <td width="168"><b>Name</b></td>
        <td width="168"><b>Salary</b></td>
        <td width="168"><b>Position</b></td>
    </tr>
    <%
        @SuppressWarnings("unchecked")
        List<Employee> data = (List<Employee>) request.getAttribute("EmployeeList");
        for (Employee employee : data) {
            if (!employee.equals(null)) {
    %>

    <tr>
        <td width="119"><%=employee.getId()%>
        </td>
        <td width="119"><%=employee.getName()%>
        </td>
        <td width="119"><%=employee.getSalary()%>
        </td>
        <td width="119"><%=employee.getPosition()%>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>

</body>
</html>