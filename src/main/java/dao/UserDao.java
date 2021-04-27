package dao;

import domain.User;

/**
 * @author jy
 * @date 2021年04月16日 22:57
 */
public interface UserDao {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 保存用户信息
     * @param user
     */
    public void save(User user);

    /**
     * 根据激活码查找用户
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 设置指定用户的激活状态
     * @param user
     */
    void setStatus(User user);

    /**
     * 根据用户名和密码查找用户
     * @return
     * @param username
     * @param password
     */
    User findByUsernameAndPassword(String username, String password);
}
