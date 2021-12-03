package cn.enncy.mybatis.core.result;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * //TODO
 * <br/>Created in 13:40 2021/11/19
 *
 * @author enncy
 */
public class SingleResultHandler extends MapResultHandler {


    public SingleResultHandler(ResultSet resultSet, Map<String, Class<?>> resultMap) {
        super(resultSet, resultMap);
    }

    @Override
    public Object handle() throws SQLException {

        if (resultSet.next()) {
            Map<String, Object> stringObjectMap = super.handleMap();
            // 直接返回第一个
            return stringObjectMap.entrySet().iterator().next().getValue();
        }

        return null;
    }
}
