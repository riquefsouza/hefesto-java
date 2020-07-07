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

import br.com.hfs.admin.controller.dto.AdmParameterDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class AdmParameterTests extends BaseTests {
	
	@Test
	@Order(1)
	public void insertTest() {
		Response r = 
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
			.accept(ContentType.JSON)
			.body("{\"code\": \"OLD_CODE\","
					+ "\"description\": \"OLD_PARAMETER\","
					+ "\"value\": \"OLD_VALUE\","
					+ "\"admParameterCategory\": {\"id\": 1,\"description\": \"Court Parameters\",\"order\": 1}}")
		.when()
			.post("admParameter");
		
		AdmParameterDTO dto = r.then()
			.statusCode(HttpStatus.CREATED.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterDTO.class);
		
		String slocation = r.getHeader("Location");
		id = dto.getId();
		
		assertEquals("OLD_PARAMETER", dto.getDescription());
		assertEquals(uriBase + "admParameter/" + id, slocation);
	}
	
	@Test
	@Order(2)
	public void updateTest() {
		AdmParameterDTO dto =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
			.accept(ContentType.JSON)
			.body("{\"code\": \"NEW_CODE\","
					+ "\"description\": \"NEW_PARAMETER\","
					+ "\"value\": \"NEW_VALUE\","
					+ "\"admParameterCategory\": {\"id\": 1,\"description\": \"Court Parameters\",\"order\": 1}}")
		.when()
			.put("admParameter/" + id)
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterDTO.class);
		
		assertEquals("NEW_PARAMETER", dto.getDescription());
	}
	
	@Test
	@Order(3)
	public void deleteTest() {
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.delete("admParameter/" + id)
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	@Order(4)
	void findAllTest() {
		AdmParameterDTO[] dtos =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.get("admParameter")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterDTO[].class);
		
		assertEquals(15, dtos.length);
	}

	@Test
	@Order(5)
	public void findByIdTest() {
		AdmParameterDTO dto = 
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.get("admParameter/2")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterDTO.class);
		
		assertEquals("TRT1", dto.getValue());
		assertThat("TRT1").isEqualTo(dto.getValue());
	}
	
	@Test
	@Order(6)
	void pagedAllTest() {
		final Response response =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.get("admParameter/paged");
				
		String content = response.then()
				.statusCode(HttpStatus.OK.value())
				.contentType(ContentType.JSON)
				.extract().asString();
		
		List<Map<String, String>> lst = from(content).getList("content");
		assertThat(lst).hasSize(15);
		assertEquals(lst.size(), 15);
		assertNotNull(lst.get(1));
		assertNotNull(lst.get(1).get("id"));
		assertEquals("TRT1", lst.get(1).get("value"));
		
		//response.prettyPrint();
	}
	
}
