package dao.sql;

import dao.UserStoryDAO;
import domain.UserStory;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class SQLUserStoryDAO extends SQLDAO<UserStory> implements UserStoryDAO {

    @Override
    protected UserStory createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new UserStory(
            getInteger(resultSet, "project"),
            resultSet.getString("description"),
            resultSet.getString("priority"),
            getInteger(resultSet, "difficulty"),
            getInteger(resultSet, "sprint"),
            getInteger(resultSet, "id")
        );
    }

    @Override
    public UserStory getById(int projectId, int id) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=? AND id=?";

        List<Object> opt = Arrays.asList(projectId, id);

        return queryFirstObject(statement, opt);
    }

    @Override
    public List<UserStory> getBySprint(int projectId, int sprintId) throws Exception {
        String statement = "SELECT * FROM us WHERE project=? AND sprint=?";

        List<Object> opt = Arrays.asList(projectId, sprintId);

        return queryAllObjects(statement, opt);
    }

    @Override
    public List<UserStory> getAllForProject(int projectId) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=?";

        List<Object> opt = Arrays.asList(projectId);

        return queryAllObjects(statement, opt);
    }

    @Override
    public List<UserStory> getUnreleasedForProject(int projectId) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=? AND id NOT IN (SELECT us FROM release_us WHERE project = ?)";

        List<Object> opt = Arrays.asList(projectId, projectId);

        return queryAllObjects(statement, opt);
    }

    @Override
    public UserStory insert(UserStory us) throws SQLException {
        if (us.id != null)
            throw new SQLException("This us has an id, cannot insert.");

        String statement = "{CALL insert_us(?, ?, ?, ?, ?, @id)}";

        List<Object> opt = Arrays.asList(
                us.projectId,
                us.description,
                us.priority,
                us.difficulty,
                us.sprint
        );

        return new UserStory(us.projectId, us.description, us.priority, us.difficulty, us.sprint, doInsert(statement, opt));
    }

    @Override
    public void update(UserStory us) throws SQLException {
        if (us.id == null)
            throw new SQLException("This us doesn't have an id, cannot update.");

        String statement = "UPDATE us SET description = ?, priority = ?, difficulty = ?, sprint = ? WHERE project = ? AND id = ?";

        List<Object> opt = Arrays.asList(
                us.description,
                us.priority,
                us.difficulty,
                us.sprint,
                us.projectId,
                us.id
        );

        SQLDatabase.exec(statement, opt);
    }

    @Override
    public void delete(UserStory us) throws SQLException {
        if (us.id == null)
            throw new SQLException("This us doesn't have an id, cannot delete");

        String statement = "UPDATE task SET us = null WHERE project = ? AND us = ?";

        List<Object> opt = Arrays.asList(us.projectId, us.id);

        SQLDatabase.exec(statement, opt);

        statement = "DELETE FROM us WHERE project = ? AND id = ?";

        opt = Arrays.asList(us.projectId, us.id);

        SQLDatabase.exec(statement, opt);
    }

    @Override
    public List<UserStory> getByPriority(int projectId, String priority) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=? AND priority = ?";

        List<Object> opt = Arrays.asList(projectId, priority);

        return queryAllObjects(statement, opt);
    }

    @Override
    public List<UserStory> getByDifficulty(int projectId, int difficulty) throws SQLException {
        String statement = "SELECT * FROM us WHERE project=? AND difficulty = ?";

        List<Object> opt = Arrays.asList(projectId, difficulty);

        return queryAllObjects(statement, opt);
    }
}
