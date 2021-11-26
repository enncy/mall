package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.Info;
import cn.enncy.mall.annotaion.Reference;
import cn.enncy.mall.annotaion.Select;
import cn.enncy.mall.constant.InputType;
import cn.enncy.mall.constant.ServiceMapping;
import cn.enncy.mall.constant.Tag;

/**
 * //TODO
 * <br/>Created in 14:20 2021/11/18
 *
 * @author enncy
 */
public class Cart extends BaseObject {
    @Reference(ServiceMapping.USER)
    @Info(value = "用户id", tag = Tag.REFERENCE)
    private long userId;
    @Reference(ServiceMapping.GOODS)
    @Info(value = "商品id", tag = Tag.REFERENCE)
    private long goodsId;
    @Info(value = "数量", type = InputType.NUMBER)
    private int count;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userId=" + userId +
                ", goodsId=" + goodsId +
                ", count=" + count +
                '}';
    }
}
