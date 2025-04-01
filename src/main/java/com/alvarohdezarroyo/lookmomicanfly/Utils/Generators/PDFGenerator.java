package com.alvarohdezarroyo.lookmomicanfly.Utils.Generators;

import com.alvarohdezarroyo.lookmomicanfly.Config.AppConfig;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class PDFGenerator {

    public static String generateSalePDF(String saleId, String shippingLabelPath){
        final String pdfPath= AppConfig.getShippingLabelsPath()+ File.separator+saleId+".pdf";
        PDDocument pdfDocument=new PDDocument();
        PDPage page=new PDPage();
        pdfDocument.addPage(page);
        try{
            final PDPageContentStream pdContentStream=new PDPageContentStream(pdfDocument,page); // this class is just for writing inside the document
            PDImageXObject shippingLabel= PDImageXObject.createFromFile(shippingLabelPath, pdfDocument);
            pdContentStream.drawImage(shippingLabel, 20, 400, 800, 350);
            pdContentStream.close();
            pdfDocument.save(pdfPath);
            pdfDocument.close();
            return pdfPath;
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }

    }

}
