package com.safeqr.qrfish_detector.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerdictService {

    @Autowired
    private VirusTotalService vtService;

    public String analyzeURL(String url) {
        if (url.endsWith(".exe")) {
            return "block";
        }

        JSONObject verdictJson = vtService.getUrlVerdict(url);
        if (verdictJson == null) return "error";

        try {
            int malicious = verdictJson.optInt("malicious", 0);
            int suspicious = verdictJson.optInt("suspicious", 0);

            if (malicious > 0 || suspicious > 0) {
                return "block";
            } else {
                return "safe";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
