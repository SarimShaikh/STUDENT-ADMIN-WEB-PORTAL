package controller;

import com.google.gson.Gson;
import dao.DBManager;
import entities.Courses;
import entities.Modules;
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
 * Created by Syed Asher Ahmed on 4/24/2020.
 */
@WebServlet("/modules-servlet/*")
public class ModulesServlet extends HttpServlet {

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
        if (pathInfo.equals("/add-module")) {

            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            Modules modules = gson.fromJson(payload, Modules.class);
            System.out.println("payload" + payload);
            int row = DBManager.addModules(modules);
            if (row > 0) {
                customResponseDto.setResponseCode("201");
                customResponseDto.setStatus("created");
                sendAsJson(response, customResponseDto);
            } else {
                customResponseDto.setResponseCode("401");
                customResponseDto.setStatus("not created");
                customResponseDto.setMessage("Module not added!");
            }

        } else if (pathInfo.equals("/update-module")) {

            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            Modules modules = gson.fromJson(payload, Modules.class);
            int row = DBManager.updateModules(modules);
            if (row > 0) {
                customResponseDto.setResponseCode("201");
                customResponseDto.setStatus("updated");
                sendAsJson(response, customResponseDto);
            } else {
                customResponseDto.setResponseCode("401");
                customResponseDto.setStatus("not created");
                customResponseDto.setMessage("Module not updated!");
            }
        } else if (pathInfo.equals("/delete-module")) {

            String moduleId = request.getParameter("moduleId");
            int row = DBManager.deleteModule(Integer.parseInt(moduleId));
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
        if (pathInfo.equals("/getCoursesByAdminId")) {
            String param = request.getParameter("adminId");
            List<Courses> courseList = DBManager.getAllCoursesForCombo(param);
            sendAsJson(response, courseList);
        } else if (pathInfo.equals("/getModules")) {
            String param = request.getParameter("adminId");
            System.out.println("param" + param);
            List<Courses> courseList = DBManager.getAllCoursesWithModulesByAdminID(param);
            sendAsJson(response, courseList);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

    }
}
