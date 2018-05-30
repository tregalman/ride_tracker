package com.pluralsight.repository;

import com.pluralsight.model.Ride;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRideRepository implements RideRepository {

  private static final String NAME = "name";
  private static final String ID = "id";
  private static final String IDS = "ids";
  private static final String DURATION = "duration";
  private static final String FIND_ALL_RIDES = "SELECT ID, NAME, DURATION FROM RIDE";
  private static final String FIND_BY_ID = "SELECT ID, NAME, DURATION FROM RIDE WHERE ID = :id";
  private static final String FIND_ALL_BY_ID = "SELECT ID, NAME, DURATION FROM RIDE WHERE ID IN(:ids)";
  private static final String CREATE_RIDE = "INSERT INTO RIDE (ID, NAME, DURATION) VALUES(DEFAULT,:name,:duration)";
  private static final String UPDATE_RIDE = "UPDATE RIDE SET NAME = :name, DURATION = :duration WHERE ID = :id";
  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  public JdbcRideRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Ride create(Ride ride) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
        CREATE_RIDE,
        new MapSqlParameterSource(NAME, ride.getName())
            .addValue(DURATION, ride.getDuration()),
        keyHolder);

    return findById((Integer) keyHolder.getKey());
  }

  @Override
  public Ride update(Ride ride) {
    jdbcTemplate.update(
        UPDATE_RIDE,
        new MapSqlParameterSource(NAME, ride.getName())
            .addValue(DURATION, ride.getDuration()));

    return findById(ride.getId());
  }

  @Override
  public List<Ride> update(List<Ride> rides) {
    MapSqlParameterSource[] batchParameters = rides.stream().map(ride ->
        new MapSqlParameterSource(ID, ride.getId())
            .addValue(NAME, ride.getName())
            .addValue(DURATION, ride.getDuration()))
        .toArray(MapSqlParameterSource[]::new);
    jdbcTemplate.batchUpdate(UPDATE_RIDE, batchParameters);

    List<Integer> ids = rides.stream().map(Ride::getId).collect(Collectors.toList());

    return findById(ids);
  }

  @Override
  public List<Ride> findById(List<Integer> ids) {
    return jdbcTemplate
        .query(
            FIND_ALL_BY_ID,
            new MapSqlParameterSource(IDS, ids),
            new RideRowMapper());
  }

  @Override
  public Ride findById(int id) {
    return jdbcTemplate
        .queryForObject(
            FIND_BY_ID,
            new MapSqlParameterSource(ID, id),
            new RideRowMapper());
  }

  @Override
  public List<Ride> findAll() {
    return jdbcTemplate.query(FIND_ALL_RIDES, new RideRowMapper());
  }

  private class RideRowMapper implements RowMapper<Ride> {

    @Override
    public Ride mapRow(ResultSet resultSet, int i) throws SQLException {
      return Ride.builder()
          .withName(resultSet.getString(NAME))
          .withDuration(resultSet.getInt(DURATION))
          .withId(resultSet.getInt(ID))
          .build();
    }
  }
}
