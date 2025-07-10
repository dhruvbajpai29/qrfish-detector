package com.safeqr.qrfish_detector.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "download_logs")
@Data
public class DownloadLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    private boolean blocked;

    @Column(name = "detected_at")
    private LocalDateTime detectedAt;
}
