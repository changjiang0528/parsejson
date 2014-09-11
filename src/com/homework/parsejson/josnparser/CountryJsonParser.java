package com.homework.parsejson.josnparser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.homework.parsejson.constant.GlobalConstant;
import com.homework.parsejson.database.Country;
import com.homework.parsejson.utils.Utils;
import android.os.Handler;


/**
 * 
 * @author chang
 * @note this class used to parse json form http url, 
 *       main parse tool is org.json.JSONObject;
 */
public class CountryJsonParser {
	
	private static CountryJsonParser gCountryJsonParser = null;
	private final String KEY_TITLE = "title";
	private final String KEY_ROWS = "rows";
	private final String KEY_DESCRIPTION = "description";
	private final String KEY_IMAGEHREF = "imageHref";
	
	private CountryJsonParser(){
		
	}
	
	public static CountryJsonParser getInstance() {
		if(gCountryJsonParser==null) {
			gCountryJsonParser = new CountryJsonParser();
		}
		return gCountryJsonParser;
	}
	
	public void parse(final String urlString, final Handler handler) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String jsonString = downloadJson(urlString);
				Country country = new Country();
				parseJsonString(jsonString, country);
				handler.sendMessage(handler.obtainMessage(
						GlobalConstant.MSG_JSONDOWNLOADFINISHED, country));
			}
		})
		.start();
		
	}
	
	
	private void parseJsonString(String jsonString, Country country) {
	try {
		JSONObject jsonObj = new JSONObject(jsonString);
		String title = jsonObj.getString(KEY_TITLE);
		country.setTitle(title);
		JSONArray jsonArray = jsonObj.getJSONArray(KEY_ROWS);
    	parseJsonArray(jsonArray, country);
    	
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	private void parseJsonObject(JSONObject jsonObj, Country country) throws JSONException{
		Iterator<?> it = jsonObj.keys();
		String key = null, value = null;
		country.addOneFeatureToLast();
		int nullCount = 0;
		while(it.hasNext()){
			key = it.next().toString();
			value = jsonObj.getString(key);
			Utils.LOGD("getString value = " + value);
			if(value.equals("null")){
				nullCount ++;
				value = null;
			}
            if(key.equals(KEY_TITLE)){
            	country.setFeatureTitleByPos(country.getFeaturesSize()-1, value);
            }else if(key.equals(KEY_DESCRIPTION)){
            	country.setFeatureDescriptionByPos(country.getFeaturesSize()-1, value);
            }else if(key.equals(KEY_IMAGEHREF)){
            	country.setFeatureImageHrefByPos(country.getFeaturesSize()-1, value);
            }
        }
		if(nullCount==GlobalConstant.featureCount){
			country.removeLastFeature();
		}
	}
	
	private void parseJsonArray(JSONArray jsonArray, Country country) throws JSONException{
		for(int i = 0; i < jsonArray.length(); i++){
            JSONObject tJsonObj = jsonArray.getJSONObject(i);
        	
            parseJsonObject(tJsonObj, country);  
        }
	}

	
	
	private String downloadJson(String urlString){
		URL url = null;
		InputStream is = null;
		String jsonString = null;
        try {
        	url = new URL(urlString);
        	is = (InputStream) url.getContent();
        	jsonString = Utils.inputStream2String(is);
        	is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Utils.LOGD("download json over, jsonString = " + jsonString);
        return jsonString;
	}
}
