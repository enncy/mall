package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.Info;
import cn.enncy.mall.annotaion.Reference;
import cn.enncy.mall.constant.ServiceMapping;
import cn.enncy.mall.constant.Tag;

/**
 * //TODO
 * <br/>Created in 13:59 2021/12/4
 *
 * @author enncy
 */

public class Comment extends BaseObject{

    @Reference(ServiceMapping.USER)
    @Info(value = "用户id",tag = Tag.REFERENCE)
    private long userId;

    @Reference(ServiceMapping.GOODS)
    @Info(value = "商品Id",tag = Tag.REFERENCE)
    private long goodsId;

    @Info(value = "回复id",tag = Tag.REFERENCE)
    private long parentId;


    @Info("评论")
    private String content;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", parentId=" + parentId +
                ", content='" + content + '\'' +
                '}';
    }
}
