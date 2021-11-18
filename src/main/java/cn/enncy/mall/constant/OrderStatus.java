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
    FINISH("finished","完成"),
    CANCEL("cancel","取消")
    ;

    public String status;
    public String description;

    OrderStatus(String status, String desc) {
        this.status = status;
        this.description = desc;
    }

}
