package main.service;

import main.model.Category;
import main.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("Test method for add ")
    void testAddCategoryMethod() {
        // given
        Category category = new Category(6L, "Matematica", "Mate pentru pitici");
        when(categoryRepository.save(category))
                .thenReturn(category);

        // when
        Category result = categoryService.addCategory(category);

        // then
        verify(categoryRepository).save(category);
    }

    @Test
    @DisplayName("Test method for update category")
    void testUpdateCategoryMethod() {
        // given
        Long categoryId = 6L;
        Category oldCategory = new Category("Matematica", "Mate pentru pitici");
        Category newCategory = new Category("Matematica", "Matematia pentru pitici");
        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(oldCategory));
        when(categoryRepository.save(newCategory))
                .thenReturn(newCategory);

        // when
        boolean result = categoryService.updateCategory(newCategory, categoryId);

        // then
        assertTrue(result);
        verify(categoryRepository).save(newCategory);
    }

    @Test
    @DisplayName("Test method for update category when category id is invalid")
    void testUpdateCategoryMethodWhenCategoryIdIsInvalid() {
        // given
        Long categoryId = 6L;
        Category newCategory = new Category("Matematica", "Matematia pentru pitici");
        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.empty());

        // when
        boolean result = categoryService.updateCategory(newCategory, categoryId);

        // then
        assertFalse(result);
        verify(categoryRepository, times(0)).save(newCategory);
    }

    @Test
    @DisplayName("Test method to get all categories ")
    void testGetAllCategoriesMethod() {
        //given
        Long categoryId = 6L;
        Category category = new Category("Romana", "Descrp");
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        //when
        List<Category> result = categoryService.getCategories();

        //then
        assertNotNull(result);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test method to get category ")
    void testGetCategoryMethod() {
        //given
        Long categoryId = 6L;
        Category category = new Category("Romana", "Descrp");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        //when
        Optional<Category> result = categoryService.getCategory(categoryId);

        //then
        assertNotNull(result);
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    @DisplayName("Test method for patch category")
    void testPatchCategoryMethod() {
        // given
        Long categoryId = 6L;
        Category oldCategory = new Category("Matematica", "Mate pentru pitici");
        Category newCategory = new Category("Matematica", "Matematia pentru pitici");
        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(oldCategory));

        // when
        boolean result = categoryService.patchCategory(newCategory, categoryId);

        // then
        assertTrue(result);
        verify(categoryRepository, times(1)).save(oldCategory);
    }

    @Test
    @DisplayName("Test method for patch category when category id is invalid")
    void testPatchCategoryMethodWhenCategoryIdIsInvalid() {
        // given
        Long categoryId = 6L;
        Category newCategory = new Category("Matematica", "Matematia pentru pitici");
        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.empty());

        // when
        boolean result = categoryService.patchCategory(newCategory, categoryId);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("Test delete category happyPath")
    void testDeleteChapter() {
        Long categoryId = 6L;

        categoryService.deleteCategory(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

}
