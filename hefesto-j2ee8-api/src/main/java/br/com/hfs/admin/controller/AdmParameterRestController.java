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

import br.com.hfs.admin.controller.dto.AdmParameterDTO;
import br.com.hfs.admin.controller.form.AdmParameterForm;
import br.com.hfs.admin.model.AdmParameter;
import br.com.hfs.admin.service.AdmParameterCategoryService;
import br.com.hfs.admin.service.AdmParameterService;

@Path("/admParameter")
public class AdmParameterRestController {

	@Inject
	private AdmParameterService admParameterService;
	
	@Inject
	private AdmParameterCategoryService admParameterCategoryService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		List<AdmParameter> objList = admParameterService.findAll();
		return Response.ok(AdmParameterDTO.convert(objList)).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") Long id) {
		Optional<AdmParameter> obj = admParameterService.findById(id);
		if (obj.isPresent()) {
			obj.get().setAdmParameterCategory(
					admParameterCategoryService.findById(obj.get().getIdAdmParameterCategory())
					.get());
			return Response.ok(new AdmParameterDTO(obj.get())).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(AdmParameterForm form) {
		AdmParameter obj = form.convert();
		obj.setAdmParameterCategory(admParameterCategoryService.findById(obj.getIdAdmParameterCategory()).get());
		obj = admParameterService.insert(obj);
		
		return Response.ok(new AdmParameterDTO(obj))
				.location(URI.create("admParameter/" + obj.getId()))
				.status(Status.CREATED).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, AdmParameterForm form) {
		Optional<AdmParameter> obj = admParameterService.findById(id);
		if (obj.isPresent()) {
			AdmParameter bean = form.update(id, admParameterService);
			bean = admParameterService.update(bean);
			return Response.ok(new AdmParameterDTO(bean)).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		Optional<AdmParameter> obj = admParameterService.findById(id);
		if (obj.isPresent()) {
			admParameterService.deleteById(id);
			return Response.ok().build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
}
