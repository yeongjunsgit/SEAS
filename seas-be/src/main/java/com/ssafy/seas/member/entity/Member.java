package com.ssafy.seas.member.entity;

import com.ssafy.seas.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {
	@Column(name = "member_id", nullable = false, unique = true)
	private String memberId;
	@Column(name = "pwd", nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, unique = true)
	private String email;
	private Integer point = 0;

}
