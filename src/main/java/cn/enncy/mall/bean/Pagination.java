package cn.enncy.mall.bean;


/**
 * //TODO
 * <br/>Created in 17:23 2021/11/21
 *
 * @author enncy
 */
public class Pagination {

    private int index;
    private int size;
    private int count;
    private int pre;
    private int next;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPre() {
        return pre;
    }

    public void setPre(int pre) {
        this.pre = pre;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }


    @Override
    public String toString() {
        return "Pagination{" +
                "index=" + index +
                ", size=" + size +
                ", count=" + count +
                ", pre=" + pre +
                ", next=" + next +
                '}';
    }
}
