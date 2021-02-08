package org.dnd4.yorijori.domain.recipe.controller;

import java.util.ArrayList;
import java.util.List;

import org.dnd4.yorijori.domain.recipe.dto.ResponseDto;
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
	public List<ResponseDto> recipeList(@RequestParam(required = false) String queryType,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue = "10") int limit,
			@RequestParam(required = false, defaultValue = "0") int offset,
			@RequestParam(required = false) String timeRange, @RequestParam(required = false) String step,
			@RequestParam(required = false, defaultValue = "0000-00-00") String startDate,
			@RequestParam(required = false, defaultValue = "9999-99-99") String endDate) {
		List<ResponseDto> result = new ArrayList<ResponseDto>();
		if (queryType == null) {
			result = recipeListService.findAll(limit, offset);
		}
		else if (queryType.equals("search")) {
			result = recipeListService.searchRecipes(keyword, limit, offset);
		}
		else if (queryType.equals("time")) {
			result = recipeListService.timeRecipes(Integer.parseInt(timeRange), limit, offset);
		}
		else if (queryType.equals("step")) {
			result = recipeListService.stepRecipes(Integer.parseInt(step), limit, offset);
		}
		else if (queryType.equals("label")) {
			result = recipeListService.labelCountDesc(limit, startDate, endDate);
		}
		return result;
	}
}