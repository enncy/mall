package cn.enncy.mall.pojo;


/**
 * //TODO
 * <br/>Created in 22:59 2021/11/7
 *
 * @author enncy
 */
public class Address extends BaseObject {

    private long userId;
    private String phone;
    private String detail;
    private String receiver;
    private String alias;
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
