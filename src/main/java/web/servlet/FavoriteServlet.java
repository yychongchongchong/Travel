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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/favorite/*")
public class FavoriteServlet extends BaseServlet {

    //声明FavoriteService业务对象
    private FavoriteService favoriteService = new FavoriteServiceImp();



    /**
     * 判断当前用户是否收藏了该线路
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取当前线路
        String rid = request.getParameter("rid");
        //获取当前登录用户
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        int uid = 0;
        try {
            uid = loginUser.getUid();
        } catch (Exception e) {

        }

        //调用service方法
        boolean flag = favoriteService.isFavorite(rid, uid);
        //将数据写回客户端
        writeValueAsString(flag, response);
    }



    /**
     * 切换收藏状态
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void changeFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取rid
        String rid = request.getParameter("rid");
        //获取当前登录用户
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        int uid = loginUser.getUid();
        //获取当前是否被收藏
        boolean flag = favoriteService.isFavorite(rid, uid);
        //调用service方法
        favoriteService.changeFavorite(rid, uid,flag);
    }

    /**
     * 查询用户当前所有收藏信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAllFavoriteByUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取当前登录用户
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        //获取当前的uid
        int uid = loginUser.getUid();
        //int uid = 9;
        //获取当前页数
        String currentPageStr = request.getParameter("currentPage");
        //获取每页大小
        String pageSizeStr = request.getParameter("pageSize");
        //参数处理
        int currentPage = 1;
        int pageSize = 6;
        if (currentPageStr != null && currentPageStr.length() > 0 && !"null".equals(currentPageStr)) {
            currentPage = Integer.parseInt(currentPageStr);
        }
        if (pageSizeStr != null && pageSizeStr.length() > 0 && !"null".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        //调用service方法
        PageBean<Favorite> pb = favoriteService.PageQuery(uid, currentPage, pageSize);
        //将数据序列化为json写回客户端
        writeValueAsString(pb, response);
    }

    /**
     * 查询所有用户的收藏信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAllFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取参数
        //获取当前页码
        String currentPageStr = request.getParameter("currentPage");
        //获取页面大小
        String pageSizeStr = request.getParameter("pageSize");
        //获取线路名称rname
        String rnameStr = request.getParameter("rname");
        //获取线路最低金额
        String min_priceStr = request.getParameter("min_price");
        //获取线路最高金额
        String max_priceStr = request.getParameter("max_price");
        //参数处理
        int currentPage = 1;
        int pageSize = 6;
        String rname = "";
        int min_price = 0;
        int max_price = 99999999;
        if (currentPageStr != null && currentPageStr.length() > 0 && !"null".equals(currentPageStr)) {
            currentPage = Integer.parseInt(currentPageStr);
        }
        if (pageSizeStr != null && pageSizeStr.length() > 0 && !"null".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if (rnameStr != null && rnameStr.length() > 0 && !"null".equals(rnameStr)) {
            //处理乱码
            //rnameStr = new String(rnameStr.getBytes("iso-8859-1"), "utf-8");
            rname = rnameStr;
        }
        if (min_priceStr != null && min_priceStr.length() > 0 && !"null".equals(min_priceStr)) {
            min_price = Integer.parseInt(min_priceStr);
        }
        if (max_priceStr != null && max_priceStr.length() > 0 && !"null".equals(max_priceStr)) {
            max_price = Integer.parseInt(max_priceStr);
        }

        //调用service方法
        PageBean<Favorite> pb = favoriteService.queryAll(currentPage,pageSize,rname,min_price,max_price);
        //将数据序列化为json写回客户端
        writeValueAsString(pb, response);
    }
}