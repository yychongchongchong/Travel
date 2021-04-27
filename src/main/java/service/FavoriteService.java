package service;

import domain.Favorite;
import domain.PageBean;

/**
 * @author jy
 * @date 2021年04月19日 19:38
 */
public interface FavoriteService {

    /**
     * 判断该用户是否收藏
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(String rid,int uid);

    /**
     * 添加路线到收藏
     * @param rid
     * @param uid
     * @param flag
     */
    void changeFavorite(String rid, int uid, boolean flag);

    /**
     * 分页查询用户所有收藏信息
     * @param uid
     * @return
     */
    PageBean<Favorite> PageQuery(int uid, int currentPage, int pageSize);


    /**
     * 查询所有收藏信息
     * @param currentPage
     * @param pageSize
     * @param rname
     * @param min_price
     * @param max_price
     * @return
     */
    PageBean<Favorite> queryAll(int currentPage, int pageSize, String rname, int min_price, int max_price);
}
