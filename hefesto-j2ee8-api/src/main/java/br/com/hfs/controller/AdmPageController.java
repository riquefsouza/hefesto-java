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

import br.com.hfs.model.AdmPage;
import br.com.hfs.service.AdmPageService;

@Path("/admPage")
public class AdmPageController {

	@Inject
	private AdmPageService pageService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		return Response.ok(pageService.findAll()).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {
		return Response.ok(pageService.findById(id).orElseGet(null)).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response save(@FormParam("url") @NotBlank String url,
			@FormParam("description") @NotBlank String description) {

		AdmPage bean = pageService.insert(new AdmPage(url, description));

		return Response.created(URI.create("admPage/" + bean.getId())).build();
	}
}
