package fund.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fund.dto.Sal;
import fund.dto.Sponsor;
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


    ////////////////////////////// download
    static Font headerFont;
    static CellStyle headerCellStyle;
    static CellStyle numberStyle;
    static CellStyle n2Style;
    static CellStyle dateCellStyle;

    static void createStyle(Workbook workbook) {
        headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short)12);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        numberStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        numberStyle.setDataFormat(format.getFormat("#,####"));

        n2Style = workbook.createCellStyle();
        n2Style.setDataFormat(format.getFormat("#,####.##"));

        dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));
    }


    public static Workbook downloadExcel1a(List<Map<String,Object>> list) {
        Workbook workbook = new XSSFWorkbook();
        createStyle(workbook);
        Sheet sheet = workbook.createSheet("납입목록");

        Row headerRow = sheet.createRow(0);
        String[] columns = new String[] { "회원번호", "회원명", "회원구분", "소속", "정기/비정기", "기부목적", "납입일", "납입액", "납입방법", "약정번호", "상태", "시작일", "종료일", "월납입액" };
        setColumnHeader(headerRow, columns);

        for (int i = 0; i < list.size(); ++i) {
            Map<String, Object> map = list.get(i);

            Row row = sheet.createRow(i + 1);
            createCell(row, 0, (String)map.get("sponsorNo"));
            createCell(row, 1, (String)map.get("name"));
            createCell(row, 2, (String)map.get("sponsorType2Name"));
            createCell(row, 3, (String)map.get("churchName"));
            createCell(row, 4, (String)map.get("regular"));
            createCell(row, 5, (String)map.get("corporateName") + "/" + (String)map.get("organizationName") + "/" + (String)map.get("donationPurposeName"));
            createCell(row, 6, (Date)map.get("paymentDate"), dateCellStyle);
            createCell(row, 7, (Integer)map.get("amount"), numberStyle);
            createCell(row, 8, (String)map.get("paymentMethodName"));
            createCell(row, 9, (String)map.get("commitmentNo"));
            createCell(row, 10, (String)map.get("state"));
            createCell(row, 11, (Date)map.get("startDate"), dateCellStyle);
            createCell(row, 12, (Date)map.get("endDate"), dateCellStyle);
            createCell(row, 13, (Integer)map.get("amountPerMonth"), numberStyle);
        }

        autoSizeColumn(sheet, columns);
        return workbook;
    }

    public static Workbook downloadExcel1b(List<Map<String,Object>> list) {
        Workbook workbook = new XSSFWorkbook();
        createStyle(workbook);
        Sheet sheet = workbook.createSheet("납입합계");

        Row headerRow = sheet.createRow(0);

        String[] columns = new String[] { "회원번호", "회원명", "회원구분", "소속", "납입액", "납입건수" };
        setColumnHeader(headerRow, columns);

        for (int i = 0; i < list.size(); ++i) {
            Map<String, Object> map = list.get(i);

            Row row = sheet.createRow(i + 1);
            createCell(row, 0, (String)map.get("sponsorNo"));
            createCell(row, 1, (String)map.get("name"));
            createCell(row, 2, (String)map.get("sponsorType2Name"));
            createCell(row, 3, (String)map.get("churchName"));
            createCell(row, 4, (Long)map.get("amount"), numberStyle);
            createCell(row, 5, (Integer)map.get("count"), numberStyle);
        }
        autoSizeColumn(sheet, columns);
        return workbook;
    }

    public static Workbook downloadExcel2(String title, List<Map<String,Object>> list) {
        Workbook workbook = new XSSFWorkbook();
        createStyle(workbook);
        Sheet sheet = workbook.createSheet("납입합계");

        Row headerRow = sheet.createRow(0);

        String[] columns = new String[] { title, "회원수", "납입수", "금액", "비율" };
        setColumnHeader(headerRow, columns);

        for (int i = 0; i < list.size(); ++i) {
            Map<String, Object> map = list.get(i);

            Row row = sheet.createRow(i + 1);
            createCell(row, 0, (String)map.get("name"));
            createCell(row, 1, (Integer)map.get("sponsorCount"), numberStyle);
            createCell(row, 2, (Integer)map.get("paymentCount"), numberStyle);
            createCell(row, 3, (Long)map.get("amount"), numberStyle);

            BigDecimal value = (BigDecimal)map.get("ratio");
            if (value != null)
                createCell(row, 4, value.doubleValue(), n2Style);
        }
        autoSizeColumn(sheet, columns);
        return workbook;
    }

    static void createCell(Row row, int index, String value) {
        if (value == null) return;
        row.createCell(index).setCellValue(value);
    }

    static void createCell(Row row, int index, String value1, String value2) {
        if (value1 == null) value1 = "";
        if (value2 == null) value2 = "";
        row.createCell(index).setCellValue(value1 + value2);
    }

    static void createCell(Row row, int index, String value1, String value2, String value3) {
        if (value1 == null) value1 = "";
        if (value2 == null) value2 = "";
        if (value3 == null) value3 = "";
        row.createCell(index).setCellValue(value1 + value2 + value3);
    }

    static void createCell(Row row, int index, String value, CellStyle cellStyle) {
        if (value == null) return;
        row.createCell(index).setCellValue(value);
        row.getCell(index).setCellStyle(cellStyle);
    }

    static void createCell(Row row, int index, Date value, CellStyle cellStyle) {
        if (value == null) return;
        row.createCell(index).setCellValue(value);
        row.getCell(index).setCellStyle(cellStyle);
    }

    static void createCell(Row row, int index, Integer value) {
        if (value == null) return;
        row.createCell(index).setCellValue(value);
    }

    static void createCell(Row row, int index, Integer value, CellStyle cellStyle) {
        if (value == null) return;
        row.createCell(index).setCellValue(value);
        row.getCell(index).setCellStyle(cellStyle);
    }

    static void createCell(Row row, int index, Long value) {
        if (value == null) return;
        row.createCell(index).setCellValue(value);
    }

    static void createCell(Row row, int index, Long value, CellStyle cellStyle) {
        if (value == null) return;
        row.createCell(index).setCellValue(value);
        row.getCell(index).setCellStyle(cellStyle);
    }

    static void createCell(Row row, int index, Double value) {
        if (value == null) return;
        row.createCell(index).setCellValue(value);
    }

    static void createCell(Row row, int index, Double value, CellStyle cellStyle) {
        if (value == null) return;
        row.createCell(index).setCellValue(value);
        row.getCell(index).setCellStyle(cellStyle);
    }

    public static Workbook downloadDMExcel(List<Sponsor> list) {
        Workbook workbook = new XSSFWorkbook();
        createStyle(workbook);
        Sheet sheet = workbook.createSheet("우편발송");

        String[] columns = new String[] { "회원번호", "이름", "회원구분", "소속", "직장명", "부서/직위", "법인담당자", "우편번호", "주소", "상세주소", "이메일", "휴대폰"};
        Row headerRow = sheet.createRow(0);
        setColumnHeader(headerRow, columns);

        for (int i = 0; i < list.size(); ++i) {
            Sponsor sponsor = list.get(i);

            Row row = sheet.createRow(i + 1);
            createCell(row, 0, sponsor.getSponsorNo());
            createCell(row, 1, sponsor.getName());
            createCell(row, 2, sponsor.getSponsorType2());
            createCell(row, 3, sponsor.getChurch());
            createCell(row, 4, sponsor.getCompany());
            createCell(row, 5, sponsor.getDepartment(), " / ", sponsor.getPosition());
            createCell(row, 6, sponsor.getLiaison());
            createCell(row, 7, sponsor.getPostCode());
            createCell(row, 8, sponsor.getRoadAddress());
            createCell(row, 9, sponsor.getDetailAddress());
            createCell(row, 10, sponsor.getEmail());
            createCell(row, 11, sponsor.getMobilePhone());
        }
        autoSizeColumn(sheet, columns);
        return workbook;
    }

    public static Workbook downloadSponsorListExcel(List<Sponsor> list) {
        Workbook workbook = new XSSFWorkbook();
        createStyle(workbook);
        Sheet sheet = workbook.createSheet("회원목록");

        String[] columns = new String[] { "회원번호", "이름", "가입구분", "회원구분", "소속", "가입일", "핸드폰번호", "이메일"};
        Row headerRow = sheet.createRow(0);
        setColumnHeader(headerRow, columns);

        for (int i = 0; i < list.size(); ++i) {
            Sponsor sponsor = list.get(i);

            Row row = sheet.createRow(i + 1);
            createCell(row, 0, sponsor.getSponsorNo());
            createCell(row, 1, sponsor.getName());
            createCell(row, 2, sponsor.getSponsorType1());
            createCell(row, 3, sponsor.getSponsorType2());
            createCell(row, 4, sponsor.getChurch());
            createCell(row, 5, sponsor.getSignUpDate(), dateCellStyle);
            createCell(row, 6, sponsor.getMobilePhone());
            createCell(row, 7, sponsor.getEmail());
        }
        autoSizeColumn(sheet, columns);
        return workbook;
    }

    private static void autoSizeColumn(Sheet sheet, String[] columns) {
        for(int i = 0; i < columns.length; i++)
            sheet.autoSizeColumn(i);
    }

    private static void setColumnHeader(Row headerRow, String[] columns) {
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    public static Workbook downloadTaxDataExcel(List<Map<String,Object>> list) {
        Workbook workbook = new XSSFWorkbook();
        createStyle(workbook);
        Sheet sheet = workbook.createSheet("국세청보고자료");

        Row headerRow = sheet.createRow(0);
        String[] columns = new String[] { "주민번호", "회원명", "기부내용구분", "기부금단체코드", "납입일", "납입액" };
        setColumnHeader(headerRow, columns);

        for (int i = 0; i < list.size(); ++i) {
            Map<String, Object> map = list.get(i);

            Row row = sheet.createRow(i + 1);
            createCell(row, 0, (String)map.get("juminNo"));
            createCell(row, 1, (String)map.get("name"));
            createCell(row, 2, (String)map.get("code1"));
            createCell(row, 3, (String)map.get("code2"));
            createCell(row, 4, (Date)map.get("paymentDate"), dateCellStyle);
            createCell(row, 5, (Integer)map.get("amount"), numberStyle);
        }

        autoSizeColumn(sheet, columns);
        return workbook;
    }

}
