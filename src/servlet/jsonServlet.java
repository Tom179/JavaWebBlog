package servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


@WebServlet("/articles")
public class jsonServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson=new Gson();


       articleResp respObj=new articleResp(true,200,"返回文章列表",new article[]{new article(1,"文章1",10,10,1,"2013","jack","这里是正文",new tag[]{new tag(1,null,"标签1")})});
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        System.out.println("进入到post函数");
        String jsonText=gson.toJson(respObj);
        resp.getWriter().println(jsonText);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          doPost(req,resp);
    }

    class articleReq{
        int Page;
        int PageSize;
    }
    static class articleResp{

        boolean success;
        int code;
        String msg;
        article[] articles;

        public articleResp(boolean success, int code, String msg, article[] articles) {
            this.success = success;
            this.code = code;
            this.msg = msg;
            this.articles = articles;
        }
    }

    public static <T> T JsonToObj(String json, Class<T> cls) {
        Gson gson = new Gson();
        if (Objects.isNull(json)) return null;
        T obj = gson.fromJson(json, cls);
        if (Objects.isNull(obj)) {
            return null;
        } else {
            return obj;
        }
    }
}
