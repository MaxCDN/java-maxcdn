package com.maxcdn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.maxcdn.MaxCDNException;

public class MaxCDNObject extends JSONObject {
	/**
	 * Code of request.
	 */
	public int code;
	/**
	 * Specifies if request has error.
	 */
	public boolean error = false;
	
	public MaxCDNObject() throws JSONException{
		super();
	}
	public MaxCDNObject(String json) throws JSONException,MaxCDNException {
		super(new JSONObject(json).has("data") ? ((JSONObject) new JSONObject(json).get("data")).toString() : json );
		error = this.has("error");
		if( error ){
			throw new MaxCDNException( this.getJson("error").getString("message") );
		}
		code = new JSONObject(json).getInt("code");	
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
	/**
	 * Set/Add key and object pair. use method remove(Object value) to remove a key.
	 * @return MaxCDNObject 
	 */
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
