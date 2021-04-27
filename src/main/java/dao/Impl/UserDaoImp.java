package dao.Impl;

import dao.UserDao;
import domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

/**
 * @author jy
 * @date 2021年04月16日 22:57
 */
public class UserDaoImp implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findByUsername(String username) {
        User user = null;
        try {
            //定义sql
            String sql = "select * from tab_user where username = ?";
            //执行
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {
            return null;
        }

        return user;
    }

    @Override
    public void save(User user) {

        //定义sql
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?) ";
        //执行
        template.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }

    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (Exception e) {

        }

        return user;
    }

    @Override
    public void setStatus(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ?";
        template.update(sql,user.getUid());
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ? and password = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username,password);
        } catch (DataAccessException e) {
            //e.printStackTrace();
        }
        return user;
    }
}
