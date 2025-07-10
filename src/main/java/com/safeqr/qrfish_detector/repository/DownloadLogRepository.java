package com.safeqr.qrfish_detector.repository;

import com.safeqr.qrfish_detector.entities.DownloadLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DownloadLogRepository extends JpaRepository<DownloadLog,Long> {
    List<DownloadLog> findByBlockedTrue();

    List<DownloadLog> findByFileType(String fileType);
}
