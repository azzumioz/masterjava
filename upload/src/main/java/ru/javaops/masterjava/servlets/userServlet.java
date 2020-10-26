package ru.javaops.masterjava.servlets;

import ru.javaops.masterjava.model.User;
import ru.javaops.masterjava.util.UserExport;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/upload", name = "userServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB

public class userServlet extends HttpServlet {

    private static final String SAVE_DIR = "uploadFiles";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String appPath = req.getServletContext().getRealPath("");
        String savePath = appPath + File.separator + SAVE_DIR;
        String fileName = "";

        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        for (Part part : req.getParts()) {
            fileName = extractFileName(part);
            fileName = new File(fileName).getName();
            String filePath = savePath + File.separator + fileName;
            part.write(filePath);
        }

        try {
            List<User> users = UserExport.getUserListStax(savePath + File.separator + fileName);
            req.setAttribute("users", users);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getServletContext().getRequestDispatcher("/userList.jsp").forward(req, res);
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

}
