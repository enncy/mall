package cn.enncy.mall.pojo;

/**
 * //TODO
 * <br/>Created in 17:04 2021/11/7
 *
 * @author enncy
 */
public enum Role {
    // 用户
    USER("user"),
    // 管理员
    ADMIN("admin");

    public String value;

    Role(String value){
        this.value = value;
    }
}
