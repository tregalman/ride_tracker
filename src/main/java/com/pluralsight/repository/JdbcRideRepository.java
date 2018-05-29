package com.pluralsight.repository;

import com.pluralsight.model.Ride;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRideRepository implements RideRepository {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public JdbcRideRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Ride createRide(Ride ride) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement preparedStatement = connection
          .prepareStatement("INSERT INTO RIDE (ID, NAME, DURATION) VALUES(DEFAULT,?,?)",
              new String[]{"id"});
      preparedStatement.setString(1, ride.getName());
      preparedStatement.setInt(2, ride.getDuration());
      return preparedStatement;
    }, keyHolder);
    return getRide(keyHolder.getKey());
  }

  private Ride getRide(Number id) {
    return jdbcTemplate
        .queryForObject("SELECT ID, NAME, DURATION FROM RIDE WHERE ID = ?", new RideRowMapper(),
            id);
  }

  @Override
  public List<Ride> getRides() {
    return jdbcTemplate.query("SELECT * FROM RIDE", new RideRowMapper());
  }

  private class RideRowMapper implements RowMapper<Ride> {

    @Override
    public Ride mapRow(ResultSet resultSet, int i) throws SQLException {
      return Ride.builder()
          .withName(resultSet.getString("name"))
          .withDuration(resultSet.getInt("duration"))
          .withId(resultSet.getInt("id"))
          .build();
    }
  }
}
