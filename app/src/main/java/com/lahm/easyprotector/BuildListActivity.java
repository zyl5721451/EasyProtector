package com.lahm.easyprotector;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;



public class BuildListActivity extends AppCompatActivity {
    private DeviceInfoListView mDeivceInfoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_list);

        mDeivceInfoView = (DeviceInfoListView) findViewById(R.id.rv_recycle);
        final ArrayList<String> deviceInfo = getBuildInfo();
        Log.d("chao","deviceinfoView:"+mDeivceInfoView);
        NetWorkUtil.getRealIpAdress(new NetWorkUtil.GetRealIpCallback() {
            @Override
            public void onSucess(String ip) {
                deviceInfo.add("realIP:"+ip);
                mDeivceInfoView.setMoreTags(deviceInfo);
            }
        });

//        Log.d("chao","onCreate:"+SharedPref.getXValue("serial"));
//         Log.d("chao","onCreate:"+SharedPref.getXValue("getBaseband"));
        Log.d("chao", "onCreate:" + Process.myPid());
//        isXposedExistByThrow();
//
//
//        try {
//            ClassLoader.getSystemClassLoader().loadClass("de.robv.android.xposed.XposedBridge");
//        } catch (ClassNotFoundException e) {
//            Log.d("chao", "getSystemClassLoader:" + e);
//            e.printStackTrace();
//        }
//
//        try {
//            Class.forName("de.robv.android.xposed.XposedBridge");
//        } catch (ClassNotFoundException e) {
//            Log.d("chao", "froname:" + e);
//            e.printStackTrace();
//        }
//        tryShutdownXposed();

//        boolean hook = TestUtil.isSUExist();
//        Log.d("chao", "isRoot:" + hook);

//          boolean hook = TestUtil.isEmulator(this);
//          Log.d("chao", "isEmulator:" + hook);
//        boolean isHook = CheckHook.isHook(this);
//        boolean isRoot = CheckRoot.isDeviceRooted();
//        boolean isVirtual = CheckVirtual.isRunInVirtual();
//        boolean isEnulator = EmulatorDetector.isEmulator(this);
//        Log.d("chao", "isHook:" + isHook);
//        Log.d("chao", "isRoot:" + isRoot);
//        Log.d("chao", "isVirtual:" + isVirtual);
//        Log.d("chao", "isEnulator:" + isEnulator);
    }


    @NonNull
    private ArrayList<String> getBuildInfo() {

        boolean isHook = CheckHook.isHook(this);
        boolean isRoot = CheckRoot.isDeviceRooted();
        boolean isVirtual = CheckVirtual.isRunInVirtual();
        boolean isEnulator = EmulatorDetector.isEmulator(this);


        ArrayList<String> deviceInfo = new ArrayList<>();
        deviceInfo.add("-----------Build基本信息-------------"); //
        deviceInfo.add("isHook:"+ isHook); //设备显示的版本包 固件版本
        deviceInfo.add("isRoot:"+ isRoot); //设备显示的版本包 固件版本
        deviceInfo.add("isVirtual:"+ isVirtual); //设备显示的版本包 固件版本
        deviceInfo.add("isEnulator:"+ isEnulator); //设备显示的版本包 固件版本

        deviceInfo.add("版本名display:"+ Build.DISPLAY); //设备显示的版本包 固件版本
        deviceInfo.add("指令集cpu_abi:"+ Build.CPU_ABI); //CPU指令集
        deviceInfo.add("指令集cpu_abi2:"+ Build.CPU_ABI2); //CPU指令集
        deviceInfo.add("手机制造商manufacturer:"+ Build.MANUFACTURER); //手机制造商，例如：HUAWEI
        deviceInfo.add("品牌brand:"+ Build.BRAND); //手机品牌，例如：HONOR
        deviceInfo.add("CPU型号hardware:"+ Build.HARDWARE); //CPU型号
        deviceInfo.add("手机型号product:"+ Build.PRODUCT); //手机型号，设置-关于手机-型号
        deviceInfo.add("指纹信息fingerprint:"+ Build.FINGERPRINT); //build的指纹信息
        deviceInfo.add("基带radioversion1:"+ Build.getRadioVersion()); //基带版本
        deviceInfo.add("基带radioversion2:"+ Build.RADIO); //基带版本
        deviceInfo.add("主板board:"+ Build.BOARD); //主版
        deviceInfo.add("设备驱动名称device:"+ Build.DEVICE); //设备驱动名称
        deviceInfo.add("设备版本号id:"+ Build.ID); //设备版本号
        deviceInfo.add("手机型号mddel:"+ Build.MODEL); //手机型号
        deviceInfo.add("设备引导程序booltloader:"+ Build.BOOTLOADER); //主板引导程序
        deviceInfo.add("设备主机地址host:"+ Build.HOST); //设备主机地址
        deviceInfo.add("设备版本标签build_tags:"+ Build.TAGS); //描述标签
        deviceInfo.add("设备版本类型build_type:"+ Build.TYPE); //设备版本类型
        deviceInfo.add("源码控制版本号incremental:"+ Build.VERSION.INCREMENTAL); //源码控制版本号
        deviceInfo.add("Andorid系统版本:"+ Build.VERSION.RELEASE); //
        deviceInfo.add("Android系统api版本:"+ Build.VERSION.SDK_INT);
        deviceInfo.add("固定build时间:"+ Build.TIME);
        deviceInfo.add("AndroidID:"+ Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID)); //设备版本类型



        deviceInfo.add("---------android.os.SystemProperties---------------"); //
        try {
            Class<?> classSysProp = Class
                    .forName("android.os.SystemProperties");
            Method method2 = classSysProp.getDeclaredMethod("get", String.class, String.class);
            Method method1 = classSysProp.getDeclaredMethod("get", String.class);
            Object obj = classSysProp.getConstructor().newInstance();
            Object one = method1.invoke(obj,"gsm.version.baseband"); //基带版本
            Object two = method1.invoke(obj,"gsm.version.baseband"); //基带版本
            Object description = method1.invoke(obj,"ro.build.description"); //基带版本

            deviceInfo.add("基带radioversion3:"+one); //gsm.version.baseband
            deviceInfo.add("基带radioversion4:"+two); //gsm.version.baseband
            deviceInfo.add("描述信息:"+description);
        } catch (Exception e) {
            e.printStackTrace();
        }
        deviceInfo.add("---------TelephoneManager相关---------------"); //
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, "android.permission.READ_PHONE_NUMBERS") != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS,"android.permission.READ_PHONE_NUMBERS", Manifest.permission.READ_PHONE_STATE},1);
        }else {
            deviceInfo.add("IMEI:"+telephonyManager.getDeviceId());
            deviceInfo.add("蓝牙地址:"+ BluetoothAdapter.getDefaultAdapter().getAddress());
            try {
                Class<?> bluetooth = Class.forName("android.bluetooth.BluetoothDevice");
                Field field = bluetooth.getDeclaredField("mAddress");
                Object obj = bluetooth.getConstructor().newInstance();
                field.setAccessible(true);
                deviceInfo.add("蓝牙地址:"+field.get(obj));
            } catch (Exception e) {
                e.printStackTrace();
            }
            WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();

            deviceInfo.add("WiFiMac地址:"+info.getMacAddress());
            deviceInfo.add("WiFi名称:"+info.getSSID());
            deviceInfo.add("接入点的识别地址:"+info.getBSSID());
            deviceInfo.add("电话号码:"+telephonyManager.getLine1Number());
            deviceInfo.add("手机卡序列号:"+telephonyManager.getSimSerialNumber());
            deviceInfo.add("网络运营商类型:"+telephonyManager.getNetworkOperator());
            deviceInfo.add("网络类型名称:"+telephonyManager.getNetworkOperatorName());
            deviceInfo.add("sim卡运营商类型:"+telephonyManager.getSimOperator());
            deviceInfo.add("sim卡运营商名称:"+telephonyManager.getSimOperatorName());
            deviceInfo.add("网络ISO代码:"+telephonyManager.getNetworkCountryIso());
            deviceInfo.add("sim卡ISO代码:"+telephonyManager.getSimCountryIso());

            deviceInfo.add("系统版本:"+telephonyManager.getDeviceSoftwareVersion());
            deviceInfo.add("网络链接类型:"+telephonyManager.getNetworkType());
            deviceInfo.add("手机类型:"+telephonyManager.getPhoneType());
            deviceInfo.add("sim卡状态:"+telephonyManager.getSimState());
            Display display = getWindowManager().getDefaultDisplay();
            deviceInfo.add("手机宽高:" + display.getWidth()+"："+display.getHeight());
            deviceInfo.add("手机内网ip地址:"+NetWorkUtil.getLocalIpAddress(this));
            deviceInfo.add("---------显示相关---------------"); //
            Resources resources=getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            deviceInfo.add("屏幕densityDpi:"+displayMetrics.densityDpi);
            deviceInfo.add("屏幕density:"+displayMetrics.density);
            deviceInfo.add("屏幕xdpi:"+displayMetrics.xdpi);
            deviceInfo.add("屏幕ydpi:"+displayMetrics.ydpi);
            deviceInfo.add("屏幕scalDensity:"+displayMetrics.scaledDensity);



        }













        return deviceInfo;
    }



}
