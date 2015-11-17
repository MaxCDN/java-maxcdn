# MaxCDN REST API Java Client (BETA)

## Requirements

 - Java 1.6 or Later

## Usage
	import com.maxcdn.MaxCDN;
	import com.maxcdn.MaxCDNObject;
	import com.maxcdn.MaxCDNRequest;
	....
	//Class construction
	MaxCDN api = new MaxCDN(YOUR_ALIAS ,YOUR_CONSUMER_KEY,YOUR_CONSUMER_SECRET);
	//Performing a GET request
	MaxCDNObject data = api.get("/account.json");

## Advanced Usage
This section describes how to perform PUT and POST requests.

	//Creating a Push Zone
	try {
		MaxCDNRequest post_parameters = new MaxCDNRequest("name", "NewZone").append("password", "password");
		api.post("/zones/push.json", post_parameters);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

## Error Handling

	//Attempting to create a Push Zone with the same name
	try {
		MaxCDNRequest post_parameters = new MaxCDNRequest("name", "NewZone").append("password", "password");
		MaxCDNObject data = api.post("/zones/push.json", post_parameters);
		if(data.error) System.out.println(data.getErrorMessage());
		else {
		  System.out.println("Operation successful");
		}
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}


## Supported OAuth request methods

#### GET
	api.get("/account.json");
#### DELETE
	String id = "33706";
	api.delete("/users.json/"+id);
#### PUT
	try {

	MaxCDNRequest params = new MaxCDNRequest("street1", "1234 Main Street")
	.append("street2", "apt 42")
	.append("state", "CA");

	api.put("/account.json/address",params);

	} catch (JSONException){
	 e.printStrackTrace();
	}
#### POST

	try {

		MaxCDNRequest post_parameters = new MaxCDNRequest("name", "NewZone")
		.append("password", "password");

		api.post("/zones/push.json", post_parameters);

	} catch (JSONException e) {
		e.printStackTrace();
	}
