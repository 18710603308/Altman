package com.mzy.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jack Miao
 * @date 2021/1/11 14:11
 * @desc
 */
public class SqlServerTest {

    public static void main(String[] args) {

        query();
    }

    private static void query(){

        Connection connection=null;
        //SQL数据库引擎
        String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        //数据源  ！！！注意若出现加载或者连接数据库失败一般是这里出现问题
        String dbURL="jdbc:sqlserver://172.24.141.167:1433;DatabaseName=dms";
        String Name="sa";
        String Pwd="1qaz@WSX";

        try{
           //加载驱动
            Class.forName(driverName);
            //获取连接对象
            connection = DriverManager.getConnection(dbURL, Name, Pwd);
           //获取预编译对象
            String id = "40E0B5CA-223C-4910-AABA-00002A109029";
            String sql = "SELECT * FROM tb_Customer WHERE ID = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString("CustomerNameCN"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
