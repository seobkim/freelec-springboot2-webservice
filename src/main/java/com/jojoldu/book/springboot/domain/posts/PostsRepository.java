package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {

    //Spring Data Jpa에서 제공하지 않는 메소드는 아래처럼 쿼리로 작성할수 있음
    //실제 앞의 코드는 SpringDataJpa에서 제공하는 기본 메소드만으로 해결 가능
    //다만 @Query가 훨씬 가동석이 좋으니 선택해서 사용할 것
    @Query("SELECT p from Posts p order by p.id DESC")
    List<Posts> findAllDesc();

}
