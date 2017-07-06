package com.xiaoyezi.midicore.teststorage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.ComponentName;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Debug.MemoryInfo;
import android.os.Environment;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.StatFs;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.app.KeyguardManager;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class AppUtils {
    public static final long DEFAULT_VIBRATE_TIME = 800;
    private static final String TAG = "AppUtils";

    public static int getAvailableSDCardBlocks() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long availCount = sf.getAvailableBlocks();
            return (int)(availCount * blockSize / 1024);
        }
        else {
            return 0;
        }
    }

    public static int getTotalSDCardBlocks() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long availCount = sf.getBlockCount();
            return (int)(availCount * blockSize / 1024);
        }
        else {
            return 0;
        }
    }

    public static int getAvailableSystemBlocks() {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        long availCount = sf.getAvailableBlocks();
        return (int)(availCount * blockSize / 1024);
    }

    public static int getTotalSystemBlocks() {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        long totalCount = sf.getBlockCount();
        return (int)(totalCount * blockSize / 1024);
    }

    /**
     * 获取操作系统的版本号
     *
     * @return
     */
    public static String getAndroidVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取当前手机使用的语言
     *
     * @return
     */
    public static String getDeviceLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * Calculates the free memory of the device. This is based on an inspection
     * of the filesystem, which in android devices is stored in RAM.
     *
     * @return Number of bytes available.
     */
    public static double getAvailableInternalMemorySize() {
        final File path = Environment.getDataDirectory();
        final StatFs stat = new StatFs(path.getPath());
        final long blockSize = stat.getBlockSize();
        final long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * Calculates the total memory of the device. This is based on an inspection
     * of the filesystem, which in android devices is stored in RAM.
     *
     * @return Total number of bytes.
     */
    public static long getTotalInternalMemorySize() {
        final File path = Environment.getDataDirectory();
        final StatFs stat = new StatFs(path.getPath());
        final long blockSize = stat.getBlockSize();
        final long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    private static WakeLock sWakeLock;

    /**
     * 需要权限<uses-permission
     * android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     * <uses-permission
     * android:name="android.permission.INTERNET"></uses-permission> 获取本机IP地址
     *
     * @return
     */
    @Deprecated
    public static String getLocalIpAddress() {
        try {
            for (final Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                final NetworkInterface intf = en.nextElement();
                for (final Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    final InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (final SocketException ex) {
            Log.e("ifo", ex.toString());
        }
        return "";
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }

    public static String getMacAddressByInterface(final String iface) {
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/" + iface + "/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            String line;
            while ((line = input.readLine()) != null) {
                macSerial += line.trim();
            }

            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return macSerial;
    }

}
