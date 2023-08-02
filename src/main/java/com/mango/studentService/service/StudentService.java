package com.mango.studentService.service;

import com.mango.studentService.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> getStudents();

    Student addStudent(Student student);

    Student getStudent(String id);

    void deleteStudent(String id);

    Student putStudent(String id, Student student);

    Student patchStudent(String id, Student student);

}
