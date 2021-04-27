package service;

import domain.User;

/**
 * @author jy
 * @date 2021年04月16日 23:11
 */
public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    public boolean register(User user);

    /**
     * 激活用户
     * @return
     * @param code
     */
    boolean Active(String code);

    /**
     * 登录用户
     * @param user
     * @return
     */
    User login(User user);
}
