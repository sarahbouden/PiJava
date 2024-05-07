package services;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Reservation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ServicePDF {
    public void generate(final List<Reservation> reservations) {
        final Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("iTextTable.pdf"));
        } catch (final DocumentException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        document.open();
        final List<String> attributes = new ArrayList<>() {{
            this.add("date debut");
            this.add("Date fin");
            this.add("nombre de personne");
            this.add("type chambre");
        }};
        final float[] widths = {50, 50, 50, 50};
        final PdfPTable table = new PdfPTable(widths);
        this.addTableHeader(table, attributes);
        this.addRows(table, reservations);
        // addCustomRows(table);

        try {
            document.add(table);
        } catch (final DocumentException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        document.close();
    }

    private void addTableHeader(final PdfPTable table, final List<String> attributes) {
        attributes.forEach(columnTitle -> {
            final PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setIndent(10);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
    }


    private void addRows(final PdfPTable table, final List<Reservation> reservations) {
        for (final Reservation reservation :reservations ) {
            table.addCell(String.valueOf(reservation.getDate_debut_r()));
            table.addCell(String.valueOf(reservation.getDate_fin_r()));
            table.addCell(String.valueOf(reservation.getNbr_perso()));
            table.addCell(String.valueOf(reservation.getType_room()));


        }
    }
//
//    private void addCustomRows(PdfPTable table) {
//        Path path = null;
//        try {
//            path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());
//        } catch (URISyntaxException e) {
//            System.out.println(e.getMessage());
//            throw new RuntimeException(e);
//        }
//        Image img = null;
//        try {
//            img = Image.getInstance(path.toAbsolutePath().toString());
//        } catch (BadElementException | IOException e) {
//            System.out.println(e.getMessage());
//            throw new RuntimeException(e);
//        }
//        img.scalePercent(10);
//
//        PdfPCell imageCell = new PdfPCell(img);
//        table.addCell(imageCell);
//
//        PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
//        horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(horizontalAlignCell);
//
//        PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
//        verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
//        table.addCell(verticalAlignCell);
//    }
}