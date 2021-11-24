package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.Info;
import cn.enncy.mall.annotaion.Option;
import cn.enncy.mall.annotaion.Select;
import cn.enncy.mall.constant.InputType;
import cn.enncy.mall.constant.Tag;

import java.math.BigDecimal;

/**
 * //TODO
 * <br/>Created in 23:21 2021/11/17
 *
 * @author enncy
 */
public class Goods extends BaseObject{

    @Info("名字")
    private String name;
    @Info(value = "价格",type = InputType.NUMBER)
    private BigDecimal price;
    @Info("描述")
    private String description;
    @Info("图片路径")
    private String img;

    @Info(value = "是否上架",tag = Tag.SELECT)
    @Select(options = {
            @Option(value = "true",description = "已上架"),@Option(value = "false",description = "已下架")
    })
    private boolean selling;


    @Info(value = "库存",type = InputType.NUMBER)
    private int count;
    @Info("标签")
    private String tag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isSelling() {
        return selling;
    }

    public void setSelling(boolean selling) {
        this.selling = selling;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", img='" + img + '\'' +
                ", selling=" + selling +
                ", count=" + count +
                ", tag='" + tag + '\'' +
                '}';
    }
}
