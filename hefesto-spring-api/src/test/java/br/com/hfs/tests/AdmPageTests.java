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

import br.com.hfs.admin.controller.dto.AdmPageDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class AdmPageTests extends BaseTests {

	@Test
	@Order(1)
	public void insertTest() {
		Response r = 
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
			.accept(ContentType.JSON)
			.body("{\"url\": \"admin/admTest/listTest.xhtml\","
					+ "\"description\": \"OLD_PAGE\"}")
		.when()
			.post("admPage");
		
		AdmPageDTO dto = r.then()
			.statusCode(HttpStatus.CREATED.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmPageDTO.class);
		
		String slocation = r.getHeader("Location");
		id = dto.getId();
		
		assertEquals("OLD_PAGE", dto.getDescription());
		assertEquals(uriBase + "admPage/" + id, slocation);
	}
	
	@Test
	@Order(2)
	public void updateTest() {
		AdmPageDTO dto =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
			.accept(ContentType.JSON)
			.body("{\"url\": \"admin/admTest/listTest.xhtml\","
					+ "\"description\": \"NEW_PAGE\"}")
		.when()
			.put("admPage/" + id)
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmPageDTO.class);
		
		assertEquals("NEW_PAGE", dto.getDescription());
	}
	
	@Test
	@Order(3)
	public void deleteTest() {
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.delete("admPage/" + id)
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	@Order(4)
	void findAllTest() {
		AdmPageDTO[] dtos =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.get("admPage")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmPageDTO[].class);
		
		assertEquals(12, dtos.length);
	}

	@Test
	@Order(5)
	public void findByIdTest() {
		AdmPageDTO dto = 
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.get("admPage/2")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.extract().as(AdmPageDTO.class);
		
		assertEquals("admin/admParameterCategory/editAdmParameterCategory.xhtml", dto.getUrl());
		assertThat("admin/admParameterCategory/editAdmParameterCategory.xhtml").isEqualTo(dto.getUrl());
	}
	
	@Test
	@Order(6)
	void pagedAllTest() {
		final Response response =
		given()
			.spec(spec)
			.header(AUTHORIZATION, accessToken)
		.when()
			.get("admPage/paged");
				
		String content = response.then()
				.statusCode(HttpStatus.OK.value())
				.contentType(ContentType.JSON)
				.extract().asString();
		
		List<Map<String, String>> lst = from(content).getList("content");
		assertThat(lst).hasSize(12);
		assertEquals(lst.size(), 12);
		assertNotNull(lst.get(1));
		assertNotNull(lst.get(1).get("id"));
		assertEquals("admin/admParameterCategory/editAdmParameterCategory.xhtml", lst.get(1).get("url"));
		
		//response.prettyPrint();
	}
	
}
