package br.com.hfs.controller;

import java.net.URI;

import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.hfs.model.AdmParameterCategory;
import br.com.hfs.service.AdmParameterCategoryService;

@Path("/admParameterCategory")
public class AdmParameterCategoryController {

	@Inject
	private AdmParameterCategoryService parameterCategoryService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		return Response.ok(parameterCategoryService.findAll()).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {
		return Response.ok(parameterCategoryService.findById(id).orElseGet(null)).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response save(@FormParam("description") @NotBlank String description,
			@FormParam("order") @NotBlank Long order) {

		AdmParameterCategory bean = parameterCategoryService.insert(new AdmParameterCategory(description, order));

		return Response.created(URI.create("admParameterCategory/" + bean.getId())).build();
	}
}
