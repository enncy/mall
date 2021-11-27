package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.*;
import cn.enncy.mall.constant.InputType;

import cn.enncy.mall.constant.ServiceMapping;
import cn.enncy.mall.constant.Tag;

/**
 * //TODO
 * <br/>Created in 14:20 2021/11/18
 *
 * @author enncy
 */
public class Order extends BaseObject{

    @Reference( ServiceMapping.USER )
    @Show
    @Info(value = "用户id",tag = Tag.REFERENCE)
    private long userId;

    @Show
    @Reference( ServiceMapping.ADDRESS )
    @Info(value = "地址id",tag = Tag.REFERENCE)
    private long addressId;

    @Show
    @Reference( ServiceMapping.GOODS )
    @Info(value = "商品id",tag = Tag.REFERENCE)
    private long goodsId;

    @Show
    @Info(value = "数量",type = InputType.NUMBER)
    private int count;

    @Show
    @Info(value = "状态",tag = Tag.SELECT)
    @Select(options = {
            @Option(value = "payment",description = "待付款"),@Option(value = "receiving",description = "配送中"),@Option(value = "finished",description = "完成"),@Option(value = "cancel",description = "取消")
    })
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
