package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.Info;

/**
 * //TODO
 * <br/>Created in 14:20 2021/11/18
 *
 * @author  enncy
 */
public class Cart extends BaseObject {
    @Info("用户id")
    private long userId;
    @Info("商品id")
    private long goodsId;
    @Info("数量")
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
