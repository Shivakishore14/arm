package com.test.root.arm;



import java.sql.*;

/**
 * Created by root on 20/4/16.
 */
public class util {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static String serverIp,userName,password;

    static boolean testServerConnection(String ip,String user,String password) throws SQLException {
        try{
            Connection conn= DriverManager.getConnection("jdbc:mysql://" + ip, user, password);
            boolean flag = ( conn != null);
            conn.close();
            return flag;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static void closecon(){
        try {
            if(conn != null)
                conn.close();
            if(stmt != null)
                stmt.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public static ResultSet getResult(String name,String q){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://"+serverIp+"/"+name,userName,password);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(q);
            return rs;

        } catch(Exception e){
            e.printStackTrace();
        } finally{

        }
        return null;
    }
    static void SQLUpdate(String name,String update) {
        try {
            Connection conn=DriverManager.getConnection("jdbc:mysql://"+serverIp+"/"+name,userName,password);
            try {
                Statement stmt=conn.createStatement();
                stmt.executeUpdate(update);
                conn.close();
                stmt.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        serverIp = args[0];
        userName = args[1];
        password = args[2];
    }
}
