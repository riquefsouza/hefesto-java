package br.com.hfs.controller;

import java.math.BigDecimal;
import java.net.URI;

import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.hfs.model.Job;
import br.com.hfs.service.JobService;

@Path("/job")
public class JobController {

	@Inject
	private JobService jobService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		return Response.ok(jobService.findAll()).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {
		return Response.ok(jobService.findById(id).orElseGet(null)).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response save(@FormParam("companyName") @NotBlank String companyName,
			@FormParam("description") @NotBlank String description, @FormParam("salary") @NotNull Double salary,
			@FormParam("office") @NotBlank String office) {

		Job job = jobService.save(Job.build(companyName, description, new BigDecimal(salary), office));

		return Response.created(URI.create("job/" + job.getId())).build();

	}

}
