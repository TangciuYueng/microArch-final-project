package cn.edu.tongji.login.repository;

import cn.edu.tongji.login.model.GenderType;
import cn.edu.tongji.login.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findFirstById(Long id);
    List<User> findAllByPhone(String phone);

    @Modifying
    @Query("UPDATE User u SET " +
            "u.age = CASE WHEN :age IS NOT NULL THEN :age ELSE u.age END, " +
            "u.gender = CASE WHEN :gender IS NOT NULL THEN :gender ELSE u.gender END, " +
            "u.name = CASE WHEN :name IS NOT NULL THEN :name ELSE u.name END, " +
            "u.region = CASE WHEN :region IS NOT NULL THEN :region ELSE u.region END, " +
            "u.email = CASE WHEN :email IS NOT NULL THEN :email ELSE u.email END " +
            "WHERE u.id = :id")
    @Transactional
    void updateInfoById(
            @Param("id") Long id,
            @Param("age") Integer age,
            @Param("gender") GenderType gender,
            @Param("name") String name,
            @Param("region") String region,
            @Param("email") String email
    );

    @Modifying
    @Query("UPDATE User u SET u.avatar = :avatar WHERE u.id = :id")
    void updateAvatarById(@Param("id") Long id, @Param("avatar") String avatar);

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
    void updatePasswordById(@Param("id") Long id, @Param("password") String password);

    Integer findFirstFriendCountById(Long id);

    @Modifying
    @Query("UPDATE User u SET u.friendCount = :friendCount WHERE u.id = :id")
    void updateFriendCountById(@Param("id") Long id, @Param("friendCount") Integer friendCount);
}
