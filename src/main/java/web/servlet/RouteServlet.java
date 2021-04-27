package web.servlet;

import domain.Favorite;
import domain.PageBean;
import domain.Route;
import domain.User;
import service.FavoriteService;
import service.Impl.FavoriteServiceImp;
import service.Impl.RouteServiceImp;
import service.RouteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    //声明RouterService业务对象
    private RouteService routeService = new RouteServiceImp();

    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        //接收参数
        String currentPageStr = request.getParameter("currentPage");
        String cidStr = request.getParameter("cid");
        String pageSizeStr = request.getParameter("pageSize");
        String rname = request.getParameter("rname");

        //处理乱码
        //rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        int cid = 0;
        int currentPage = 0;
        int pageSize = 0;

        //处理参数
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            //不传递当前页码，默认第一页
            currentPage = 1;
        }
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            //不传递每页显示条数，默认5条
            pageSize = 8;
        }

        //调用service方法
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize, rname);

        //将分页对象json化写回客户端
        writeValueAsString(pb, response);
    }


    /**
     * 根据id查询一个旅游线路详情信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //接收id
        String rid = request.getParameter("rid");
        //调用service查询route对象
        Route route = routeService.findDetail(rid);
        //转化为json，写回客户端
        writeValueAsString(route, response);
    }








}