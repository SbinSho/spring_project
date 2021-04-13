package com.goCamping.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.goCamping.dao.MemberDAO;
import com.goCamping.domain.MemberVO;
import com.goCamping.dto.MemberChIdDTO;
import com.goCamping.dto.MemberChPassDTO;
import com.goCamping.dto.MemberDeleteDTO;
import com.goCamping.dto.MemberJoinDTO;
import com.goCamping.dto.MemberLoginDTO;

@Service
public class MeberServiceImpl implements MemberService {

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
	public Boolean member_create(MemberJoinDTO MemberJoinDTO) {
		
		// 사용자에게 입력받은 비밀번호 및 메일 추출
		String Passowrd = MemberJoinDTO.getUser_pwd();
		
		// 암호화 된  비밀번호로 변경
		MemberJoinDTO.setUser_pwd(passwordEncoder.encode(Passowrd));
		
		// DB에 저장하기 위한 VO객체 생성 ( 개인 정보 암호화 객체 )
		MemberVO memberVO = new MemberVO(MemberJoinDTO);
		
		if(mdao.member_create(memberVO)) {
			return true;
		}
		
		return false;
		
	}

	// 로그인 처리
	@Override
	public boolean member_login(MemberLoginDTO memberLoginDTO) throws Exception {
		
		// 사용자에게 입력받은 비밀번호
		String user_id = memberLoginDTO.getUser_id();
		String rawPassword = memberLoginDTO.getUser_pwd();
		
		// DB에서 현재 입력된 id에 해당하는 정보를 가져와서 MemberVO 타입의 객체 생성
		MemberVO memberVO = mdao.member_login(memberLoginDTO);
		
		if(memberVO != null) {
			// DB에서 가져온 인코딩된 비밀번호
			String encodedPassword = memberVO.getUser_pwd();
			// DB에서 가져온 비밀번호와 사용자에게 입력받은 비밀번호가 일치하는지 체크
			if(passwordEncoder.matches(rawPassword, encodedPassword) && user_id.equals(memberVO.getUser_id())) {
				return true;
			}
		}
		
		return false;
	}

	// 회원정보 불러오기
	@Override
	public HashMap<String, Object> member_select(String user_id) {
		return mdao.member_select(user_id);
	}
	// 아이디 수정
	@Override
	public Boolean member_chid(MemberChIdDTO memberChIdDTO) {
		return mdao.member_chid(memberChIdDTO);
	}
	
	// 비밀번호 수정
	@Override
	public boolean member_chpass(MemberChPassDTO memberChPassDTO) {
		// 비밀번호 변경 할 아이디
		String user_id = memberChPassDTO.getUser_id();
		// DB에 저장된 비밀번호
		String DBuser_pwd = mdao.member_chpassCheck(user_id);
		
		if(DBuser_pwd != null) {
			// 사용자에게 입력받은 비밀번호
			String rawPassword = memberChPassDTO.getUser_pwd();
			
			// 사용자에게 입력받은 비밀번호와 DB에 저장된 비밀번호 체크
			if(passwordEncoder.matches(rawPassword, DBuser_pwd)) {
				// 사용자에게 입력 받은 비밀번호 암호화
				memberChPassDTO.setCh_user_pwd(passwordEncoder.encode(memberChPassDTO.getCh_user_pwd()));
				return mdao.member_chpass(memberChPassDTO);
			}
			
		} 
		
		return false;
	
	}

	// 회원 탈퇴
	@Override
	public boolean member_delete(MemberDeleteDTO memberDeleteDTO) {
		
		// DB에 저장된 비밀번호
		String DBuser_pwd = mdao.member_chpassCheck(memberDeleteDTO.getUser_id());
		
		if(DBuser_pwd != null) {
			// 사용자에게 입력받은 비밀번호
			String rawPassword = memberDeleteDTO.getUser_pwd();
			
			// 사용자에게 입력받은 비밀번호와 DB에 저장된 비밀번호 체크
			if(passwordEncoder.matches(rawPassword, DBuser_pwd)) {
				// 회원탈퇴
				return mdao.member_delete(memberDeleteDTO.getUser_id());
			}
			
		} 
		return false;
	}
	
	
}
