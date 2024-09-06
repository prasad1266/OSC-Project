package com.osc.sessiondataservice.repository;

import com.osc.sessiondataservice.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session,String> {

    @Query("SELECT s FROM Session s WHERE s.userId = :userId AND s.sessionId = :sessionId")
    Session findByUserIdAndSessionId(@Param("userId") String userId, @Param("sessionId") String sessionId);


}
