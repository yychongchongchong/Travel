package service.Impl;

import dao.Impl.UserDaoImp;
import dao.UserDao;
import domain.User;
import service.UserService;
import util.MailUtils;
import util.UuidUtil;

/**
 * @author jy
 * @date 2021年04月16日 23:12
 */
public class UserServiceImp implements UserService {
    private UserDao dao = new UserDaoImp();
    @Override
    public boolean register(User user) {
        //根据用户名查询用户对象
        User registerUser = dao.findByUsername(user.getUsername());
        if(registerUser == null){
            //该用户不存在
            //保存用户信息
            //设置激活码和激活状态
            user.setCode(UuidUtil.getUuid());
            user.setStatus("N");
            dao.save(user);
            //发送激活邮件
            //设置邮件内容
            String content = "<a href='http://39117e50r5.qicp.vip:44565/travel/user/activeUser?code="+ user.getCode() +"'>点击激活</a>";
            //发送邮件
            MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        }else {
            //该用户存在
            return false;
        }

        return true;
    }

    @Override
    public boolean Active(String code) {
        //根据激活码查询用户对象
        User user = dao.findByCode(code);
        if(user != null){
            //判断用户是否已经被激活
            if(user.getStatus().equals("Y")){
                //已经激活
            }else {
                //修改状态为Y
                dao.setStatus(user);
                return true;
            }

        }else {
            //激活码不存在
        }
        return false;
    }

    @Override
    public User login(User user) {
        return dao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
