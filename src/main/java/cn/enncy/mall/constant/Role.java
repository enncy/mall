package cn.enncy.mall.constant;

import java.util.Arrays;

/**
 * //TODO
 * <br/>Created in 17:04 2021/11/7
 *
 * @author enncy
 */
public enum Role {
    // 游客
    VISITOR("visitor",0),
    // 用户
    USER("user",1),
    // 管理员
    ADMIN("admin",2);

    public String value;
    public int rank;

    Role(String value,int rank){
        this.value = value;
        this.rank = rank;
    }

    public static Role parseRole(String str){
        Role[] roles = Role.values();
        for (Role role : roles) {
            if(role.value.equals(str)){
                return role;
            }
        }
        return Role.VISITOR;
    }


}
