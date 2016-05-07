package com.leaf.base.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 读取指定路径的密文文件
 * @author zhangsf 创建 2012-3-15
 * @version 1.0 Copyright(c) 北京神州数码思特奇信息技术股份有限公司
 */
public class ReadEcrypt {

    private static Map<String, String> ecryptMap = null;

    private final Log log = LogFactory.getLog(this.getClass());

    private ReadEcrypt() {
        File absoluteFile = getAbsolutePath();
        ecryptMap = readFileByLines(absoluteFile);
    }

    private static ReadEcrypt ecrypt = new ReadEcrypt();

    public static ReadEcrypt getInstance() {
        return ecrypt;
    }

    public Map<String, String> getEcryptMap() {
        return ecryptMap;
    }

    /**
     * 从文件中读取密文
     * @param file 抽象文件
     * @return map 密文文件键值对
     */
    public Map<String, String> readFileByLines(File file) {

        BufferedReader reader = null;
        Map<String, String> ecryptMap = new ConcurrentHashMap<String, String>();
        try {
            log.info("读取数据库用户名,密码  ：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                // log.info("line " + line + ": " + tempString);
                tempString = tempString.replaceAll("\\s*", "");
                String value = tempString.substring(tempString.indexOf("=") + 1);
                if (tempString.startsWith(EcryptConst.DB_USER)) {
                    ecryptMap.put(EcryptConst.DB_USER, value);
                } else if (tempString.startsWith(EcryptConst.DB_PASSWD)) {
                    ecryptMap.put(EcryptConst.DB_PASSWD, value);
                } else if (tempString.startsWith(EcryptConst.FTP_USER)) {
                    ecryptMap.put(EcryptConst.FTP_USER, value);
                } else if (tempString.startsWith(EcryptConst.FTP_PASSWD)) {
                    ecryptMap.put(EcryptConst.FTP_PASSWD, value);
                } else {
                    log.info("未找到任何用户名和密码");
                }
            }
            reader.close();
        } catch (IOException e) {
            log.error("读取数据库加密文件错误！", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return ecryptMap;
    }

    /**
     * 获取数据配置文件
     **/
    public File getAbsolutePath() {
        ResourceBundle resource = ResourceBundle.getBundle("ijdbc");
        String ecryptPath = resource.getString(EcryptConst.ECYPT_FILE_PATH);
        ecryptPath = StringUtil.formatFilePath(ecryptPath);
        File ecryptFile = new File(ecryptPath);
        return ecryptFile;
    }

    public static void main(String[] args) {
        ReadEcrypt re = new ReadEcrypt();
        re.getInstance().getEcryptMap();
    }
}
