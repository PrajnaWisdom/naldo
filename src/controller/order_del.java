package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import factory.BeanFactory;
import net.sf.json.JSONObject;
import service.impl.orderServiceImpl;
import service.orderService;

/**
 * Servlet implementation class order_del
 */
@WebServlet("/order_del")
public class order_del extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public order_del() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		orderService orderServiceimpl= new orderServiceImpl();
		String order_no=request.getParameter("order_no");
		boolean result=orderServiceimpl.delorderByorder_no(order_no);
		//System.out.println("del");
        PrintWriter pw = response.getWriter();
		if (request.getParameter("type").equals("user")){
			response.sendRedirect("/order_list.html");
		}else if (request.getParameter("type").equals("admin")){
            JSONObject json=new JSONObject();
            json.put("result", result);
            pw.print(json);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
