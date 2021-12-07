package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.*;
import cn.enncy.mall.constant.InputType;

import cn.enncy.mall.constant.ServiceMapping;
import cn.enncy.mall.constant.Tag;
import cn.enncy.mall.utils.formatter.DateFormatter;

import java.math.BigDecimal;

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

    @Info("订单编号")
    private String uid;

    @Show
    @Info("地址详情")
    private String addressDetail;

    @Show
    @Info(value = "状态",tag = Tag.SELECT)
    @Select(options = {
            @Option(value = "payment",description = "待付款"),@Option(value = "receiving",description = "配送中"),@Option(value = "finished",description = "完成"),@Option(value = "cancel",description = "取消")
    })
    private String status;

    @Show
    @Info(value = "总金额",type = InputType.NUMBER,disabled = true)
    private BigDecimal totalPrice;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", userId=" + userId +
                ", uid='" + uid + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", status='" + status + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }

    // 创建唯一订单号  精确到秒级的日期 + 用户id
    public  static   String createUid(long userId){
        return DateFormatter.format(System.currentTimeMillis(), "yyyyMMddHHmmss") + userId;
    }
}
