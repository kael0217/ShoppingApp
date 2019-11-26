package com.levent.pcd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.levent.pcd.model.Tailors;

@Repository("tailorRepository")
public interface TailorsRepository extends MongoRepository<Tailors, String> {

}
