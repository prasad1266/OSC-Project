package com.osc.recentlyviewservice.repository;

import com.osc.recentlyviewservice.entity.RecentViewHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecentViewHistoryRepository extends JpaRepository<RecentViewHistoryEntity,String> {

}
