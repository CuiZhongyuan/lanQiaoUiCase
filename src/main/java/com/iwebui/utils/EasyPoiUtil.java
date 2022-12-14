package com.iwebui.utils;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.export.ExcelExportService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

import static cn.afterturn.easypoi.excel.ExcelExportUtil.USE_SXSSF_LIMIT;

/**
 * @Description: Excel文件导入导出Util(EasyPoi)
 * @Author: lhc
 * @CreateDate: 2020/7/6 14:20
 */
public class EasyPoiUtil {
    /**
     * 功能描述：复杂导出Excel，包括文件名以及表名。创建表头
     *
     * @param list           导出的实体类
     * @param title          表头名称
     * @param sheetName      sheet表名
     * @param pojoClass      映射的实体类
     * @param isCreateHeader 是否创建表头
     * @param fileName
     * @param response
     * @return
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        exportParams.setStyle(EasyPoiExcelStyleUtil.class);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        exportParams.setStyle(EasyPoiExcelStyleUtil.class);
        defaultExport(list, pojoClass, fileName, exportParams);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(fileName, workbook);
        }
    }
    /**
     * 功能描述：Excel导出
     *
     * @param fileName 文件名称
     * @param workbook Excel对象
     * @return
     */
    public static void downLoadExcel(String fileName, Workbook workbook) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            workbook.write(fos);
        } catch (IOException e) {
            System.out.println("文件打开异常，请确认是否已打开");
        }
    }
    /**
     * 功能描述：复杂导出Excel，包括文件名以及表名,不创建表头
     *
     * @param list      导出的实体类
     * @param title     表头名称
     * @param sheetName sheet表名
     * @param pojoClass 映射的实体类
     * @param fileName
     * @param response
     * @return
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setStyle(EasyPoiExcelStyleUtil.class);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    /**
     * 功能描述：Map 集合导出
     *
     * @param list     实体集合
     * @param fileName 导出的文件名称
     * @param response
     * @return
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }

    /**
     * 功能描述：默认导出方法
     *
     * @param list         导出的实体集合
     * @param fileName     导出的文件名
     * @param pojoClass    pojo实体
     * @param exportParams ExportParams封装实体
     * @param response
     * @return
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * 功能描述：Excel导出
     *
     * @param fileName 文件名称
     * @param response
     * @param workbook Excel对象
     * @return
     */
    public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述：默认导出方法
     *
     * @param list     导出的实体集合
     * @param fileName 导出的文件名
     * @param response
     * @return
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * 功能描述：根据文件路径来导入Excel
     *
     * @param filePath   文件路径
     * @param titleRows  表标题的行数--对应数据对象
     * @param headerRows 表头行数
     * @param pojoClass  Excel实体类
     * @return
     */
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        //判断文件是否存在
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 功能描述：根据接收的Excel文件来导入Excel,并封装成实体类
     *
     * @param file       上传的文件
     * @param titleRows  表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass  Excel实体类
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("excel文件不能为空");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    /**
     * 功能描述：根据接收的Excel文件来导入多个sheet,根据索引可返回一个集合
     * @param filePath   导入文件路径
     * @param sheetIndex  导入sheet索引
     * @param titleRows  表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass  Excel实体类
     * @return
     */
    public static <T> List<T> importExcel(String filePath, int sheetIndex, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        // 根据file得到Workbook,主要是要根据这个对象获取,传过来的excel有几个sheet页
        ImportParams params = new ImportParams();
        // 第几个sheet页
        params.setStartSheetIndex(sheetIndex);
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 功能描述：根据接收的Excel文件来导入多个sheet,根据索引可返回一个集合
     * @param list   实体对象集合
     * @param type  表格类型
     * @return
     */
    public static Workbook exportExcel(List<Map<String, Object>> list, ExcelType type) {
        Workbook workbook = getWorkbook(type, 0);
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            Map<String, Object> map = (Map)var3.next();
            ExcelExportService service = new ExcelExportService();
            service.createSheet(workbook, (ExportParams)map.get("title"), (Class)map.get("entity"), (Collection)map.get("data"));
        }
        return workbook;
    }
    private static Workbook getWorkbook(ExcelType type, int size) {
        if (ExcelType.HSSF.equals(type)) {
            return new HSSFWorkbook();
        } else {
            return (Workbook)(size < USE_SXSSF_LIMIT ? new XSSFWorkbook() : new SXSSFWorkbook());
        }
    }


}
