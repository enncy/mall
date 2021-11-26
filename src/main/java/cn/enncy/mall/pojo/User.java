package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.Info;
import cn.enncy.mall.annotaion.Option;
import cn.enncy.mall.annotaion.Select;
import cn.enncy.mall.annotaion.Showable;
import cn.enncy.mall.constant.InputType;
import cn.enncy.mall.constant.Role;
import cn.enncy.mall.constant.Tag;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * //TODO
 * <br/>Created in 17:35 2021/11/6
 *
 * @author enncy
 */
public class User extends BaseObject {
    /**
     * 昵称
     **/
    @Showable
    @Info("昵称")
    private String nickname;
    /**
     * 账号
     **/
    @Showable
    @Info("账号")
    private String account;
    /**
     * 密码
     **/
    @Info(value = "密码",type = InputType.PASSWORD)
    private String password;
    /**
     * 权限
     **/
    @Showable
    @Info(value = "权限",tag = Tag.SELECT)

    @Select(options = {
            @Option(value = "user",description = "普通用户"),@Option(value = "admin",description = "管理员")
    })
    private String role;
    /**
     * 邮箱
     **/
    @Info(value = "邮箱",type = InputType.EMAIL)
    private String email;
    /**
     * 头像
     **/

    @Info(value = "头像路径",tag = Tag.IMAGE)
    private String avatar;
    /**
     * 简介
     **/
    @Info("简介")
    private String profile;
    /**
     * 余额
     **/
    @Info(value = "余额",type = InputType.NUMBER, disabled = true)
    private BigDecimal balance = new BigDecimal("0");
    /**
     * 是否激活
     **/
    @Select(options = {
            @Option(value = "true",description = "已激活"),@Option(value = "false",description = "未激活")
    })
    @Info(value = "是否激活",tag = Tag.SELECT, disabled = true)
    private boolean active;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", nickname='" + nickname + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", profile='" + profile + '\'' +
                ", balance=" + balance +
                ", active=" + active +
                '}';
    }
}
