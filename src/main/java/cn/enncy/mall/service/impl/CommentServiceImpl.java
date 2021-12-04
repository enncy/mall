package cn.enncy.mall.service.impl;


import cn.enncy.mall.mapper.CommentMapper;
import cn.enncy.mall.pojo.Cart;
import cn.enncy.mall.pojo.Comment;
import cn.enncy.mall.service.CommentService;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 14:06 2021/12/4
 *
 * @author enncy
 */
public class CommentServiceImpl  extends  ServiceImpl<Comment, CommentMapper>  implements CommentService {

    public CommentServiceImpl( ) {
        super(CommentMapper.class);
    }

    @Override
    public List<Comment> findByGoodsId(long goodsId) {
        return mapper.findByGoodsId(goodsId);
    }
}
