package ru.rsreu.lab1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final DataSource springDataSource;

    @GetMapping("/lab1")
    public Map<String, String> index() throws SQLException {
        Map<String, String> properties = new LinkedHashMap<>();

        Connection connection = springDataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        properties.put("URL", metaData.getURL());
        properties.put("Driver name", metaData.getDriverName());
        properties.put("Driver version", metaData.getDriverVersion());

        return properties;
    }
}
