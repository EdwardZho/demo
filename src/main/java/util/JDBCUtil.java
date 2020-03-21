package util;


import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 数据库的连接，数据的插入保存，
 */
public class JDBCUtil {

    private static final String driverClass = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://127.0.0.1:3306/excel";
    private static final String username = "root";
    private static final String password = "";

    static {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    /**
     * 插入保存数据，身份证，学号，生日，省份，性别，分数
     *
     * @param connection 数据库连接
     * @param user       用户
     * @return 修改的行数
     * @throws SQLException sql exception
     */
    public static int save(Connection connection, User user) throws SQLException {
        //显示数据库的字段的样子
        String sql = "INSERT INTO user (id_card, stu_no, birthday, province, gender, score, zodiac, constellation) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getIdCard());
        preparedStatement.setString(2, user.getStuNo());
        preparedStatement.setDate(3, new java.sql.Date(user.getBirthday().getTime()));
        preparedStatement.setString(4, user.getProvince());
        preparedStatement.setString(5, user.getGender());
        preparedStatement.setDouble(6, user.getScore());
        preparedStatement.setString(7, user.getZodiac());
        preparedStatement.setString(8, user.getConstellation());
        int row = preparedStatement.executeUpdate();
        close(null, preparedStatement);
        return row;
    }

    /**
     * 释放资源
     */
    public static void close(Connection connection, PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
