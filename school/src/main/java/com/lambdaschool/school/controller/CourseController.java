package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.service.CourseService;
import com.lambdaschool.school.view.CountStudentsInCourses;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/courses")
public class CourseController
{
    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "Return all courses using Paging and Sorting",
                  response = Course.class,
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
    @ApiResponses({@ApiResponse(code=200, message="Courses found")})
    @GetMapping(value = "/courses",
                produces = {"application/json"})
    public ResponseEntity<?> listAllCoursesSorted(
            @PageableDefault(page = 0,
                             size = 5)
                    Pageable pageable)
    {
        ArrayList<Course> myCourses = courseService.findAll(pageable);
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    @ApiOperation(value="Returns all courses", response = Course.class, responseContainer = "List")
    @GetMapping(value = "/allcourses",
                produces = {"application/json"})
    public ResponseEntity<?> listAllCourses(Pageable pageable)
    {
        ArrayList<Course> myCourses = courseService.findAll(pageable);
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }
    @ApiOperation(value = "Returns Courses and Number of Students enrolled", response = CountStudentsInCourses.class, responseContainer = "List")
    @GetMapping(value = "/studcount",
                produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses()
    {
        return new ResponseEntity<>(courseService.getCountStudentsInCourse(), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a course with the provided course id", response = void.class)
    @DeleteMapping("/courses/{courseid}")
    public ResponseEntity<?> deleteCourseById(
            @ApiParam(value = "Course Id", required = true, example="1")
            @PathVariable
                    long courseid)
    {
        courseService.delete(courseid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
