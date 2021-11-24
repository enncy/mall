package cn.enncy.mall.constant;

/**
 * //TODO
 * <br/>Created in 20:59 2021/11/23
 *
 * @author enncy
 */
public enum Tag {
    // 元素标签
    INPUT("input"),SELECT("select"),TEXTAREA("textarea");
    public String value;

    Tag(String value){
        this.value = value;
    }
}
