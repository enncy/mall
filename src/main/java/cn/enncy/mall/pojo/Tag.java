package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.Info;
import cn.enncy.mall.annotaion.Show;
import cn.enncy.mall.constant.InputType;

/**
 * //TODO
 * <br/>Created in 11:58 2021/11/27
 *
 * @author enncy
 */

public class Tag extends BaseObject {

    @Show
    @Info(value = "标签名")
    private String name;

    @Info(value = "引用数量",type = InputType.NUMBER)
    private int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
