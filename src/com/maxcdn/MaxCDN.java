package com.maxcdn;

/**
 * MaxCDN - Java API client
 * @author Cheikh Seck
 * @version 0.0.1
 */
import java.security.SignatureException;
import org.json.JSONException;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;


public class MaxCDN {
	
	/**
	 * Instance's API alias.
	 */
	public String alias;
	/**
	 * Instance's API key.
	 */
	public String key;
	/**
	 * Instance's API secret.
	 */
	public String secret;
	/**
	 * Access token of instance.
	 */
	private Token token_stored;
	/**
	 * MaxCDN RWS URL
	 */
	public String MaxCDNrws_url = "https://rws.maxcdn.com/";
	
	
	/**
	 * Initialize client without API access token.
	 */
	public MaxCDN(String alias, String consumer_key, String consumer_secret){
		this.alias = alias;
		this.key = consumer_key;
		this.secret = consumer_secret;
		token_stored = null;
		
	}
	
	/**
	 * Initialize client with API token.
	 * @param token String of a valid API token.
	 */
	public MaxCDN(String alias, String consumer_key, String consumer_secret,Token token){
		this.alias = alias;
		this.key = consumer_key;
		this.secret = consumer_secret;
		this.token_stored = token;
		
	}
	
	/**
	 * Perform a request with verb GET to specified endpoint.
	 * @param endpoint API endpoint to perform GET request on.
	 * @return MaxCDNObject
	 */
	public MaxCDNObject get(String endpoint) throws JSONException,SignatureException, Exception {
		return this.request(endpoint, Verb.GET, null);
	}
	/**
	 * Perform a request with verb DELETE to specified endpoint.
	 * @param endpoint String of API endpoint to use.
	 * @return MaxCDNObject
	 */	
	public MaxCDNObject delete(String endpoint) throws JSONException,SignatureException, Exception {
		return this.request(endpoint, Verb.DELETE, null);
	}
	/**
	 * Perform a request with verb PUT to specified endpoint.
	 * @param endpoint String of API endpoint to use.
	 * @param request Data to be submitted with request.
	 * @return MaxCDNObject
	 */	
	public MaxCDNObject put(String endpoint, MaxCDNRequest request) throws JSONException,SignatureException, Exception{
		return this.request(endpoint, Verb.PUT, request);
	}
	/**
	 * Perform a request with verb POST to specified endpoint.
	 * @param endpoint String of API endpoint to use.
	 * @param request Data to be submitted with request.
	 * @return MaxCDNObject
	 */	
	public MaxCDNObject post(String endpoint, MaxCDNRequest request) throws JSONException,SignatureException, Exception{
		return this.request(endpoint, Verb.POST, request);
	}
	
	/**
	 * Generate a request token.
	 */
	public Token getRequestToken(){
		OAuthService service = new ServiceBuilder()
		   .provider(MaxCDNApi.class)
		   .apiKey(key)
		   .apiSecret(secret)
		   .build();
        return service.getRequestToken();
	}
	/**
	 * Get authorization URL.
	 * @param requestToken.
	 * @return String of authorization URL.
	 */		
	public String getAuthUrl(Token requestToken){
	    // Obtain the Request Token
		OAuthService service = new ServiceBuilder()
		   .provider(MaxCDNApi.class)
		   .apiKey(key)
		   .apiSecret(secret)
		   .build();
		
	
	    return service.getAuthorizationUrl(requestToken);
	}
	
	/**
	 * Get access token from MaxCDN.
	 * @param requestToken Request token to submit with request.
	 * @param verify Temporary access token to get long lived access token.
	 * @return
	 */
	public Token getAccessToken(Token requestToken, String verify){
		OAuthService service = new ServiceBuilder()
		   .provider(MaxCDNApi.class)
		   .apiKey(key)
		   .apiSecret(secret)
		   .build();
		return service.getAccessToken(requestToken, new Verifier(verify) );
	}
	
	/**
	 * Perform a request wih a custom verb. The current instance's access token will be used.
	 * @param end String of API endpoint to use.
	 * @param verb String of request verb to use.
	 * @param body Data to be submitted with request.
	 * @return MaxCDNObject
	 */
	public synchronized MaxCDNObject request(String end, Verb verb, MaxCDNRequest body) throws JSONException,SignatureException, Exception {
			if(token_stored == null)
			return new MaxCDNObject(this._request(end, verb, body));
			else return new MaxCDNObject(this._request(end, verb, body,token_stored));
	}
	
	/**
	 * Perform a request wih a custom verb and access token.
	 * @param end String of API endpoint to use.
	 * @param verb String of request verb to use.
	 * @param body Data to be submitted with request.
	 * @param token access token to use with request.
	 * @return MaxCDNObject
	 */
	public synchronized MaxCDNObject request(String end, Verb verb, MaxCDNRequest body,Token token) throws JSONException,SignatureException, Exception {
			return new MaxCDNObject(this._request(end, verb, body,token));
	}
	
	/**
	 * Set access token
	 * @param token Token to set as the instance's token.
	 */
	public boolean setToken(Token token){
		token_stored = token;
		return true;
	}
	
	public synchronized String _request(String end, Verb verb, MaxCDNRequest body) throws SignatureException, Exception{	
		return _request(end, verb,body,null);
	}
	public synchronized String _request(String end, Verb verb, MaxCDNRequest body,Token token) throws SignatureException, Exception{	
		OAuthService service = new ServiceBuilder()
		   .provider(MaxCDNApi.class)
		   .apiKey(key)
		   .apiSecret(secret)
		   .build();
		 
		OAuthRequest request = new OAuthRequest(verb, String.format("%s%s%s", this.MaxCDNrws_url , alias, end));
		request.addHeader("User-Agent", "Java MaxCDN API Client");
		if(verb == Verb.PUT || verb == Verb.POST){
			for(int i = 0;i < body.names().length(); i++){
				String key = (String) body.names().get(i);
				request.addBodyParameter(key, body.getString(key));
			}
		}
		service.signRequest((token == null) ? new Token("","") : token, request);
		 
		Response response = request.send();
		//Console.log(response.getBody());

	    return response.getBody();
	}
}
