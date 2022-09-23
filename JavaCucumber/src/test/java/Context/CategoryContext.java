package Context;

import Models.Category.Request.CreateCategoryDto;

public class CategoryContext {
    private CreateCategoryDto createCategoryDto;

    public CategoryContext(CreateCategoryDto createCategoryDto) {
        this.createCategoryDto = createCategoryDto;
    }

    public CreateCategoryDto getCreateCategoryDto() {
        return createCategoryDto;
    }
}
