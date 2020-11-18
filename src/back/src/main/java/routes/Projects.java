package routes;

import dao.DAOFactory;
import dao.ProjectDAO;
import domain.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("projects")
public class Projects {
    @GET
    @Produces("application/json")
    public Response getProjects() {
        ProjectDAO projectDAO = DAOFactory.getInstance().getProjectDAO();

        try {
            return Response.status(Response.Status.OK).entity(projectDAO.getAll()).build();
        } catch (Exception exception) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("{id}")
    @GET
    @Produces("application/json")
    public Response getProject(@PathParam("id") int id) {
        ProjectDAO projectDAO = DAOFactory.getInstance().getProjectDAO();

        try {
            return Response.status(Response.Status.OK).entity(projectDAO.getById(id)).build();
        } catch (Exception exception) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response postProject(Project project) {
        ProjectDAO projectDAO = DAOFactory.getInstance().getProjectDAO();
        Project built;
        try {
            built = projectDAO.insert(project);
        } catch (Exception exception) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).entity(built).build();
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response putProject(Project project) {
        ProjectDAO projectDAO = DAOFactory.getInstance().getProjectDAO();
        try {
            projectDAO.update(project);
        } catch (Exception exception) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).entity(project).build();
    }

    @Path("{id}")
    @DELETE
    public Response deleteProject(@PathParam("id") int id) {
        ProjectDAO projectDAO = DAOFactory.getInstance().getProjectDAO();

        try {
            Project project = projectDAO.getById(id);
            projectDAO.delete(project);
        } catch (Exception exception) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).build();
    }
}
