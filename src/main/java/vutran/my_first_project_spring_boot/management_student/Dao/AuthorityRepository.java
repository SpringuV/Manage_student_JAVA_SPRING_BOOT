package vutran.my_first_project_spring_boot.management_student.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vutran.my_first_project_spring_boot.management_student.Entity.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    public Authority findByName(String username);
}


/*
* DAO (Data Access Object) là một mẫu thiết kế để quản lý việc truy cập dữ liệu từ cơ sở dữ liệu,
*  nhưng trong Spring Data JPA, các repository đã đóng vai trò như DAO. Vì thế, thay vì gọi là dao,
* nhiều dự án Spring Boot hiện đại đặt tên thư mục này là repository, để phản ánh rõ hơn vai trò của chúng.
* */