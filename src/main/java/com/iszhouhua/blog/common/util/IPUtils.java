package com.iszhouhua.blog.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP工具类
 * https://gitee.com/lionsoul/ip2region/tree/master/binding/java
 */
@Slf4j
public class IPUtils {
    /**
     * ip2region.db文件的存储位置
     */
    private static final String DB_PATH = "data/ip2region.xdb";

    private static byte[] vIndex;

    /**
     * 将resources中的data/ip2region.db文件复制到真实目录
     */
    static {
        try {
            File dbFile = new File(DB_PATH);
            if (!dbFile.exists()) {
                FileUtils.copyInputStreamToFile(new ClassPathResource("ip2region.xdb").getInputStream(), dbFile);
            }
            // 从 dbPath 中预先加载 VectorIndex 缓存，并且把这个得到的数据作为全局变量，后续反复使用。
            vIndex = Searcher.loadVectorIndexFromFile(DB_PATH);
        } catch (IOException e) {
            log.error("加载ip2region.xdb失败", e);
        }
    }

    /**
     * 获取当前网络ip
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        //"***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 获取IP归属地信息
     *
     * @param ip IP地址
     * @return
     */
    public static String getRegion(String ip) {
        try {
            // 使用全局的 vIndex 创建带 VectorIndex 缓存的查询对象。
            Searcher searcher = Searcher.newWithVectorIndex(DB_PATH, vIndex);
            // 查询
           return searcher.searchByStr(ip);
        } catch (Exception e) {
            log.error("getCity is error,ip:{}",ip,e);
        }
        return null;
    }
}