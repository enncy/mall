package cn.enncy.mall.mapper;

import cn.enncy.mybatis.annotation.*;

import java.util.List;

import static cn.enncy.mybatis.core.SqlConstant.*;

/**
 * //TODO
 * <br/>Created in 16:39 2021/11/6
 *
 * @author enncy
 */


public interface BaseMapper<T> {

    @Insert("INSERT IGNORE INTO #{TABLE_NAME}(#{" + KEY_ARRAY + "}) value(#{" + VALUE_ARRAY + "});")
    boolean insert(@Body() T baseObject);

    @Delete("DELETE FROM #{TABLE_NAME} WHERE id=#{id};")
    boolean deleteById(@Param("id") long id);

    @Select("SELECT * FROM #{TABLE_NAME} WHERE id=#{id};")
    T findOneById(@Param("id") long id);

    @Select("SELECT * FROM #{TABLE_NAME} LIMIT #{page},#{size};")
    List<T> findByPages(@Param("page") int page, @Param("size") int size);

    @Select("SELECT * FROM #{TABLE_NAME};")
    List<T> findAll();

    @Update("UPDATE IGNORE #{TABLE_NAME} SET #{" + SET_ARRAY + "} WHERE id=#{id};")
    boolean update(@Body() T baseObject);

}
