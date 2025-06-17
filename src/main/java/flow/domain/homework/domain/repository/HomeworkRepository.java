package flow.domain.homework.domain.repository;

import feign.Param;
import flow.domain.homework.domain.entity.ExtensionType;
import flow.domain.homework.domain.entity.Homework;
import flow.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    @Query("SELECT h FROM Homework h WHERE h.user = :user AND h.type = 'FIXED' ORDER BY h.createdAt ASC")
    List<Homework> findFixedExtensionsByUser(@Param("user") User user);

    @Query("SELECT h FROM Homework h WHERE h.user = :user AND h.type = 'CUSTOM' ORDER BY h.createdAt ASC")
    List<Homework> findCustomExtensionsByUser(@Param("user") User user);

    // 중복 조회 검증 있으면 true 없으면 false
    boolean existsByUserAndExtension(User user, String extension);
}
