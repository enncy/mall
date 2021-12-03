package cn.enncy.mybatis.core.result;


import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * //TODO
 * <br/>Created in 23:23 2021/11/18
 *
 * @author enncy
 */
public class ListResultHandler extends MapResultHandler implements ResultSetHandler {
    ResultSet resultSet;
    Class<?> resultType;

    public ListResultHandler(ResultSet resultSet, Class<?> resultType, Map<String, Class<?>> resultMap) {
        super(resultSet,resultMap);
        this.resultSet = resultSet;
        this.resultType = resultType;

    }

    @Override
    public Object handle() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // 目标转换类型根据 resultType 字段 sql 注解的优先级最高，其次 到mapper 的注解
        List<Object> list = new ArrayList<>();
        if (Map.class.isAssignableFrom(resultType)) {
            while (resultSet.next()) {
                list.add(this.handleMap());
            }
        } else {
            while (resultSet.next()) {
                list.add(ObjectResultHandler.createResultTarget(resultSet, resultType));
            }
        }

        return list;

    }
}
