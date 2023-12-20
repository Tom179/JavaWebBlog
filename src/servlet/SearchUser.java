package servlet;

import DB.UserDB;
import Model.User;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/v1/users")
public class SearchUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        String keyName=req.getParameter("username");
        int pageSize= Integer.parseInt(req.getParameter("pagesize"));//每一页数
        int pageNum= Integer.parseInt(req.getParameter("pagenum"));//当前页数
//        System.out.println("获取的请求参数  keyName："+keyName+" pageSize:"+pageSize+" pageNum"+pageNum);

        //默认发送Num:1、Size:0,也就是一页查询所有内容
        respObj respObj; Gson gson=new Gson();
        User[] users=null;int totalPage;
        try {
            users= UserDB.GetUsers(keyName,pageSize,pageNum);////
            totalPage= UserDB.CountTotalUser();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        respObj=new respObj(users,"用户列表",200,totalPage);
        String respJson=gson.toJson(respObj);
        resp.getWriter().println(respJson);
    }

    public class respObj{
        int status;
        User[] data;
        String message;
        int total;


        public respObj(User[] data, String message, int status,int total) {
            this.data = data;
            this.message = message;
            this.status = status;
            this.total=total;
        }
    }

}
