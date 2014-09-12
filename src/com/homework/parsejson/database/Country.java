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

	public String mTitle;
	public List<Feature> mFeatures;

	public Country() {
		mFeatures = new ArrayList<Feature>();
		mTitle = null;
		mFeatures.clear();
	}

	public void addOneFeatureToLast(Feature feature) {
		mFeatures.add(feature);
	}

	public void removeLastFeature() {
		mFeatures.remove(mFeatures.size() - 1);
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
		if (mFeatures != null) {
			if(mFeatures.size() > 0){
				mFeatures.clear();
			}
			mFeatures = null;
		}
	}

}
