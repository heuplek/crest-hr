package edu.vt.crest.hr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;

import edu.vt.crest.hr.entity.DepartmentEntity;
import edu.vt.crest.hr.entity.EmployeeEntity;
import edu.vt.crest.hr.services.DepartmentService;

/**
 * Serves as a RESTful endpoint for manipulating DepartmentEntity(s)
 */
@Stateless
@Path("/departments")
public class DepartmentResource {

	//Used to interact with DepartmentEntity(s)
	@Inject
	DepartmentService departmentService;

	/**
	 * TODO - Implement this method
	 * @param department the DepartmentEntity to create
	 * @return a Response containing the new DepartmentEntity
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(DepartmentEntity department) {
		DepartmentEntity createdDepartment;
		try {
			createdDepartment = departmentService.createDepartment(department);
		}catch(Exception e){
			System.out.println(e);
			return Response.serverError().build();
		}
		return Response.ok(createdDepartment).build();
	}

	/**
	 * TODO - Implement this method
	 * @param id of the DepartmentEntity to return
	 * @return a Response containing the matching DepartmentEntity
	 */
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {
		DepartmentEntity departmentFound;
		try {
			departmentFound  = departmentService.findById(id);
		}catch(Exception e){
			return Response.serverError().build();
		}
		return Response.ok(departmentFound ).build();
	}

	/**
	 * TODO - Implement this method
	 * @param startPosition the index of the first DepartmentEntity to return
	 * @param maxResult the maximum number of DepartmentEntity(s) to return
	 *                  beyond the startPosition
	 * @return a list of DepartmentEntity(s)
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DepartmentEntity> listAll(@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {

		//start and max are both null here
		List<DepartmentEntity> departmentList = departmentService.listAll(startPosition, maxResult);
		return departmentList;

	}

	/**
	 * TODO - Implement this method
	 * @param id the id of the DepartmentEntity to update
	 * @param department the entity used to update
	 * @return a Response containing the updated DepartmentEntity
	 */
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, DepartmentEntity department) {
		DepartmentEntity updatedDepartment;
		try{
			updatedDepartment = departmentService.update(id, department);
		}catch(Exception e){
			System.out.println(e);
			return Response.serverError().build();
		}
		return Response.ok(updatedDepartment).build();
	}

}
