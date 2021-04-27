package dao.Impl;

import dao.RouteImgDao;
import domain.RouteImg;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.util.List;

/**
 * @author jy
 * @date 2021年04月19日 16:13
 */
public class RouteImgDaoImp implements RouteImgDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<RouteImg> findByRid(int rid) {

        String sql = "select * from tab_route_img where rid = ?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
    }
}
