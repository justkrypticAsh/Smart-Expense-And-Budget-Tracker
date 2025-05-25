package test;

import models.Category;
import services.CategoryService;

import java.util.List;

public class TestCategoryService {
    public static void main(String[] args) {
        CategoryService categoryService = new CategoryService();

        // Add a new category
        Category newCategory = new Category(0, "Food");
        categoryService.addCategory(newCategory);

        // Get all categories
        List<Category> categories = categoryService.getAllCategories();
        for (Category category : categories) {
            System.out.println("ID: " + category.getId() + ", Name: " + category.getName());
        }
    }
}
