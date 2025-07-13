package farmix.com;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResumeAnalyzerService {

    public ResumeAnalysisResult analyze(String resumeText, String jobDescriptionText) {
        RestTemplate restTemplate = new RestTemplate();

        String pythonApiUrl = "http://localhost:5000/analyze";

        Map<String, String> payload = new HashMap<>();
        payload.put("resume", resumeText);
        payload.put("jd", jobDescriptionText);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<ResumeAnalysisResult> response = restTemplate
                .postForEntity(pythonApiUrl, request, ResumeAnalysisResult.class);

        return response.getBody();
    }
}

