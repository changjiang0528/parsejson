package com.homework.parsejson.imagerloader;

import android.graphics.Bitmap;

/**
 * 
 * @author chang
 * @note this listener used to call back when download finish or error
 */
public interface ImagerLoadListener {
	public void loadImageFinished(Bitmap bitmap, String imageUrl);
	public void loadImageError(String errorInfo, String imageUrl);
}
