import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.Assert.*;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TestMethods {

	private String requestBody;
	private Response response;
	private String baseURL;
	private String path;

	public JSONObject readVariablesFile() {
		String data = "";
		try {
			data = new String(Files.readAllBytes(Paths.get("./variables.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = new JSONObject(data);
		
		return jsonObject;
	}

	public String getToken() {
		baseURL = "https://next-cdn.tajawal.com";
		path = "/hotels/_next/static/Z0kPdx6I64hxaUYb694z4/pages/_app.js";
		response = given().contentType("text/html")
				.get("https://next-cdn.tajawal.com/hotels/_next/static/Z0kPdx6I64hxaUYb694z4/pages/_app.js");
		String stringRes = response.getBody().asString();
		String token = stringRes.substring(stringRes.indexOf("x-auth-token\":\"") + 15, stringRes.indexOf("x-auth-token\":\"") + 37);
		//System.out.println(token.toString());
		return token;
	}

	@Test
	public void postHotels() throws Exception {
		baseURL = "https://www.almosafer.com";
		path = "/api/enigma/search/async/";
		JSONObject variables = readVariablesFile();
		requestBody = "{\"checkIn\":\"" + variables.get("checkIn") + "\",\"checkOut\":\"" + variables.get("checkOut")
				+ "\",\"roomsInfo\":[{\"adultsCount\":" + variables.get("adultsCount") + ",\"kidsAges\":"
				+ variables.get("kidsAges") + "}],\"placeId\":\"" + variables.get("placeId") + "\"}";
		response = given().accept(ContentType.JSON).header("token", getToken()).contentType("application/json")
				.body(requestBody.toString()).post(baseURL + path);
		System.out.println("postHotels Response: " + response.getBody().asString());
		// Assertion:
		//JSONObject jsonResponse = new JSONObject(response.getBody().asString());
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(response.getStatusCode()).isEqualTo(200);
		softAssertions.assertThat(response.getHeader("token")).isEqualTo(getToken());
		//softAssertions.assertThat(jsonResponse.get("sId")).isNotNull();
		softAssertions.assertAll();
	}

	//@Test
	public void getHotels() throws Exception {
		JSONObject variables = readVariablesFile();
		baseURL = "https://www.almosafer.com";
		path = "/en/hotels/" + variables.get("city") + "/" + variables.get("checkIn") + "/" + variables.get("checkOut")
				+ "/" + variables.get("adultsCount") + "_adult?placeId=" + variables.get("placeId") + "&city="
				+ variables.get("city");
		response = given().get(baseURL + path);
		//System.out.println("getHotels Response: " + response.getBody().asString());
		// Assertion:
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(response.getStatusCode()).isEqualTo(200);
		softAssertions.assertThat(response.getHeader("content-type")).isEqualTo("text/html; charset=utf-8");
		softAssertions.assertAll();
	}

	/*
	 * @Test public void readVariablesFile() throws Exception{ BufferedReader br =
	 * new BufferedReader( new FileReader("C:/Users/Moody/Desktop/variables.txt"));
	 * //File file = new File("C:/Users/Moody/Desktop/variables.txt"); //Scanner
	 * scan = new Scanner(file); //System.out.println("Scanner: " + scan.nextInt());
	 * String file; // = "ttt"; while((file = br.readLine()) != null) { file =
	 * file.concat(br.readLine()); System.out.println(file);
	 * 
	 * } System.out.println("Scanner: " + file);
	 * 
	 * //return str; }
	 */

}
