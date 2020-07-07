package br.com.hfs.admin.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.hfs.admin.controller.dto.AdmMenuDTO;
import br.com.hfs.admin.controller.form.AdmMenuForm;
import br.com.hfs.admin.model.AdmMenu;
import br.com.hfs.admin.service.AdmMenuService;

@Path("/admMenu")
public class AdmMenuRestController {

	@Inject
	private AdmMenuService admMenuService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		List<AdmMenu> objList = admMenuService.findAll();
		return Response.ok(AdmMenuDTO.convert(objList)).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") Long id) {
		Optional<AdmMenu> obj = admMenuService.findById(id);
		if (obj.isPresent()) {
			return Response.ok(new AdmMenuDTO(obj.get())).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(AdmMenuForm form) {
		AdmMenu obj = form.convert();
		obj = admMenuService.insert(obj);
		obj = admMenuService.findById(obj.getId()).get();
		
		return Response.ok(new AdmMenuDTO(obj))
				.location(URI.create("admMenu/" + obj.getId()))
				.status(Status.CREATED).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, AdmMenuForm form) {
		Optional<AdmMenu> obj = admMenuService.findById(id);
		if (obj.isPresent()) {
			AdmMenu bean = form.update(id, admMenuService);
			bean = admMenuService.update(bean);
			return Response.ok(new AdmMenuDTO(bean)).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		Optional<AdmMenu> obj = admMenuService.findById(id);
		if (obj.isPresent()) {
			admMenuService.directDeleteById(id);
			return Response.ok().build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
}
