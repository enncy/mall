package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.*;
import cn.enncy.mall.constant.InputType;
import cn.enncy.mall.constant.ServiceMapping;
import cn.enncy.mall.constant.Tag;

import java.math.BigDecimal;

/**
 * //TODO
 * <br/>Created in 23:21 2021/11/17
 *
 * @author enncy
 */
public class Goods extends BaseObject{
    @Show
    @Info("名字")
    private String name;

    @Show
    @Reference(ServiceMapping.TAG)
    @Info(value = "标签",tag = Tag.REFERENCE)
    private long tagId;

    @Show
    @Info(value = "价格",type = InputType.NUMBER)
    private BigDecimal price;


    @Info("描述")
    private String description;


    @Info("图片路径")
    private String img;

    @Show
    @Info(value = "是否上架",tag = Tag.SELECT)
    @Select(options = {
            @Option(value = "true",description = "已上架"),@Option(value = "false",description = "已下架")
    })
    private boolean selling;

    @Info(value = "库存",type = InputType.NUMBER)
    private int stock;


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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", name='" + name + '\'' +
                ", tagId=" + tagId +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", img='" + img + '\'' +
                ", selling=" + selling +
                ", stock=" + stock +
                '}';
    }
}
