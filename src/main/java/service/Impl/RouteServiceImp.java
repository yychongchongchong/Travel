package service.Impl;

import dao.FavoriteDao;
import dao.Impl.FavoriteDaoImp;
import dao.Impl.RouteDaoImp;
import dao.Impl.RouteImgDaoImp;
import dao.Impl.SellerDaoImp;
import dao.RouteDao;
import dao.RouteImgDao;
import dao.SellerDao;
import domain.PageBean;
import domain.Route;
import domain.RouteImg;
import domain.Seller;
import service.FavoriteService;
import service.RouteService;

import java.util.List;

/**
 * @author jy
 * @date 2021年04月18日 15:11
 */
public class RouteServiceImp implements RouteService {

    private RouteDao routeDao = new RouteDaoImp();
    private RouteImgDao routeImgDao = new RouteImgDaoImp();
    private SellerDao sellerDao = new SellerDaoImp();
    private FavoriteDao favoriteDao = new FavoriteDaoImp();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {

        //封装PageBean
        PageBean<Route> pb = new PageBean<Route>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        pb.setTotalPage(totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1);
        List<Route> list = routeDao.findByPage(cid,(currentPage - 1) * pageSize, pageSize,rname);
        pb.setList(list);
        return pb;
    }

    @Override
    public Route findDetail(String rid) {

        //根据rid去route表中查询route对象
        Route route = routeDao.findById(Integer.parseInt(rid));

        //根据rid在route_img表中查询图片信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());

        //将查询到的图片集合设置到route对象中
        route.setRouteImgList(routeImgList);

        //根据sid查询卖家信息
        Seller seller = sellerDao.findById(route.getSid());

        //将查询到的卖家设置到route对象中
        route.setSeller(seller);

        //查询收藏次数
        int FavoriteCount = favoriteDao.findCountByRid(route.getRid());

        //将查询的收藏次数设置到route对象中
        route.setCount(FavoriteCount);
        return route;
    }
}
