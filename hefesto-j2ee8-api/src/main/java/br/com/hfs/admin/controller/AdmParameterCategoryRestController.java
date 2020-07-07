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

import br.com.hfs.admin.controller.dto.AdmParameterCategoryDTO;
import br.com.hfs.admin.controller.form.AdmParameterCategoryForm;
import br.com.hfs.admin.model.AdmParameterCategory;
import br.com.hfs.admin.service.AdmParameterCategoryService;

@Path("/admParameterCategory")
public class AdmParameterCategoryRestController {

	@Inject
	private AdmParameterCategoryService admParameterCategoryService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		List<AdmParameterCategory> objList = admParameterCategoryService.findAll();
		return Response.ok(AdmParameterCategoryDTO.convert(objList)).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") Long id) {
		Optional<AdmParameterCategory> obj = admParameterCategoryService.findById(id);
		if (obj.isPresent()) {
			return Response.ok(new AdmParameterCategoryDTO(obj.get())).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(AdmParameterCategoryForm form) {
		AdmParameterCategory obj = form.convert();
		obj = admParameterCategoryService.insert(obj);
		
		return Response.ok(new AdmParameterCategoryDTO(obj))
				.location(URI.create("admParameterCategory/" + obj.getId()))
				.status(Status.CREATED).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, AdmParameterCategoryForm form) {
		Optional<AdmParameterCategory> obj = admParameterCategoryService.findById(id);
		if (obj.isPresent()) {
			AdmParameterCategory bean = form.update(id, admParameterCategoryService);
			bean = admParameterCategoryService.update(bean);
			return Response.ok(new AdmParameterCategoryDTO(bean)).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		Optional<AdmParameterCategory> obj = admParameterCategoryService.findById(id);
		if (obj.isPresent()) {
			admParameterCategoryService.deleteById(id);
			return Response.ok().build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
}
