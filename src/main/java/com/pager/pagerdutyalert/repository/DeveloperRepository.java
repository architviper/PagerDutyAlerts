package com.pager.pagerdutyalert.repository;

import com.pager.pagerdutyalert.entity.DeveloperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperRepository extends JpaRepository<DeveloperEntity, Long> {


    @Query(value = "SELECT * FROM developer WHERE team_id=:teamId", nativeQuery = true)
    List<DeveloperEntity> findAllById(@Param("teamId") Long teamId);
}
