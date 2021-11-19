package cn.enncy.mybatis.core.result;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * //TODO
 * <br/>Created in 23:24 2021/11/18
 *
 * @author enncy
 */
public interface ResultSetHandler {

    Object handle() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
