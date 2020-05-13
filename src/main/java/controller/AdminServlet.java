package controller;

import dao.DBManager;
import entities.Admin;

import com.google.gson.Gson;
import entities.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Syed Asher Ahmed on 4/19/2020.
 */
@WebServlet("/adminServlet/*")
public class AdminServlet extends HttpServlet {
    private Gson gson = new Gson();

    private void sendAsJson(HttpServletResponse response, Object obj) throws IOException {

        response.setContentType("application/json");
        String res = gson.toJson(obj);
        PrintWriter out = response.getWriter();
        out.print(res);
        out.flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/save-admin")) {

            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            Admin admin = gson.fromJson(payload, Admin.class);
            int row = DBManager.saveAdmins(admin);
            if(row > 0){
//                sendAsJson(response, admin);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo.equals("/getStudentsWithCourse")) {
            String param = request.getParameter("adminId");
            List<Student> studentList = DBManager.getAllStudentsWithCourse(Integer.parseInt(param));
            sendAsJson(response, studentList);
        }else if(pathInfo.equals("/getStudentsWithModules")){
            String param = request.getParameter("adminId");
            List<Student> studentList = DBManager.getAllStudentsWithModules(Integer.parseInt(param));
            sendAsJson(response, studentList);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

    }
}
