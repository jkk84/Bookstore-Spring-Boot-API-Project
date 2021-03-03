package pl.bookstore.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bookstore.restapi.model.CategoryEntity;
import pl.bookstore.restapi.service.CategoryService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryEntity> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping(params = {"categoryId"})
    public ResponseEntity<CategoryEntity> getCategory(@RequestParam long categoryId) {
        return categoryService.getCategory(categoryId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoryEntity> addCategory(@RequestBody CategoryEntity categoryEntity) {
        return ResponseEntity.ok(categoryService.addCategory(categoryEntity));
    }

    @PutMapping
    public ResponseEntity<CategoryEntity> updateCategory
            (@RequestBody CategoryEntity categoryEntity, @RequestParam long categoryId) {
        return categoryService.updateCategory(categoryEntity, categoryId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public void deleteCategory(@RequestParam long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
