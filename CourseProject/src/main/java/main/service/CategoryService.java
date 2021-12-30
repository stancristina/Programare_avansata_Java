package main.service;

import main.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.repository.CategoryRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategory(Long id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findById(id);
    }

    public Category addCategory(Category category) {
        log.debug("Request to add Category : {}", category);
        categoryRepository.save(category);
        return category;
    }

    public boolean updateCategory(Category category, Long id) {
        log.debug("Request to update Category : {}", category);
        if (categoryRepository.findById(id).isPresent()) {
            category.setId(id);
            categoryRepository.save(category);
            return true;
        } else {
            return false;
        }
    }

    public boolean patchCategory(Category category, Long id) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isPresent()) {
            existingCategory.get().patch(category);
            categoryRepository.save(existingCategory.get());
            return true;
        } else {
            return false;
        }
    }

    public void deleteCategory(Long id) throws IOException {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
    }
}
