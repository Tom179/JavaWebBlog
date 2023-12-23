package servlet;

import DB.UserDB;
import Model.User;
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

@WebServlet("/api/v1/user/*")//增删改
public class OperateUser extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            if(!UserDB.IsUserExist(username)){
            UserDB.createUser(username,password,role);
            respObj=new addUserResp("添加成功",200);}
            else
                respObj=new addUserResp("用户名已存在,添加失败",300);
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
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        String requestURI = req.getRequestURI();
        String[] pathSegments = requestURI.split("/");
        if (pathSegments.length >= 2) {
            String userId = pathSegments[pathSegments.length - 1];
            deleteUserResp resObj; Gson gson=new Gson();
            try {
                UserDB.deleteUser(Integer.parseInt(userId));
                resObj=new deleteUserResp("删除成功",200);
                String respJson=gson.toJson(resObj);
                resp.getWriter().println(respJson);


            } catch (Exception e) {
                resObj=new deleteUserResp("删除失败",300);
                String respJson=gson.toJson(resObj);
                resp.getWriter().println(respJson);
                throw new RuntimeException(e);
            }
        }
    }

    class deleteUserResp{
        String message;
        int status;

        public deleteUserResp(String message, int status) {
            this.message = message;
            this.status = status;
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        String requestURI = req.getRequestURI();
        String[] pathSegments = requestURI.split("/");
        if (pathSegments.length >= 2) {
            String userId = pathSegments[pathSegments.length - 1];
             Gson gson=new Gson();
//            System.out.println(u.toString());
            getUserResp respObj=new getUserResp(200,"查询用户成功",UserDB.GetUserById(Integer.parseInt(userId)));
            String respJson=gson.toJson(respObj);
            resp.getWriter().println(respJson);
        }
    }

    class getUserResp{
        int status;
        String message;
        User data;

        public getUserResp(int status, String message, User user) {
            this.status = status;
            this.message = message;
            data = user;
        }
    }

}
