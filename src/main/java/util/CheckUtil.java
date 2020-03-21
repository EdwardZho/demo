package util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 在pom文件添加一个Common的 lang3依赖就可以使用stringUtils
 */
public class CheckUtil {

    /**
     * 校验对象中的所有字段都不为空
     * Field类代表一个的元数据信息
     *
     * @param o 对象
     * @return 是否都不为空
     */
    public static boolean checkObjectProperty(Object o) throws IllegalAccessException {
        //要获取类的信息，首先要获取类的类型，传递的是哪个子类的对象，class是该子类的类型
        Class<?> clazz = o.getClass();
        //获取的是该类的成员变量的信息
        Field[] declaredFields = clazz.getDeclaredFields();
        //获取所有的public的 成员变量的信息
        Field[] fields = clazz.getFields();

        Set<Field> fieldSet = new HashSet<>();
        fieldSet.addAll(Arrays.asList(declaredFields));
        fieldSet.addAll(Arrays.asList(fields));

        for (Field field : fieldSet) {
            Object value = field.get(o);
            if (value == null) {
                return false;
            }
        }

        return true;
    }

    /**
     * 校验身份证是否有效
     *
     * @param idCart 身份证
     * @return 是否合法
     */
    public static boolean checkIdCart(String idCart) {
        if (StringUtils.isBlank(idCart)) {
            return false;
        }

        // 大陆
        String idCartRegChina = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
        if (idCart.matches(idCartRegChina)) {
            return true;
        }

        // 香港
        String idCartHK = "^((\\s?[A-Za-z])|([A-Za-z]{2}))\\d{6}(([0−9aA])|([0-9aA]))$";
        if (idCart.matches(idCartHK)) {
            return true;
        }

        // 台湾
        String idCartTW = "^[a-zA-Z][0-9]{9}$";
        if (idCart.matches(idCartTW)) {
            return true;
        }

        // 澳门
        String idCartMC = "^[1|5|7][0-9]{6}\\([0-9Aa]\\)";
        if (idCart.matches(idCartMC)) {
            return true;
        }
        //如果上叙条件都不符合，就返回去false
        return false;
    }

    /**
     * 校验字符串格式的出生日期
     *
     * @param birthday 字符串格式的出生日期
     * @return 是否合法
     */
    public static boolean checkBirthdayStr(String birthday) {
        String birthdayStrReg = "^(\\d{4}[-/]\\d{2}[-/]\\d{2})|(\\d{4}年\\d{2}月\\d{2}日)|(\\d{8})$";

        if (StringUtils.isNotBlank(birthday) && birthday.matches(birthdayStrReg)) {
            return true;
        }

        return false;
    }

    /**
     * 校验分数
     *
     * @param score 分数
     * @return 是否合法
     */
    public static boolean checkScore(double score) {
        return score <= 750;
    }

    /**
     * 判断资源是否为空，如果是空，返回错误信息；否则返回null
     *
     * @param resource     资源
     * @param propertyName 资源名称
     * @return 返回错误信息
     */
    public static String isBlank(String resource, String propertyName) {
        if (StringUtils.isBlank(resource)) {
            return propertyName + " is blank";
        }
        return null;
    }

}
