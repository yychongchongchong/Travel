package dao.Impl;

import dao.FavoriteDao;
import domain.Favorite;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jy
 * @date 2021年04月19日 19:42
 */
public class FavoriteDaoImp implements FavoriteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = "select * from tab_favorite where rid = ? and uid = ?";
            favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
           //e.printStackTrace();

        }
        return favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public void changeFavorite(int rid, int uid, boolean flag) {
        String sql = null;
        if(flag){
            //用户已经收藏
            //删除该收藏
            sql = "delete from tab_favorite where uid = ? and rid = ?";
            jdbcTemplate.update(sql,uid,rid);
        }else {
            //用户未收藏，添加收藏
            sql = "insert into tab_favorite values(?,?,?)";
            jdbcTemplate.update(sql,rid,new Date(),uid);
        }

    }

    @Override
    public int findCountByUid(int uid) {
        String sql = "select count(*) from tab_favorite where uid = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,uid);
    }

    @Override
    public List<Favorite> findByPage(int uid, int start, int pageSize) {
        String sql = "select * from tab_favorite where uid = ? limit ?,?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),uid,start,pageSize);
    }

    @Override
    public int findAllCount(String rname, int min_price, int max_price) {
//        String sql = "select count(distinct rid) from tab_favorite";
//        return jdbcTemplate.queryForObject(sql,Integer.class);
        String sql = "select count(*)  " +
                "from tab_route join (" +
                " select rid,count(*) as total " +
                " from tab_favorite " +
                " group by rid) as fav_new " +
                " on fav_new.rid = tab_route.rid ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if(rname.length() > 0){
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        sb.append(" and price > ? ");
        params.add(min_price);
        sb.append(" and price < ? ");
        params.add(max_price);
        sql = sb.toString();
        return jdbcTemplate.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Favorite> findAllFavoriteAndRoute(int start, int pageSize, String rname, int min_price, int max_price) {
        //String sql = "select * from tab_favorite group by rid order by count(*) desc limit ?,?";
        //String sql = "select * from tab_route where 1 = 1 ";
//        StringBuilder sb = new StringBuilder(sql);
//        List params = new ArrayList();
//        if(rname.length() > 0){
//            sb.append(" and rname like ? ");
//            params.add("%" + rname + "%");
//        }
//        sb.append(" and price > ? ");
//        params.add(min_price);
//        sb.append(" and price < ? ");
//        params.add(max_price);
//        sb.append(" and rid in (select rid from tab_favorite) ");
//        sql = sb.toString();
        String sql = "select tab_route.rid " +
                     "from tab_route join (" +
                                        " select rid,count(*) as total " +
                                        " from tab_favorite " +
                                         " group by rid) as fav_new " +
                                     " on fav_new.rid = tab_route.rid ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if(rname.length() > 0){
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        sb.append(" and price > ? ");
        params.add(min_price);
        sb.append(" and price < ? ");
        params.add(max_price);
        sb.append(" order by total desc ");
        sb.append(" limit  ?,? ");
        params.add(start);
        params.add(pageSize);
        sql = sb.toString();
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),params.toArray());
    }
}
