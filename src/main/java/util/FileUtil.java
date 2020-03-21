package util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

public class FileUtil {

    public static void writeToFile(PrintWriter writer, Object obj) {
        if (writer == null || obj == null) {
            return;
        }

        // 将传入的对象转成 JSON 格式字符串
        String jsonString = JSON.toJSONString(obj);
        // 把字符串写入流中,输出到文件
        writer.println(jsonString);
        writer.flush();
    }

    /**
     * 根据文件路径，创建文件输出流，如果路径错误，返回null
     *
     * @param filePath 文件路径
     * @return 文件输出流
     */
    public static PrintWriter getWriter(String filePath) throws IOException {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }

        File file = new File(filePath);
        // 如果文件不存在
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            boolean mkdirs = parentFile.mkdirs();
            boolean newFile = file.createNewFile();
        } else if (file.isDirectory()) {
            boolean newFile = file.createNewFile();
        }

        // 创建文件打印流， 准备向文件写入内容
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, Boolean.TRUE)));

        return printWriter;
    }

    /**
     * 关闭输出流，释放资源
     *
     * @param writer 输出流
     */
    public static void close(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
