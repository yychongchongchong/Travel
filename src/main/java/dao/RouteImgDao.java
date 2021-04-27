package dao;

import domain.RouteImg;

import java.util.List;

/**
 * @author jy
 * @date 2021年04月19日 16:11
 */
public interface RouteImgDao {

    /**
     * 根据rid查询图片集合
     * @param rid
     * @return
     */
    public List<RouteImg> findByRid(int rid);
}
