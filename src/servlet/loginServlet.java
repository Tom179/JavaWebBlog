package servlet;

import DB.UserDB;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import Utils.Json;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/v1/login")
public class loginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");


        JsonObject jsonObject = Json.Parse(req);
        // 获取特定参数
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();
        System.out.println(username+":"+password);


        respObj r;  Gson gson=new Gson();
        try {
            if(UserDB.VarifyUser(username,password)){
              r=new respObj("登录成功",200,"true");
                String jsonText=gson.toJson(r);
                resp.getWriter().println(jsonText);//返回响应
            }
            else {
                r = new respObj("用户名或密码错误，请重新输入", 300, "false");
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
        String token;

        public respObj(String message, int status, String token) {
            this.message = message;
            this.status = status;
            this.token = token;
        }
    }
}
