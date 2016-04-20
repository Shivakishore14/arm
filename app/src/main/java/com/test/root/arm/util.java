package com.test.root.arm;



import android.os.AsyncTask;

import java.sql.*;

/**
 * Created by root on 20/4/16.
 */
public class util {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static String serverIp,username,password;
    public util(String ip,String name ,String pass){
        serverIp = ip;
        username = name;
        password = pass;
    }

    static boolean testServerConnection() throws SQLException {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn= DriverManager.getConnection("jdbc:mysql://" + serverIp, username, password);
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
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+serverIp+"/"+name,username,password);
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
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://"+serverIp+"/"+name,username,password);
            try {
                Statement stmt=conn.createStatement();
                stmt.executeUpdate(update);
                conn.close();
                stmt.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){

    }
}

