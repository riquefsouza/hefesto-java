package br.com.hfs.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpStatus;

import br.com.hfs.admin.controller.dto.AdmProfileDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class AdmProfileTests extends BaseTests {
	
	@Test
	@Order(1)
	public void insertTest() {
		Response r = 
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
			.accept(ContentType.JSON)
			.body("{\"administrator\": false,"
					+ "\"description\": \"OLD_PROFILE\","
					+ "\"general\": false,"
					+ "\"admPages\": [],"
					+ "\"admUsers\": []}")
		.when()
			.post("admProfile");
		
		AdmProfileDTO dto = r.then()
			.statusCode(HttpStatus.CREATED.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmProfileDTO.class);
		
		String slocation = r.getHeader("Location");
		id = dto.getId();
		
		assertEquals("OLD_PROFILE", dto.getDescription());
		assertEquals(uriBase + "admProfile/" + id, slocation);
	}
	
	@Test
	@Order(2)
	public void updateTest() {
		AdmProfileDTO dto =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
			.accept(ContentType.JSON)
			.body("{\"administrator\": false,"
					+ "\"description\": \"NEW_PROFILE\","
					+ "\"general\": false,"
					+ "\"admPages\": [],"
					+ "\"admUsers\": []}")
		.when()
			.put("admProfile/" + id)
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmProfileDTO.class);
		
		assertEquals("NEW_PROFILE", dto.getDescription());
	}
	
	@Test
	@Order(3)
	public void deleteTest() {
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.delete("admProfile/" + id)
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	@Order(4)
	void findAllTest() {
		AdmProfileDTO[] dtos =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.get("admProfile")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmProfileDTO[].class);
		
		assertEquals(2, dtos.length);
	}

	@Test
	@Order(5)
	public void findByIdTest() {
		AdmProfileDTO dto = 
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
			//.relaxedHTTPSValidation()
			//.header("Accept", "application/json")
			//.contentType(ContentType.JSON)
			//.param("foo1", "bar1")
			//.param("foo2", "bar2")
		.when()
			.get("admProfile/2")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			//.body("headers.host", is("postman-echo.com"))
			//.body("description", containsString("[ADMIN, USER]"));
			.extract().as(AdmProfileDTO.class);
		
		assertEquals("USER", dto.getDescription());
		assertThat("USER").isEqualTo(dto.getDescription());
	}
	
	@Test
	@Order(6)
	void pagedAllTest() {
		final Response response =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.get("admProfile/paged");
				
		String content = response.then()
				.statusCode(HttpStatus.OK.value())
				.contentType(ContentType.JSON)
				.extract().asString();
		
		List<Map<String, String>> lst = from(content).getList("content");
		assertThat(lst).hasSize(2);
		assertEquals(lst.size(), 2);
		assertNotNull(lst.get(1));
		assertNotNull(lst.get(1).get("id"));
		assertEquals("USER", lst.get(1).get("description"));
		
		//response.prettyPrint();
	}
	
}
