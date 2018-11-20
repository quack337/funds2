package fund.service;

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fund.dto.Sal;
import fund.dto.SponsorEvent;
import fund.dto.Xfer;

@Service
public class ExcelService {

    static Date d1900_01_01 = new GregorianCalendar(1900, 0, 1).getTime();
    @Autowired LogService logService;

    public List<Xfer> get자동이체Result(InputStream input) throws Exception {
        List<Xfer> result = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(input);
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; ++i) {
            Sheet sheet = workbook.getSheetAt(i);
            for (int r = 1; r < sheet.getPhysicalNumberOfRows() ; ++r) {
                Row row = sheet.getRow(r);
                String accountNo = "에러";
                Date date = d1900_01_01;
                int amount = -9;
                String etc1 = "에러";
                String etc2 = "에러";
                boolean valid = false;
                try {
                    if (row.getCell(5) != null) accountNo = row.getCell(5).getStringCellValue();
                    if (StringUtils.isBlank(accountNo)) continue;
                    if ("159-22-01424-5(240-890012-16304)".equals(accountNo)) continue;
                    if (row.getCell(9) != null) date = getDateValue(row.getCell(9));
                    if (row.getCell(12) != null) amount = getIntValue(row.getCell(12));
                    if (row.getCell(16) != null) etc1 = row.getCell(16).getStringCellValue();
                    if (row.getCell(17) != null) etc2 = row.getCell(17).getStringCellValue();
                    valid = true;
                } catch (Exception e) {
                    logService.insert(e);
                }
                result.add(new Xfer(accountNo, date, amount, etc1, etc2, valid));
            }
        }
        return result;
    }

    public List<Sal> get급여공제Result(InputStream input) throws Exception {
        List<Sal> result = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(input);
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; ++i) {
            Sheet sheet = workbook.getSheetAt(i);
            for (int r = 1; r < sheet.getPhysicalNumberOfRows() ; ++r) {
                Row row = sheet.getRow(r);
                String commitmentNo = "에러";
                String name = "에러";
                int amount = -9;
                Date date = d1900_01_01;
                String etc = "에러";
                boolean valid = false;
                try {
                    if (row.getCell(0) != null) commitmentNo = row.getCell(0).getStringCellValue();
                    if (row.getCell(2) != null) name = row.getCell(2).getStringCellValue();
                    if (row.getCell(6) != null) amount = getIntValue(row.getCell(6));
                    if (row.getCell(8) != null) date = getDateValue(row.getCell(8));
                    if (row.getCell(10) != null) etc = row.getCell(10).getStringCellValue();
                    valid = true;
                } catch (Exception e) {
                    logService.insert(e);
                }
                result.add(new Sal(commitmentNo, name, amount, date, etc, valid));
            }
        }
        return result;
    }

    public List<SponsorEvent> get예우업로드Result(InputStream input) throws Exception {
        List<SponsorEvent> result = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(input);
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; ++i) {
            Sheet sheet = workbook.getSheetAt(i);
            for (int r = 1; r < sheet.getPhysicalNumberOfRows() ; ++r) {
                Row row = sheet.getRow(r);
                String sponsorNo = null;
                Date date = null;
                String description = null;
                String etc = null;
                try {
                    if (row.getCell(0) != null) sponsorNo = row.getCell(0).getStringCellValue();
                    if (StringUtils.isBlank(sponsorNo)) continue;
                    if (row.getCell(1) != null) description = row.getCell(1).getStringCellValue();
                    if (row.getCell(2) != null) date = getDateValue(row.getCell(2));
                    if (row.getCell(3) != null) etc = row.getCell(3).getStringCellValue();
                    result.add(new SponsorEvent(sponsorNo, description, new java.sql.Date(date.getTime()), etc));
                } catch (Exception e) {
                    logService.insert(e);
                }
            }
        }
        return result;
    }


    static int getIntValue(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_STRING)
            return (int)(double)Double.valueOf(cell.getStringCellValue().replaceAll(",", ""));
        return (int)cell.getNumericCellValue();
    }

    static Date getDateValue(Cell cell) throws ParseException {
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            String s = cell.getStringCellValue().replaceAll("/", "-");
            return Util.parseYMD(s);
        }
        return cell.getDateCellValue();
    }
}
