package com.safeqr.qrfish_detector.repository;


import com.safeqr.qrfish_detector.entities.BlacklistedUrl;
import com.safeqr.qrfish_detector.entities.DownloadLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistedUrlRepository extends JpaRepository<BlacklistedUrl,Long> {
    boolean existsByUrl(String url);

}
