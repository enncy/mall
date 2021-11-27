package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.Info;
import cn.enncy.mall.annotaion.Showable;
import cn.enncy.mall.constant.InputType;

/**
 * //TODO
 * <br/>Created in 11:58 2021/11/27
 *
 * @author enncy
 */

public class Tag extends BaseObject {

    @Showable
    @Info(value = "标签名")
    private String name;

    @Info(value = "引用数量",type = InputType.NUMBER)
    private int count;

}
