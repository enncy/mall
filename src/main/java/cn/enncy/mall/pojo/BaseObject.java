package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.Info;
import cn.enncy.mall.annotaion.Showable;
import cn.enncy.mall.constant.InputType;
import cn.enncy.mall.utils.formatter.DateFormatter;


/**
 * //TODO
 * <br/>Created in 17:35 2021/11/6
 *
 * @author enncy
 */
public class BaseObject {
    @Showable
    @Info(value = "id", type = InputType.NUMBER, disabled = true, rank = 1)
    protected long id;
    @Info(value = "创建时间", formatter = DateFormatter.class, disabled = true)
    protected long createTime;

    @Info(value = "更新时间", formatter = DateFormatter.class, disabled = true)
    protected long updateTime;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "BaseObject{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
