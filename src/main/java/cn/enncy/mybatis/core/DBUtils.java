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


    static Object connect(Connector connector) throws ClassNotFoundException {

        Class.forName(DATA_SOURCE.getDriver());

        try(
                Connection connection = DriverManager.getConnection(DATA_SOURCE.getUrl(), DATA_SOURCE.getUsername(), DATA_SOURCE.getPassword());
                Statement statement = connection.createStatement()

        ){
           return connector.run(statement);
        }catch(Throwable throwable){
            throwable.printStackTrace();
        }

        return null;

    }

}
