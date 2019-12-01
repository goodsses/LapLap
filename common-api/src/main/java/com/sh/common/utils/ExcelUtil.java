package com.sh.common.utils;

import com.sh.common.anno.Excel;
import com.sh.common.anno.ExcelProperty;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    private static final String EXTENSION_XLS = "xls";
    private static final String EXTENSION_XLSX = "xlsx";
    public static final String ALIGN_RIGHT = "RIGHT";

    public static <T> byte[] beanToExcelBytes(List<T> tList) {
        byte[] bytes = new byte[0];
        HSSFWorkbook workbook = new HSSFWorkbook();
        if (tList != null && !tList.isEmpty()) {
            T t0 = tList.get(0);
            Class<?> t0Clazz = t0.getClass();
            Excel excel = t0Clazz.getAnnotation(Excel.class);
            String sheetName = excel.sheetName();
            String[] headers = excel.header();
            int rowCount = 0;

            HSSFSheet sheet = workbook.createSheet(sheetName);
            //使B1列添加筛选功能
            CellRangeAddress filter = CellRangeAddress.valueOf("C1:E1");
            sheet.setAutoFilter(filter);
            if (headers.length > 0) {
                HSSFRow row = sheet.createRow(rowCount++);
                int cellCount = 0;
                for (String head : headers) {
                    HSSFCell cell = row.createCell(cellCount++);
                    cell.setCellValue(head);
                }
            }
            Map<String, Field> fieldMap = new HashMap<>(8);
            Class tempClazz = t0Clazz;
            do {
                Field[] declaredFields = tempClazz.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    fieldMap.put(declaredField.getName(), declaredField);
                }
                tempClazz = tempClazz.getSuperclass();
            } while (!tempClazz.equals(Object.class));
            for (T t : tList) {
                HSSFRow row = sheet.createRow(rowCount++);
                for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
                    Field field = entry.getValue();
                    ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                    if (excelProperty != null) {
                        int index = excelProperty.index();
                        Cell cell = row.createCell(index);
                        Method method = null;
                        try {
                            method = t0Clazz.getMethod("get" + StringUtils.capitalize(field.getName()));
                        } catch (NoSuchMethodException e) {
                            try {
                                method = t0Clazz.getMethod("is" + StringUtils.capitalize(field.getName()));
                            } catch (NoSuchMethodException e1) {
                                LOGGER.error("no such method: {}", field.getName());
                            }
                        }
                        String cellValue;
                        try {
                            cellValue = method.invoke(t).toString();
                        } catch (IllegalAccessException | InvocationTargetException | NullPointerException e) {
                            cellValue = "";
                        }
                        cell.setCellValue(cellValue);
                    }
                }
            }
            for (int colNum = 0; colNum < headers.length; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    // 当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellTypeEnum() == CellType.STRING) {
                            int length = currentCell.getStringCellValue().getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if (colNum == 0) {
                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else {
                    sheet.autoSizeColumn(colNum, true);
                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                workbook.write(baos);
                bytes = baos.toByteArray();
            } catch (IOException e) {
                LOGGER.error("", e);
            } finally {
                try {
                    baos.close();
                    workbook.close();
                } catch (IOException e) {
                    LOGGER.error("", e);
                }
            }
        }
        return bytes;
    }

    public static byte[] beans2ExcelBytes(List<Class<?>> tList, List<?>... lists) {
        byte[] bytes = new byte[0];
        XSSFWorkbook workbook = new XSSFWorkbook();
        int index = 0;
        for (Class<?> aClass : tList) {
            setCell(workbook, aClass, lists[index]);
            index++;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            LOGGER.error("", e);
        } finally {
            try {
                byteArrayOutputStream.close();
                workbook.close();
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
        return bytes;
    }

    public static void setCell(XSSFWorkbook workbook, Class<?> aClass, List<?> tList) {
        Excel excel = aClass.getAnnotation(Excel.class);
        // sheetName
        String sheetName = excel.sheetName();
        Field[] fields = aClass.getDeclaredFields();
        // 边框
        CellStyle defStyle = workbook.createCellStyle();
        defStyle.setBorderTop(BorderStyle.THIN);
        defStyle.setBorderLeft(BorderStyle.THIN);
        defStyle.setBorderBottom(BorderStyle.THIN);
        defStyle.setBorderRight(BorderStyle.THIN);
        // 表头
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.cloneStyleFrom(defStyle);
        headStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        List<String> headers = new ArrayList<>();
        for (Field field : fields) {
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (excelProperty != null) {
                headers.add(excelProperty.value()[0]);
            }
        }
        int rowCount = 0;
        XSSFSheet sheet = workbook.createSheet(sheetName);
        if (headers.size() > 0) {
            Row row = sheet.createRow(rowCount++);
            int cellCount = 0;
            for (String head : headers) {
                Cell cell = row.createCell(cellCount++);
                cell.setCellValue(head);
                cell.setCellStyle(headStyle);
            }
        }
        // 数据
        if (tList != null && !tList.isEmpty()) {
            // 格式
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.cloneStyleFrom(defStyle);
            DataFormat format = workbook.createDataFormat();
            // 字段定义
            Map<String, Field> fieldMap = new HashMap<>(8);
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                fieldMap.put(declaredField.getName(), declaredField);
            }
            for (Object t : tList) {
                // 行
                Row row = sheet.createRow(rowCount++);
                // 单元格写入值
                for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
                    Field field = entry.getValue();
                    ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                    if (excelProperty != null) {
                        int index = excelProperty.index();
                        int cellType = excelProperty.cellType();
                        int decimals = excelProperty.decimals();
                        boolean percent = excelProperty.percent();
                        Cell cell = row.createCell(index);
                        cell.setCellStyle(defStyle);
                        Method method = null;
                        try {
                            method = aClass.getMethod("get" + StringUtils.capitalize(field.getName()));
                        } catch (NoSuchMethodException e) {
                            try {
                                method = aClass.getMethod("is" + StringUtils.capitalize(field.getName()));
                            } catch (NoSuchMethodException e1) {
                                LOGGER.error("no such method: {}", field.getName());
                            }
                        }
                        Object cellValue;
                        try {
                            if (method != null) {
                                cellValue = method.invoke(t);
                            } else {
                                cellValue = "";
                            }
                        } catch (IllegalAccessException | InvocationTargetException | NullPointerException e) {
                            cellValue = "";
                        }
                        if (cellValue == null) {
                            cellValue = "";
                        }
                        // 日期
                        if (cellValue instanceof Date) {
                            cell.setCellValue(TypeUtil.formatDate((Date) cellValue, excelProperty.format()));
                        } else {
                            // CellType.NUMERIC
                            if (cellType == 0) {
                                if (StringUtils.isNotBlank(cellValue.toString())) {
                                    if (decimals == 4) {
                                        if (percent) {
                                            cellStyle.setDataFormat(format.getFormat("#,##0.0000%"));
                                            cell.setCellStyle(cellStyle);
                                        } else {
                                            cellStyle.setDataFormat(format.getFormat("#,##0.0000"));
                                            cell.setCellStyle(cellStyle);
                                        }
                                    } else {
                                        cellStyle.setDataFormat(format.getFormat("#,##0.00"));
                                        cell.setCellStyle(cellStyle);
                                    }
                                    cell.setCellValue(Double.valueOf(cellValue.toString()));
                                }
                            } else if (cellType == 2) {
                                if (StringUtils.isNotBlank(cellValue.toString())) {
                                    cell.setCellValue(Integer.valueOf(cellValue.toString()));
                                } else {
                                    cell.setCellValue(cellValue.toString());
                                }
                            } else {
                                cell.setCellValue(cellValue.toString());
                            }
                        }
                    }
                }
            }
            // 设置单元格宽度
            for (int colNum = 0; colNum < headers.size(); colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    Row currentRow;
                    //当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        Cell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellTypeEnum() == CellType.STRING) {
                            int length = currentCell.getStringCellValue().getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if (colNum == 0) {
                    sheet.setColumnWidth(colNum, (columnWidth + 2) * 256);
                } else {
                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                }
            }
        }
    }


    public static String getCellValue(final Cell cell) {
        return getCellValue(cell, null);
    }

    /**
     * 获取单元格Cell数据（支持公式计算值）
     */
    public static String getCellValue(final Cell cell, final FormulaEvaluator evaluator) {
        String value = "";
        if (cell != null) {
            CellType cellTypeEnum = cell.getCellTypeEnum();
            switch (cellTypeEnum) {
                case BLANK:
                    break;
                case BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case ERROR:
                    break;
                case NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        value = DateTime_Old.getSdf14().format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                        // 纯数字
                    } else {
                        value = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                case STRING:
                    value = cell.getStringCellValue().trim();
                    break;
                case FORMULA:
                    if (null != evaluator) {
                        final Cell cellValue = evaluator.evaluateInCell(cell);
                        value = getCellValue(cellValue, null);
                    } else {
                        value = cell.getStringCellValue().trim();
                    }
                    break;
                default:
                    value = cell.getStringCellValue().trim();
            }
        }
        return value;
    }
}