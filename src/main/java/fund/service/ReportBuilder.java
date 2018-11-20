package fund.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.export.type.HtmlSizeUnitEnum;

public class ReportBuilder {
    static String reportFolderPath;
    HttpServletRequest request;
    HttpServletResponse response;
    JasperReport report;
    String downloadFileName = "report";
    Map<String,Object> params;
    List<String> subReports;

    JRDataSource dataSource;
    Connection connection;

    public ReportBuilder(String reportFileName, String downloadFileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.request = request;
        this.response = response;
        this.downloadFileName  = URLEncoder.encode(downloadFileName, "UTF-8");
        if (reportFolderPath == null)
            reportFolderPath = request.getSession().getServletContext().getRealPath("/WEB-INF/report");
        String reportFilePath = reportFolderPath + "/" + reportFileName + ".jasper";
        this.report = (JasperReport)JRLoader.loadObjectFromFile(reportFilePath);
        params = new HashMap<String,Object>();
    }

    public void setCollection(Collection<?> collection) {
        this.dataSource = new JRBeanCollectionDataSource(collection);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void addSubReport(String fileName) {
        if (subReports == null) subReports = new ArrayList<String>();
        subReports.add(fileName);
    }

    public void setParameter(String name, String value) {
        params.put(name, value);
    }

    public void setParameter(Map<String,Object> map) {
        for (String key : map.keySet())
            this.params.put(key, map.get(key));
    }

    private void addSubReportToParams(Map<String,Object> params) {
        if (subReports != null && subReports.size() > 0)
            for (String s : subReports) {
                String path = reportFolderPath + "/" + s;
                params.put(s, path);
            }
    }

    public void build(String type) throws JRException, IOException {
        switch (type) {
        case "pdf": buildPDFReport(); break;
        case "xlsx": buildXlsxReport(); break;
        default: buildHtmlReport(); break;
        }
    }

    private List<JasperPrint> getJasperPrintList() throws JRException {
        JasperPrint jasperPrint = null;
        if (dataSource != null) jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
        if (connection != null) jasperPrint = JasperFillManager.fillReport(report, params, connection);
        List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
        jasperPrintList.add(jasperPrint);
        return jasperPrintList;
    }

    public void buildHtmlReport() throws JRException, IOException {
        response.setCharacterEncoding("UTF-8");
        params.put(JRParameter.IS_IGNORE_PAGINATION, true);
        if (subReports != null) addSubReportToParams(params);
        HtmlExporter exporter = new HtmlExporter();
        List<JasperPrint> jasperPrintList = getJasperPrintList();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
        SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
        configuration.setIgnorePageMargins(true);
        configuration.setBorderCollapse("collapse");
        configuration.setSizeUnit(HtmlSizeUnitEnum.POINT);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }

    public void buildXlsxReport() throws IOException, JRException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=" + downloadFileName  + ";");
        params.put(JRParameter.IS_IGNORE_PAGINATION, true);
        if (subReports != null) addSubReportToParams(params);
        List<JasperPrint> jasperPrintList = getJasperPrintList();
        JRXlsxExporter  exporter = new JRXlsxExporter ();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setOnePagePerSheet(false);
        configuration.setIgnorePageMargins(true);
        configuration.setIgnoreTextFormatting(true);
        configuration.setRemoveEmptySpaceBetweenColumns(true);
        configuration.setRemoveEmptySpaceBetweenRows(true);
        configuration.setWhitePageBackground(false);
        configuration.setCollapseRowSpan(false);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }

    private byte[] getPdfBytes() throws JRException {
        if (connection != null) return JasperRunManager.runReportToPdf(report, params, connection);
        if (dataSource != null) return JasperRunManager.runReportToPdf(report, null, dataSource);
        return null;
    }

    public void buildPDFReport() throws JRException, IOException {
        if (subReports != null) addSubReportToParams(params);
        byte[] bytes = null;
        bytes = getPdfBytes();
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);
        response.setHeader("Content-Disposition", "attachment;filename=" + downloadFileName  + ";");
        ServletOutputStream ouputStream = response.getOutputStream();
        ouputStream.write(bytes, 0, bytes.length);
        ouputStream.flush();
        ouputStream.close();
    }
}
