package controller;

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

import dao.Trade_informationDao;
import dao.impl.Trade_informationDaoImpl;
import domain.Order;
import domain.Trade_information;
import domain.UserInfo;
import factory.BeanFactory;
import net.sf.json.JSONObject;
import service.impl.orderServiceImpl;
import service.orderService;

/**
 * Servlet implementation class orderServlet
 */
@WebServlet("/order_ShowServlet")
public class order_ShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public order_ShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//载入各个serviceimpl 使用protected 以被子类继承
        //System.out.println("order");
		orderService orderserviceimpl= new orderServiceImpl();
		/*int currPage=1; //前端传回来的当前页码
		final int PAGE_SIZE=10; //每页显示10条
		int count=orderserviceimpl.numOforder();//总记录数
		int pages;//总页数
		if (count%PAGE_SIZE==0) {
			pages=count/PAGE_SIZE;
		} else {
			pages=count/PAGE_SIZE+1;
		}*/
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
		List<Order> orders=orderserviceimpl.findorderByuser_id(userInfo.getId());
        Trade_informationDao informationDao = new Trade_informationDaoImpl();
		//List<Double> prices = new ArrayList<>();
        System.out.println(orders.size());
        System.out.println(orders);
		for (int i = 0; i < orders.size(); i++){
		    List<Trade_information> trade_informations = informationDao.queryById(orders.get(i).getOrder_no());
		    double temp = 0;
		    //System.out.println(trade_informations.size());
            for (int j = 0; j < trade_informations.size(); j++ ) {
                temp = trade_informations.get(j).getAmount_payable();
            }
            System.out.println(temp);
            //System.out.println(temp);
            orders.get(i).setPrices(temp);
        }
        System.out.println(orders);
		JSONObject json=new JSONObject();
		json.put("orders", orders);
		//json.put("prices",prices);
		PrintWriter out=response.getWriter();
		//System.out.println("订单");
		System.out.println(json.toString());
		out.print(json.toString());//返回分页后的ordersJSON数据到前端
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
