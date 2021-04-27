package dao;

import domain.Category;

import java.util.List;

/**
 * @author jy
 * @date 2021年04月18日 11:37
 */
public interface CategoryDao {
    /**
     * 查询所有类目
     * @return
     */
    public List<Category> findAll();
}
