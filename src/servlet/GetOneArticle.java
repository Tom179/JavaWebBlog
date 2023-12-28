package servlet;

import DB.ArticleDB;
import Model.Article;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/v1/article/info/*")
public class GetOneArticle extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        String requestURI = req.getRequestURI();
        System.out.println(requestURI);
        String[] pathSegments = requestURI.split("/");
        if (pathSegments.length >= 2) {
            String id = pathSegments[pathSegments.length - 1];
            System.out.println("要查询的id文章为"+id);
            GetArticleResp resObj; Gson gson=new Gson();
            try {
               Article a= ArticleDB.GetArticle(Integer.parseInt(id));

               resObj=new GetArticleResp("查询成功",200,a);
                String respJson=gson.toJson(resObj);
                resp.getWriter().println(respJson);
                System.out.println("文章信息查询成功:"+a.toString());


            } catch (Exception e) {
                resObj=new GetArticleResp("查询失败",300,null);
                String respJson=gson.toJson(resObj);
                resp.getWriter().println(respJson);
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

    class GetArticleResp   {
        String message;
        int status;
        Article article;

        public GetArticleResp(String message, int status, Article article) {
            this.message = message;
            this.status = status;
            this.article = article;
        }
    }
}
