package dao.Impl;

import dao.RouteDao;
import domain.Route;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jy
 * @date 2021年04月18日 14:09
 */
public class RouteDaoImp implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(int cid, String rname) {
        //String sql = "select count(*) from tab_route where cid = ?";
        //定义sql模板
        String sql = "select count(*) from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        //判断参数是否有值
        if(cid != 0){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname != null && !"null".equals(rname)){
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        sql = sb.toString();
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        //String sql = "select * from tab_route where cid = ? limit ?,? ";
        String sql = "select * from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if(cid != 0){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname != null && !"null".equals(rname)){
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%" );
        }
        sb.append(" limit ?,? ");
        params.add(start);
        params.add(pageSize);
        sql = sb.toString();
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    @Override
    public Route findById(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
