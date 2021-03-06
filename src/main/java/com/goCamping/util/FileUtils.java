package com.goCamping.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component
public class FileUtils {

	// 파일이 저장 될 위치
	private static final String filePath = "C:\\spring_project\\upload\\";
	
	public List<Map<String, Object>> parseInsertFileInfo(
			MultipartHttpServletRequest multipartHttpServletRequest /*,String[] files, String[] fileNames*/) throws Exception{
		// 업로드 된 파일들의 이름 목록을 제공하는 Iterator를 구한다. ( 파일들의 이름은 input 태그의 name 값이다. )
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		// 업로드 파일 정보를 담기 위한 객체
		MultipartFile multipartFile = null;
		// 원본 파일명
		String originalFileName = null;
		// 원본 파일의 확장자명
		String originalFileExtension = null;
		// 서버에 저장 될 파일명
		String storedFileName = null;
		
		// 모든 파일 정보를 List 형식으로 담기 위한 List
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// DB에 저장하기 위해 파일 정보를 담을 Map
		Map<String, Object> listMap = null;
		
		File file = new File(filePath);
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		while(iterator.hasNext()) {
			multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			
			if(multipartFile.isEmpty() == false) {
				// 원본 파일명
				originalFileName = multipartFile.getOriginalFilename();
				// 원본 파일 확장자명
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				// 서버에 저장될 파일명
				storedFileName = getRandomString() + originalFileExtension;
				// 파일 객체 생성
				file = new File(filePath, storedFileName);
				// 파일 저장
				multipartFile.transferTo(file);
				// DB에 원본 파일명, DB 저장 파일명, 파일 크기 저장 하기위해 list 입력
				listMap = new HashMap<String, Object>();
				listMap.put("org_file_name", originalFileName);
				listMap.put("stored_file_name", storedFileName);
				listMap.put("file_size", multipartFile.getSize());
				
				list.add(listMap);
			}
		}
		return list;
	}
	
	// 32글자의 랜덤한 문자열(숫자포함)을 만들어서 반환
	private String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
