package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.Info;

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
    @Info("昵称")
    private String nickname;
    /**
     * 账号
     **/
    @Info("账号")
    private String account;
    /**
     * 密码
     **/
    @Info("密码")
    private String password;
    /**
     * 权限
     **/
    @Info("权限")
    private String role;
    /**
     * 邮箱
     **/
    @Info("邮箱")
    private String email;
    /**
     * 头像
     **/
    @Info("头像路径")
    private String avatar;
    /**
     * 简介
     **/
    @Info("简介")
    private String profile;
    /**
     * 余额
     **/
    @Info("余额")
    private BigDecimal balance = new BigDecimal("0");
    /**
     * 是否激活
     **/
    @Info("是否激活")
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
                "nickname='" + nickname + '\'' +
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
