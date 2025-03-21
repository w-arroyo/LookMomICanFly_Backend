package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,String> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE posts SET active=false WHERE id= :id AND user_id= :userId", nativeQuery = true)
    int deactivatePost(@Param("id") String id, @Param("user_id") String userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE posts SET active=false, finalized=true WHERE id= :id", nativeQuery = true)
    int completePost(@Param("id") String id);

    @Query("SELECT a FROM Ask a WHERE a.user.id = :id AND a.active = true")
    List<Post> getAllUserActiveAsks(@Param("id") String id);

    @Query("SELECT a FROM Bid a WHERE a.user.id = :id AND a.active = true")
    List<Post> getAllUserActiveBids(@Param("id") String id);

}
