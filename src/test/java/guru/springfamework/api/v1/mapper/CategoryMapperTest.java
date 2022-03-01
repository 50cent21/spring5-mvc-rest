package guru.springfamework.api.v1.mapper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;

public class CategoryMapperTest {

	private static final long ID = 1L;
	private static final String NAME = "Joe";
	
	CategoryMapper categoryMapper = CategoryMapper.INSTANCE;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCategoryToCategoryDTO() throws Exception{
		
		//given
		Category category = new Category();
		category.setName(NAME);
		category.setId(ID);
		
		//when
		CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
		
		//then
		assertEquals(Long.valueOf(ID), categoryDTO.getId());
		assertEquals(NAME, categoryDTO.getName());
	}

}
