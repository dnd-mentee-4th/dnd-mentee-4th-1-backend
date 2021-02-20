package org.dnd4.yorijori.domain.monthly_view.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.dnd4.yorijori.domain.recipe.dto.ResponseDto;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.dnd4.yorijori.domain.recipe.service.RecipeService;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonthlyViewService {
	
	@Resource(name = "redisTemplate")
	private ZSetOperations<String, Long> zSetOperations;
	private final RecipeService recipeService;
	
	public void visit(Long recipe_id){
		zSetOperations.incrementScore("view", recipe_id, 1);
    }
	
	public List<ResponseDto> rank(int top) {
		List<Recipe> result = new ArrayList<>();
		Set<Long> recipes = zSetOperations.reverseRange("view", 0, top - 1);
		for (Long rid : recipes) {
			result.add(recipeService.get(rid));
		}
		return result.stream()
				.map(ResponseDto::new).collect(Collectors.toList());
	}
	
}
