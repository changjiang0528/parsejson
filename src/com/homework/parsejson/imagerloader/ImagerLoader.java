package com.homework.parsejson.imagerloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.homework.parsejson.constant.GlobalConstant;
import com.homework.parsejson.database.Country;
import com.homework.parsejson.utils.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;

/**
 * 
 * @author chang
 * @note image load tool,some image url can not be opened in here
 */
public class ImagerLoader {

	private static ImagerLoader gImageLoader = null;
	private static LruCache<String, Bitmap> gMemoryCache = null;

	private ImagerLoader() {

	}

	public static ImagerLoader getInstance() {
		/**
		 * Initialize the image cache maxSize = (The application available
		 * memory)/10
		 */
		if (gMemoryCache == null) {
			int maxMemory = (int) Runtime.getRuntime().maxMemory();
			int mCacheSize = maxMemory / 10;
			Utils.LOGD("image CacheSize = " + mCacheSize);
			gMemoryCache = new LruCache<String, Bitmap>(mCacheSize) {
				@Override
				protected int sizeOf(String key, Bitmap value) {

					int byteCount = value.getByteCount();
					Utils.LOGD("sizeOf bitmap byteCount = " + byteCount);
					return byteCount;
				}
			};
		}

		if (gImageLoader == null) {
			gImageLoader = new ImagerLoader();
		}
		return gImageLoader;
	}

	public void loadHttpImage(final String imgUrl,
			final ImagerLoadListener imagerLoadListener) {
		LoadHttpImageAsyncTask loadImageTask = new LoadHttpImageAsyncTask(
				imagerLoadListener);
		loadImageTask.execute(imgUrl);
	}

	public void clearCache() {
		if (gMemoryCache != null) {
			gMemoryCache.evictAll();
		}
	}

	class LoadHttpImageAsyncTask extends AsyncTask<String, Integer, Bitmap> {
		private ImagerLoadListener mImagerLoadListener;
		private String mImgUrl;

		public LoadHttpImageAsyncTask(ImagerLoadListener imagerLoadListener) {
			mImagerLoadListener = imagerLoadListener;
		}

		@Override
		protected Bitmap doInBackground(String... arg0) {

			mImgUrl = arg0[0];
			Bitmap bitmap = gMemoryCache.get(mImgUrl);

			if (bitmap == null) {
				Utils.LOGD("get bitmap from cache, but bitmap = null");
				bitmap = downloadImage(mImgUrl);
				if (bitmap != null) {
					gMemoryCache.put(mImgUrl, bitmap);
					Utils.LOGD("put bitmap to cache");
				}
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if(result==null){
				mImagerLoadListener.loadImageError("", mImgUrl);
			}else{
				mImagerLoadListener.loadImageFinished(result, mImgUrl);
			}
			super.onPostExecute(result);
		}

		private Bitmap downloadImage(String imgUrl) {
			URL url;
			InputStream is = null;
			Bitmap bitmap = null;
			try {
				url = new URL(imgUrl);
				is = (InputStream) url.getContent();
				bitmap = BitmapFactory.decodeStream(is);
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bitmap;
		}
	}
}
