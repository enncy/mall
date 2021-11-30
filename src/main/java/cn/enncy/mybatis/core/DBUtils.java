package cn.enncy.mybatis.core;


import cn.enncy.mybatis.entity.DataSource;
import cn.enncy.mybatis.entity.MybatisException;
import cn.enncy.spring.mvc.PropertiesUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * //TODO
 * <br/>Created in 15:52 2021/11/6
 *
 * @author enncy
 */
public class DBUtils {

    private static final DataSource DATA_SOURCE = new DataSource();

    private static final Map<Thread, Connection> CONNECTION_MAP = new HashMap<>();

    public static void closeCurrentConnection() throws SQLException {
        Connection conn = CONNECTION_MAP.remove(Thread.currentThread());
        conn.close();
    }


    static {
        try {
            PropertiesUtils propertiesUtils = new PropertiesUtils(Objects.requireNonNull(DBUtils.class.getClassLoader().getResource("db.properties")).getPath());
            DATA_SOURCE.setUsername(propertiesUtils.get("username"));
            DATA_SOURCE.setPassword(propertiesUtils.get("password"));
            DATA_SOURCE.setDriver(propertiesUtils.get("driver"));
            DATA_SOURCE.setUrl(propertiesUtils.get("url"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object connect(Connector connector) throws   MybatisException {
        Thread thread = Thread.currentThread();
        Connection conn = CONNECTION_MAP.get(thread);



        try {
            if(conn==null){
                Class.forName(DATA_SOURCE.getDriver());
                conn = DriverManager.getConnection(DATA_SOURCE.getUrl(), DATA_SOURCE.getUsername(), DATA_SOURCE.getPassword());
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
