/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author root
 */
@WebServlet(urlPatterns = {"/StudentsUpdate"})
public class StudentsUpdate extends HttpServlet {


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
            out.println("<title>Servlet StudentsUpdate</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StudentsUpdate at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
            String a = request.getParameter("students");
            String class1 = request.getParameter("class");
            String hour = request.getParameter("hour");
            String name;
            HttpSession session=request.getSession(false);  
            if(session!=null){ 
                name = (String)session.getAttribute("name");
                PrintWriter out = response.getWriter();
                getStud(a);
                util.toDatabase(stud,pa,class1,hour);
                out.println(a);
            }else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                System.out.println("not auth");
            }
            
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

    List<String> stud,pa= new ArrayList<String>();;
    
    private void getStud(String result){
        System.out.println("result ==>"+result);
        try {
            JSONParser parser = new JSONParser();
            JSONObject mainjsonObj = (JSONObject) parser.parse(result);
            JSONArray jsonar =(JSONArray) mainjsonObj.get("students");
            for (int i = 0; i < jsonar.size(); i++) {
                JSONObject c = (JSONObject)jsonar.get(i);
               // System.out.println(c.get("pa"));
               // stud.add("sk");
               // pa.add((String)c.get("pa"));
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
}

