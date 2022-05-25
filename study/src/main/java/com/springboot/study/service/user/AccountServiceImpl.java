package com.springboot.study.service.user;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.study.config.auth.PrincipalDetails;
import com.springboot.study.domain.user.User;
import com.springboot.study.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	
	@Value("${file.path}") // 서비스가 생성되는 시점에 filePath에 ("${file.path}")값 대입 /"${file.path} : yml에 있는 file의 path의 값 
	private String filePath;
	
	private final UserRepository userRepository;
	
	@Override
	public boolean updateProfileImg(MultipartFile file,PrincipalDetails principalDetails) {
		if(file != null) {//파일이 있으면
			String originalfileName = file.getOriginalFilename();
			String tempFileName = UUID.randomUUID().toString().replaceAll("-", "")+ "_" + originalfileName;
			
			//파일저장경로 따로 지정해줘야함
			Path uploadPath = Paths.get(filePath, "profile/" + tempFileName);
			
			File f = new File(filePath + "profile");
			if(!f.exists()) {//f.exists():해당경로가 존재하면 true / 해당경로가 존재하지 않으면 
				f.mkdirs(); //경로 만듦
			}
			
			try {
				Files.write(uploadPath, file.getBytes()); //사진,동영상->바이너리 -> 
				
				User user = principalDetails.getUser();
				user.setProfile_img_url(tempFileName);
				
				return userRepository.updateProfileImg(user) > 0 ? true: false; //update처리되면 true
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
