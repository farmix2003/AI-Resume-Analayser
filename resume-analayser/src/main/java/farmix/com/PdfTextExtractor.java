package farmix.com;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class PdfTextExtractor {

    public static String extractText(InputStream inputStream) throws IOException {
        try (PDDocument document = PDDocument.load(inputStream)) {
            String text = new org.apache.pdfbox.text.PDFTextStripper().getText(document).trim();
            if (!text.isEmpty()) {
                return text;
            }

            PDFRenderer renderer = new PDFRenderer(document);
            StringBuilder ocrResult = new StringBuilder();

            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
            tesseract.setLanguage("eng");

            for (int i = 0; i < document.getNumberOfPages(); i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 300);
                String result = tesseract.doOCR(image);
                ocrResult.append(result).append("\n");
            }

            return ocrResult.toString().trim();
        } catch (TesseractException e) {
            throw new IOException("OCR failed", e);
        }
    }
}
