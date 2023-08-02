package com.mango.studentService.controller;

import com.mango.studentService.model.Student;
import com.mango.studentService.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable String id) {
        return studentService.getStudent(id);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
    }

    @PutMapping("/{id}")
    public Student putStudent(@PathVariable String id, @Valid @RequestBody Student student) {
        return studentService.putStudent(id, student);
    }

    @PatchMapping("/{id}")
    public Student patchStudent(@PathVariable String id, @Valid @RequestBody Student student) {
        return studentService.patchStudent(id, student);
    }

}
