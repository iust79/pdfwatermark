package pdfwatermark;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;

public class PdfWatermarkApp {
    public static void main(String[] args) throws Exception {
        String inputPdfPath = "c:\\Users\\iulian.stoian\\Desktop\\TEST\\sample.pdf"; // Path to your existing PDF
        String outputPdfPath = "c:\\Users\\iulian.stoian\\Desktop\\TEST\\MOD\\output.pdf"; // Path for the watermarked PDF

        PdfDocument pdfDoc = new PdfDocument(new PdfReader(inputPdfPath), new PdfWriter(outputPdfPath));
        ImageData image = ImageDataFactory.create("c:\\Users\\iulian.stoian\\Desktop\\TEST\\logo.png");

        // Load your watermark image
        PdfImageXObject watermarkImage = new PdfImageXObject(image); // Replace with your image path

        // Set transparency for the watermark (adjust as needed)
        PdfExtGState gs = new PdfExtGState();
        gs.setFillOpacity(0.5f); // 50% opacity

        // Iterate through each page and add the watermark
        for (int pageNum = 1; pageNum <= pdfDoc.getNumberOfPages(); pageNum++) {
            PdfPage page = pdfDoc.getPage(pageNum);
            PdfCanvas canvas = new PdfCanvas(page);

            // Position the watermark (e.g., centered)
            Rectangle pageSize = page.getPageSize();
   //         float x = (pageSize.getLeft() + pageSize.getRight()) / 2;
   //         float y = (pageSize.getTop() + pageSize.getBottom()) / 2;
            float x = 50;
            float y = 10;


            // Add the image watermark
            canvas.saveState();
            canvas.setExtGState(gs);
 //           canvas.addImage(watermarkImage, 100, 0, 0, 100, x, y);
            canvas.addImage(image, 100, 0, 0, 160, y, x);
            canvas.restoreState();
        }

        pdfDoc.close();
    }
}

