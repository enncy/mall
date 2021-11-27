package cn.enncy.mybatis.core;


import cn.enncy.mybatis.entity.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * //TODO
 * <br/>Created in 15:52 2021/11/6
 *
 * @author enncy
 */
public class DBUtils {

    private static final DataSource DATA_SOURCE = new DataSource("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/mall?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&charsetEncoding=UTF-8", "root", "enncymysql");


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
