package com.safeqr.qrfish_detector.repository;

import com.safeqr.qrfish_detector.entities.ScannedUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java .util.List;

@Repository
public interface ScannedUrlRepository extends JpaRepository<ScannedUrl,Long> {

    List<ScannedUrl> findByVerdict( String verdict);

    List<ScannedUrl> findByUrlContaining (String keywords);
}
