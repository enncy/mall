package cn.enncy.mybatis.core;
import cn.enncy.mall.mapper.TestMapper;
import cn.enncy.mall.pojo.User;
import cn.enncy.mybatis.entity.MybatisException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * //TODO
 * <br/>Created in 15:52 2021/11/6
 *
 * @author enncy
 */
public class DBUtils {
    // c3p0
    private static final ComboPooledDataSource DATA_SOURCE = new ComboPooledDataSource();
    // 同一线程，同一链接
    private static final ThreadLocal<Connection> CONNECTION_MAP = new ThreadLocal<>();

    // 手动关闭此线程的连接
    public static void closeCurrentConnection() throws SQLException {
        Connection conn = CONNECTION_MAP.get();
        if(conn!=null){
            conn.close();
        }
        CONNECTION_MAP.remove();
    }

    // 执行 sql
    public static Object connect(Connector connector) throws   MybatisException {
        // 获取线程链接
        Connection conn = CONNECTION_MAP.get();
        try {
            // 如果连接为空，则新建连接，并保存
            if(conn==null){
                conn = DATA_SOURCE.getConnection();
                CONNECTION_MAP.set(conn);
            }
            try(
                    Statement statement = conn.createStatement()
            ){
                // 回调
                return connector.run(statement);
            }catch(Throwable throwable){
                throwable.printStackTrace();
            }
        }catch (Exception e){
            throw new MybatisException(e.getMessage());
        }
        return null;
    }
}
