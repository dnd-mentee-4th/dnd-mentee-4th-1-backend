package org.dnd4.yorijori.domain.recipe.controller;

import java.util.ArrayList;
import java.util.List;

import org.dnd4.yorijori.domain.recipe.dto.RecipeListDto;
import org.dnd4.yorijori.domain.recipe.service.RecipeListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class RecipeListController {

	private final RecipeListService recipeListService;

	@GetMapping("/recipes")
	public List<RecipeListDto> recipeList(@RequestParam(required = false) String queryType,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue = "0") int limit,
			@RequestParam(required = false, defaultValue = "10") int offset) {
		List<RecipeListDto> result = new ArrayList<RecipeListDto>();
		if (queryType == null) {
			result = recipeListService.findAll(limit, offset);
		} else if (queryType.equals("search")) {
			result = recipeListService.searchRecipes(keyword);
		}
		return result;
	}
}