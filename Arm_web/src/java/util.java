/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import java.text.*;
import java.io.*;
import java.sql.*;
/**
 *
 * @author root
 */
public class util {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/armtest";
   
    static final String USER = "root";
    static final String PASS = "sken";
   
    static Connection conn = null;
    static Statement stmt = null;
    public static int fun(String name,String pass){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT username FROM login where username like '"+name+"' && password like '"+pass+"';";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("w");
            if(rs.first())
                return 1;
            else
                return 0;
            
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
        System.out.println("ws");
        return 1;
    }
    static String[] dummy = {"a","b","c","d","e","f","g","h"};
    static String hour="8",current="d";
    static String stud[]={"sken","ksihore","breakfree","stud2","stud3","kasda","asdasd","stud22","stud4"};
    static String pa[]={"present","absent","present","onDuty","present","absent","onDuty","late","present"};
    
    public static String[] getStudents(String a){
        //function here
        return stud;
    }
    public static String[] getpa(String class1,String hour){
        //function here
        String pa[]={"present","present","present","present","present","absent","absent","absent","absent"};
        return pa;
    }
    
}
