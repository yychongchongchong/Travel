package web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //完成方法分发
        //1.获取请求路径  /travel/user/xxx
        String uri = req.getRequestURI();
        //2.获取方法名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
        //3.获取方法对象Method
        //UserServlet调用BaseServlet，this指UserServlet
        try {
            //getClass()获取字节码文件
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //4.执行方法
            //暴力反射
            //method.setAccessible(true);
            method.invoke(this,req,resp);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将传入对象序列化为json，写回客户端，字节流方式
     * @param obj
     * @param response
     */
    public void writeValue(Object obj,HttpServletResponse response) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(),obj);
    }

    /**
     * 将传入对象序列化为json，写回客户端，字符流方式
     * @param obj
     * @param response
     * @throws IOException
     */
    public void writeValueAsString(Object obj,HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(obj);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
}
