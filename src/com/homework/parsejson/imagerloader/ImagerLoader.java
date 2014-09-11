package com.homework.parsejson.imagerloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;








import com.homework.parsejson.constant.GlobalConstant;
import com.homework.parsejson.utils.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;

/**
 * 
 * @author chang
 * @note image load tool,some image url can not be opened in here
 */
public class ImagerLoader{
	
	private static ImagerLoader gImageLoader = null;
	private static LruCache<String, Bitmap> gMemoryCache = null;  
	private ImagerLoader(){
		
	}
	
	public static ImagerLoader getInstance() {
		/**
		 * Initialize the image cache
		 * maxSize = (The application available memory)/10
		 */
		if(gMemoryCache==null) {
			int maxMemory = (int) Runtime.getRuntime().maxMemory();    
	        int mCacheSize = maxMemory / 10;  
	        Utils.LOGD("image CacheSize = " + mCacheSize);
	        gMemoryCache = new LruCache<String, Bitmap>(mCacheSize){
				@Override
				protected int sizeOf(String key, Bitmap value) {
					// TODO Auto-generated method stub
					int byteCount = value.getByteCount();
					Utils.LOGD("sizeOf bitmap byteCount = " + byteCount);
					return byteCount;
				}
			};
		}
		
		if(gImageLoader==null) {
			gImageLoader = new ImagerLoader();
		}
		return gImageLoader;
	}
	
	public void loadHttpImage(final String imgUrl, final ImagerLoadListener imagerLoadListener){
	
		final Handler handler = new Handler() {  
	        public void handleMessage(Message message) {  
	        	imagerLoadListener.loadImageFinished((Bitmap) message.obj, imgUrl);  
	        }
	    };
        
        new Thread() {  
            @Override  
            public void run() {
            	Bitmap bitmap = gMemoryCache.get(imgUrl);
            	
            	if(bitmap==null) {
            		Utils.LOGD("get bitmap from cache, but bitmap = null");
            		bitmap = downloadImage(imgUrl, imagerLoadListener);
            		if(bitmap!=null) {
            			gMemoryCache.put(imgUrl, bitmap);
            			Utils.LOGD("put bitmap to cache");
            		}
            	}
            	
            	if(bitmap!=null) {
            		Message message = handler.obtainMessage(0, bitmap);
            		handler.sendMessage(message);  
            	}
            }  
        }.start(); 

	}
	
	public void clearCache(){
		if(gMemoryCache!=null) {
			gMemoryCache.evictAll();
		}
	}
	private Bitmap downloadImage(String imgUrl, ImagerLoadListener imagerLoadListener) {
		URL url;  
        InputStream is = null;
        Bitmap bitmap = null;
        try {
        	url = new URL(imgUrl);  
        	is = (InputStream) url.getContent();
        	bitmap = BitmapFactory.decodeStream(is);
        	is.close();
        } catch (MalformedURLException e) {
        	imagerLoadListener.loadImageError(e.toString(), imgUrl);
            e.printStackTrace();  
        } catch (IOException e) {
        	imagerLoadListener.loadImageError(e.toString(), imgUrl);
            e.printStackTrace();
        }
        return bitmap;
	}
	
	
}
