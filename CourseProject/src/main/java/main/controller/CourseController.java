package main.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import main.model.BaseModel;
import main.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.service.CourseService;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    @PostMapping(path = "/courses", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> addCourses(String title, String description, String difficulty, String released, String thumbnailUrl,
                                           @RequestParam long categoryId) {
        log.debug("REST request to add Course");
        Course course = new Course(title, description, difficulty, LocalDate.parse(released), thumbnailUrl);
        boolean isSuccessful = courseService.addCourse(course, categoryId);
        if (!isSuccessful) {
            return ResponseEntity.notFound().build();
        }

        URI uri = WebMvcLinkBuilder.linkTo(getClass()).slash("courses").slash(course.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Update a course", operationId = "updateCourse")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Course was updated"),
            @ApiResponse(responseCode = "500", description = "Something went wrong"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @PutMapping("/courses/{id}")
    public ResponseEntity<Void> updateCourse(@RequestBody Course course, @PathVariable Long id, @RequestParam long categoryId) {
        log.debug("REST request to update Course");
        if (courseService.updateCourse(course, id, categoryId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
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
        if (courseService.patchCourse(course, id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
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

    /**
     * {@code GET  /evaluation/{evaluationId} : get course by evaluation id
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courses in body.
     */
    @GetMapping("/course/evaluation/{evaluationId}")
    public ResponseEntity<Course> getCourseByEvaluationId(@PathVariable long evaluationId) {
        log.debug("REST request to get course  by evaluation id");
        Optional<Course> course = courseService.getCourseByEvaluationId(evaluationId);
        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /category/{categoryId} : get courses by category id
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courses in body.
     */
    @GetMapping("/courses/category/{categoryId}")
    public ResponseEntity<List<Course>> getCoursesByCategoryId(@PathVariable long categoryId) {
        log.debug("REST request to get courses  by category id");
        List<Course> courses = courseService.getCourseByCategoryId(categoryId);
        if (courses == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(courses);
        }
    }

    @Operation(summary = "Search tasks", operationId = "getCourses")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found courses",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Object[].class))}
            ),
            @ApiResponse(responseCode = "204", description = "No courses found")
    })
    @GetMapping("/courses/complex")
    public ResponseEntity<List<Object>> getCourses (
                                                  @RequestHeader(required = false, name="X-Fields") String fields,
                                                  @RequestHeader(required = false, name="X-Sort") String sort) {
        List<Course> courses = courseService.getCourses();
        if(courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            if(sort != null && !sort.isBlank()) {
                courses = courses.stream().sorted((first, second) -> BaseModel.sorter(sort).compare(first, second)).collect(Collectors.toList());
            }
            List<Object> items;
            if(fields != null && !fields.isBlank()) {
                items = courses.stream().map(course -> course.sparseFields(fields.split(","))).collect(Collectors.toList());
            } else {
                items = new ArrayList<>(courses);
            }
            return ResponseEntity.ok(items);
        }
    }

}
