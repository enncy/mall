package cn.enncy.mall.mapper;

import cn.enncy.mybatis.annotation.method.*;
import cn.enncy.mybatis.annotation.param.Body;
import cn.enncy.mybatis.annotation.param.Param;
import cn.enncy.mybatis.annotation.type.Result;
import cn.enncy.mybatis.core.result.SingleResultHandler;

import java.util.List;

import static cn.enncy.mybatis.core.SqlConstant.*;

/**
 * //TODO
 * <br/>Created in 16:39 2021/11/6
 *
 * @author enncy
 */


public interface BaseMapper<T> {

    @Executable(handler = SingleResultHandler.class, resultMaps = {
            @Result(key = "count", target = int.class)
    })
    @Select("SELECT COUNT(*) as count FROM #{TABLE_NAME};")
    int count();

    @Insert("INSERT IGNORE INTO #{TABLE_NAME}(#{" + KEY_ARRAY + "}) value(#{" + VALUE_ARRAY + "});")
    boolean insert(@Body() T baseObject);

    @Delete("DELETE FROM #{TABLE_NAME} WHERE id=#{id};")
    boolean deleteById(@Param("id") long id);

    @Select("SELECT * FROM #{TABLE_NAME} WHERE id=#{id};")
    T findOneById(@Param("id") long id);

    @Select("SELECT * FROM #{TABLE_NAME}  LIMIT #{skip} ,#{limit}; ")
    List<T> findByPages(@Param("skip") int skip, @Param("limit") int limit);

    @Select("SELECT * FROM #{TABLE_NAME};")
    List<T> findAll();

    @Update("UPDATE IGNORE #{TABLE_NAME} SET #{" + SET_ARRAY + "} WHERE id=#{id};")
    boolean update(@Body() T baseObject);



}
