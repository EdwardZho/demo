package util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 校验日期格式
 */
public class DateUtil {

    public static final int[] dayArr = new int[]{20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
    public static final String[] constellationArr = new String[]{"摩羯座", "水瓶座", "双鱼座", "白羊座",
            "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};
    public static final String[] years = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊",
            "猴", "鸡", "狗", "猪"};

    public static Date format(String source) {
        // 如果参数为空，返回null
        if (StringUtils.isBlank(source)) {
            return null;
        }

        // 4个数字 - 2个数字 - 2个数字
        String reg1 = "^\\d{4}-\\d{2}-\\d{2}$";
        if (source.matches(reg1)) {
            return formatByPattern(source, "yyyy-MM-dd");
        }

        // 4个数字 / 2个数字 / 2个数字
        String reg2 = "^\\d{4}/\\d{2}/\\d{2}$";
        if (source.matches(reg2)) {
            return formatByPattern(source, "yyyy/MM/dd");
        }

        // 4个数字 年 2个数字 月 2个数字 日
        String reg3 = "^\\d{4}年\\d{2}月\\d{2}日$";
        if (source.matches(reg3)) {
            return formatByPattern(source, "yyyy年MM月dd日");
        }

        // 8个连续的数字
        String reg4 = "^\\d{8}$";
        if (source.matches(reg4)) {
            return formatByPattern(source, "yyyyMMdd");
        }

        // 如果匹配不正确，返回null
        return null;
    }

    private static Date formatByPattern(String source, String pattern) {
        //source转换为pattern
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            //这个方法是按照特定的格式把字符串解析为日期对象
            return sdf.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据生日，计算生肖
     *
     * @param birthday 生日
     * @return 生肖
     */
    public static String getZodiac(Date birthday) {
        if (birthday == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String format = sdf.format(birthday);
        int year = Integer.parseInt(format);

        if (year < 1900) {
            return "未知";
        }

        String zodiac = years[(year - 1900) % years.length];

        return zodiac;
    }

    /**
     * 根据生日，计算星座
     *
     * @param birthday 生日
     * @return 星座
     */
    public static String getConstellation(Date birthday) {
        if (birthday == null) {
            return null;
        }

        // 获取月份和日期 如：12-28
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        String format = sdf.format(birthday);

        String[] split = format.split("-");
        int month = Integer.parseInt(split[0]);
        int day = Integer.parseInt(split[1]);

        String constellation = day > dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];

        return constellation;
    }

}
