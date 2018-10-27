package com.pdfAndExcel.service;

import com.pdfAndExcel.entity.Employee;
import com.pdfAndExcel.repository.EmployeeRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    public boolean createPdf(List<Employee> employees, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        Document document = new Document(PageSize.A4, 15, 15, 45,30);
        try {
            String filePath = context.getRealPath("/resources/report");
            File file = new File(filePath);
            boolean exists = new File(filePath).exists();
            if (!exists){
                new  File(filePath).mkdirs();
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"employees"+".pdf"));
            document.open();
            Font mainFont = FontFactory.getFont("Arial", 10, BaseColor.BLACK);


            Paragraph paragraph = new Paragraph("All Employee", mainFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(4);//column amount
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font tableHeader = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
            Font tableBody = FontFactory.getFont("Arial", 9, BaseColor.BLACK);

            float[] columnWidths = {2f, 2f, 2f, 2f};
            table.setWidths(columnWidths);

            PdfPCell name = new PdfPCell(new Paragraph("name", tableHeader));
            name.setBorderColor(BaseColor.BLACK);
            name.setPaddingLeft(10);
            name.setHorizontalAlignment(Element.ALIGN_CENTER);
            name.setVerticalAlignment(Element.ALIGN_CENTER);
            name.setBackgroundColor(BaseColor.DARK_GRAY);
            name.setExtraParagraphSpace(5f);
            table.addCell(name);

            PdfPCell email = new PdfPCell(new Paragraph("email", tableHeader));
            email.setBorderColor(BaseColor.BLACK);
            email.setPaddingLeft(10);
            email.setHorizontalAlignment(Element.ALIGN_CENTER);
            email.setVerticalAlignment(Element.ALIGN_CENTER);
            email.setBackgroundColor(BaseColor.DARK_GRAY);
            email.setExtraParagraphSpace(5f);
            table.addCell(email);

            PdfPCell mobile = new PdfPCell(new Paragraph("mobile", tableHeader));
            mobile.setBorderColor(BaseColor.BLACK);
            mobile.setPaddingLeft(10);
            mobile.setHorizontalAlignment(Element.ALIGN_CENTER);
            mobile.setVerticalAlignment(Element.ALIGN_CENTER);
            mobile.setBackgroundColor(BaseColor.DARK_GRAY);
            mobile.setExtraParagraphSpace(5f);
            table.addCell(mobile);

            PdfPCell address = new PdfPCell(new Paragraph("address", tableHeader));
            address.setBorderColor(BaseColor.BLACK);
            address.setPaddingLeft(10);
            address.setHorizontalAlignment(Element.ALIGN_CENTER);
            address.setVerticalAlignment(Element.ALIGN_CENTER);
            address.setBackgroundColor(BaseColor.DARK_GRAY);
            address.setExtraParagraphSpace(5f);
            table.addCell(address);

            for (Employee employee : employees){
                PdfPCell nameValue = new PdfPCell(new Paragraph(employee.getName(), tableHeader));
                nameValue.setBorderColor(BaseColor.BLACK);
                nameValue.setPaddingLeft(10);
                nameValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                nameValue.setVerticalAlignment(Element.ALIGN_CENTER);
                nameValue.setBackgroundColor(BaseColor.WHITE);
                nameValue.setExtraParagraphSpace(5f);
                table.addCell(nameValue);

                PdfPCell emailValue = new PdfPCell(new Paragraph(employee.getEmail(), tableHeader));
                emailValue.setBorderColor(BaseColor.BLACK);
                emailValue.setPaddingLeft(10);
                emailValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                emailValue.setVerticalAlignment(Element.ALIGN_CENTER);
                emailValue.setBackgroundColor(BaseColor.WHITE);
                emailValue.setExtraParagraphSpace(5f);
                table.addCell(emailValue);

                PdfPCell mobileValue = new PdfPCell(new Paragraph(employee.getMobile(), tableHeader));
                mobileValue.setBorderColor(BaseColor.BLACK);
                mobileValue.setPaddingLeft(10);
                mobileValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                mobileValue.setVerticalAlignment(Element.ALIGN_CENTER);
                mobileValue.setBackgroundColor(BaseColor.WHITE);
                mobileValue.setExtraParagraphSpace(5f);
                table.addCell(mobileValue);

                PdfPCell addressValue = new PdfPCell(new Paragraph(employee.getAddress(), tableHeader));
                addressValue.setBorderColor(BaseColor.BLACK);
                addressValue.setPaddingLeft(10);
                addressValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                addressValue.setVerticalAlignment(Element.ALIGN_CENTER);
                addressValue.setBackgroundColor(BaseColor.WHITE);
                addressValue.setExtraParagraphSpace(5f);
                table.addCell(addressValue);
            }

            document.add(table);
            document.close();
            writer.close();
            return true;


        }catch (Exception e){
        return false;
        }
    }

    public boolean createExcell(List<Employee> employees, ServletContext context, HttpServletRequest request, HttpServletResponse response) {

        String filePath = context.getRealPath("/resources/report");
        File file = new File(filePath);
        boolean exists = new File(filePath).exists();
        if (!exists){
            new File(filePath).mkdirs();
        }
        try{
            FileOutputStream outputStream = new FileOutputStream(file+"/"+"employees"+".xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet workSheet = workbook.createSheet("employees");
            workSheet.setDefaultColumnWidth(30);

            HSSFCellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);
            headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFRow headerRow = workSheet.createRow(0);

            HSSFCell name = headerRow.createCell(0);
            name.setCellStyle(headerCellStyle);

            HSSFCell email = headerRow.createCell(1);
            email.setCellStyle(headerCellStyle);

            HSSFCell mobile = headerRow.createCell(2);
            mobile.setCellStyle(headerCellStyle);

            HSSFCell address = headerRow.createCell(3);
            address.setCellStyle(headerCellStyle);

            Integer i = 1;

            for (Employee employee : employees){
                HSSFRow bodyRow = workSheet.createRow(i);

                HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
                bodyCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);

                HSSFCell nameValue = bodyRow.createCell(0);
                nameValue.setCellValue(employee.getName());
                nameValue.setCellStyle(bodyCellStyle);

                HSSFCell emailValue = bodyRow.createCell(1);
                emailValue.setCellValue(employee.getEmail());
                emailValue.setCellStyle(bodyCellStyle);

                HSSFCell mobileValue = bodyRow.createCell(2);
                mobileValue.setCellValue(employee.getMobile());
                mobileValue.setCellStyle(bodyCellStyle);

                HSSFCell addressValue = bodyRow.createCell(3);
                addressValue.setCellValue(employee.getAddress());
                addressValue.setCellStyle(bodyCellStyle);

                i++;
            }

                workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            return true;

        }catch (Exception e){
            return false;
        }
    }
}
