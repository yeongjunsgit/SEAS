package com.ssafy.seas.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.entity.Member;
import com.ssafy.seas.member.mapper.MemberMapper;
import com.ssafy.seas.member.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;

	@Transactional
	public String signup(MemberDto.Post memberDto) {
		memberRepository.save(memberMapper.MemberDtoToMember(memberDto));
		return "";
	}

	@Transactional
	public MemberDto.Response signin(MemberDto.Post memberDto) {
		Member member = memberRepository.findByMemberIdAndPassword(memberDto.getMemberId(), memberDto.getPassword());
		if (member == null) {
			return new MemberDto.Response();
		}
		return memberMapper.MemberToMemberDtoResponse(member);
	}

	public String findMemberNicknameByMemberid(String memberId) {
		Member member = memberRepository.findByMemberId(memberId)
			.orElseThrow(() -> new RuntimeException("일치하는 사용자가 없습니다 !!!"));
		return memberMapper.MemberToMemberDtoResponse(member)
			.getNickname();
	}

	/**
	 * 로그인 id 중복 검사
	 * @param memberId : 로그인할 때 쓰는 아이디
	 * @return boolean 중복 여부. true면 중복(가입불가), false면 중복 없음 (가입 가능)
	 */
	public MemberDto.checkIdResult isDuplicatedId(String memberId) {
		Optional<Member> member = memberRepository.findByMemberId(memberId);
		if (member.isPresent()) {
			return new MemberDto.checkIdResult(true);
		}
		return new MemberDto.checkIdResult(false);

	}
}
