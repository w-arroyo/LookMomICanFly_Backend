package com.alvarohdezarroyo.lookmomicanfly.Utils.Generators;

import com.alvarohdezarroyo.lookmomicanfly.Config.AppConfig;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.FileValidator;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ShippingLabelGenerator {

    public static String generateShippingLabel(String trackingNumber){
        FileValidator.checkIfADirectoryExistsAndCreateIt(AppConfig.getShippingLabelsPath());
        try {
            final BitMatrix bitMatrix= new MultiFormatWriter().encode(trackingNumber, BarcodeFormat.CODE_128,300,100);
            final String filePath= AppConfig.getShippingLabelsPath() + File.separator+trackingNumber+".png";
            final Path path= Paths.get(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix,"PNG",path); // converts the file to a matrix and saves it as a file
            return filePath;
        }
        catch(IOException | WriterException ex){
            System.out.println("Error generating shipping label.");
            throw new RuntimeException(ex.getMessage());
        }
    }

}
