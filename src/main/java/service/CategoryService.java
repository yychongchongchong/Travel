package service;

import domain.Category;

import java.util.List;

/**
 * @author jy
 * @date 2021年04月18日 11:36
 */
public interface CategoryService {

    /**
     * 查询所有类目信息
     * @return
     */
    public List<Category> findAll();
}
