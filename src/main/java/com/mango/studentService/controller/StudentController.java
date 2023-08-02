package com.mango.studentService.controller;

import com.mango.studentService.model.Student;
import com.mango.studentService.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping("/student")
    public Student addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    @GetMapping("student")
    public List<Student> getStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("student/{id}")
    public Optional<Student> findStudentById(@PathVariable String id){
        return studentService.findStudentById(id);
    }
}
