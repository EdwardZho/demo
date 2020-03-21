package demo;

import model.FileJson;
import model.ReadResult;
import model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.*;

public class App {

    public static void main(String[] args) throws Exception {

        // 3s后执行定时器，每30秒执行一次
        long milliseconds = 3 * 1000;
        // 定时器
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    String excelFilePath = "C:/Users/64385/Desktop/excel/excel.xlsx";
                    // 读取 Excel
                    List<Map<String, Object>> resultList = readExcel(excelFilePath);
                    // 将 excel 中读取到的内容转化为 ReadResult 对象
                    ReadResult readResult = getReadResult(resultList);

                    if (readResult == null) {
                        return;
                    }

                    // 将坏的数据写入文件
                    List<FileJson> bad = readResult.getBad();
                    if (bad != null && !bad.isEmpty()) {
                        String path = "d:/test.txt";
                        PrintWriter writer = FileUtil.getWriter(path);
                        for (FileJson fileJson : bad) {
                            FileUtil.writeToFile(writer, fileJson);
                        }
                        FileUtil.close(writer);
                    }

                    // 将好的数据写入数据库
                    List<User> good = readResult.getGood();
                    if (good != null && !good.isEmpty()) {
                        Connection connection = JDBCUtil.getConnection();
                        for (User user : good) {
                            JDBCUtil.save(connection, user);
                        }
                        JDBCUtil.close(connection, null);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, milliseconds, 30 * 1000);





        /*
        //日期格式工具
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //12秒后执行定时器，每1s执行一次
        System.out.print(sdf.format(new Date()));
        System.out.println("the timer two will be executed after 12 seconds...");
        //启动后延迟时间
        long afterSs = 12 * 1000;
        //执行周期
        long intervalSs1 = 1 * 1000;
        timer.schedule(new TimerTask() {
            // 执行计数器
            int i = 0;

            @Override
            public void run() {
                System.out.print(sdf.format(new Date()));
                System.out.println("the timer two has execution " + (++i) + " timers");
                // 执行10次后关闭定时器
                if (i == 10) {
                    this.cancel();
                }
            }
        }, afterSs, intervalSs1);


        // 指定时间执行定时器，仅执行一次
        System.out.print(sdf.format(new Date()));
        Date date = sdf.parse("2017-06-27 21:47:00");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.print(sdf.format(new Date()));
                System.out.println("the timer three has finished execution");
            }
        }, date);

        // 从指定时间开始周期性执行
        System.out.print(sdf.format(new Date()));
        // 执行间隔周期
        long intervalSs = 1 * 1000;
        // 开始执行时间
        Date beginTime = sdf.parse("2017-06-27 21:48:00");
        timer.schedule(new TimerTask() {
            // 执行计数器
            int i = 0;

            @Override
            public void run() {
                System.out.print(sdf.format(new Date()));
                // 执行10次后关闭定时器
                if (i == 10) {
                    this.cancel();
                }
            }
        }, beginTime, intervalSs);*/
    }

    /**
     * 读取excel，返回 Excel 中的每一行数据
     *
     * @param filePath excel 文件的路径
     * @return excel 的内容
     * @throws Exception exception
     */
    public static List<Map<String, Object>> readExcel(String filePath) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();

        File file = new File(filePath);
        if (!file.isFile() || !file.exists()) {
            return null;
        }

        String[] split = file.getName().split("\\.");
        Workbook wb;
        if ("xls".equals(split[1])) {
            FileInputStream inputStream = new FileInputStream(file);
            wb = new HSSFWorkbook(inputStream);
        } else if ("xlsx".equals(split[1])) {
            wb = new XSSFWorkbook(file);
        } else {
            System.out.println("文件类型错误");
            return null;
        }

        //开始解析
        Sheet sheet = wb.getSheetAt(0);
        //第一行是列名，所以从第二行开始遍历
        int firstRowNum = sheet.getFirstRowNum() + 1;
        int lastRowNum = sheet.getLastRowNum();
        //遍历行
        for (int rIndex = firstRowNum; rIndex <= lastRowNum; rIndex++) {
            //获取当前行的内容
            Row row = sheet.getRow(rIndex);
            if (row == null) {
                continue;
            }

            Map<String, Object> map = new HashMap<>();
            //获取单元格数据
            Cell idCardCell = row.getCell(0);
            Cell stuNoCell = row.getCell(1);
            Cell birthdayCell = row.getCell(2);
            Cell provinceCell = row.getCell(3);
            Cell genderCell = row.getCell(4);
            Cell scoreCell = row.getCell(5);
            //因为可能存在空格
            String idCard = idCardCell == null ? "" : idCardCell.getStringCellValue();
            String stuNo = stuNoCell == null ? "" : stuNoCell.getStringCellValue();
            String birthday = birthdayCell == null ? "" : birthdayCell.getStringCellValue();
            String province = provinceCell == null ? "" : provinceCell.getStringCellValue();
            String gender = genderCell == null ? "" : genderCell.getStringCellValue();
            String score = scoreCell == null ? "" : scoreCell.getStringCellValue();

            System.out.println(idCard + " --- " + stuNo + " --- " + birthday + " --- " + province + " --- " + gender + " --- " + score);

            map.put("idCard", idCard);
            map.put("stuNo", stuNo);
            map.put("birthday", birthday);
            map.put("province", province);
            map.put("gender", gender);
            map.put("score", score);

            result.add(map);
        }

        return result;
    }

    //获取list集合中的数据
    public static ReadResult getReadResult(List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        ReadResult readResult = new ReadResult();
        List<FileJson> bad = readResult.getBad();
        List<User> good = readResult.getGood();

        for (Map<String, Object> map : list) {
            FileJson fileJson = new FileJson();
            //在里面存放一些：属性名+“is blank”
            List<String> errorMsg = new ArrayList<>();

            // 以idCart作为key获取map中的value值
            String idCard = (String) map.get("idCard");
            // 校验身份证的正确性
            boolean idCardRight = IdCardUtil.validateCard(idCard);
            if (!idCardRight) {
                errorMsg.add("IdCard Is Illegal");
            }

            String birthday = (String) map.get("birthday");
            if (StringUtils.isBlank(birthday) && idCardRight) {
                // 如果生日为空并且身份证正确，则根据身份证计算出生日
                birthday = IdCardUtil.getBirthByIdCard(idCard);
            }

            String score = (String) map.get("score");
            String stuNo = (String) map.get("stuNo");

            String province = (String) map.get("province");
            if (StringUtils.isBlank(province) && idCardRight) {
                // 如果省份为空并且身份证正确，则根据身份证计算出省份
                province = IdCardUtil.getProvinceByIdCard(idCard);
            }

            String gender = (String) map.get("gender");

            //判断是否存在空值，如果为空就返回“idCart + is blank”;不为空就返回null     要求：其它字段不能为空
            String birthdayErrorMsg = CheckUtil.isBlank(birthday, "birthday");
            String scoreErrorMsg = CheckUtil.isBlank(score, "score");
            String stuNoErrorMsg = CheckUtil.isBlank(stuNo, "stuNo");
            String provinceErrorMsg = CheckUtil.isBlank(province, "province");
            String genderErrorMsg = CheckUtil.isBlank(gender, "gender");

            // 校验
            if (birthdayErrorMsg != null) {
                errorMsg.add(birthdayErrorMsg);
            } else if (!CheckUtil.checkBirthdayStr(birthday)) {
                errorMsg.add("Birthday Is Illegal");
            }
            if (scoreErrorMsg != null) {
                errorMsg.add(scoreErrorMsg);
                //isDigits:判断字符串中是否全为数字或者把Double.parseDouble数字类型的字符串转换成double类型
            } else if (!NumberUtils.isDigits(score) || !CheckUtil.checkScore(Double.parseDouble(score))) {
                errorMsg.add("Score Is Illegal");
            }
            if (stuNoErrorMsg != null) {
                errorMsg.add(stuNoErrorMsg);
            }
            if (provinceErrorMsg != null) {
                errorMsg.add(provinceErrorMsg);
            }
            if (genderErrorMsg != null) {
                errorMsg.add(genderErrorMsg);
            }

            //在表格中可能有些数据是空
            if (!errorMsg.isEmpty()) {
                fileJson.setBadData(map);
                fileJson.setMsg(errorMsg);
                bad.add(fileJson);
                continue;
            }

            Date birthdayDate = DateUtil.format(birthday);

            User user = new User();
            user.setIdCard(idCard);
            user.setStuNo(stuNo);
            user.setBirthday(birthdayDate);
            user.setProvince(province);
            user.setGender(gender);
            user.setScore(Double.parseDouble(score));
            // 计算生肖
            user.setZodiac(DateUtil.getZodiac(birthdayDate));
            // 计算星座
            user.setConstellation(DateUtil.getConstellation(birthdayDate));

            good.add(user);
        }

        return readResult;
    }

}
