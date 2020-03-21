package model;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义两个属性，bad 存放坏的数据对象 FileJSON，good存放好的数据对象 User
 */
public class ReadResult {

    private List<FileJson> bad = new ArrayList<>();
    private List<User> good = new ArrayList<>();

    public List<FileJson> getBad() {
        return bad;
    }

    public void setBad(List<FileJson> bad) {
        this.bad = bad;
    }

    public List<User> getGood() {
        return good;
    }

    public void setGood(List<User> good) {
        this.good = good;
    }

}
