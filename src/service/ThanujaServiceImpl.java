/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import util.DBConnect;
import util.SMDBconnect;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 *
 * @author Thanuja Chamika
 */
public class ThanujaServiceImpl {
    private Connection conn;
    private PreparedStatement pst;
    
    public void generateReport(String path){
        conn = SMDBconnect.connect();
        ResultSet rs = null;
                    
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path + "\\Report.pdf"));
            document.open();

            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance("C:\\Users\\Chathura Harshanga\\Documents\\NetBeansProjects\\ITP\\src\\images\\Untitled-3.png");
            document.add(image);

            document.add(new Paragraph("Wijesinghe Motors", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD, BaseColor.RED)));
            document.add(new Paragraph(new Date().toString()));
            document.add(new Paragraph("---------------------------------------------------------------------------------------"));

            PdfPTable table = new PdfPTable(3);

            PdfPCell cell = new PdfPCell(new Paragraph("Type"));
            cell.setColspan(1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.CYAN);
            table.addCell(cell);
            
            PdfPCell cell2 = new PdfPCell(new Paragraph("Month"));
            cell2.setColspan(1);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setBackgroundColor(BaseColor.CYAN);
            table.addCell(cell2);
            
            PdfPCell cell3 = new PdfPCell(new Paragraph("Amount"));
            cell3.setColspan(1);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setBackgroundColor(BaseColor.CYAN);
            table.addCell(cell3);
            
            String query = "SELECT * FROM BILLPAYMENT";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            int total = 0;
            
            while (rs.next()) {
                if(rs.getInt("type") == 1){
                    table.addCell("Electricity bill");
                }
                else{
                    table.addCell("Water bill");
                }
                
                //table.addCell(Integer.toString(rs.getInt("type")));
                table.addCell(Integer.toString(rs.getInt("month")));
                table.addCell(Integer.toString(rs.getInt("amount")));
                total = total + rs.getInt("amount");
            }
            document.add(table);
            
            PdfPTable table2 = new PdfPTable(2);
            table2.addCell("Total payments");
            table2.addCell(Integer.toString(total));
            document.add(table2);

            document.close();            
        } catch (Exception e) {

        }    
    }
}
