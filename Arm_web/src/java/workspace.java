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
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
    
/**
 *
 * @author root
 */
@WebServlet(urlPatterns = {"/workspace"})
public class workspace extends HttpServlet {

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
            out.println("<title>Servlet workspace</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet workspace at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
    private JSONArray getJsonArrayFromStringArray(String[] a,String[] b){
        JSONObject[]obj = new JSONObject[a.length];
        JSONArray ja = new JSONArray();
        for(int i = 0;i< a.length;i++){
                obj[i] = new JSONObject();
                obj[i].put("pa",b[i]);
                obj[i].put("stud",a[i]);
                ja.add(obj[i]);
        }
        return ja;
    }
    private void current(PrintWriter out,String class1) throws Exception{
        String[] students= util.getStudents(class1);
        
        JSONArray ja = new JSONArray(),ja1 = new JSONArray();
        JSONObject mainObj = new JSONObject();
        ja = getJsonArrayFromStringArray(students,util.pa);
        mainObj.put("students",ja);
        StringWriter out1 = new StringWriter();
        mainObj.writeJSONString(out1);
      
        String jsonText = out1.toString();
        out.println(jsonText);
    }
    private void importlast(PrintWriter out,String class1,String hour) throws Exception{
        String[] students= util.getStudents(class1);
        String[] pa = util.getpa(class1,hour);
        JSONArray ja = new JSONArray(),ja1 = new JSONArray();
        JSONObject mainObj = new JSONObject();
        ja = getJsonArrayFromStringArray(students,pa);
        mainObj.put("students",ja);
        StringWriter out1 = new StringWriter();
        mainObj.writeJSONString(out1);
      
        String jsonText = out1.toString();
        out.println(jsonText);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String class1 = request.getParameter("class");
        String hour = request.getParameter("hour");
        HttpSession session = request.getSession(false);
        if(session == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else {
            if(hour == null){
                try {
                    current(out,class1);
                }catch(Exception e){}
            }else{
                try {
                    importlast(out,class1,hour);
                }catch(Exception e){}
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
