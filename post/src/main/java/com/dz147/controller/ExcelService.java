package com.dz147.controller;

import com.dz147.dao.AuthorMapper;
import com.dz147.dao.EmployeeMapper;
import com.dz147.dao.ExcelMapper;
import com.dz147.entity.Author;
import com.dz147.entity.Employee;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExcelService implements ExcelMapper {

    Logger log  = Logger.getLogger(ExcelService.class);//log.info() 调用

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    //导出Excel
    @Override
    public byte[] exporTxcel(List<Employee> employees) throws IOException {
        // 1.创建HSSFWorkbook，一个HSSFWorkbook对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("sheet1");
        // 3.设置表头，即每个列的列名
        String[] titel = {"姓名", "性别", "学历", "薪资"};
        // 3.1创建第一行
        HSSFRow row = sheet.createRow(0);
        // 此处创建一个序号列
        row.createCell(0).setCellValue("序号");
        // 将列名写入
        for (int i = 0; i < titel.length; i++) {
            // 给列写入数据,创建单元格，写入数据
            row.createCell(i + 1).setCellValue(titel[i]);
        }
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 写入正式数据
        for (int i = 0; i < employees.size(); i++) {
            // 创建行
            row = sheet.createRow(i + 1);
            // 序号
            row.createCell(0).setCellValue(Integer.parseInt(employees.get(i).getNumber()));
            // 作者名称
            row.createCell(1).setCellValue(employees.get(i).getEmpName());
            // 电话号码
            row.createCell(2).setCellValue(employees.get(i).getEmpSex());
            // 出生日期
            row.createCell(3).setCellValue(employees.get(i).getEducation());
            // 备注
            row.createCell(4).setCellValue((double) employees.get(i).getMonthly());
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        wb.write(stream);
        return stream.toByteArray();
        //return null;
    }

    @Override
    public String upLoadImg(MultipartFile file, String path) throws IOException {
        ArrayList<String> strings = new ArrayList<>();
        if (file.isEmpty()) {
            strings.add("请选择文件！");
        }
        //得到文件的类型
        String fileType = file.getContentType();
        //第一种方式Arrays.asList("image/jpeg","image/png")
        if (!fileType.contains("image/")) {
            strings.add("只允许上传图片！");
        }
        //只允许上传的图片小于5MB
        log.info(file.getSize());
        if (file.getSize() > 1024 * 1024 * 1024 * 5) {
            strings.add("只允许上传5M的图片！");
        }
        String fileName = "";

        String[] formatName = getFormatName(file.getOriginalFilename());
        file.transferTo(new File(path + File.separator + formatName[0] + formatName[1] + formatName[2]));
        //得到图片的相对路径
        fileName = formatName[0] + formatName[1] + formatName[2];

        if (strings.size() > 0) {
            fileName = "";
            fileName = strings.get(0);
        }
        return fileName;
    }

    public String[] getFormatName(String fileName) {
        //设置日期格式yyyy-MM-dd
        SimpleDateFormat df = new SimpleDateFormat("_yyyyMMddHHmmss");
        // new Date()为获取当前系统时间
        String now = df.format(new Date());
        //获得文件名去掉后缀
        String prefix = fileName.substring(0, fileName.lastIndexOf("."));
        //得到文件后缀带.
        String postfix = fileName.substring(fileName.lastIndexOf("."));
        return new String[]{prefix, now, postfix};
    }

    //导入
    @Override
    public Boolean readExcelFromFileName(InputStream is) throws IOException {
        HSSFWorkbook workbook = null;
        workbook = new HSSFWorkbook(is);
        HSSFSheet sheet = workbook.getSheetAt(0);

        int lastRowNum = sheet.getLastRowNum();
        for (int i = 1; i <= lastRowNum; i++) {
            HSSFRow row = sheet.getRow(i);
            int id = (int) row.getCell(0).getNumericCellValue();
            String name = row.getCell(1).getStringCellValue();
            String sex = row.getCell(2).getStringCellValue();
            String xl = row.getCell(3).getStringCellValue();
            Long xz = (long) row.getCell(4).getNumericCellValue();
            Employee employee = new Employee();
            employee.setNumber(id + "");
            employee.setEmpName(name);
            employee.setEmpSex(sex);
            employee.setEducation(xl);
            employee.setMonthly(xz);
            log.info(employee.toString());
            employeeMapper.insert(employee);
        }
        return true;
    }
}
