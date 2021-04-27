package service.Impl;

import dao.FavoriteDao;
import dao.Impl.FavoriteDaoImp;
import dao.Impl.RouteDaoImp;
import dao.RouteDao;
import domain.Favorite;
import domain.PageBean;
import domain.Route;
import service.FavoriteService;
import service.RouteService;

import java.util.List;

/**
 * @author jy
 * @date 2021年04月19日 19:40
 */
public class FavoriteServiceImp implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImp();
    private RouteDao routeDao = new RouteDaoImp();
    @Override
    public boolean isFavorite(String rid, int uid) {
        //调用dao方法判断是否收藏
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        if(favorite == null){
            return false;
        }
        return true;
    }

    @Override
    public void changeFavorite(String rid, int uid, boolean flag) {
        favoriteDao.changeFavorite(Integer.parseInt(rid),uid,flag);
    }

    @Override
    public PageBean<Favorite> PageQuery(int uid, int currentPage, int pageSize) {

        //创建PageBean对象
        PageBean<Favorite> pb = new PageBean<Favorite>();
        //查询uid查询收藏的总次数
        int totalCount = favoriteDao.findCountByUid(uid);
        //根据uid查询指定个数的收藏时间和rid
        List<Favorite> list = favoriteDao.findByPage(uid,(currentPage - 1) * pageSize,pageSize);
        //根据rid查询路线信息
        for (Favorite favorite : list) {
            //获取rid
            int rid = favorite.getRid();
            //查询路线信息
            Route route = routeDao.findById(rid);
            //赋值给Favorite对象的route
            favorite.setRoute(route);
        }
        //封装PageBean对象
        pb.setTotalCount(totalCount);
        pb.setPageSize(pageSize);
        pb.setTotalPage(totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize + 1);
        pb.setCurrentPage(currentPage);
        pb.setList(list);
        return pb;
    }

    @Override
    public PageBean<Favorite> queryAll(int currentPage, int pageSize, String rname, int min_price, int max_price) {
        //创建PageBean对象
        PageBean<Favorite> pb = new PageBean<>();
        //查询所有收藏信息总条数
        int totalCount = favoriteDao.findAllCount(rname,min_price,max_price);
        //查询所有收藏信息
        List<Favorite> list = favoriteDao.findAllFavoriteAndRoute((currentPage - 1) * pageSize, pageSize, rname, min_price, max_price);

        for (Favorite favorite : list) {
            //根据rid查询所有路线详情信息
            int rid = favorite.getRid();
            //创建RouteService实现类对象
            RouteService routeService = new RouteServiceImp();
            Route route = routeService.findDetail(Integer.toString(rid));
            //赋值给Favorite对象的route
            favorite.setRoute(route);
        }

        //封装PageBean对象
        pb.setTotalCount(totalCount);
        pb.setPageSize(pageSize);
        pb.setTotalPage(totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize + 1);
        pb.setCurrentPage(currentPage);
        pb.setList(list);
        return pb;
    }

}
