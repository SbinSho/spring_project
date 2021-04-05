package com.goCamping.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.goCamping.dao.MemberDAO;
import com.goCamping.domain.MemberVO;
import com.goCamping.dto.EncryptDTO;

@Service
public class MeberServiceImpl implements MemberService {

	private static final Logger logger = LogManager.getLogger(MemberService.class);
	
	@Autowired
	private MemberDAO mdao;
	
	
	// 사용자 비밀번호 암호화 위한 구문 spring.security... 의 BCryptPasswordEncoder를 사용
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// 아이디 중복확인
	@Override
	public int id_Check(String user_id) throws Exception {
		return mdao.id_Check(user_id);
	}

	// 닉네임 중복확인
	@Override
	public int nick_Check(String user_nickname) throws Exception {
		return mdao.nick_Check(user_nickname);
	}

	// 회원가입 처리
	@Override
	public int member_create(EncryptDTO encryptDTO) throws Exception {
		
		// 사용자에게 입력받은 비밀번호 및 메일 추출
		String rawMail = encryptDTO.getEn_userMail();
		String rawPassowrd = encryptDTO.getEn_userPwd();
		
		
		// 암호화된 메일 및 비밀번호로 변경
		encryptDTO.setEn_userPwd(passwordEncoder.encode(rawPassowrd));
		encryptDTO.setEn_userMail(passwordEncoder.encode(rawMail));
		
		logger.info("암호화 처리 완료");
		logger.info("암호화된 이메일 : "+ encryptDTO.getEn_userMail());
		logger.info("암호화된 비밀번호 : "+ encryptDTO.getEn_userPwd());
		
		// DB에 저장하기 위한 VO객체 생성 ( 개인 정보 암호화 객체 )
		MemberVO memberVO = new MemberVO(encryptDTO);
		
		return mdao.member_create(memberVO);
		
	}

	
	
	
}
