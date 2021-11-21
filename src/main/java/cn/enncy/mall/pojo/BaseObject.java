package cn.enncy.mall.pojo;


import cn.enncy.mall.annotaion.Info;


/**
 * //TODO
 * <br/>Created in 17:35 2021/11/6
 *
 * @author enncy
 */
public class BaseObject {
    @Info("id")
    private long id;
    @Info("创建时间")
    private long createTime;

    @Info("更新时间")
    private long updateTime;


    public BaseObject() {
        this.createTime = System.currentTimeMillis();
        this.updateTime = this.createTime;
    }


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
