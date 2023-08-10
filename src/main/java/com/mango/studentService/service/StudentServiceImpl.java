package com.mango.studentService.service;

import com.mango.studentService.exception.StudentError;
import com.mango.studentService.exception.StudentException;
import com.mango.studentService.model.Status;
import com.mango.studentService.model.Student;
import com.mango.studentService.repo.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getStudents(Status status) {
        if (status != null) {
            return studentRepository.findAllByStatus(status);
        }
        return studentRepository.findAll();
    }

    @Override
    public Student addStudent(Student student) {
        vaildateStudentEmailExists(student);
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
        if (!Status.ACTIVE.equals(student.getStatus())) {
            throw new StudentException(StudentError.STUDENT_IS_NOT_ACTIVE);
        }
        return student;
    }

    @Override
    public void deleteStudent(String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
        student.setStatus(Status.INACTIVE);
        studentRepository.save(student);
    }

    @Override
    public Student putStudent(String id, Student student) {
        return studentRepository.findById(id)
                .map(studentFromDb -> {
                    if (!studentFromDb.getEmail().equals(student.getEmail())
                            && studentRepository.existsByEmail(student.getEmail())
                    ) {
                        throw new StudentException(StudentError.STUDENT_EMAIL_ALREADY_EXISTS);
                    }
                    studentFromDb.setEmail(student.getEmail());
                    studentFromDb.setFirstName(student.getFirstName());
                    studentFromDb.setLastName(student.getLastName());
                    studentFromDb.setStatus(student.getStatus());
                    return studentRepository.save(studentFromDb);
                }).orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));

    }

    @Override
    public Student patchStudent(String id, Student student) {
        return studentRepository.findById(id)
                .map((studentFromDb -> {
                    if (!StringUtils.isEmpty(student.getFirstName())) {
                        studentFromDb.setFirstName(student.getFirstName());
                    }
                    if (!StringUtils.isEmpty(student.getLastName())) {
                        studentFromDb.setLastName(student.getLastName());
                    }
                    if (!StringUtils.isEmpty(student.getStatus())) {
                        studentFromDb.setStatus(student.getStatus());
                    }
                    return studentRepository.save(studentFromDb);
                })).orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
    }

    @Override
    public List<Student> getStudentsByEmails(List<String> emails) {
        return studentRepository.findALLByEmailIn(emails);
    }

    private void vaildateStudentEmailExists(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new StudentException(StudentError.STUDENT_EMAIL_ALREADY_EXISTS);
        }
    }
}
