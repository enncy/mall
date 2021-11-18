package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.Info;

/**
 * //TODO
 * <br/>Created in 14:20 2021/11/18
 *
 * @author enncy
 */
public class Order extends BaseObject{

    @Info("用户id")
    private long userId;
    @Info("地址id")
    private long addressId;
    @Info("商品id")
    private long goodsId;
    @Info("数量")
    private int count;
    @Info("状态")
    private String status;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId=" + userId +
                ", addressId=" + addressId +
                ", goodsId=" + goodsId +
                ", count=" + count +
                ", status='" + status + '\'' +
                '}';
    }
}
