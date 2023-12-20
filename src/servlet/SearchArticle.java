package servlet;

import DB.ArticleDB;
import DB.UserDB;
import Model.Article;
import Model.User;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/v1/article")
public class SearchArticle extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        String keyTitle=req.getParameter("title");
        int pageSize= Integer.parseInt(req.getParameter("pagesize"));//每一页数
        int pageNum= Integer.parseInt(req.getParameter("pagenum"));//当前页数

        respObj respObj; Gson gson=new Gson();
        Article[] articles=null;int totalPage;
        try {
            articles= ArticleDB.GetArticles(keyTitle,pageSize,pageNum);////
            totalPage= UserDB.CountTotalUser();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        respObj=new respObj(articles,"用户列表",200,totalPage);
        String respJson=gson.toJson(respObj);
        resp.getWriter().println(respJson);
    }

    public class respObj{
        int status;
        Article[] data;
        String message;
        int total;


        public respObj(Article[] data, String message, int status,int total) {
            this.data = data;
            this.message = message;
            this.status = status;
            this.total=total;
        }
    }

}
