import org.bytedeco.javacpp.*;
import org.junit.Test;

import com.darkprograms.speech.translator.GoogleTranslate;

import static org.bytedeco.javacpp.lept.*;
import static org.bytedeco.javacpp.tesseract.*;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

public class BasicTesseractExampleTest {
    
    @Test
    public void givenTessBaseApi_whenImageOcrd_thenTextDisplayed() throws Exception {
        BytePointer outText;

        TessBaseAPI api = new TessBaseAPI();
        // Initialize tesseract-ocr with English, without specifying tessdata path
        if (api.Init(".", "ENG") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }

        // Open input image with leptonica library
        PIX image = pixRead("test.png");
        api.SetImage(image);
        // Get OCR result
        outText = api.GetUTF8Text();
        String string = outText.getString();
        
        assertTrue(!string.isEmpty());
        System.out.println("OCR output:\n" + string);
        System.out.println("\n");
       try {
    	 //English to IGBO
        System.out.println(GoogleTranslate.translate("ig",string.toString().replace("\n", "").replace("\r", "")));
        
      //English to  GREEK
        System.out.println(GoogleTranslate.translate("el",string.toString()));
        
        //English to  HAUSA
        System.out.println(GoogleTranslate.translate("ha",string.toString()));
        
        //English to  Yoruba
        System.out.println(GoogleTranslate.translate("yo",string.toString()));
       }catch (IOException e) {
		// TODO: handle exception
    	   e.printStackTrace();
	}
        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image);
    }
}