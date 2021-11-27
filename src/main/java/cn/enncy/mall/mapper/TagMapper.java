package cn.enncy.mall.mapper;


import cn.enncy.mall.pojo.Tag;
import cn.enncy.mybatis.annotation.method.Select;
import cn.enncy.mybatis.annotation.param.Param;
import cn.enncy.mybatis.annotation.type.Mapper;

import java.util.List;

import static cn.enncy.mybatis.core.SqlConstant.TABLE_NAME;

/**
 * //TODO
 * <br/>Created in 12:00 2021/11/27
 *
 * @author enncy
 */

@Mapper(table = "tag",target = Tag.class)
public interface TagMapper  extends BaseMapper<Tag> , Searchable<Tag>{


    @Select("select * from #{"+ TABLE_NAME +"}  where  name = '#{name}'")
    Tag findOneByName(@Param("name") String name);

    @Select("select * from #{"+ TABLE_NAME +"}  order by `count` desc  LIMIT 0,10 ;")
    List<Tag> findByCount();


    @Override
    @Select("select * from #{"+  TABLE_NAME +"}  where  name like '%#{key}%'   LIMIT #{skip} ,#{limit};")
    List<Tag> search(@Param("key") String key,@Param("skip") int skip,@Param("limit") int limit);
}
