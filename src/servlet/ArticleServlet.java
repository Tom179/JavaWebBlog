package servlet;

import DB.ArticleDB;
import DB.UserDB;
import Model.Article;
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

@WebServlet("/api/v1/Art/*")//删查改
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

        System.out.println("进入到修改文章的post函数");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        JsonObject jsonObject = Json.Parse(req);
        // 获取特定参数
        String id=jsonObject.get("id").getAsString();
        String title = jsonObject.get("title").getAsString();
        String desc = jsonObject.get("description").getAsString();
        String content = jsonObject.get("content").getAsString();
        String img = jsonObject.get("img").getAsString();
        String created_by=jsonObject.get("UserID").getAsString();


        Gson gson=new Gson();
        updateArtResp respObj;
        System.out.println("id:"+id+"\nimg:"+img);
        try {
            ArticleDB.modifyArticle(Integer.parseInt(id),title,desc,content,Integer.parseInt(created_by),img);
            respObj=new updateArtResp("修改文章成功",200);
            String respJson=gson.toJson(respObj);
            resp.getWriter().println(respJson);//返回响应
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            respObj=new updateArtResp("文章修改失败",300);
            String respJson=gson.toJson(respObj);
            resp.getWriter().println(respJson);//返回响应
            throw new RuntimeException(e);
        }


    }


    class updateArtResp{
        int status;
        String message;

        public updateArtResp(String message,int status) {
            this.status = status;
            this.message = message;
        }
    }

}
