package controller;

import dao.CommodityDao;
import dao.impl.CommodityDaoImpl;
import domain.Comments;
import domain.Commodity;
import domain.CommodityAllInfo;
import factory.BeanFactory;
import net.sf.json.JSONObject;
import service.CommodityService;
import service.commentsService;
import service.impl.CommodityAdd;
import service.impl.CommodityDel;
import service.impl.commentsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @auther:houkexin
 * @date: 2018/7/30
 * @description:
 * @version: 1.0
 */
@WebServlet("/commodityServlet")
public class CommodityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        JSONObject json = new JSONObject();
        PrintWriter pw = response.getWriter();
        if (type.equals("listAll")){
            CommodityDao commodityDao = new CommodityDaoImpl();
            List<CommodityAllInfo> list = commodityDao.queryAll();
            json.put("result",list);
        }else if (type.equals("add")){
            CommodityService commodityService = new CommodityAdd();
            boolean isSuccess = commodityService.dispose(request,response);
            json.put("result",isSuccess);
        }else if (type.equals("del")){
            CommodityService del = new CommodityDel();
            json.put("result",del.dispose(request,response));
        }else if (type.equals("query")){
            int id = Integer.parseInt(request.getParameter("id"));
            CommodityDao commodityDao = new CommodityDaoImpl();
            json.put("result",commodityDao.queryAllInfoById(id));
            //PrintWriter pw = response.getWriter();
            commentsService commentsServiceimpl=new commentsServiceImpl();
            List<Comments> comments=null;
            final int PAGESIZE=10;
            int count=commentsServiceimpl.numOfcomments();//总数
            int pages=count%PAGESIZE==0?count/PAGESIZE:count/PAGESIZE+1;//总页数
            //前端传回来当前页码
            int currpage=1;
            comments=commentsServiceimpl.showcomments_Bycommodity_id(id, (currpage-1)*PAGESIZE, PAGESIZE);
            json.put("comments", comments);
        }else if (type.equals("listAlli")){
            CommodityDao commodityDao = new CommodityDaoImpl();
            List<CommodityAllInfo> list = commodityDao.queryAlli();
            json.put("result",list);
        }
        pw.print(json.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
