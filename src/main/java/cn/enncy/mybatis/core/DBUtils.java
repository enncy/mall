package cn.enncy.mybatis.core;
import cn.enncy.mybatis.entity.MybatisException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


/**
 * //TODO
 * <br/>Created in 15:52 2021/11/6
 *
 * @author enncy
 */
public class DBUtils {
    private static final ComboPooledDataSource DATA_SOURCE = new ComboPooledDataSource();
    private static final Map<Thread, Connection> CONNECTION_MAP = new HashMap<>();

    public static void closeCurrentConnection() throws SQLException {
        Connection conn = CONNECTION_MAP.remove(Thread.currentThread());
        if(conn!=null){
            conn.close();
        }
    }

    // 数据库 连接池创建连接
    public static Connection getConnect() throws SQLException {
        return DATA_SOURCE.getConnection();
    }


    public static Object connect(Connector connector) throws   MybatisException {
        Thread thread = Thread.currentThread();
        Connection conn = CONNECTION_MAP.get(thread);

        try {
            if(conn==null){
                conn = getConnect();
                CONNECTION_MAP.put(thread, conn);
            }
            try(
                    Statement statement = conn.createStatement()
            ){
                return connector.run(statement);
            }catch(Throwable throwable){
                throwable.printStackTrace();
            }
        }catch (Exception e){
            throw new MybatisException(e.getMessage());
        }


        return null;
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DBUtils.connect(statement -> {

            statement.execute("begin");
            statement.execute("select date(now());");


            return true;
        });
    }


}
