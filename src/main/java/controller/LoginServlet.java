package controller;

import com.google.gson.Gson;
import dao.DBManager;
import entities.dto.UserLoginResponseDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by Sarim on 4/19/2020.
 */
@WebServlet("/LoginServlet/*")
public class LoginServlet extends HttpServlet {
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
        if (pathInfo.equals("/login")) {

            UserLoginResponseDto userLoginResponseDto = null;
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();
            HashMap<String, Object> map = new Gson().fromJson(payload.toString(), HashMap.class);
            if (map.get("type").equals("S")) {
                try {
                    userLoginResponseDto = DBManager.studentLogIn(map.get("username").toString(), map.get("password").toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (userLoginResponseDto.getResponseCode().equals("200")) {
                    sendAsJson(response, userLoginResponseDto);
                } else if (userLoginResponseDto.getResponseCode().equals("401")) {
                    sendAsJson(response, userLoginResponseDto);
                }
            }else if (map.get("type").equals("A")) {
                try {
                    userLoginResponseDto = DBManager.adminLogIn(map.get("username").toString(), map.get("password").toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (userLoginResponseDto.getResponseCode().equals("200")) {
                    sendAsJson(response, userLoginResponseDto);
                } else if (userLoginResponseDto.getResponseCode().equals("401")) {
                    sendAsJson(response, userLoginResponseDto);
                }
            }

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
