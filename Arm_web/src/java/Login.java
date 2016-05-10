/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 *
 * @author root
 */
@WebServlet(urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Login</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Login at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
    
    private JSONArray getJsonArrayFromStringArray(String[] a){
        JSONObject[] obj = new JSONObject[a.length];
        JSONArray ja = new JSONArray();
        for(int i = 0;i< a.length;i++){
                obj[i] = new JSONObject();
                obj[i].put("Class",a[i]);
                ja.add(obj[i]);
        }
        return ja;
    }
    private JSONArray getJsonArrayFromJsonObjects(JSONObject... a){
        JSONArray ja = new JSONArray();
        for(int i=0;i < a.length;i++){
            ja.add(a[i]);
        }
        return ja;
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        String name = request.getParameter("name");
        String pass = request.getParameter("password"); 
        PrintWriter out = response.getWriter();
        if (util.fun(name,pass) == 1){
            JSONArray ja = new JSONArray(),ja1 = new JSONArray(),ja2 = new JSONArray();
            JSONObject obj1 = new JSONObject(), obj2 = new JSONObject(),obj3 = new JSONObject(),obj4 = new JSONObject(),mainObj =  new JSONObject();
            ja = getJsonArrayFromStringArray(util.dummy);
            //ja2 = getJsonArrayFromStringArray(util.stud);
            obj1.put("classes", ja);
            obj2.put("Hour",util.hour);
            obj3.put("current",util.current);
            //obj4.put("students",ja2);
            //ja1 = getJsonArrayFromJsonObjects(obj1,obj2,obj3,obj4);
            ja1 = getJsonArrayFromJsonObjects(obj1,obj2,obj3);
 
            mainObj.put("json",ja1);
            StringWriter out1 = new StringWriter();
            mainObj.writeJSONString(out1);
        
            String jsonText = out1.toString();
            out.println(jsonText);
        }else{
            out.print("NOT AUTHENTICATED");
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
