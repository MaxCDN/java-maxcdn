package tests;



import org.json.JSONException;

import com.maxcdn.Console;
import com.maxcdn.MaxCDN;
import com.maxcdn.MaxCDNObject;
import com.maxcdn.MaxCDNRequest;

public class ApiDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Console.log("Attempting Request ---");
		MaxCDN api = new MaxCDN("" , //alias
				"" , //Cons. key
				"");	// Cons. Secret
		
		    //Console.log(api.getAuthUrl(api.getRequestToken()));
			
	
			MaxCDNObject data = api.get("/account.json");
			if(!data.error)
			Console.log(data);
			else {
				Console.log("Error " + data.getErrorMessage());
			}
			
			
			try {
				MaxCDNObject post_example = api.post("/zones/push.json", new MaxCDNRequest("name", "NewZone91").append("password", "password"));
				if(post_example.error){
					Console.log(post_example.getErrorMessage());
					return;
				}
				Console.log(post_example);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}

}
