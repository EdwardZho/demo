package model;

import java.io.Serializable;
import java.util.Date;

/**
 * Serializable是一个空接口，没有什么具体的内容，用于表示一个类的对象可以被序列化
 * 1、需要把内存中的对象写入到硬盘中的时候；
 * 2、想用套接字在网络上传送对象的时候；
 * 3、当你想通过RMI传输对象的时候。
 */
public class User implements Serializable {

    private Long id;
    private String idCard;
    private String stuNo;
    private Date birthday;
    private String province;
    private String gender;
    private Double score;
    private String zodiac;
    private String constellation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

}
