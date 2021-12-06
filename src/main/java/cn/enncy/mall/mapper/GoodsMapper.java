package cn.enncy.mall.mapper;


import cn.enncy.mall.pojo.Goods;

import cn.enncy.mybatis.annotation.method.Executable;
import cn.enncy.mybatis.annotation.type.Mapper;
import cn.enncy.mybatis.annotation.param.Param;
import cn.enncy.mybatis.annotation.method.Select;
import cn.enncy.mybatis.annotation.type.Result;
import cn.enncy.mybatis.core.result.SingleResultHandler;

import java.util.List;

import static cn.enncy.mybatis.core.SqlConstant.TABLE_NAME;

/**
 * //TODO
 * <br/>Created in 23:23 2021/11/17
 *
 * @author  enncy
 */

@Mapper(table = "goods",target = Goods.class)
public interface GoodsMapper extends BaseMapper<Goods> , Searchable<Goods> {

    @Override
    @Select("select * from goods where  selling = 1 and description like '%#{str}%'  LIMIT #{skip} ,#{limit};")
    List<Goods> search(@Param("str") String str,@Param("skip") int skip,@Param("limit") int limit);

    @Select("select * from goods where selling = 1 and description like '%#{str}%' ")
    List<Goods> searchAll(@Param("str") String str);

    @Select("select goods.* from goods left join tag on goods.tag_id = tag.id where  selling = 1 and tag.name =  '#{tag}'  LIMIT #{skip} ,#{limit}; ")
    List<Goods> findByTagName(@Param("tag") String tag,@Param("skip") int skip,@Param("limit") int limit);

    @Executable(singleResult = true, resultMaps = {
            @Result(key = "count", target = int.class)
    })
    @Select("select count(*) as count from goods left join tag on goods.tag_id = tag.id where selling = 1 and  tag.name =  '#{tag}'")
    int countByTagName(@Param("tag") String tag);
}
