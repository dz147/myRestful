package com.dz147.dao;

import com.dz147.entity.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public interface ExcelMapper {
    Boolean readExcelFromFileName(InputStream is) throws IOException;

    byte[] exporTxcel(List<Employee> employees) throws IOException;

    String upLoadImg(MultipartFile file, String path) throws IOException;
}
