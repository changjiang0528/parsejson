package com.homework.parsejson.database;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author chang
 * @note:a Country(json Object actually), Used to save and operate json object
 *               classes
 */
public class Country {

	private String mTitle;
	private List<Feature> mFeatures;

	public Country() {
		mFeatures = new ArrayList<Feature>();
		mTitle = null;
		mFeatures.clear();
	}

	public void setTitle(String title) {
		if (title == null)
			title = "";
		mTitle = title;
	}

	public String getTitle() {
		return mTitle;
	}

	public void addOneFeatureToLast(Feature feature) {
		mFeatures.add(feature);
	}

	public void removeLastFeature() {
		mFeatures.remove(mFeatures.size() - 1);
	}

	public String getFeatureTitleByPos(int pos) {
		return mFeatures.get(pos).getTitle();
	}

	public String getFeatureDescriptionByPos(int pos) {
		return mFeatures.get(pos).getDescription();
	}

	public String getFeatureImageHrefByPos(int pos) {
		return mFeatures.get(pos).getImageHref();
	}

	public int getFeaturesSize() {
		return mFeatures.size();
	}

	public Feature getFeature(int pos) {
		return mFeatures.get(pos);
	}

	public void clear() {
		if (mTitle != null)
			mTitle = null;
		if (mFeatures != null && mFeatures.size() > 0) {
			mFeatures.clear();
			mFeatures = null;
		}
	}

}
