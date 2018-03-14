package com.example.user.util;

import com.example.user.entity.KmData;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取excel
 *
 * @author QuiFar
 * @version V1.0
 **/
public class ReadExcel {

    private ReadExcel() {
    }

    /**
     * 读取excel 内容
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static List<KmData> readXls(String path) throws IOException {

        List<KmData> result = new ArrayList<KmData>();

        InputStream is = new FileInputStream(path);
        //HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);

        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }

            // 循环行Row
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow hssfRow = xssfSheet.getRow(rowNum);

                //读取每个cell
                if (hssfRow != null) {

                    XSSFCell number = hssfRow.getCell(0);
                    XSSFCell name = hssfRow.getCell(1);
                    XSSFCell spec = hssfRow.getCell(2);
                    XSSFCell source = hssfRow.getCell(3);
                    XSSFCell productionUnit = hssfRow.getCell(4);
                    XSSFCell documentNumber = hssfRow.getCell(5);
                    XSSFCell type = hssfRow.getCell(6);
                    XSSFCell isPrescription = hssfRow.getCell(7);

                    // 是否医保
                    XSSFCell isMedical = hssfRow.getCell(8);
                    XSSFCell isSpecial = hssfRow.getCell(9);
                    XSSFCell unit = hssfRow.getCell(10);
                    XSSFCell agentType = hssfRow.getCell(11);

                    XSSFCell explain = hssfRow.getCell(12);

//                    result.add(getValue(number));
//                    result.add(getValue(name));
//                    result.add(getValue(spec));
//                    result.add(getValue(source));
//                    result.add(getValue(productionUnit));
//                    result.add(getValue(documentNumber));
//                    result.add(getValue(type));
//                    result.add(getValue(isPrescription));
//                    result.add(getValue(isMedical));
//                    result.add(getValue(isSpecial));
//                    result.add(getValue(agentType));
//                    result.add(getValue(explain));

                    //插入数据库
                    KmData kmData = new KmData();
                    kmData.setNumber(getValue(number));

                    if (StringUtils.isEmpty(kmData.getNumber())) {
                        continue;
                    }

                    kmData.setName(getValue(name));
                    kmData.setSpec(getValue(spec));
                    kmData.setSource(getValue(source));
                    kmData.setProductionUnit(getValue(productionUnit));
                    kmData.setDocumentNumber(getValue(documentNumber));
                    kmData.setType(getValue(type));
                    kmData.setIsPrescription(getValue(isPrescription));
                    kmData.setIsMedical(getValue(isMedical));
                    kmData.setIsSpecial(getValue(isSpecial));
                    kmData.setAgentType(getValue(agentType));
                    kmData.setExplain1(getValue(explain));
                    kmData.setUnit(getValue(unit));
                    result.add(kmData);

                }
            }
        }

        return result;
    }


    private static String getValue(XSSFCell hssfCell) {

        if (hssfCell == null) {
            return "";
        }

        if (hssfCell.getCellType() == CellType.BOOLEAN.getCode()) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == CellType.NUMERIC.getCode()) {

            DecimalFormat df = new DecimalFormat("0");
            return df.format(hssfCell.getNumericCellValue());

            //return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

}
