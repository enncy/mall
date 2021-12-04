package cn.enncy.mall.service;


import cn.enncy.mall.pojo.Cart;
import cn.enncy.mall.pojo.Comment;

import java.util.List;



/**
 * //TODO
 * <br/>Created in 14:05 2021/12/4
 *
 * @author enncy
 */
public interface CommentService  extends  BaseService<Comment> {

    List<Comment> findByGoodsId( long goodsId);


}
