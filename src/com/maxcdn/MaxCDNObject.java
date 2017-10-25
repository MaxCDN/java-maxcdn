package com.maxcdn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MaxCDNObject extends JSONObject {
	public int code;
	public boolean error = false;
	
	public MaxCDNObject() throws JSONException{
		super();
		//this.put(key, data);
		//code = this.getInt("code");
	}
	public MaxCDNObject(String json) throws JSONException{
		super(new JSONObject(json).has("data") ? ((JSONObject) new JSONObject(json).get("data")).toString() : json );
		code = new JSONObject(json).getInt("code");	
		if(this.has("error")) error = true;
	}
	
	public String getErrorMessage() throws JSONException {
		return error ? this.getJson("error").getString("message") : null;
	}
	public MaxCDNObject(String key,Object data) throws JSONException{
		super();
		this.put(key, data);
		//code = this.getInt("code");
	}
	public MaxCDNObject(Object json) throws JSONException{
		super(((JSONObject)json).toString() );
		
	}
	
	public MaxCDNObject append(String key, Object value) throws JSONException{
		this.put(key, value);
		return this;
	}
	public MaxCDNObject getJson(String key) throws JSONException{
			return new MaxCDNObject(this.get(key));
	}
	
	public Number getNumber(String key) throws JSONException {
			return (Number) this.get(key);
	}
	public String getString(String key) throws JSONException{
			return (String) this.get(key);
	}
	
	public JSONArray getArray(String key) throws JSONException{
			return  this.getJSONArray(key);
	}
}
