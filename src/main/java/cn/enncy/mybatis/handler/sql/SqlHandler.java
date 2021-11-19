package cn.enncy.mybatis.handler.sql;

import cn.enncy.mybatis.entity.MybatisSqlError;
import cn.enncy.mybatis.entity.SQL;

import java.sql.SQLException;

/**
 * //TODO
 * <br/>Created in 18:31 2021/11/19
 *
 * @author enncy
 */
public interface SqlHandler {
    SQL handle(SQL sql) throws MybatisSqlError, SQLException;
}
