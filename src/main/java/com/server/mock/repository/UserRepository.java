package com.server.mock.repository;

import com.server.mock.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    Optional<User> findByUserNameIgnoreCase(String username);

    Optional<Object> findByEmailIgnoreCase(String email);

    @Query("select u from User u where "
            + " ( CAST(u.id AS string) like %:param1% or "
            + " u.userName like %:param1% or "
            + "u.email like %:param1% ) ")
    Optional<List<User>> findBySearchTerm(@Param("param1") String searchTerm);
}