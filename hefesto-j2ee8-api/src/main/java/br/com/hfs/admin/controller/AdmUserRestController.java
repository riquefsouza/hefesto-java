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

import br.com.hfs.admin.controller.dto.AdmUserDTO;
import br.com.hfs.admin.controller.form.AdmUserForm;
import br.com.hfs.admin.model.AdmUser;
import br.com.hfs.admin.service.AdmProfileService;
import br.com.hfs.admin.service.AdmUserService;

@Path("/admUser")
public class AdmUserRestController {

	@Inject
	private AdmUserService admUserService;
	
	@Inject
	private AdmProfileService admProfileService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		admUserService.setAdmProfileService(admProfileService);
		List<AdmUser> objList = admUserService.findAll();
		return Response.ok(AdmUserDTO.convert(objList)).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") Long id) {
		admUserService.setAdmProfileService(admProfileService);
		Optional<AdmUser> obj = admUserService.findById(id);
		if (obj.isPresent()) {
			return Response.ok(new AdmUserDTO(obj.get())).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(AdmUserForm form) {
		AdmUser obj = form.convert();
		admUserService.setAdmProfileService(admProfileService);
		obj = admUserService.insert(obj);
		
		return Response.ok(new AdmUserDTO(obj))
				.location(URI.create("admUser/" + obj.getId()))
				.status(Status.CREATED).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, AdmUserForm form) {
		admUserService.setAdmProfileService(admProfileService);
		Optional<AdmUser> obj = admUserService.findById(id);
		if (obj.isPresent()) {
			AdmUser bean = form.update(id, admUserService);
			bean = admUserService.update(bean);
			return Response.ok(new AdmUserDTO(bean)).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		admUserService.setAdmProfileService(admProfileService);
		Optional<AdmUser> obj = admUserService.findById(id);
		if (obj.isPresent()) {
			admUserService.deleteById(id);
			return Response.ok().build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
}
