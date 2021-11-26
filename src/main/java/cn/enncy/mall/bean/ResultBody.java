package cn.enncy.mall.bean;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * //TODO
 * <br/>Created in 23:07 2021/11/25
 *
 * @author enncy
 */
public class ResultBody {

    private boolean status;
    private String msg;
    private Object data;


    public ResultBody(Object data) {
        this.status = true;
        this.data = data;
        this.msg = "无";
    }

    public ResultBody(boolean status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.PrettyFormat);
    }


    public static ResultBody of(Object data) {
        return new ResultBody(data != null, data == null ? "获取失败" : "获取成功", data);
    }

    public static ResultBody error() {
        return new ResultBody(false, "获取失败", null);
    }

    public static ResultBody success() {
        return new ResultBody(true, "获取成功", null);
    }
}
