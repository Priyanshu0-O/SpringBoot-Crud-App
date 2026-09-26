package in.files.CrudApp.Service;

import in.files.CrudApp.DTO.RequestStudentDto;
import in.files.CrudApp.DTO.ResponseStudentDto;
import in.files.CrudApp.Entity.Student;
import in.files.CrudApp.Repository.StudentRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class StudentService {
    private StudentRepository studentRepository;

    StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public ResponseStudentDto createStudent(RequestStudentDto studentDto){
        Student student = new Student();

        student.setId(studentDto.getId());
        student.setName(studentDto.getName());
        student.setAge(studentDto.getAge());
        student.setEmail(studentDto.getEmail());
        student.setRollNo(studentDto.getRollNo());
        student.setSubject(studentDto.getSubject());
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(null);
        student.setDeleted(false);

        studentRepository.save(student);
        ResponseStudentDto studentResp = new ResponseStudentDto(student);

        return studentResp;
    }

    //get
    public ResponseStudentDto getStudent(Long id){
        if(!studentRepository.existsById(id)) return null;
        if(studentRepository.getReferenceById(id).getDeleted())
            return null;
        Student student = studentRepository.getReferenceById(id);
        ResponseStudentDto studentDto = new ResponseStudentDto(student);
        return studentDto;
    }

    //update
    public ResponseStudentDto studentUpdate(@NonNull RequestStudentDto s){
        if(!studentRepository.existsById(s.getId())) return null;
        if(studentRepository.getReferenceById(s.getId()).getDeleted())
            return null;
        Student student = studentRepository.getReferenceById(s.getId());
        student.setName(s.getName());
        student.setAge(s.getAge());
        student.setEmail(s.getEmail());
        student.setRollNo(s.getRollNo());
        student.setSubject(s.getSubject());
        student.setUpdatedAt(LocalDateTime.now());

        studentRepository.save(student);
        ResponseStudentDto studenResp = new ResponseStudentDto(student);

        return studenResp;
    }

    //soft-delete
    public Boolean studentSoftDel(Long id){
        if(!studentRepository.existsById(id)) return null;

        Student s = studentRepository.getReferenceById(id);
        s.setDeleted(true);
        studentRepository.save(s);

        return true;
    }
}
