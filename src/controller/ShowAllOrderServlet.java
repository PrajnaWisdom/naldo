package controller;

import dao.Trade_informationDao;
import dao.impl.Trade_informationDaoImpl;
import domain.Order;
import domain.Trade_information;
import domain.UserInfo;
import net.sf.json.JSONObject;
import service.impl.orderServiceImpl;
import service.orderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @auther:houkexin
 * @date: 2018/8/9
 * @description:
 * @version: 1.0
 */
@WebServlet("/ShowAllOrderServlet")
public class ShowAllOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        orderService orderserviceimpl= new orderServiceImpl();
        HttpSession session = request.getSession();
        List<Order> orders=orderserviceimpl.showAllOrder();
        Trade_informationDao informationDao = new Trade_informationDaoImpl();
        for (int i = 0; i < orders.size(); i++){
            List<Trade_information> trade_informations = informationDao.queryById(orders.get(i).getOrder_no());
            double temp = 0;
            //System.out.println(trade_informations.size());
            for (int j = 0; j < trade_informations.size(); j++ ) {
                temp = trade_informations.get(j).getAmount_payable();
            }
            //System.out.println(temp);
            orders.get(i).setPrices(temp);
        }
        System.out.println(orders);
        JSONObject json=new JSONObject();
        json.put("orders", orders);
        //json.put("prices",prices);
        PrintWriter out=response.getWriter();
        out.print(json.toString());//返回分页后的ordersJSON数据到前端
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
