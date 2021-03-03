package softuni.boardgames.service.impl;

import org.springframework.stereotype.Service;
import softuni.boardgames.model.entity.CategoryEntity;
import softuni.boardgames.model.enums.GameCategoriesEnum;
import softuni.boardgames.repository.CategoryRepository;
import softuni.boardgames.service.CategoryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void initCategories() {
        if(categoryRepository.count() == 0){
            List<CategoryEntity> categoryEntities = new ArrayList<>();
            GameCategoriesEnum[] categories = GameCategoriesEnum.values();
            Arrays.stream(categories)
                    .forEach(c -> categoryEntities.add(new CategoryEntity(c)));
            categoryEntities.forEach(categoryRepository::save);
        }

    }
}
