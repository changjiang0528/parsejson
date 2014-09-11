package com.homework.parsejson.view;

import com.homework.parsejson.R;
import com.homework.parsejson.database.Country;
import com.homework.parsejson.imagerloader.ImagerLoadListener;
import com.homework.parsejson.imagerloader.ImagerLoader;
import com.homework.parsejson.utils.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author chang
 * @note Adapter used to Adapt listview items
 */

public class ListViewAdapter extends BaseAdapter {

	private Context mContext;
	private Country mCountry;
	private LayoutInflater mInflater;
	private ListView mListView;

	public ListViewAdapter(Context context, Country country, ListView listView) {

		mContext = context;
		mCountry = country;
		mInflater = LayoutInflater.from(mContext);
		mListView = listView;
	}

	@Override
	public Object getItem(int arg0) {
		return mCountry.getFeature(arg0);
	}

	@Override
	public int getCount() {
		return mCountry.getFeaturesSize();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Utils.LOGD("getView position = " + position);
		View view = null;
		ViewHolder viewHolder = null;
		String text = null;
		if (convertView != null) {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		} else {
			view = mInflater.inflate(R.layout.listview_item, null);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) view.findViewById(R.id.item_title);
			viewHolder.imageView = (ImageView) view
					.findViewById(R.id.item_image);
			viewHolder.desc = (TextView) view.findViewById(R.id.item_desc);
			view.setTag(viewHolder);
		}

		/**
		 * if Text is null, Not Display TextView
		 */
		text = mCountry.getFeatureTitleByPos(position);
		if (text == null) {
			viewHolder.title.setVisibility(View.GONE);
		} else {
			viewHolder.title.setVisibility(View.VISIBLE);
			viewHolder.title.setText(text);
		}

		text = mCountry.getFeatureDescriptionByPos(position);
		if (text == null) {
			viewHolder.desc.setVisibility(View.GONE);
		} else {
			viewHolder.desc.setVisibility(View.VISIBLE);
			viewHolder.desc.setText(text);
		}

		/**
		 * for convertView's reusing, set ImageBitmap = null
		 */
		viewHolder.imageView.setImageBitmap(null);

		/**
		 * load image
		 */
		String imageUrl = mCountry.getFeatureImageHrefByPos(position);
		Utils.LOGD("loadImage url = " + imageUrl);

		if (imageUrl != null) {
			viewHolder.imageView.setTag(imageUrl);
			ImagerLoader.getInstance().loadHttpImage(imageUrl,
					new ImagerLoadListener() {

						@Override
						public void loadImageFinished(Bitmap bitmap,
								String imageUrl) {
							ImageView imageView = (ImageView) mListView
									.findViewWithTag(imageUrl);
							if (imageView != null) {
								imageView.setImageBitmap(bitmap);
							}

						}

						@Override
						public void loadImageError(String errorInfo,
								String imageUrl) {
							Utils.LOGD("loadImageError url = " + imageUrl);
						}
					});
		}
		return view;
	}

	public class ViewHolder {
		private ImageView imageView = null;
		private TextView title = null;
		private TextView desc = null;
	}

}
