package servlet;

import DB.ArticleDB;
import DB.UserDB;
import Utils.Json;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/v1/Art/*")//增删改
public class ArticleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //TODO
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        JsonObject jsonObject = Json.Parse(req);
        // 获取特定参数
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();
        int role = jsonObject.get("role").getAsInt();
        Gson gson=new Gson();
        addUserResp respObj;
        System.out.println(username+":"+password+":"+role);
        try {
            UserDB.createUser(username,password,role);
            respObj=new addUserResp("添加成功",200);
        } catch (SQLException e) {
            respObj=new addUserResp("添加失败",300);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            respObj=new addUserResp("添加失败",300);
            throw new RuntimeException(e);
        }
        String respJson=gson.toJson(respObj);
        resp.getWriter().println(respJson);//返回响应
    }
    class addUserReq{
        String username;
        String password;
        int role;
    }

    class addUserResp{
        String message;
        int status;

        public addUserResp(String message, int status) {
            this.message = message;
            this.status = status;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO
        System.out.println("进入到文章delete函数");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        String requestURI = req.getRequestURI();
        String[] pathSegments = requestURI.split("/");
        if (pathSegments.length >= 2) {
            String articleID = pathSegments[pathSegments.length - 1];
            deleteArtResp resObj; Gson gson=new Gson();
            System.out.println("articleID:"+articleID);
            try {
                ArticleDB.deleteArticle(Integer.parseInt(articleID));
                resObj=new deleteArtResp("删除成功",200);
                String respJson=gson.toJson(resObj);
                resp.getWriter().println(respJson);


            } catch (Exception e) {
                resObj=new deleteArtResp("删除失败",300);
                String respJson=gson.toJson(resObj);
                resp.getWriter().println(respJson);
                throw new RuntimeException(e);
            }
        }
    }

    class deleteArtResp{
        String message;
        int status;

        public deleteArtResp(String message, int status) {
            this.message = message;
            this.status = status;
        }
    }



    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
    class modifyUserReq{

    }
    class modifyUserResp{

    }




    public static boolean AddUser(){

        return true;
    }
}
