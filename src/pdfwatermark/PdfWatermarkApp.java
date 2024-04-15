package pdfwatermark;

import com.itextpdf.kernel.pdf.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class PdfWatermarkApp {
	
	public static void modPdf(String item, ImageData wimage, int i, String inputPdfPath2, String outputPdfPath2, PdfExtGState gs ) {
		
		
		PdfDocument pdfDoc;
		try {
			pdfDoc = new PdfDocument(new PdfReader(inputPdfPath2+item.toString()), new PdfWriter(outputPdfPath2 + item.toString()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		   
    	
        System.out.println("create pdfDoc nr. " +i);
        // Iterate through each page and add the watermark
 //       for (int pageNum = 1; pageNum <= pdfDoc.getNumberOfPages(); pageNum++) {
 //           PdfPage page = pdfDoc.getPage(pageNum);
        	PdfPage page = pdfDoc.getPage(1);
        	System.out.println("get page for pdfDoc "+i);
        	
            PdfCanvas canvas = new PdfCanvas(page);

            System.out.println("create canvas for pdfDoc "+i);
            
            // Position the watermark (e.g., centered)
   //         Rectangle pageSize = page.getPageSize();
   //         float x = (pageSize.getLeft() + pageSize.getRight()) / 2;
   //         float y = (pageSize.getTop() + pageSize.getBottom()) / 2;
            float x = 50;
            float y = 10;


            // Add the image watermark
            canvas.saveState();
            canvas.setExtGState(gs);
 //           canvas.addImage(watermarkImage, 100, 0, 0, 100, x, y);
            canvas.addImage(wimage, 100, 0, 0, 160, y, x);
            canvas.restoreState();
  //      }
            System.out.println("Ready to close pdfDOc nr. "+i);
            
        pdfDoc.close();
	}
	
//	static String inputPdfPath = "i:\\_LOTURI_MF\\T\\TEST\\";
//	static String outputPdfPath = "i:\\_LOTURI_MF\\T\\TEST\\MOD\\";
//	static String directoryPath = "i:\\_LOTURI_MF\\T\\TEST\\"; // replace with your directory
//	static ImageData image = ImageDataFactory.create("i:\\_LOTURI_MF\\T\\TEST\\STAMP\\logo.png");
//	static PdfExtGState gs = new PdfExtGState();
	
    public static void main(String[] args) throws Exception {
 //       String inputPdfPath = "c:\\Users\\iulian.stoian\\Desktop\\TEST\\sample.pdf"; // Path to your existing PDF
    	
    	String inputPdfPath = "i:\\_LOTURI_MF\\T\\TEST\\";
    	
//        String outputPdfPath = "c:\\Users\\iulian.stoian\\Desktop\\TEST\\MOD\\output.pdf"; // Path for the watermarked PDF

        String outputPdfPath = "i:\\_LOTURI_MF\\T\\TEST\\MOD\\";
        		
        String directoryPath = "i:\\_LOTURI_MF\\T\\TEST\\"; // replace with your directory
   //     String newFileName = "newFile"; // replace with your new file name
        
        List<String> fileNames = new ArrayList<>();
        
 //       PdfDocument pdfDoc = new PdfDocument(new PdfReader(inputPdfPath), new PdfWriter(outputPdfPath));
        ImageData image = ImageDataFactory.create("i:\\_LOTURI_MF\\T\\TEST\\STAMP\\logo.png");

        // Load your watermark image
//        PdfImageXObject watermarkImage = new PdfImageXObject(image); // Replace with your image path

        // Set transparency for the watermark (adjust as needed)
        PdfExtGState gs = new PdfExtGState();
        gs.setFillOpacity(0.5f); // 50% opacity
        
        // Extract pdf file from path
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            paths
                .filter(Files::isRegularFile) // is a file
                .filter(path -> path.toString().endsWith(".pdf")) // is a PDF
                .forEach(path -> fileNames.add(path.getFileName().toString())); // add the file name to the list
        } catch (IOException e) {
            e.printStackTrace();
        }

 //       System.out.println("PDF file names: " + fileNames);
        
        //iterate through eache file and add watermark
        int i=1;
        
        //test
       
        for (String item: fileNames) {
        	
        	System.out.println(inputPdfPath+item.toString());	
        	System.out.println(outputPdfPath + item.toString());
        	
        	modPdf(item, image, i, inputPdfPath, outputPdfPath, gs);
        
                
        i=i+1;
  
        }

       
    }
}

