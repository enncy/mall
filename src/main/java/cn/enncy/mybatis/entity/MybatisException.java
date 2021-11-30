package cn.enncy.mybatis.entity;


import java.sql.SQLException;

/**
 * //TODO
 * <br/>Created in 23:08 2021/11/7
 *
 * @author enncy
 */
public class MybatisException extends SQLException {
    public MybatisException(String message) {
        super(message);
    }
}
