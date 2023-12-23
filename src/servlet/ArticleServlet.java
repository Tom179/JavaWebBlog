package servlet;

import DB.ArticleDB;
import DB.UserDB;
import Model.Article;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/v1/Art/*")//增删改
public class ArticleServlet extends HttpServlet {


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



    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入查询文章函数");
        //TODO
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        String requestURI = req.getRequestURI();
        String[] pathSegments = requestURI.split("/");
        if (pathSegments.length >= 2) {
            String articleID = pathSegments[pathSegments.length - 1];
            getArticleResp resObj; Gson gson=new Gson();
            System.out.println("articleID:"+articleID);
            try {
                Article article=ArticleDB.GetArticle(Integer.parseInt(articleID));
                resObj=new getArticleResp(200,"文章信息查询成功", article, UserDB.GetUserById(article.created_by).getUsername());
//                System.out.println(article.toString());
                String respJson=gson.toJson(resObj);
                resp.getWriter().println(respJson);


            } catch (Exception e) {
                resObj=new getArticleResp(300,"文章信息获取失败",null,null);
                String respJson=gson.toJson(resObj);
                resp.getWriter().println(respJson);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    class getArticleResp{
        int status;
        String message;
        Article data;
        String authorName;

        public getArticleResp(int status, String message, Article data,String authorName) {
            this.status = status;
            this.message = message;
            this.data = data;
            this.authorName=authorName;
        }
    }

    @Override//修改文章
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
