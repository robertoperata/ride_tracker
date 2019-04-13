package com.pluralsight.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pluralsight.repository.util.RideRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Ride createRide(Ride ride) {
//        jdbcTemplate.update("INSERT INTO ride  (NAME, DURATION) VALUES (?, ?)", ride.getName(), ride.getDuration());
//        KeyHolder key = new GeneratedKeyHolder();
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//
//                PreparedStatement ps = connection.prepareStatement("INSERT INTO ride  (NAME, DURATION) VALUES (?, ?)", new String[] {"id"});
//                ps.setString(1, ride.getName());
//                ps.setInt(2, ride.getDuration());
//                return  ps;
//            }
//        }, key);
//
//        Number id = key.getKey();
//        return  getRide(id.intValue());

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);

        List<String> columns = new ArrayList<>();
        columns.add("name");
        columns.add("duration");

        insert.setTableName("ride");
        insert.setColumnNames(columns);

        Map<String, Object> data = new HashMap<>();
        data.put("name", ride.getName());
        data.put("duration", ride.getDuration());

        insert.setGeneratedKeyName("id");

        Number key = insert.executeAndReturnKey(data);

        return getRide(key.intValue());
    }

    @Override
    public Ride getRide(Integer id) {
        Ride ride = jdbcTemplate.queryForObject("select * from ride where id = ?", new RideRowMapper(), id);
        return ride;

    }

    @Override
    public List<Ride> getRides() {

        List<Ride> rides = jdbcTemplate.query("SELECT * FROM RIDE", new RideRowMapper());
        return rides;
    }

    @Override
    public Ride updateRide(Ride ride) {
        jdbcTemplate.update("UPDATE ride  SET name = ?, duration = ? WHERE id = ?",
                ride.getName(), ride.getDuration(),  ride.getId() );
        return ride;
    }

    @Override
    public void updateRides(List<Object[]> pairs) {
        jdbcTemplate.batchUpdate("UPDATE ride  SET ride_date = ? WHERE id = ?", pairs);
    }

    @Override
    public void deleteRide(Integer id) {
        jdbcTemplate.update("DELETE FROM ride where id = ?", id );
    }
}
