package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.ErrorDetail;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.StudentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController
{
    @Autowired
    private StudentService studentService;

    // Please note there is no way to add students to course yet!
    @ApiOperation(value = "Returns a list of All Students",
                  response = Student.class,
                  responseContainer = "List")
    @GetMapping(value = "/allstudents",
                produces = {"application/json"})
    public ResponseEntity<?> listAllStudents(Pageable pageable)
    {
        List<Student> myStudents = studentService.findAll(pageable);
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns a list of All Students based on Paging and Sorting",
                  response = Student.class,
                  responseContainer = "List")
    @ApiImplicitParams({@ApiImplicitParam(name = "page",
                                          dataType = "integer",
                                          paramType = "query",
                                          value = "Results page you want to retrieve(0..N)"), @ApiImplicitParam(name = "size",
                                                                                                                dataType = "integer",
                                                                                                                paramType = "query",
                                                                                                                value = "Number of Records per page"), @ApiImplicitParam(name = "sort",
                                                                                                                                                                         allowMultiple = true,
                                                                                                                                                                         dataType = "string",
                                                                                                                                                                         paramType = "query",

                                                                                                                                                                         value = "Sorting criteria in the format: property(,asc|desc).  Default sort order is ascending.  Multiple sort criteria are supported")})

    @GetMapping(value = "/students",
                produces = {"application/json"})
    public ResponseEntity<?> listAllStudentsSorted(
            @PageableDefault(page = 0,
                             size = 5)
                    Pageable pageable)
    {
        List<Student> myStudents = studentService.findAll(pageable);
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Student by Student Id",
                  response = Student.class)
    @ApiResponses({@ApiResponse(code = 200,
                                message = "Student Found",
                                response = Student.class), @ApiResponse(code = 404,
                                                                        message = "Student Not Found",
                                                                        response = ErrorDetail.class)})
    @GetMapping(value = "/Student/{StudentId}",
                produces = {"application/json"})
    public ResponseEntity<?> getStudentById(
            @ApiParam(name = "Student Id",
                      required = true,
                      example = "1")
            @PathVariable
                    Long StudentId)
    {
        Student r = studentService.findStudentById(StudentId);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @ApiOperation(value = "Find Student by Name containing supplied name variable",
                  response = Student.class,
                  responseContainer = "List")
    @ApiResponses({@ApiResponse(code = 200,
                                message = "Student Found",
                                response = Student.class), @ApiResponse(code = 404,
                                                                        message = "Student Not Found",
                                                                        response = ErrorDetail.class)})
    @GetMapping(value = "/student/namelike/{name}",
                produces = {"application/json"})
    public ResponseEntity<?> getStudentByNameContaining(
            @ApiParam(name = "Name",
                      required = true,
                      example = "Don")
            @PathVariable
                    String name)
    {
        List<Student> myStudents = studentService.findStudentByNameLike(name);
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a New Student",
                  notes = "The newly created student id will be sent in the location header",
                  response = void.class)
    @ApiResponses({@ApiResponse(code = 201,
                                message = "Student Created",
                                response = void.class),
                          @ApiResponse(code = 500,
                                       message = "Error creating Student",
                                       response = ErrorDetail.class)})
    @PostMapping(value = "/Student",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewStudent(@Valid
                                           @RequestBody
                                                   Student newStudent) throws URISyntaxException
    {
        newStudent = studentService.save(newStudent);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newStudentURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{Studentid}")
                .buildAndExpand(newStudent.getStudid())
                .toUri();
        responseHeaders.setLocation(newStudentURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value="Update the student with the provided Student id", response = void.class)
    @PutMapping(value = "/Student/{Studentid}")
    public ResponseEntity<?> updateStudent(
            @RequestBody
                    Student updateStudent,
            @ApiParam(name="Student id", required = true, example = "1")
            @PathVariable
                    long Studentid)
    {
        studentService.update(updateStudent, Studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="Delete a Student with the provided Student Id", response=void.class)
    @DeleteMapping("/Student/{Studentid}")
    public ResponseEntity<?> deleteStudentById(
            @ApiParam(name="Student Id", required = true, example = "1")
            @PathVariable
                    long Studentid)
    {
        studentService.delete(Studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
