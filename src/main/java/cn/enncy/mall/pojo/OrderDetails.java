package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.Info;
import cn.enncy.mall.annotaion.Reference;
import cn.enncy.mall.annotaion.Show;
import cn.enncy.mall.constant.InputType;
import cn.enncy.mall.constant.ServiceMapping;
import cn.enncy.mall.constant.Tag;

import java.math.BigDecimal;

/**
 * //TODO
 * <br/>Created in 21:03 2021/11/29
 *
 * @author enncy
 */
public class OrderDetails extends BaseObject{

    @Reference(value = ServiceMapping.ORDER, key = "uid")
    @Info(value = "订单编号", tag = Tag.REFERENCE, disabled = true)
    private String orderUid;


    @Reference(ServiceMapping.GOODS)
    @Info(value = "商品id",tag = Tag.REFERENCE)
    private long goodsId;


    @Show
    @Info("商品描述")
    private String description;

    @Show
    @Info(value = "商品单价",type = InputType.NUMBER)
    private BigDecimal price;

    @Info(value = "商品图片",tag = Tag.IMAGE,disabled = true)
    private String img;

    @Info(value = "商品数量",type = InputType.NUMBER)
    private int count;


    public String getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(String orderUid) {
        this.orderUid = orderUid;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", orderUid='" + orderUid + '\'' +
                ", goodsId=" + goodsId +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", img='" + img + '\'' +
                ", count=" + count +
                '}';
    }

    public BigDecimal getTotalPrice(){
        return this.getPrice().multiply(BigDecimal.valueOf(this.getCount()));
    }

    public static OrderDetails createOrderDetails(String orderUid,int count,Goods goods){
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setGoodsId(goods.getId());
        orderDetails.setOrderUid(orderUid);
        orderDetails.setDescription(goods.getDescription());
        orderDetails.setImg(goods.getImg());
        orderDetails.setPrice(goods.getRealPrice());
        orderDetails.setCount(count);
        return orderDetails;
    }
}
