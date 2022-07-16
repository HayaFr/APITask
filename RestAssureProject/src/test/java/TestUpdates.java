import org.junit.Test;
	import org.junit.jupiter.api.BeforeAll;
	import static io.restassured.RestAssured.given;
	import io.restassured.RestAssured;
	import io.restassured.response.Response;
	import org.json.*;  



	import org.apache.commons.lang3.StringUtils;

	import io.restassured.http.ContentType;

public class TestUpdates {
	

		
	    @BeforeAll
	    public static void setup() {
	        RestAssured.baseURI = "https://www.almosafer.com";
	    }
	    
	    private String token;
	    private String requestBody;
	    private Response response;
	    private String baseURL;
	    private String path;

	    
	    
	    
		public String getToken() {
	    	baseURL = "https://next-cdn.tajawal.com";
	    	path = "/hotels/_next/static/Z0kPdx6I64hxaUYb694z4/pages/_app.js";
			response = given().get(baseURL + path);
			String getTokenResponse = response.getBody().asString();
			token = getTokenResponse.substring(getTokenResponse.indexOf("x-auth-token\":\"")+15, getTokenResponse.indexOf("x-auth-token\":\"") + 37);
			System.out.println("Token: " + token);
			return token;
	    }
	    
		@Test
		public void postHotels() {
	    	baseURL = "https://www.almosafer.com";
	    	path = "/api/enigma/search/async/";
			requestBody = "{\"checkIn\":\"2022-07-25\",\"checkOut\":\"2022-07-26\",\"roomsInfo\":[{\"adultsCount\":2,\"kidsAges\":[]}],\"placeId\":\"ChIJr4F5XbhfGxUR6GXc-MAwHGM\"}";
			response = given().accept(ContentType.JSON)
					.header("token", getToken())
					.contentType("application/json")
					.body(requestBody.toString())
	        		.post(baseURL + path);
			System.out.println("postHotels Response: " + response.getBody().asString());
		}
	    
		/*@Test
		public void postChalete() {
			 String requestBody = "{\"searchCriteria\":[{\"lookupTypeId\":2,\"lookupId\":[9]}],\"checkIn\":\"2022-07-20\",\"checkOut\":\"2022-07-28\",\"sortBy\":\"rank\",\"sortOrder\":\"DESC\",\"rankType\":\"dynamic\",\"pageNo\":1,\"pageSize\":10}";
	        Response response2 = given().accept(ContentType.JSON)
					.contentType("application/json")
					.body(requestBody.toString())
	        		.post("https://www.almosafer.com/api/accommodation/property/search");
			//System.out.println("postChalete =" + response2.getBody().asString());
		} */

		
	    
	    
	    /*public void getHotels() {
			baseURL = "https://www.almosafer.com";
	    	path = "/en/hotels/Amman/25-07-2022/26-07-2022/2_adult?placeId=ChIJr4F5XbhfGxUR6GXc-MAwHGM&city=Amman";
			response = given().get(baseURL + path);
			
		}*/

}
