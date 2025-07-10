package com.safeqr.qrfish_detector.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "url_scans")
@Data           //for getters and setters
public class ScannedUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String verdict;

    @Column(name = "file_attempted")
    private Boolean fileAttempted;

    @Column(name = "scan_time")
    private LocalDateTime scanTime;
}
