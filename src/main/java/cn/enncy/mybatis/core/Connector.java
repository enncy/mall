package cn.enncy.mybatis.core;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * //TODO
 * <br/>Created in 15:53 2021/11/6
 *
 * @author enncy
 */
public interface Connector {
    Object run(Statement statement) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
