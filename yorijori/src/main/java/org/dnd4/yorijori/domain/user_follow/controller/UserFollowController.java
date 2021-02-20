package org.dnd4.yorijori.domain.user_follow.controller;

import java.util.List;

import org.dnd4.yorijori.domain.recipe.dto.ResponseDto;
import org.dnd4.yorijori.domain.recipe.dto.UserDto;
import org.dnd4.yorijori.domain.user.entity.User;
import org.dnd4.yorijori.domain.user.repository.UserRepository;
import org.dnd4.yorijori.domain.user_follow.service.UserFollowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserFollowController {

	private final UserFollowService userFollowService;
	private final UserRepository userRepository;

	@GetMapping("/user/{userId}/follower")
	public List<UserDto> followerList(@PathVariable Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + userId));
		return userFollowService.followerList(user);
	}

	@GetMapping("/user/{userId}/following")
	public List<UserDto> followingList(@PathVariable Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + userId));
		return userFollowService.followingList(user);
	}

	@PostMapping("/user/{followingId}/follow/{followerId}")
	public void follow(@PathVariable Long followingId, @PathVariable Long followerId) {
		userFollowService.follow(followingId, followerId);
	}

	@PostMapping("/user/{followingId}/unfollow/{followerId}")
	public void unfollow(@PathVariable Long followingId, @PathVariable Long followerId) {
		userFollowService.unfollow(followingId, followerId);
	}

	@GetMapping("/user/{userId}/followerFeed")
	public List<ResponseDto> followerFeed(@PathVariable Long userId,
			@RequestParam(required = false, defaultValue = "10") int limit,
			@RequestParam(required = false, defaultValue = "0") int offset) {
		List<ResponseDto> result = userFollowService.followerFeed(userId, limit, offset);
		return result;
	}

	@GetMapping("/user/{userId}/followingFeed")
	public List<ResponseDto> followingFeed(@PathVariable Long userId,
			@RequestParam(required = false, defaultValue = "10") int limit,
			@RequestParam(required = false, defaultValue = "0") int offset) {
		List<ResponseDto> result = userFollowService.followingFeed(userId, limit, offset);
		return result;
	}

}
