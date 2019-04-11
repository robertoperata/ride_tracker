package com.pluralsight.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Ride createRide(Ride ride) {
		jdbcTemplate.update("INSERT INTO ride  (NAME, DURATION) VALUES (?, ?)", ride.getName(), ride.getDuration());
		return null;
	}

	@Override
	public List<Ride> getRides() {

		List<Ride> rides = jdbcTemplate.query("SELECT * FROM RIDE", new RowMapper<Ride>() {
			@Override
			public Ride mapRow(ResultSet resultSet, int i) throws SQLException {
				Ride ride = new Ride();
				ride.setId(resultSet.getInt("id"));
				ride.setName(resultSet.getString("name"));
				ride.setDuration(resultSet.getInt("duration"));
				return ride;
			}
		});

		return rides;
	}
	
}
