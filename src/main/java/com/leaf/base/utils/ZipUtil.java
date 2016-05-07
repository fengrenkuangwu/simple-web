package com.leaf.base.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 上午11:42
 * To change this template use File | Settings | File Templates.
 */
public class ZipUtil {

    private  static  final String STRING_ENCODING = "ISO-8859-1";
    /**
     * 字符串压缩
     *
     * @param str  需要压缩的字符串
     * @return  ISO-8859-1编码压缩的字符串
     * @throws java.io.IOException 操作异常
     */
    public static String compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        return out.toString(STRING_ENCODING);
    }

    /**
     * @param str  ISO-8859-1编码压缩的字符串
     * @return  解压后的处理结果
     * @throws java.io.IOException  压缩异常
     */
    public static String uncompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes(STRING_ENCODING));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return out.toString();
    }
}
