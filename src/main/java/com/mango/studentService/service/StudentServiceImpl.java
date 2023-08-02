package com.mango.studentService.service;

import com.mango.studentService.exception.StudentError;
import com.mango.studentService.exception.StudentException;
import com.mango.studentService.model.Student;
import com.mango.studentService.repo.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(String id) {
        return studentRepository.findById(id)
                .orElseThrow(()-> new StudentException(StudentError.STUDENT_NOT_FOUND));
    }

    @Override
    public void deleteStudent(String id) {
        Student student = studentRepository.findStudentById(id)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));

        studentRepository.delete(student);
    }

    @Override
    public Student putStudent(String id, Student student) {
       return studentRepository.findById(id)
                .map(studentFromDb -> {
                    studentFromDb.setFirstName(student.getFirstName());
                    studentFromDb.setLastName(student.getLastName());
                    studentFromDb.setEmail(student.getEmail());
                    return studentRepository.save(studentFromDb);
                }).orElseThrow(()-> new StudentException(StudentError.STUDENT_NOT_FOUND));

    }

    @Override
    public Student patchStudent(String id, Student student) {
        return studentRepository.findById(id)
                .map((studentFromDb -> {
                    if(!StringUtils.isEmpty(student.getFirstName())){
                        studentFromDb.setFirstName(student.getFirstName());
                    }
                    if(!StringUtils.isEmpty(student.getLastName())){
                        studentFromDb.setLastName(student.getLastName());
                    }
                    return studentRepository.save(studentFromDb);
                })).orElseThrow(()-> new StudentException(StudentError.STUDENT_NOT_FOUND));
    }
}
