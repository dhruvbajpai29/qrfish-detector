package com.safeqr.qrfish_detector.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="blacklisted_urls")
@Data
public class BlacklistedUrl {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String url;

    @Column(name = "added_at")
    private LocalDateTime addedAt;


}
