package br.com.hfs.controller;

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

import br.com.hfs.controller.dto.AdmProfileDTO;
import br.com.hfs.controller.form.AdmProfileForm;
import br.com.hfs.model.AdmProfile;
import br.com.hfs.service.AdmProfileService;

@Path("/admProfile")
public class AdmProfileController {

	@Inject
	private AdmProfileService profileService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		List<AdmProfile> objList = profileService.findAll();
		return Response.ok(AdmProfileDTO.convert(objList)).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") Long id) {
		Optional<AdmProfile> obj = profileService.findById(id);
		if (obj.isPresent()) {
			return Response.ok(new AdmProfileDTO(obj.get())).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(AdmProfileForm form) {
		AdmProfile obj = form.convert();
		obj = profileService.insert(obj);
		
		return Response.ok(new AdmProfileDTO(obj))
				.location(URI.create("admProfile/" + obj.getId()))
				.status(Status.CREATED).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, AdmProfileForm form) {
		Optional<AdmProfile> obj = profileService.findById(id);
		if (obj.isPresent()) {
			AdmProfile bean = form.update(id, profileService);
			bean = profileService.update(bean);
			return Response.ok(new AdmProfileDTO(bean)).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		Optional<AdmProfile> obj = profileService.findById(id);
		if (obj.isPresent()) {
			profileService.deleteById(id);
			return Response.ok().build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
}
