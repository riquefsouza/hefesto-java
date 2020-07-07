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

import br.com.hfs.admin.controller.dto.AdmUserDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class AdmUserTests extends BaseTests {
	
	@Test
	@Order(1)
	public void insertTest() {
		Response r = 
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
			.accept(ContentType.JSON)
			.body("{\"login\": \"OLD_USER\","
					+ "\"password\": \"OLD_PASSWORD\","
					+ "\"name\": \"OLD_NAME\","
					+ "\"email\": \"old_mail@gmail.com\"}")
		.when()
			.post("admUser");
		
		AdmUserDTO dto = r.then()
			.statusCode(HttpStatus.CREATED.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmUserDTO.class);
		
		String slocation = r.getHeader("Location");
		id = dto.getId();
		
		assertEquals("OLD_USER", dto.getLogin());
		assertEquals(uriBase + "admUser/" + id, slocation);
	}
	
	@Test
	@Order(2)
	public void updateTest() {
		AdmUserDTO dto =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
			.accept(ContentType.JSON)
			.body("{\"login\": \"NEW_USER\","
					+ "\"password\": \"NEW_PASSWORD\","
					+ "\"name\": \"NEW_NAME\","
					+ "\"email\": \"new_mail@gmail.com\"}")
		.when()
			.put("admUser/" + id)
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmUserDTO.class);
		
		assertEquals("NEW_USER", dto.getLogin());
	}
	
	@Test
	@Order(3)
	public void deleteTest() {
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.delete("admUser/" + id)
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	@Order(4)
	void findAllTest() {
		AdmUserDTO[] dtos =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.get("admUser")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmUserDTO[].class);
		
		assertEquals(1, dtos.length);
	}

	@Test
	@Order(5)
	public void findByIdTest() {
		AdmUserDTO dto = 
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.get("admUser/1")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmUserDTO.class);
		
		assertEquals("admin", dto.getLogin());
		assertThat("admin").isEqualTo(dto.getLogin());
	}
	
	@Test
	@Order(6)
	void pagedAllTest() {
		final Response response =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.get("admUser/paged");
				
		String content = response.then()
				.statusCode(HttpStatus.OK.value())
				.contentType(ContentType.JSON)
				.extract().asString();
		
		List<Map<String, String>> lst = from(content).getList("content");
		assertThat(lst).hasSize(1);
		assertEquals(lst.size(), 1);
		assertNotNull(lst.get(0));
		assertNotNull(lst.get(0).get("id"));
		assertEquals("admin", lst.get(0).get("login"));
		
		//response.prettyPrint();
	}
	
	
}
