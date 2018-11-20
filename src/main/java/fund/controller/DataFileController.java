package fund.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fund.dto.DataFile;
import fund.mapper2.DataFileMapper;

@Controller
public class DataFileController {

    @Autowired DataFileMapper dataFileMapper;

    @RequestMapping(value="/dataFile/upload.do", method=RequestMethod.POST)
    public String upload(@RequestParam("foreignId") int foreignId,
            @RequestParam("foreignType") String foreignType,
            @RequestParam("returnUrl") String url,
            @RequestParam("data") MultipartFile uploadedFile) throws IOException {
        url = URLDecoder.decode(url, "UTF-8");
        if (uploadedFile.getSize() > 0 ) {
            DataFile dataFile = new DataFile();
            dataFile.setForeignType(foreignType);
            dataFile.setForeignId(foreignId);
            dataFile.setFileName(Paths.get(uploadedFile.getOriginalFilename()).getFileName().toString());
            dataFile.setFileSize((int)uploadedFile.getSize());
            dataFile.setData(uploadedFile.getBytes());
            dataFileMapper.insert(dataFile);
        }
        return "redirect:" + url;
    }

    @RequestMapping("/dataFile/download.do")
    public void download(@RequestParam("id") int id, HttpServletResponse response) throws IOException {
        DataFile file = dataFileMapper.selectById(id);
        if (file == null) return;
        String fileName = URLEncoder.encode(file.getFileName(),"UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
        try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) {
            output.write(file.getData());
        }
    }

    @RequestMapping("/dataFile/delete.do")
    public String dalete(Model model, @RequestParam("id") int id, @RequestParam("returnUrl") String url) {
        dataFileMapper.delete(id);
        return "redirect:" + url;
    }
}
