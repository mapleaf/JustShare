package com.logan.util;

import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AsyncImageLoader {
	private final String TAG = "AsyncImageLoader";
	private HashMap<String, SoftReference<Drawable>> imageCache = null;

	public AsyncImageLoader() {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	public Drawable loadDrawable(final String imageUrl,
			final ImageCallback callback) {
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				return drawable;
			}
		}
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				callback.imageLoaded((Drawable) msg.obj, imageUrl);
			}
		};
		new Thread() {
			@Override
			public void run() {
				Drawable drawable = loadImageFromUrl(imageUrl);
				if(drawable!=null)
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			};
		}.start();

		return null;
	}

	protected Drawable loadImageFromUrl(String imageUrl) {
		try {
			return Drawable.createFromStream(new URL(imageUrl).openStream(),
					"src");
		} catch (Exception e) {
			Log.v(TAG, "loadImageFromUrl Error,imageUrl is " + imageUrl);
			// throw new RuntimeException(e);
			return null;
		}

	}

	// call back interface
	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}

}
