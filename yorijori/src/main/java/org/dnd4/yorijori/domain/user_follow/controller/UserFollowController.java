package org.dnd4.yorijori.domain.user_follow.controller;

import java.util.List;

import org.dnd4.yorijori.domain.common.ResultList;
import org.dnd4.yorijori.domain.recipe.dto.ResponseDto;
import org.dnd4.yorijori.domain.recipe.dto.UserDto;
import org.dnd4.yorijori.domain.user.entity.User;
import org.dnd4.yorijori.domain.user.repository.UserRepository;
import org.dnd4.yorijori.domain.user_follow.service.UserFollowService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
	@GetMapping("/user/{userId}/followers")
	@PreAuthorize("#userId==principal.id")
	public ResultList<UserDto> followerList(@PathVariable Long userId,
			@RequestParam(required = false, defaultValue = "10") int limit,
			@RequestParam(required = false, defaultValue = "0") int offset) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + userId));
		return new ResultList<UserDto> (userFollowService.followerList(user, limit, offset));
	}

	@GetMapping("/user/{userId}/followings")
	@PreAuthorize("#userId==principal.id")
	public ResultList<UserDto> followingList(@PathVariable Long userId,
			@RequestParam(required = false, defaultValue = "10") int limit,
			@RequestParam(required = false, defaultValue = "0") int offset) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + userId));
		return new ResultList<UserDto> (userFollowService.followingList(user, limit, offset));
	}

	@PostMapping("user/{followerId}/follow/{followingId}")
	@PreAuthorize("#followerId==principal.id")
	public void follow(@PathVariable Long followingId, @PathVariable Long followerId) {
		userFollowService.follow(followingId, followerId);
	}

	@DeleteMapping("/user/{followerId}/follow/{followingId}")
	@PreAuthorize("#followerId==principal.id")
	public void unfollow(@PathVariable Long followingId, @PathVariable Long followerId) {
		userFollowService.unfollow(followingId, followerId);
	}
	
	@PostMapping("/user/{userId}/following/{followingId}/alarm")
	@PreAuthorize("#userId==principal.id")
	public void followingAlarmOn(@PathVariable Long userId, @PathVariable Long followingId) {
		userFollowService.followingAlarmOn(userId, followingId);
	}
	
	@DeleteMapping("/user/{userId}/following/{followingId}/alarm")
	@PreAuthorize("#userId==principal.id")
	public void followingAlarmOff(@PathVariable Long userId, @PathVariable Long followingId) {
		userFollowService.followingAlarmOff(userId, followingId);
	}
	
	@PostMapping("/user/{userId}/follower/{followerId}/alarm")
	@PreAuthorize("#userId==principal.id")
	public void followerAlarmOn(@PathVariable Long userId, @PathVariable Long followerId) {
		userFollowService.followerAlarmOn(userId, followerId);
	}
	
	@DeleteMapping("/user/{userId}/follower/{followerId}/alarm")
	@PreAuthorize("#userId==principal.id")
	public void followerAlarmOff(@PathVariable Long userId, @PathVariable Long followerId) {
		userFollowService.followerAlarmOff(userId, followerId);
	}
	
	@GetMapping("/user/{userId}/followerFeeds")
	@PreAuthorize("#userId==principal.id")
	public ResultList<ResponseDto> followerFeed(@PathVariable Long userId,
			@RequestParam(required = false, defaultValue = "10") int limit,
			@RequestParam(required = false, defaultValue = "0") int offset) {
		List<ResponseDto> result = userFollowService.followerFeed(userId, limit, offset);
		return new ResultList<ResponseDto>(result);
	}

	@GetMapping("/user/{userId}/followingFeeds")
	@PreAuthorize("#userId==principal.id")
	public ResultList<ResponseDto> followingFeed(@PathVariable Long userId,
			@RequestParam(required = false, defaultValue = "10") int limit,
			@RequestParam(required = false, defaultValue = "0") int offset) {
		List<ResponseDto> result = userFollowService.followingFeed(userId, limit, offset);
		return new ResultList<ResponseDto>(result);
	}

}