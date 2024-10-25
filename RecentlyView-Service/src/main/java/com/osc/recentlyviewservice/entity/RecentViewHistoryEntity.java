package com.osc.recentlyviewservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recent_view_history")
public class RecentViewHistoryEntity {

    @Id
    @Column(name = "user_id")
    private String userId;


    @Column(name = "product_ids", columnDefinition = "TEXT")
    private String productIds;
}