package main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import main.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.service.CourseService;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class CourseController {

    private final Logger log = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    public CourseService courseService;

    @GetMapping(path = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Course>> getCourses() {
        log.debug("REST request to get all Courses");
        Collection<Course> courses = courseService.getCourses();
        return courses.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(courses);
    }

    @Operation(summary = "Get a course", operationId = "getCourse")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found course",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Object.class))}
            ),
            @ApiResponse(responseCode = "404", description = "No course found")
    })
    @GetMapping("/courses/{id}")
    public ResponseEntity<Object> getCoursesById(@PathVariable Long id, @RequestHeader(required = false, name = "X-Fields") String fields) {
        Optional<Course> course = courseService.getCourse(id);
        if (course.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            if (fields != null && !fields.isBlank()) {
                return ResponseEntity.ok(course.get().sparseFields(fields.split(",")));
            } else {
                return ResponseEntity.ok(course.get());
            }
        }
    }

    @Operation(summary = "Create a course", operationId = "addCourse")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Course was created",
                    headers = {@Header(name = "location", schema = @Schema(type = "String"))}
            ),
            @ApiResponse(responseCode = "500", description = "Something went wrong"),
            @ApiResponse(responseCode = "204", description = "Bulk courses created")
    })
    @PostMapping("/courses")
    public ResponseEntity<Void> addCourse(@RequestBody String payload, @RequestHeader(required = false, name = "X-Action") String action) {
        log.debug("REST request to add Course");
        try {
            if ("bulk".equals(action)) {
                for (Course course : new ObjectMapper().registerModule(new JavaTimeModule()).readValue(payload, Course[].class)) {
                    courseService.addCourse(course);
                }
                return ResponseEntity.noContent().build();
            } else {
                Course course = courseService.addCourse(new ObjectMapper().registerModule(new JavaTimeModule()).readValue(payload, Course.class));
                URI uri = WebMvcLinkBuilder.linkTo(getClass()).slash(course.getId()).toUri();
                return ResponseEntity.created(uri).build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Update a course", operationId = "updateCourse")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Course was updated"),
            @ApiResponse(responseCode = "500", description = "Something went wrong"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @PutMapping("/courses/{id}")
    public ResponseEntity<Void> updateCourse(@RequestBody Course course, @PathVariable Long id) {
        log.debug("REST request to update Course");
        try {
            if (courseService.updateCourse(course, id)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Patch a course", operationId = "patchCourse")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Course was patched"),
            @ApiResponse(responseCode = "500", description = "Something went wrong"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @PatchMapping("/courses/{id}")
    public ResponseEntity<Void> patchCourse(@RequestBody Course course, @PathVariable Long id) {
        log.debug("REST request to patch Course");
        try {
            if (courseService.patchCourse(course, id)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Delete a course", operationId = "deleteCourse")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Course was deleted"),
            @ApiResponse(responseCode = "500", description = "Something went wrong"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        log.debug("REST request to delete Course");
        Optional<Course> existingCourse = courseService.getCourse(id);
        if (existingCourse.isPresent()) {
            try {
                courseService.deleteCourse(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Check a course", operationId = "checkCourse")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Course was found"),
            @ApiResponse(responseCode = "404", description = "Course was not found")
    })
    @RequestMapping(method = RequestMethod.HEAD, value = "/courses/{id}")
    public ResponseEntity checkCourse(@PathVariable Long id) {
        log.debug("REST request to check Course");
        Optional<Course> courseModel = courseService.getCourse(id);
        return courseModel.isPresent() ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
