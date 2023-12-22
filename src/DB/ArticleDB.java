package DB;

import Model.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class ArticleDB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/blog";//库名
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Qy85891607";

    public static Connection GetConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");// 1. 注册驱动

        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); // 2. 获取数据库连接对象
        return connection;
    }

    public static void createArticle(String title,String description,String content,String img) throws SQLException, ClassNotFoundException {
        Connection conn= GetConnection();
        String sql = "INSERT INTO article (title, description,content,created_at,img) VALUES (?,?,?,?,?)";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setString(1,title);
        ps.setString(2,description);
        ps.setString(3,content);
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTimeMillis);
        ps.setTimestamp(4, timestamp);
        ps.setString(5,img);
        ps.execute();
        System.out.println("插入成功");

        ps.close();
        conn.close();
    }


    public static void deleteArticle(int id) throws SQLException, ClassNotFoundException {
        Connection conn= GetConnection();
        String sql="delete from article where id=?";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setInt(1,id);
        ps.execute();
        System.out.println("删除成功");
        ps.close();
        conn.close();
    }


    public static Article GetArticle(int id) {
        try (Connection conn = GetConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM article WHERE id=?")) {
            ps.setInt(1, id);
Article article;
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    String title=resultSet.getString("title");
                    String description=resultSet.getString("description");
                    String content=resultSet.getString("content");
                    String created_at=resultSet.getString("created_at");
                    String img=resultSet.getString("img");
                    article=new Article(id,title,description,content,created_at,img);
                    resultSet.close();
                    ps.close();
                    conn.close();
                    return article;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // 或者使用适当的日志记录方法
        }

        return null; // 如果没有找到用户或者出现异常，返回 null
    }


    public static Article[] GetArticles(String keyName,int NumPerPage,int PageIndex) throws SQLException, ClassNotFoundException {
        List<Article> articleList = new ArrayList<>();
        Connection conn=GetConnection();

        String sql=null;
        PreparedStatement ps;
        if (NumPerPage==0){//查询所有
            sql = "SELECT * FROM article WHERE title LIKE ? order by created_at desc";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyName + "%");
        }else {
            sql = "SELECT * FROM article  WHERE title LIKE ? order by created_at desc LIMIT ? OFFSET ? ";
            ps = conn.prepareStatement(sql);
            int offset = (PageIndex - 1) * NumPerPage;
            ps.setString(1, "%" + keyName + "%");
            ps.setInt(2, NumPerPage);
            ps.setInt(3, offset);
        }

        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            int articleID=resultSet.getInt("id");
            String title=resultSet.getString("title");
            String description=resultSet.getString("description");
            String content=resultSet.getString("content");
            String created_at=resultSet.getString("created_at");
            String img=resultSet.getString("img");
            Article article = new Article(articleID,title,description,content,created_at,img);
            articleList.add(article);
        }

        resultSet.close();
        ps.close();
        conn.close();
        return articleList.toArray(new Article[0]);
    }

    public static int CountTotalArticle() throws SQLException, ClassNotFoundException {
        Connection conn=GetConnection();
        String sql = "SELECT COUNT(*) AS total FROM article";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            int count=resultSet.getInt("total");
            resultSet.close();
            ps.close();
            conn.close();
            return count;
        }
        return 0;
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        User[] users=GetUsers(0,10);
        Article[] articles=GetArticles("",0,10);
        createArticle("第二篇文章","2","asfpqpi","asdoh");
        System.out.println(Arrays.toString(articles));
//        System.out.println(VarifyUser("mike","8828"));
//        System.out.println(CountTotalUser());
    }

}
