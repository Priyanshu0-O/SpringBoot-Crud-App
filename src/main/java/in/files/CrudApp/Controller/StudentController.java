package in.files.CrudApp.Controller;

import in.files.CrudApp.DTO.RequestStudentDto;
import in.files.CrudApp.DTO.ResponseStudentDto;
import in.files.CrudApp.Entity.Student;
import in.files.CrudApp.Service.StudentService;
import org.apache.coyote.Response;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseStudentDto> createStudent(@RequestBody RequestStudentDto studentDto) {
//        System.out.println("Inside Student Controller");
        ResponseStudentDto createdStudent = studentService.createStudent(studentDto);
//        System.out.println("Exiting Student Controller");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }

    // read student
    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseStudentDto> getStudentById(@PathVariable Long id){
        ResponseStudentDto student = studentService.getStudent(id);
        if(student == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(student);
    }
    // update student
    @PutMapping("/update")
    public ResponseEntity<ResponseStudentDto> update(@RequestBody RequestStudentDto studentDto){
        ResponseStudentDto s = studentService.studentUpdate(studentDto);
        if(s == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(s);
    }
    // delete student
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteSoft(@PathVariable Long id){
        if (studentService.studentSoftDel(id)) return ResponseEntity.ok(true);

        return ResponseEntity.badRequest().build();
    }
}
