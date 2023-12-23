package servlet;

import DB.ArticleDB;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/api/v1/article/add")
public class AddArticle extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入到添加文章的post函数");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        JsonObject jsonObject = Json.Parse(req);
        // 获取特定参数
        String title = jsonObject.get("title").getAsString();
        String desc = jsonObject.get("description").getAsString();
        String content = filterSensitiveWords(jsonObject.get("content").getAsString());
        String img = jsonObject.get("img").getAsString();
        String created_by=jsonObject.get("UserID").getAsString();
//        System.out.println("上传的作者为："+created_by);


        Gson gson=new Gson();
        addArtResp respObj;
        System.out.println(title+":"+desc+":"+content+":"+img);
        try {
            System.out.println("开始添加");
            ArticleDB.createArticle(title,desc,content, Integer.parseInt(created_by),img);
//            System.out.println("完成添加");
            respObj=new addArtResp("添加文章成功",200);
            String respJson=gson.toJson(respObj);
            resp.getWriter().println(respJson);//返回响应
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            respObj=new addArtResp("文章上传失败",300);
            String respJson=gson.toJson(respObj);
            resp.getWriter().println(respJson);//返回响应
            throw new RuntimeException(e);
        }

    }


    public static String filterSensitiveWords(String input) {
        // 定义敏感词列表
        String[] sensitiveWords = {"敏感词1", "敏感词2", "敏感词3","狗屁"};

        // 构建正则表达式
        String regex = String.join("|", sensitiveWords);
        Pattern pattern = Pattern.compile(regex);

        // 使用正则表达式匹配和替换敏感词
        Matcher matcher = pattern.matcher(input);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            // 替换敏感词为"*"，根据实际需求可以修改替换策略
            matcher.appendReplacement(result, "*");
        }
        matcher.appendTail(result);

        return result.toString();
    }
    class addArtResp{
        int status;
        String message;

        public addArtResp(String message,int status) {
            this.status = status;
            this.message = message;
        }
    }

}
