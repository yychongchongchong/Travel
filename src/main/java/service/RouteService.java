package service;

import domain.PageBean;
import domain.Route;

/**
 * @author jy
 * @date 2021年04月18日 15:10
 */
public interface RouteService {

    /**
     * 根据类别分页查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     * 根据id查询路线信息
     * @param rid
     * @return
     */
    public Route findDetail(String rid);


}
