package cn.enncy.mall.constant;

/**
 * //TODO
 * <br/>Created in 14:38 2021/11/18
 *
 * @author  enncy
 */
public enum OrderStatus {
    // 订单状态
    PAYMENT("payment","待付款"),
    RECEIVING("receiving","待收货"),
    FINISH("finished","已收货"),
    CANCEL("cancel","取消")
    ;

    public String value;
    public String description;

    OrderStatus(String value, String desc) {
        this.value = value;
        this.description = desc;
    }

    public static String getDescription(String value) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if(orderStatus.value.equals(value)){
                return orderStatus.description;
            }
        }
        return "无";
    }
}

