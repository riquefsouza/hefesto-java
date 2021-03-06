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

import br.com.hfs.admin.controller.dto.AdmPageDTO;
import br.com.hfs.admin.controller.form.AdmPageForm;
import br.com.hfs.admin.model.AdmPage;
import br.com.hfs.admin.service.AdmPageService;

@Path("/admPage")
public class AdmPageRestController {

	@Inject
	private AdmPageService admPageService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		List<AdmPage> objList = admPageService.findAll();
		return Response.ok(AdmPageDTO.convert(objList)).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") Long id) {
		Optional<AdmPage> obj = admPageService.findById(id);
		if (obj.isPresent()) {
			return Response.ok(new AdmPageDTO(obj.get())).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(AdmPageForm form) {
		AdmPage obj = form.convert();
		obj = admPageService.insert(obj);
		
		return Response.ok(new AdmPageDTO(obj))
				.location(URI.create("admPage/" + obj.getId()))
				.status(Status.CREATED).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, AdmPageForm form) {
		Optional<AdmPage> obj = admPageService.findById(id);
		if (obj.isPresent()) {
			AdmPage bean = form.update(id, admPageService);
			bean = admPageService.update(bean);
			return Response.ok(new AdmPageDTO(bean)).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		Optional<AdmPage> obj = admPageService.findById(id);
		if (obj.isPresent()) {
			admPageService.deleteById(id);
			return Response.ok().build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
}
