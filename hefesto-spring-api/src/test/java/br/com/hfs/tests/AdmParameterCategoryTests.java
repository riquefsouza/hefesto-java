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

import br.com.hfs.admin.controller.dto.AdmParameterCategoryDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class AdmParameterCategoryTests extends BaseTests {
	
	@Test
	@Order(1)
	public void insertTest() {
		Response r = 
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
			.accept(ContentType.JSON)
			.body("{\"order\": 6,"
					+ "\"description\": \"OLD_PARAMETER_CATEGORY\"}")
		.when()
			.post("admParameterCategory");
		
		AdmParameterCategoryDTO dto = r.then()
			.statusCode(HttpStatus.CREATED.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterCategoryDTO.class);
		
		String slocation = r.getHeader("Location");
		id = dto.getId();
		
		assertEquals("OLD_PARAMETER_CATEGORY", dto.getDescription());
		assertEquals(uriBase + "admParameterCategory/" + id, slocation);
	}
	
	@Test
	@Order(2)
	public void updateTest() {
		AdmParameterCategoryDTO dto =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
			.accept(ContentType.JSON)
			.body("{\"order\": 6,"
					+ "\"description\": \"NEW_PARAMETER_CATEGORY\"}")
		.when()
			.put("admParameterCategory/" + id)
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterCategoryDTO.class);
		
		assertEquals("NEW_PARAMETER_CATEGORY", dto.getDescription());
	}
	
	@Test
	@Order(3)
	public void deleteTest() {
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.delete("admParameterCategory/" + id)
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	@Order(4)
	void findAllTest() {
		AdmParameterCategoryDTO[] dtos =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.get("admParameterCategory")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterCategoryDTO[].class);
		
		assertEquals(5, dtos.length);
	}

	@Test
	@Order(5)
	public void findByIdTest() {
		AdmParameterCategoryDTO dto = 
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.get("admParameterCategory/2")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmParameterCategoryDTO.class);
		
		assertEquals("Login Parameters", dto.getDescription());
		assertThat("Login Parameters").isEqualTo(dto.getDescription());
	}
	
	@Test
	@Order(6)
	void pagedAllTest() {
		final Response response =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.get("admParameterCategory/paged");
				
		String content = response.then()
				.statusCode(HttpStatus.OK.value())
				.contentType(ContentType.JSON)
				.extract().asString();
		
		List<Map<String, String>> lst = from(content).getList("content");
		assertThat(lst).hasSize(5);
		assertEquals(lst.size(), 5);
		assertNotNull(lst.get(1));
		assertNotNull(lst.get(1).get("id"));
		assertEquals("Login Parameters", lst.get(1).get("description"));
		
		//response.prettyPrint();
	}
	
}
