package org.dnd4.yorijori.domain.label.service;

import java.util.List;
import java.util.stream.Collectors;

import org.dnd4.yorijori.domain.label.entity.Label;
import org.dnd4.yorijori.domain.label.repository.LabelDslRepository;
import org.dnd4.yorijori.domain.label.repository.LabelRepository;
import org.dnd4.yorijori.domain.recipe.dto.ResponseDto;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.dnd4.yorijori.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LabelService {

	private final LabelRepository labelRepository;
	private final LabelDslRepository labelDslRepository;

	@Transactional
	public void add(User user, Recipe recipe) {
		Label entity = Label.builder().user(user).recipe(recipe).build();
		labelRepository.save(entity);
	}

	@Transactional
	public void delete(User user, Recipe recipe) {
		Label entity = labelRepository.findByUserIdAndRecipeId(user.getId(), recipe.getId());
		labelRepository.delete(entity);
	}

	public List<ResponseDto> labelList(User user, int limit, int offset) {
		return labelDslRepository.labelList(user, limit, offset).stream().map(ResponseDto::new)
				.collect(Collectors.toList());
	}
}
