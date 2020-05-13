package controller;

import com.google.gson.Gson;
import dao.DBManager;
import entities.Courses;
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
 * Created by Sarim on 4/20/2020.
 */
@WebServlet("/coursesServlet/*")
public class CoursesServlet extends HttpServlet {

    private Gson gson = new Gson();

    private void sendAsJson(HttpServletResponse response, Object obj) throws IOException {

        response.setContentType("application/json");
        String res = gson.toJson(obj);
        PrintWriter out = response.getWriter();
        out.print(res);
        out.flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CustomResponseDto customResponseDto = new CustomResponseDto();
        String pathInfo = request.getPathInfo();
        if (pathInfo.equals("/add-course")) {

            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            Courses courses = gson.fromJson(payload, Courses.class);
            int row = DBManager.addCourse(courses);
            if (row > 0) {
                customResponseDto.setResponseCode("201");
                customResponseDto.setStatus("created");
                sendAsJson(response, customResponseDto);
            } else {
                customResponseDto.setResponseCode("401");
                customResponseDto.setStatus("not created");
                customResponseDto.setMessage("Course not added!");
                sendAsJson(response, customResponseDto);
            }

        } else if (pathInfo.equals("/update-course")) {

            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            Courses courses = gson.fromJson(payload, Courses.class);
            int row = DBManager.updateCourses(courses);
            if (row > 0) {
                customResponseDto.setResponseCode("201");
                customResponseDto.setStatus("updated");
                sendAsJson(response, customResponseDto);
            } else {
                customResponseDto.setResponseCode("401");
                customResponseDto.setStatus("not created");
                customResponseDto.setMessage("Course not updated!");
                sendAsJson(response, customResponseDto);
            }
        } else if (pathInfo.equals("/delete-course")) {

            String courseId = request.getParameter("courseId");
            int row = DBManager.deleteCourse(Integer.parseInt(courseId));
            if (row > 0) {
                customResponseDto.setResponseCode("201");
                customResponseDto.setStatus("Deleted");
                sendAsJson(response, customResponseDto);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo.equals("/getCourses")) {
            String param = request.getParameter("adminId");
            List<Courses> coursesList = DBManager.getAllCoursedByAdminID(param);
            sendAsJson(response, coursesList);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }
}
