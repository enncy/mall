package cn.enncy.mall.pojo;


import java.math.BigDecimal;

/**
 * //TODO
 * <br/>Created in 23:21 2021/11/17
 *
 * @author enncy
 */
public class Goods extends BaseObject{

    private String name;
    private BigDecimal price;
    private String description;
    private String img;
    /** 是否上架 **/
    private boolean selling;
    /** 库存 **/
    private int count;
    /** 标签 **/
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
