package cn.enncy.mall.pojo;

import javax.servlet.http.HttpSession;

/**
 * //TODO
 * <br/>Created in 14:12 2021/11/8
 *
 * @author: enncy
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

    public static void save(HttpSession session, MallSession mallSession, Object value){
        session.setAttribute(mallSession.key, value);
    }

    public static <T> T from(HttpSession session, Class<T> type){
        MallSession[] values = MallSession.values();
        for (MallSession value : values) {
            if(value.type == type){
              return (T) session.getAttribute(value.key);
            }
        }
        return null;
    }

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
