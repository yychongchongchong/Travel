package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.ResultInfo;
import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.Impl.UserServiceImp;
import service.UserService;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;


@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    //声明UserService业务对象
    UserService userService = new UserServiceImp();

    /**
     * 注册用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //响应客户端消息对象
        ResultInfo info = new ResultInfo();
        //验证码校验
        //获取前端验证码
        String CheckCode = request.getParameter("check");
        //从session中获取生成的验证码
        HttpSession session = request.getSession();
        String realCheckCode = (String) session.getAttribute("CHECKCODE_SERVER");
        //清空验证码
        session.removeAttribute("CHECKCODE_SERVER");
        //判断验证码是否一致
        if(realCheckCode != null && realCheckCode.length() != 0 && realCheckCode.equalsIgnoreCase(CheckCode)){
            //一致
            //获取前端数据
            Map<String, String[]> parameterMap = request.getParameterMap();
            //封装对象
            User user = new User();
            try {
                BeanUtils.populate(user,parameterMap);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //调用service方法，实现注册
            boolean flag = userService.register(user);
            if(flag){
                //注册成功
                info.setFlag(true);
            }else {
                //注册失败
                info.setFlag(flag);
                info.setErrorMsg("注册失败！");
            }
        }else {
            //不一致
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
        }
        //将info对象序列化为json，写回客户端
        writeValueAsString(info,response);
    }

    /**
     * 登录用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //响应客户端消息对象
        ResultInfo info = new ResultInfo();
        //获取用户名，密码数据
        Map<String, String[]> parameterMap = request.getParameterMap();
        //封装user对象
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //验证码校验
        //获取前端验证码
        String CheckCode = request.getParameter("check");
        //从session中获取生成的验证码
        HttpSession session = request.getSession();
        String realCheckCode = (String) session.getAttribute("CHECKCODE_SERVER");
        //清空验证码
        session.removeAttribute("CHECKCODE_SERVER");
        //判断验证码是否一致
        if(realCheckCode != null && realCheckCode.length() != 0 && realCheckCode.equalsIgnoreCase(CheckCode)){
            //验证码正确
            //调用service方法
            User loginUser = userService.login(user);
            //判断loginUser是否为空
            if(loginUser == null){
                //登陆失败
                info.setFlag(false);
                info.setErrorMsg("用户名或密码错误！");
            }else if("N".equals(loginUser.getStatus())){
                //还未激活
                info.setFlag(false);
                info.setErrorMsg("该账户还未激活！");
            }else {
                //登陆成功
                info.setFlag(true);
                //将用户存入session中
                session.setAttribute("loginUser",loginUser);
            }
        }else {
            //验证码错误
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
        }
        //将info对象序列化为json，写回客户端
        writeValueAsString(info,response);
    }

    /**
     * 激活用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void activeUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取code
        String code = request.getParameter("code");
        if(code != null){
            //调用Service方法
            boolean flag = userService.Active(code);
            //判断标记
            String msg = null;
            if(flag){
                //激活成功
                msg = "激活成功，请<a href='login.html'>登录</a>";
            }else {
                //激活失败
                msg = "激活失败";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    /**
     * 根据session查找用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findLoginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取当前登录用户
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        //将loginUser对象序列化为json，写回客户端
        writeValueAsString(loginUser,response);
    }

    /**
     * 注销用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //销毁session
        request.getSession().invalidate();
        //跳转到登陆页面
        response.sendRedirect(request.getContextPath() + "/login.html");
        //System.out.println(request.getContextPath());/travel
    }
}
