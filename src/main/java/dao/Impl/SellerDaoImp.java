package dao.Impl;

import dao.SellerDao;
import domain.Seller;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

/**
 * @author jy
 * @date 2021年04月19日 16:26
 */
public class SellerDaoImp implements SellerDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Seller findById(int sid) {
        String sql = "select * from tab_seller where sid = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),sid);
    }
}
