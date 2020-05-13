package controller;

import com.google.gson.Gson;
import dao.DBManager;
import entities.Courses;
import entities.Student;
import entities.dto.CourseModuleDto;
import entities.dto.CustomResponseDto;

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
 * Created by Sarim on 4/17/2020.
 */
@WebServlet("/studentServlet/*")
public class StudentServlet extends HttpServlet {

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
        if (pathInfo.equals("/save-student")) {

            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            Student student = gson.fromJson(payload, Student.class);
            int row = DBManager.saveStudents(student);
            if (row > 0) {
                sendAsJson(response, student);
            }
        } else if (pathInfo.equals("/assign-courses-modules")) {

            CustomResponseDto customResponseDto = new CustomResponseDto();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            CourseModuleDto courseModuleDto = gson.fromJson(payload, CourseModuleDto.class);
            int row = DBManager.assignCourseAndModules(courseModuleDto);
            if (row > 0) {
                customResponseDto.setResponseCode("201");
                customResponseDto.setStatus("Assigned");
                sendAsJson(response, customResponseDto);
            } else {
                customResponseDto.setResponseCode("401");
                customResponseDto.setStatus("not assigned");
                customResponseDto.setMessage("Course/Modules not assigned!");
                sendAsJson(response, customResponseDto);
            }

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo.equals("/getCoursesModules")) {
            List<Courses> courseWithModulesList = DBManager.getAllCoursesWithModules();
            sendAsJson(response, courseWithModulesList);
        } else if (pathInfo.equals("/getAssignModules")) {
            String param = request.getParameter("studentId");
            List<Courses> coursesList = DBManager.getAllAssignedModules(Integer.parseInt(param));
            sendAsJson(response, coursesList);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }
}
