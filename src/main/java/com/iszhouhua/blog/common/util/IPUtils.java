package com.iszhouhua.blog.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP工具类
 */
@Slf4j
public class IPUtils {
    /**
     * ip2region.db文件的存储位置
     */
    private static final String DB_PATH="data/ip2region.db";

    /**
     * 将resources中的data/ip2region.db文件复制到真实目录
     */
    static {
        File dbFile = new File(DB_PATH) ;
        if(!dbFile.exists()){
            try {
                FileUtils.copyInputStreamToFile(IPUtils.class.getClassLoader().getResourceAsStream("ip2region.db"),dbFile);
            } catch (IOException e) {
                log.error("复制文件失败",e);
            }
        }
    }

    /**
     * 获取当前网络ip
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        //"***.***.***.***".length() = 15
        if(ipAddress!=null && ipAddress.length()>15){
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 根据IP获取城市数据
     * @param ip IP地址
     * @return
     */
    public static String getCity(String ip){
        return getCity(ip,DbSearcher.BTREE_ALGORITHM);
    }

    /**
     * 根据IP获得城市数据
     * @param ip IP地址
     * @param algorithm 查询算法
     * @return
     */
    public static String getCity(String ip,int algorithm){
        if (!Util.isIpAddress(ip)) {
            log.error("无效参数：ip");
            return null;
        }
        File file = new File(DB_PATH);
        if (!file.exists()) {
            log.error("ip2region.db文件不存在");
            return null;
        }
        try {
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, DB_PATH);
            //define the method
            Method method;
            switch ( algorithm )
            {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
                default:
                    log.error("无效参数：algorithm");
                    return null;
            }
            DataBlock dataBlock  = (DataBlock) method.invoke(searcher, ip);
            String[] region = dataBlock.getRegion().split("\\|");
            return region[region.length-2];
        } catch (Exception e) {
            log.error("根据IP获取城市信息失败",e);
        }
        return null;
    }
}