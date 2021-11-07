package cn.enncy.mybatis.entity;


/**
 * //TODO
 * <br/>Created in 18:52 2021/11/7
 *
 * @author enncy
 */
public class SQL {

    String value;
    boolean executeQuery;

    public SQL(String value, boolean executeQuery) {
        this.value = value;
        this.executeQuery = executeQuery;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isExecuteQuery() {
        return executeQuery;
    }

    public void setExecuteQuery(boolean executeQuery) {
        this.executeQuery = executeQuery;
    }

    @Override
    public String toString() {
        return "SQL{" +
                "value='" + value + '\'' +
                ", executeQuery=" + executeQuery +
                '}';
    }
}
