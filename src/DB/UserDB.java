package DB;

import Model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class UserDB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/blog";//库名
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Qy85891607";

    public static Connection GetConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");// 1. 注册驱动

        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); // 2. 获取数据库连接对象
        return connection;
    }

    public static void createUser(String username,String password,int role) throws SQLException, ClassNotFoundException {
        Connection conn= GetConnection();
        String sql = "INSERT INTO users (username, password,role) VALUES (?,?,?)";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setString(1,username);
        ps.setString(2,password);
        ps.setInt(3,role);
        ps.execute();
        System.out.println("插入成功");

        ps.close();
        conn.close();
    }


    public static void deleteUser(int id) throws SQLException, ClassNotFoundException {
        Connection conn= GetConnection();
        String sql="delete from users where id=?";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.setInt(1,id);
        ps.execute();
        System.out.println("删除成功");
        ps.close();
        conn.close();
    }


    public static String VarifyUser(String username,String password) throws SQLException, ClassNotFoundException {
       Connection conn=GetConnection();
       String sql="select * from users where username=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,username);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
//            System.out.println("该用户真实的密码为:"+resultSet.getString("password"));
            if (resultSet.getString("password").equals(password)){
                String role=resultSet.getString("role");
                resultSet.close();
                ps.close();
                conn.close();
                return role;
            }
        }

        return null;
    }


    public static boolean IsUserExist(String username) throws SQLException, ClassNotFoundException {
        Connection conn=GetConnection();
        String sql = "SELECT * FROM users where username=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,username);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            resultSet.close();
            ps.close();
            conn.close();
            return true;
        } else {
            resultSet.close();
            ps.close();
            conn.close();
            return false;
        }
    }


    public static User GetUserByUsername(String username) {
        try (Connection conn = GetConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username=?")) {
            ps.setString(1, username);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String password = resultSet.getString("password");
                    int role=resultSet.getInt("role");

                    resultSet.close();
                    ps.close();
                    conn.close();
                    return new User(userId, username, password,role);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // 或者使用适当的日志记录方法
        }

        return null; // 如果没有找到用户或者出现异常，返回 null
    }


    public static User[] GetUsers(String keyName,int NumPerPage,int PageIndex) throws SQLException, ClassNotFoundException {
        List<User> userList = new ArrayList<>();
        Connection conn=GetConnection();

        String sql=null;
        PreparedStatement ps;
            if (NumPerPage==0){//查询所有
                sql = "SELECT * FROM users WHERE username LIKE ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, "%" + keyName + "%");
            }else {
                sql = "SELECT * FROM users  WHERE username LIKE ? LIMIT ? OFFSET ?";
                ps = conn.prepareStatement(sql);
                int offset = (PageIndex - 1) * NumPerPage;
                ps.setString(1, "%" + keyName + "%");
                ps.setInt(2, NumPerPage);
                ps.setInt(3, offset);
            }

        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password=resultSet.getString("password");
            int role = resultSet.getInt("role");
            User u = new User(id,username,password,role);
            userList.add(u);
        }

        resultSet.close();
        ps.close();
        conn.close();
        return userList.toArray(new User[0]);
    }

    public static int CountTotalUser() throws SQLException, ClassNotFoundException {
            Connection conn=GetConnection();
        String sql = "SELECT COUNT(*) AS total FROM users";

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


}
