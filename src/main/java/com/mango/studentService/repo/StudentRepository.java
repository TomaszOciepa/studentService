package com.mango.studentService.repo;

import com.mango.studentService.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    boolean existsByEmail(String email);
}
