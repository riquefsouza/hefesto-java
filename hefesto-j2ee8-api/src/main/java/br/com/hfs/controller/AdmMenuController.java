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

import br.com.hfs.model.AdmMenu;
import br.com.hfs.service.AdmMenuService;

@Path("/admMenu")
public class AdmMenuController {

	@Inject
	private AdmMenuService menuService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		return Response.ok(menuService.findAll()).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {
		return Response.ok(menuService.findById(id).orElseGet(null)).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response save(@FormParam("description") @NotBlank String description,
			@FormParam("idMenuParent") @NotBlank Long idMenuParent, 
			@FormParam("idPage") @NotBlank Long idPage, 
			@FormParam("Integer") @NotBlank Integer order) {

		AdmMenu bean = menuService.insert(new AdmMenu(description, idMenuParent, idPage, order));
		
		return Response.created(URI.create("admMenu/" + bean.getId())).build();
	}
}
