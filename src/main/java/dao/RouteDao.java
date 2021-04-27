package dao;

import domain.Route;

import java.util.List;

/**
 * @author jy
 * @date 2021年04月18日 14:05
 */
public interface RouteDao {

    /**
     * 根据cid查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    public int findTotalCount(int cid, String rname);

    /**
     * 根据cid，start，pageSize查询当前页的数据集合
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    public List<Route> findByPage(int cid, int start, int pageSize, String rname);

    /**
     * 根据rid查询线路
     * @param rid
     * @return
     */
    public Route findById(int rid);
}
