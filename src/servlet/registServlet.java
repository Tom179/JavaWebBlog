package servlet;

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

@WebServlet("/api/v1/regist")
public class registServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");


        JsonObject jsonObject = Json.Parse(req);
        // 获取特定参数
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();
        System.out.println(username+":"+password);


        respObj r;  Gson gson=new Gson();
        try {

            if(!UserDB.IsUserExist(username)){
                UserDB.createUser(username,password,0);
                r=new respObj("注册成功",200);
                String jsonText=gson.toJson(r);
                resp.getWriter().println(jsonText);//返回响应
            }else{
                r = new respObj("该用户名已经存在", 300);
                String respJson=gson.toJson(r);
                resp.getWriter().println(respJson);//返回响应
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }


    }
    class respObj{

        String message;
        int status;

        public respObj(String message, int status) {
            this.message = message;
            this.status = status;
        }
    }
}
