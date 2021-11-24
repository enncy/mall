package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.Info;
import cn.enncy.mall.constant.InputType;

/**
 * //TODO
 * <br/>Created in 22:59 2021/11/7
 *
 * @author enncy
 */
public class Address extends BaseObject {

    @Info(value = "用户id",type = InputType.NUMBER)
    private long userId;
    @Info(value = "手机",type = InputType.TEL)
    private String phone;
    @Info("详情")
    private String detail;
    @Info("收件人")
    private String receiver;
    @Info("备注")
    private String alias;
    @Info("邮编")
    private String zipCode;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "userId=" + userId +
                ", phone='" + phone + '\'' +
                ", detail='" + detail + '\'' +
                ", receiver='" + receiver + '\'' +
                ", alias='" + alias + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
