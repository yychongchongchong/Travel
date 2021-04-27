package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Category;
import service.CategoryService;
import service.Impl.CategoryServiceImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    //声明CategoryService业务对象
    CategoryService categoryService = new CategoryServiceImp();

    /**
     * 查询所有分类信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //调用service方法
        List<Category> categories = categoryService.findAll();
        //将数据转化为json转发回客户端
        writeValueAsString(categories,response);
    }


}
