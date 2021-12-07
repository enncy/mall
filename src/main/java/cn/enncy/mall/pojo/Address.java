package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.*;
import cn.enncy.mall.constant.InputType;
import cn.enncy.mall.constant.ServiceMapping;
import cn.enncy.mall.constant.Tag;

/**
 * //TODO
 * <br/>Created in 22:59 2021/11/7
 *
 * @author enncy
 */
public class Address extends BaseObject {
    @Show
    @Reference(ServiceMapping.USER)
    @Info(value = "用户id",tag = Tag.REFERENCE)
    private long userId;

    @Show
    @Info("备注")
    private String alias;

    @Show
    @Info("收件人名称")
    private String receiver;

    @Show
    @Info(value = "手机号",type = InputType.TEL)
    private String phone;


    @Info("地址详情")
    private String detail;


    @Info("邮编")
    private String zipCode;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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
                ", alias='" + alias + '\'' +
                ", receiver='" + receiver + '\'' +
                ", phone='" + phone + '\'' +
                ", detail='" + detail + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    // 创建默认地址详情
    public String createOrderAddressDetails(){
        return this.getDetail().replaceAll("\\n"," ") +" - "+ this.getPhone();
    }
}
