package vn.ngocthang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.ngocthang.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    // Tìm user theo username
    Optional<User> findByUserName(String userName);
    
    // Tìm user theo email
    Optional<User> findByEmail(String email);
    
    // Kiểm tra username đã tồn tại chưa
    boolean existsByUserName(String userName);
    
    // Kiểm tra email đã tồn tại chưa
    boolean existsByEmail(String email);
    
    // Kiểm tra phone đã tồn tại chưa
    boolean existsByPhone(String phone);
    
    // Kiểm tra ID đã tồn tại chưa
    boolean existsById(Integer id);
    
    // Tìm user theo username hoặc email
    @Query("SELECT u FROM User u WHERE u.userName = :identifier OR u.email = :identifier")
    Optional<User> findByUserNameOrEmail(@Param("identifier") String identifier);
    
    // Tìm user theo tên hoặc username (cho search)
    List<User> findByFullNameContainingIgnoreCaseOrUserNameContainingIgnoreCase(String fullName, String userName);
}
