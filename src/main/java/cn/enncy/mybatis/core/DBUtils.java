package cn.enncy.mybatis.core;


import cn.enncy.mybatis.entity.DataSource;
import cn.enncy.spring.mvc.PropertiesUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * //TODO
 * <br/>Created in 15:52 2021/11/6
 *
 * @author enncy
 */
public class DBUtils {

    private static final DataSource DATA_SOURCE = new DataSource();


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

    static Object connect(Connector connector) throws ClassNotFoundException, SQLException {
        Class.forName(DATA_SOURCE.getDriver());
        Connection connection = DriverManager.getConnection(DATA_SOURCE.getUrl(), DATA_SOURCE.getUsername(), DATA_SOURCE.getPassword());
        try(
                Statement statement = connection.createStatement()
        ){
            // 开启事务
            connection.setAutoCommit(false);
            Object run = connector.run(statement);
            connection.commit();
            return run;
        }catch(Throwable throwable){
            connection.rollback();
            throwable.printStackTrace();
        }finally {
            connection.close();
        }
        return null;
    }



}
