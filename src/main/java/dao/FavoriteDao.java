package dao;

import domain.Favorite;

import java.util.List;

/**
 * @author jy
 * @date 2021年04月19日 19:41
 */
public interface FavoriteDao {

    public Favorite findByRidAndUid(int rid, int uid);

    int findCountByRid(int rid);

    void changeFavorite(int rid, int uid, boolean flag);

    int findCountByUid(int uid);

    List<Favorite> findByPage(int uid, int start, int pageSize);

    int findAllCount(String rname, int min_price, int max_price);

    List<Favorite> findAllFavoriteAndRoute(int start, int pageSize, String rname, int min_price, int max_price);
}
