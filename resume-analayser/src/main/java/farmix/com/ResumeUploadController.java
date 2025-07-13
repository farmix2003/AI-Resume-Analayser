package farmix.com;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class ResumeUploadController {

    private final ResumeAnalyzerService analyzerService;

    public ResumeUploadController(ResumeAnalyzerService analyzerService) {
        this.analyzerService = analyzerService;
    }

    @PostMapping("/analyze")
    public ResumeAnalysisResult handleUploadAndAnalyze(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jd") String jobDescription
    ) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("PDF file is missing.");
        }
        String resumeText = PdfTextExtractor.extractText(file.getInputStream());
        System.out.println("Extracted Resume Text: " + resumeText);

        return analyzerService.analyze(resumeText, jobDescription);
    }
}
