package com.pluralsight.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pluralsight.repository.util.RideRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
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
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                PreparedStatement ps = connection.prepareStatement("INSERT INTO ride  (NAME, DURATION) VALUES (?, ?)", new String[] {"id"});
                ps.setString(1, ride.getName());
                ps.setInt(2, ride.getDuration());
                return  ps;
            }
        }, key);

        Number id = key.getKey();
        return  getRide(id.intValue());
    }

    private Ride getRide(int id) {
        Ride ride = jdbcTemplate.queryForObject("select * from ride where id = ?", new RideRowMapper(), id);
        return ride;

    }

    @Override
    public List<Ride> getRides() {

        List<Ride> rides = jdbcTemplate.query("SELECT * FROM RIDE", new RideRowMapper());
        return rides;
    }
}
