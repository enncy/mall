package cn.enncy.mall.constant;

/**
 * //TODO
 * <br/>Created in 20:59 2021/11/23
 *
 * @author enncy
 */
public enum Tag {
    // 属性标签
    INPUT("input"),SELECT("select"),TEXTAREA("textarea"),REFERENCE("reference"),IMAGE("image");
    public String value;

    Tag(String value){
        this.value = value;
    }
}
