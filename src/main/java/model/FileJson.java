package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 目的：把错误的数据以及其对应的错误信息 转换为Json 字符串并保存到文件中
 */
public class FileJson implements Serializable {
    /**
     * 存放坏数据
     */
    private Map<String, Object> badData = new HashMap<>();
    /**
     * 坏数据的原因对应的信息
     */
    private List<String> msg;

    public Map<String, Object> getBadData() {
        return badData;
    }

    public void setBadData(Map<String, Object> badData) {
        this.badData = badData;
    }

    public List<String> getMsg() {
        return msg;
    }

    public void setMsg(List<String> msg) {
        this.msg = msg;
    }

}
