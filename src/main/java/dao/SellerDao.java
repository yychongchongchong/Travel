package dao;

import domain.Seller;

/**
 * @author jy
 * @date 2021年04月19日 16:25
 */
public interface SellerDao {

    /**
     * 根据sid查询商家信息
     * @param sid
     * @return
     */
    public Seller findById(int sid);
}
