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

import br.com.hfs.model.AdmParameter;
import br.com.hfs.service.AdmParameterService;

@Path("/admParameter")
public class AdmParameterController {

	@Inject
	private AdmParameterService parameterService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		return Response.ok(parameterService.findAll()).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {
		return Response.ok(parameterService.findById(id).orElseGet(null)).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response save(@FormParam("value") @NotBlank String value,
			@FormParam("description") @NotBlank String description,
			@FormParam("code") @NotBlank String code,
			@FormParam("idAdmParameterCategory") @NotBlank Long idAdmParameterCategory) {

		AdmParameter bean = parameterService.insert(new AdmParameter(value, description, code, idAdmParameterCategory));

		return Response.created(URI.create("admParameter/" + bean.getId())).build();
	}
}
