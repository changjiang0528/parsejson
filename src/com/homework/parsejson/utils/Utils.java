package com.homework.parsejson.utils;

import java.io.IOException;
import java.io.InputStream;

import com.homework.parsejson.constant.GlobalConstant;

import android.util.Log;

public class Utils {

	public static void LOGD(String msg) {
		if (GlobalConstant.DEBUGMODE)
			Log.d(GlobalConstant.TAG, msg);
	}

	public static String inputStream2String(InputStream is) throws IOException {
		StringBuffer sb = new StringBuffer();
		byte[] buffer = new byte[100];
		int rc = 0;
		while ((rc = is.read(buffer, 0, 100)) > 0) {
			sb.append(new String(buffer, 0, rc));
		}
		return sb.toString();
	}

}
