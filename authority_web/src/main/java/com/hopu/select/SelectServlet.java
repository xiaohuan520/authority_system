package com.hopu.select;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SelectServlet extends HttpServlet {
    private static final long serialVersionYID = 1L;
    public SelectServlet()
    {
        super();
    }
    public void destroy()
    {
        super.destroy();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        response.setContentType("web.xml");
        response.setHeader("Cache-Control","no-cache");
        request.setCharacterEncoding("GBK");
        response.setCharacterEncoding("UTF-8");
        String menuId = request.getParameter("id").toString();
        System.out.println(menuId);
        String xml_start = "<selects>";
        String xml_end = "</selects>";
        String xml = "";
        if (menuId.equalsIgnoreCase("0")){

        }String last_xml = xml_start+xml+xml_end;
        response.getWriter().write(last_xml);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)throws  ServletException,IOException{
        doGet(request,response);
    }
    public void init()throws ServletException{

    }
}
