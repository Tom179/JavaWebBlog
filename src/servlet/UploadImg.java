package servlet;

import Utils.gitupload;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Base64;

import static Utils.gitupload.inputStreamToByteArray;
import static Utils.gitupload.uploadToGitHub;

@WebServlet("/api/v1/upload")//资源上传
@MultipartConfig
public class UploadImg extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 获取上传的文件部分
        Part filePart = req.getPart("file");

        byte[] fileContent = inputStreamToByteArray(filePart.getInputStream());
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        String filename = "cover"+ System.currentTimeMillis()+".jpg";//加时间戳

        String str = uploadToGitHub(encodedString,filename, gitupload.token);
        System.out.println("图片上传到github成功");

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        Gson gson=new Gson();
        RespObj respObj=new RespObj(str);
        String respJson=gson.toJson(respObj);
        resp.getWriter().println(respJson);

    }

    class RespObj{
        String Url;
        public RespObj(String url) {
            Url = url;
        }
    }
}
