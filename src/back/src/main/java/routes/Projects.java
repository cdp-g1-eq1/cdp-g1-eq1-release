package routes;

import dao.DAOFactory;
import dao.ProjectDAO;
import domain.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("projects")
public class Projects {
    @GET
    @Produces("application/json")
    public List<Project> getProjects() {
        ProjectDAO projectDAO = DAOFactory.getInstance().getProjectDAO();

        return projectDAO.getAll();
    }


    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response postProject(Project project) {
        ProjectDAO projectDAO = DAOFactory.getInstance().getProjectDAO();
        Project built;
        try {
            built = projectDAO.addOne(project);
        } catch (SQLException exception) {
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
        Project built;

        try {
            projectDAO.updateOne(project);
        } catch (SQLException exception) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).entity(project).build();
    }

    @DELETE
    @Consumes("application/json")
    public Response deleteProject(Project project) {
        ProjectDAO projectDAO = DAOFactory.getInstance().getProjectDAO();

        try {
            projectDAO.deleteOne(project);
        } catch (SQLException exception) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).build();
    }
}
