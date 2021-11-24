package cn.enncy.mall.constant;

/**
 * 输入框的类型 :
 * button	定义可点击的按钮（通常与 JavaScript 一起使用来启动脚本）。
 * checkbox	定义复选框。
 * color	定义拾色器。
 * date	定义 date 控件（包括年、月、日，不包括时间）。
 * email    定义用于 e-mail 地址的字段。
 * file	定义文件选择字段和 "浏览..." 按钮，供文件上传。
 * hidden	定义隐藏输入字段。
 * image	定义图像作为提交按钮。
 * month	定义 month 和 year 控件（不带时区）。
 * number	定义用于输入数字的字段。
 * password	定义密码字段（字段中的字符会被遮蔽）。
 * radio	定义单选按钮。
 * range	定义用于精确值不重要的输入数字的控件（比如 slider 控件）。
 * reset	定义重置按钮（重置所有的表单值为默认值）。
 * search	定义用于输入搜索字符串的文本字段。
 * submit	定义提交按钮。
 * tel	定义用于输入电话号码的字段。
 * text	默认。定义一个单行的文本字段（默认宽度为 20 个字符）。
 * time	定义用于输入时间的控件（不带时区）。
 * url	定义用于输入 URL 的字段。
 * week	定义 week 和 year 控件（不带时区）。
 * <br/>Created in 20:15 2021/11/23
 *
 * @author enncy
 */
public enum InputType {
    // 输入框的类型
    BUTTON("button"),
    CHECKBOX("checkbox"),
    COLOR("color"),
    DATE("date"),
    DATETIME("datetime"),
    DATETIME_LOCAL("datetime-local"),
    EMAIL("email"),
    FILE("file"),
    HIDDEN("hidden"),
    IMAGE("image"),
    MONTH("month"),
    NUMBER("number"),
    PASSWORD("password"),
    RADIO("radio"),
    RESET("reset"),
    SEARCH("search"),
    SUBMIT("submit"),
    TEL("tel"),
    TEXT("text"),
    TIME("time"),
    URL("url"),
    WEEK("week");
    public String value;

    InputType(String value){
        this.value = value;
    }
}
