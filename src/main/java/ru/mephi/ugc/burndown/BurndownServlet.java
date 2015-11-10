package ru.mephi.ugc.burndown;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/BurndownServlet")
public class BurndownServlet extends HttpServlet {

    private static final long serialVersionUID = 628201543341459853L;


    public BurndownServlet() {
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        String employeeIdStr = request.getParameter("id");
        int employeeId = 0;
        if (employeeIdStr != null && !employeeIdStr.equals("")) {
            employeeId = Integer.parseInt(employeeIdStr);
        }
        String name = request.getParameter("name");

        String salaryStr = request.getParameter("salary");
        double salary = 0;
        if (salaryStr != null && !salaryStr.equals("")) {
            salary = Double.parseDouble(salaryStr);
        }
        String position = request.getParameter("position");


        if ("Add".equalsIgnoreCase(action)) {
        } else if ("Edit".equalsIgnoreCase(action)) {
        } else if ("Delete".equalsIgnoreCase(action)) {
        } else if ("Search".equalsIgnoreCase(action)) {
        }

        request.getRequestDispatcher("index.xhtml").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("index.xhtml").forward(request, response);
        // processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        //processRequest(request, response);
    }

}
