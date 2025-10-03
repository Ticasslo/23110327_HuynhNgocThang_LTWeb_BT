package vn.ngocthang.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.ngocthang.entity.UserInfo;
import vn.ngocthang.repository.UserInfoRepository;

@Service
public record UserService(UserInfoRepository repository, PasswordEncoder passwordEncoder) {
    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "Thêm user thành công!";
    }
}
