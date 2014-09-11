package com.homework.parsejson.database;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author chang
 * @note:a Country(json Object actually), Used to save and operate json object classes
 */
public class Country {
	
	private String mTitle;
	private List<Feature> mFeatures;
	
	public Country() {
		mFeatures = new ArrayList<Feature>();
		mTitle = null;
		mFeatures.clear();
	}
	
	
	public void setTitle(String title){
		if(title==null)
			title = "";
		mTitle = title;
	}
	
	public String getTitle(){
		return mTitle;
	}
	
	
	public void addOneFeatureToLast(){
		mFeatures.add(new Feature());
	}
	
	public void removeLastFeature() {
		mFeatures.remove(mFeatures.size()-1);
	}
	public void setFeatureTitleByPos(int pos, String title){
		mFeatures.get(pos).mTitle = title;
	}
	
	public void setFeatureDescriptionByPos(int pos, String description){
		mFeatures.get(pos).mDescription = description;
	}
	
	public void setFeatureImageHrefByPos(int pos, String imageHref){
		mFeatures.get(pos).mImageHref = imageHref;
	}
	
	public String getFeatureTitleByPos(int pos){
		return mFeatures.get(pos).mTitle;
	}
	
	public String getFeatureDescriptionByPos(int pos){
		return mFeatures.get(pos).mDescription;
	}
	
	public String getFeatureImageHrefByPos(int pos){
		return mFeatures.get(pos).mImageHref;
	}
	
	public int getFeaturesSize(){
		return mFeatures.size();
	}
	
	public Feature getFeature(int pos){
		return mFeatures.get(pos);
	}
	
	public void clear(){
		if(mTitle!=null)
			mTitle = null;
		if(mFeatures!=null&&mFeatures.size()>0){
			mFeatures.clear();
			mFeatures = null;
		}
	}
	
	/**
	 * 
	 * @author chang
	 * @note:a country feature
	 */
	public class Feature {
		public String mTitle;
		public String mDescription;
		public String mImageHref;
		
		public Feature(){
			mTitle = null;
			mDescription = null;
			mImageHref = null;
		}
	}
}
