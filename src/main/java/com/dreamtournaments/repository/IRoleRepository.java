package com.dreamtournaments.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dreamtournaments.models.ERole;
import com.dreamtournaments.models.Role;


@Repository
public interface IRoleRepository extends MongoRepository<Role, String>{

	Optional<Role> findByName(ERole name);
}
