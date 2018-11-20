package fund.service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

public class Util {

    static SimpleDateFormat formatYMD = new SimpleDateFormat("yyyy-MM-dd");

    public static String toYMD() {
        return toYMD(new Date());
    }

    public static String toYMD(Date date) {
        return formatYMD.format(date);
    }

    public static Date parseYMD(String s) throws ParseException {
        return formatYMD.parse(s);
    }

    public static boolean hasIP(String ip) {
        try {
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements()) {
                NetworkInterface n = e.nextElement();
                Enumeration<InetAddress> ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = ee.nextElement();
                    if (i.getHostAddress().equals(ip)) return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }
}
