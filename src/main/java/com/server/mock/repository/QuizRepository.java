package com.server.mock.repository;

import com.server.mock.model.exam.Quiz;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {


    Optional<List<Quiz>> findByCategory_Id(Long id);


//    @Transactional
//    @Modifying
//    @Query(nativeQuery = true,value = "delete from quiz q where q.id = :id")
//    Integer myDeleteById(@Param("id") Long id);
}