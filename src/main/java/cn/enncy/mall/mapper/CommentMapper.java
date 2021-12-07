package cn.enncy.mall.mapper;

import cn.enncy.mall.pojo.Comment;
import cn.enncy.mybatis.annotation.method.Select;
import cn.enncy.mybatis.annotation.param.Param;
import cn.enncy.mybatis.annotation.type.Mapper;

import java.util.List;

import static cn.enncy.mybatis.core.SqlConstant.TABLE_NAME;

/**
 * //TODO
 * <br/>Created in 14:03 2021/12/4
 *
 * @author enncy
 */

@Mapper(table = "comment",target = Comment.class)
public interface CommentMapper extends BaseMapper<Comment>{

    @Select("select * from comment where goods_id = #{goodsId} ")
    List<Comment> findByGoodsId(@Param("goodsId") long goodsId);

}
