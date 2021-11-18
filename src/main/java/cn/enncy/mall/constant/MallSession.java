package cn.enncy.mall.constant;

import cn.enncy.mall.pojo.User;

import javax.servlet.http.HttpSession;

/**
 * 提供 session 的类型提示，类型判断
 * <br/>Created in 14:12 2021/11/8
 *
 * @author enncy
 */
public enum MallSession {
    /** 用户 **/
    USER(User.class,"user"),
    /** 重定向自动 **/
    ORIGIN(String.class, "origin");

    public Class<?> type;
    public String key;
    MallSession(Class<?> type,String key){
        this.type = type;
        this.key = key;
    }

    /**
     *  保存 session 对象
     *
     * @param session session
     * @param mallSession 映射对象
     * @param value 值
     */
    public static void save(HttpSession session, MallSession mallSession, Object value){
        session.setAttribute(mallSession.key, value);
    }


    /**
     *  根据类型获取 session 中的指定 对象
     *
     * @param session session
     * @param type 目标类型
     * @return java.lang.Object
     */
    public static <T> T from(HttpSession session, Class<T> type){
        MallSession[] values = MallSession.values();
        for (MallSession value : values) {
            if(value.type == type){
              return (T) session.getAttribute(value.key);
            }
        }
        return null;
    }

    /**
     *  根据类获取 session 中的指定 对象
     *
     * @param session session
     * @param mallSession 目标对象
     * @return java.lang.Object
     */
    public static Object from(HttpSession session, MallSession mallSession){
        MallSession[] values = MallSession.values();
        for (MallSession value : values) {
            if(value.key.equals(mallSession.key)){
                return   session.getAttribute(value.key);
            }
        }
        return null;
    }

}
