package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.Info;
import cn.enncy.mall.constant.InputType;

/**
 * //TODO
 * <br/>Created in 14:20 2021/11/18
 *
 * @author  enncy
 */
public class Cart extends BaseObject {
    @Info(value = "用户id",type = InputType.NUMBER)
    private long userId;
    @Info(value = "商品id",type = InputType.NUMBER)
    private long goodsId;
    @Info(value = "数量",type = InputType.NUMBER)
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
