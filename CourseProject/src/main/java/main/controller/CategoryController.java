package main.controller;

import main.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.service.CategoryService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;


/**
 * REST main.controller for managing {@link Category}.
 */
@RestController
@RequestMapping("/api")
public class CategoryController {

    private final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    public CategoryService categoryService;

    /**
     * {@code GET  /categories} : get all the categories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Category>> getCategories() {
        log.debug("REST request to get all Categories");
        Collection<Category> categories = categoryService.getCategories();
        if (!categories.isEmpty()) {
            return ResponseEntity.ok(categories);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * {@code GET  /categories/:id} : get the "id" category.
     *
     * @param id the id of the categoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping(value = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> getCategory(@PathVariable("id") long id) {
        log.debug("REST request to get a Category");
        Optional<Category> category = categoryService.getCategory(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code POST  /categories} : Create a new category.
     *
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new category,
     * or with status {@code 400 (Bad Request)} if the category has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(path = "/categories", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> addCategory(String title, String description) throws URISyntaxException {
        log.debug("REST request to add Category");
        Category category = new Category(title, description);
        categoryService.addCategory(category);
        URI uri = WebMvcLinkBuilder.linkTo(getClass()).slash("categories").slash(category.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * {@code PUT  /categories} : Updates an existing category.
     *
     * @param category the category to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated category,
     * or with status {@code 400 (Bad Request)} if the category is not valid,
     * or with status {@code 500 (Internal Server Error)} if the category couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping(path = "/categories/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable("id") long id, @RequestBody Category category) throws URISyntaxException {
        log.debug("REST request to update Category");
        Optional<Category> existingCategory = categoryService.getCategory(id);
        if (existingCategory.isPresent()) {
            existingCategory.get().update(category);
            categoryService.updateCategory(existingCategory.get(), id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@code DELETE  /categories/:id} : delete the "id" category.
     *
     * @param id the id of the categoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping(path = "/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws IOException {
        log.debug("REST request to delete Category");
        Optional<Category> existingCategory = categoryService.getCategory(id);
        if (existingCategory.isPresent()) {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
