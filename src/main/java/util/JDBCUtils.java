package util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author jy
 * @date 2021年04月07日 14:14
 * JDBC工具类，使用Druid连接池
 */
public class JDBCUtils {

    private static DataSource ds;

    static {
        try {
            //1.加载配置文件
            Properties pro = new Properties();
            //使用类加载器加载配置文件并获取字节输入流
            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            //传入字节输入流，加载配置文件
            pro.load(is);
            
            //2.初始化连接池对象
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取连接池对象
     */
    public static DataSource getDataSource(){

        return ds;
    }
    /**
     * 获取Connection对象
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
