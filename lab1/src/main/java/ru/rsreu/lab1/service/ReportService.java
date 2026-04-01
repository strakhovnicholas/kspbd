package ru.rsreu.lab1.service;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import ru.rsreu.lab1.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    public byte[] generateUsersReport(List<User> users) throws JRException, IOException {

        InputStream reportStream = getClass().getResourceAsStream("/report.jrxml");

        if (reportStream == null) {
            throw new RuntimeException("report.jrxml NOT FOUND");
        }

        JasperReport report = JasperCompileManager.compileReport(reportStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);

        JasperPrint print = JasperFillManager.fillReport(
                report,
                new HashMap<>(),
                dataSource
        );

        try {
            byte[] pdf = JasperExportManager.exportReportToPdf(print);
            String xml = JasperExportManager.exportReportToXml(print);
            JasperExportManager.exportReportToHtmlFile(print, "test_report.html");
            Files.write(Paths.get("test_report.pdf"), pdf);
            Files.write(Paths.get("test_report.xml"), xml.getBytes());
            return pdf;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}