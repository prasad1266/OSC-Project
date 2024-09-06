package com.osc.sessiondataservice.utility;

public class ConcatUserIdAndDeviceName {
    public static  String concatUserIdAndDeviceName(String userId, String deviceName) {
//        if (userId == null || deviceName == null) {
//            throw new IllegalArgumentException("userId and deviceName cannot be null");
//        }
        return userId + "##" + deviceName;
    }
}
