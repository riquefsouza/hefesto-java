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

import br.com.hfs.model.AdmUser;
import br.com.hfs.service.AdmUserService;

@Path("/admUser")
public class AdmUserController {

	@Inject
	private AdmUserService userService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		return Response.ok(userService.findAll()).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {
		return Response.ok(userService.findById(id).orElseGet(null)).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response save(@FormParam("login") @NotBlank String login,
			@FormParam("password") @NotBlank String password) {
		
		AdmUser bean = userService.insert(new AdmUser(login, password));

		return Response.created(URI.create("admUser/" + bean.getId())).build();
	}
}
