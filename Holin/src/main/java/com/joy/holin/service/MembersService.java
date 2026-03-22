package com.joy.holin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.joy.holin.dto.MemberRegisterDto;
import com.joy.holin.entity.Members;
import com.joy.holin.repo.MemberRepo;

@Service
public class MembersService {

	@Autowired
	private MemberRepo membersRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Members register(MemberRegisterDto registerDto) {

		if (membersRepo.existsByEmail(registerDto.getEmail())) {
			throw new RuntimeException("此信箱已被註冊過");
		}

		Members members = new Members();
		members.setEmail(registerDto.getEmail());
		members.setPwd(passwordEncoder.encode(registerDto.getPwd()));
		members.setName(registerDto.getName());
		members.setPhone(registerDto.getPhone());
		members.setAddress(registerDto.getAddress());
		return membersRepo.save(members);
	}

	public Members verifyLogin(String email, String pwd) {
		Members members = membersRepo.findByEmail(email).orElse(null);

		if (members == null) {
			return null;
		}

		if (!passwordEncoder.matches(pwd, members.getPwd())) {
			return null;
		}

		return members;
	}

}
