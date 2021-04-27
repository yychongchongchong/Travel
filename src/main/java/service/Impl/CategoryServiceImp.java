package service.Impl;

import dao.CategoryDao;
import dao.Impl.CategoryDaoImp;
import domain.Category;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import service.CategoryService;
import util.JedisPoolUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author jy
 * @date 2021年04月18日 11:42
 */
public class CategoryServiceImp implements CategoryService {
    private CategoryDao dao = new CategoryDaoImp();
    @Override
    public List<Category> findAll() {
        //从redis中查询
        Jedis jedis = JedisPoolUtils.getJedis();
        //Set<String> category = jedis.zrange("category", 0, -1);
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        List<Category> categories = null;
        //判断查询集合是否为空
        if(category == null || category.size() == 0){
            //为空，需要从数据库查询，并将数据写入redis
            categories = dao.findAll();
            for (int i = 0; i < categories.size(); i++) {
                jedis.zadd("category",categories.get(i).getCid(),categories.get(i).getCname());
            }
        }else {
            //不为空，将查询到的set集合存入list
            categories = new ArrayList<Category>();
            for (Tuple tuple : category) {
                Category cago = new Category();
                cago.setCname(tuple.getElement());
                cago.setCid((int) tuple.getScore());
                categories.add(cago);
            }
        }
        jedis.close();
        return categories;
    }
}
