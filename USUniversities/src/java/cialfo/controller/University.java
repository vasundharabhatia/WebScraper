package cialfo.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cialfo.model.UniversityModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author vasundharabhatia
 */
@WebServlet(urlPatterns = {"/University/*"})
public class University extends HttpServlet {

    static ArrayList<UniversityModel> list;

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
        
        //Check if url path contains "getSearched" if not go to else condition. This is the ajax request sent by search button
        if ((request.getPathInfo() != null) && (request.getPathInfo().contains("getSearched"))) {

            //get searched keyword from request
            String keyword = request.getParameter("key");
            //create an arraylist to populate with universities that match search criteria
            ArrayList<UniversityModel> newList = new ArrayList<>();
            if (keyword != "" && (!request.getParameter("numberLow").isEmpty()) && (!request.getParameter("numberHigh").isEmpty())) {
                int low = Integer.parseInt(request.getParameter("numberLow"));
                int high = Integer.parseInt(request.getParameter("numberHigh"));
                for (int i = 0; i < list.size(); i++) {

                    String s = (list.get(i).getTuition()).replaceAll("[a-z]|[-]|[:]|[$]|[,]|[']|[}]", "");
                    String[] q = s.split("  ");
                    // Some universities had N/A as tuition fees which cannot be converted to integer for comparison. Needs to be handled
                    if (s != "N/A" && q.length > 1 && ((q[0] != "N/A") && (q[1] != "N/A"))) {
                        int result1 = Integer.parseInt(q[0].replaceAll(" ", ""));
                        int result2 = Integer.parseInt(q[1]);
                        if ((list.get(i).getName().toLowerCase().contains(keyword.toLowerCase())) && (((result1 > low) && (result1 < high)) || ((result2 > low) && (result2 < high)))) {

                            newList.add(list.get(i));

                        }
                    } else if (!"N/A".equals(s)) {
                        if ((list.get(i).getName().toLowerCase().contains(keyword.toLowerCase())) && ((Integer.parseInt(s) > low) && (Integer.parseInt(s) < high))) 
                        {
                            newList.add(list.get(i));
                        }
                    }

                }
                
            } else if (keyword != "" && (request.getParameter("numberLow").isEmpty()) && (request.getParameter("numberHigh").isEmpty())) {
                //only keyword is sent without tuition fee filter
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getName().toLowerCase().contains(keyword.toLowerCase())) {

                        newList.add(list.get(i));

                    }
                }
            }

            System.out.println("Size:" + newList.size());
            //Convert to json object 
            JSONArray jsonArray = new JSONArray();
            for (UniversityModel um : newList) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("Tuition", um.getTuition());

                    jsonObject.put("Name", um.getName());

                } catch (JSONException ex) {
                    //Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, ex);
                }
                jsonArray.put(jsonObject);
            }
            response.getWriter().println(jsonArray.toString());

        }
        //Else condition to get all universities
        else {
            //read all contents(universities and tuition fees) from file and add all to arraylist
            list = new ArrayList<>();
            BufferedReader br = null;

            try {

                String sCurrentLine;
                String[] parts = null;
                br = new BufferedReader(new FileReader("/Users/vasundharabhatia/Desktop/output.txt"));

                while ((sCurrentLine = br.readLine()) != null) {
                    parts = sCurrentLine.split("', ");
                }

                for (int i = 0; i < parts.length; i++) {
                    String[] model = parts[i].split(": '");
                    UniversityModel um = new UniversityModel();
                    um.setName(model[0]);
                    um.setTuition(model[1]);
                    list.add(um);

                }

                request.setAttribute("arrayList", list);
                RequestDispatcher rd = request.getRequestDispatcher("list.jsp");
                rd.forward(request, response);

            } catch (IOException e) {
                e.printStackTrace();
            }

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
        processRequest(request, response);
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
