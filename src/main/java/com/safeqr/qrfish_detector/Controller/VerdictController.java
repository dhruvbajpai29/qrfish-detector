package com.safeqr.qrfish_detector.Controller;
import com.safeqr.qrfish_detector.entities.BlacklistedUrl;
import com.safeqr.qrfish_detector.entities.ScannedUrl;
import com.safeqr.qrfish_detector.repository.BlacklistedUrlRepository;
import com.safeqr.qrfish_detector.service.VerdictService;
import com.safeqr.qrfish_detector.dto.UrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safeqr.qrfish_detector.service.RedisBlacklistService;

import com.safeqr.qrfish_detector.repository.ScannedUrlRepository;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class VerdictController {
    @Autowired
    private VerdictService verdictService;

    @Autowired
    private ScannedUrlRepository scannedUrlRepository;

    @Autowired
    private BlacklistedUrlRepository blacklistedUrlRepository;

    @Autowired
    private RedisBlacklistService redisBlacklistService;

    @PostMapping("/check")
    public ResponseEntity<String> checkURL(@RequestBody UrlRequest request) {
        try {
            System.out.println("Received URL: " + request.getUrl());
            if (redisBlacklistService.isBlackListed(request.getUrl())) {
                return ResponseEntity.ok("block (cached)");
            }
            String result = verdictService.analyzeURL(request.getUrl());

            // Save scan result to Supabase
            ScannedUrl scan = new ScannedUrl();
            scan.setUrl(request.getUrl());
            scan.setVerdict(result);
            //scan.setSource("backend");
            scan.setFileAttempted(request.getUrl().endsWith(".exe")); // basic check
            scan.setScanTime(LocalDateTime.now());

            scannedUrlRepository.save(scan);  // saved to Supabase via JPA


            if("block".equalsIgnoreCase(result)){
                if(!blacklistedUrlRepository.existsByUrl(request.getUrl())){
                    BlacklistedUrl badUrl=new BlacklistedUrl();
                    badUrl.setUrl(request.getUrl());
                    badUrl.setAddedAt(LocalDateTime.now());
                    blacklistedUrlRepository.save(badUrl);
                    redisBlacklistService.blacklistUrl(request.getUrl()); // Save to Redis cache as well

                }
            }

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();  // This will log full error in console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
