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

import br.com.hfs.admin.controller.dto.AdmMenuDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class AdmMenuTests extends BaseTests {

	@Test
	@Order(1)
	public void insertTest() {
		Response r = 
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
			.accept(ContentType.JSON)
			.body("{\"description\": \"OLD_MENU\","
					+ "\"order\": 1,"
					+ "\"idPage\": 1,"
					+ "\"idMenuParent\": 1}")
		.when()
			.post("admMenu");
		
		AdmMenuDTO dto = r.then()
			.statusCode(HttpStatus.CREATED.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmMenuDTO.class);
		
		String slocation = r.getHeader("Location");
		id = dto.getId();
		
		assertEquals("OLD_MENU", dto.getDescription());
		assertEquals(uriBase + "admMenu/" + id, slocation);
	}
	
	@Test
	@Order(2)
	public void updateTest() {
		AdmMenuDTO dto =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
			.accept(ContentType.JSON)
			.body("{\"description\": \"NEW_MENU\","
					+ "\"order\": 1,"
					+ "\"idPage\": 1,"
					+ "\"idMenuParent\": 1}")
		.when()
			.put("admMenu/" + id)
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmMenuDTO.class);
		
		assertEquals("NEW_MENU", dto.getDescription());
	}
	
	@Test
	@Order(3)
	public void deleteTest() {
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.delete("admMenu/" + id)
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	@Order(4)
	void findAllTest() {
		AdmMenuDTO[] dtos =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.get("admMenu")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmMenuDTO[].class);
		
		assertEquals(7, dtos.length);
	}

	@Test
	@Order(5)
	public void findByIdTest() {
		AdmMenuDTO dto = 
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.get("admMenu/2")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmMenuDTO.class);
		
		assertEquals("Category of Configuration Parameters", dto.getDescription());
		assertThat("Category of Configuration Parameters").isEqualTo(dto.getDescription());
	}

	@Test
	@Order(6)
	void pagedAllTest() {
		final Response response =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.get("admMenu/paged");
				
		String content = response.then()
				.statusCode(HttpStatus.OK.value())
				.contentType(ContentType.JSON)
				.extract().asString();
		
		List<Map<String, String>> lst = from(content).getList("content");
		assertThat(lst).hasSize(7);
		assertEquals(lst.size(), 7);
		assertNotNull(lst.get(1));
		assertNotNull(lst.get(1).get("id"));
		assertEquals("Category of Configuration Parameters", lst.get(1).get("description"));
		
		//response.prettyPrint();
	}
	
}
