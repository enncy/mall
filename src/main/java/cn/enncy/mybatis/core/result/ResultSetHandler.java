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
    /**
     *  结果集处理器
     *
     * @param resultSet 查询结果集
     * @param resultMap 字段类型映射表
     * @param resultType 返回类型
     * @return java.lang.Object
     */
    Object handle(ResultSet resultSet, Map<String, Class<?>> resultMap, Class<?>  resultType) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
