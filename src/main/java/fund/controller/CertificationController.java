package fund.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fund.dto.Certificate;
import fund.dto.User;
import fund.dto.pagination.PaginationReceipt;
import fund.mapper.CertificateMapper;
import fund.service.C;
import fund.service.LogService;
import fund.service.ReportBuilder;
import fund.service.UserService;

@Controller
public class CertificationController extends BaseController {

    @Autowired CertificateMapper certificateMapper;
    @Autowired LogService logService;
    @Autowired UserService userService;

    @RequestMapping("/certificate/{type}/list")
    public String list(Model model, @PathVariable("type") int type, @ModelAttribute("pagination") PaginationReceipt pagination) {
        if (type == 0 && !UserService.canAccess(C.메뉴_증서_장학증서)) return "redirect:/home/logout";
        if (type == 1 && !UserService.canAccess(C.메뉴_증서_기부증서)) return "redirect:/home/logout";
        userService.사용자소속제한(model, pagination, null);
        pagination.setOd(type);
        pagination.setRecordCount(certificateMapper.selectCount(pagination));
        model.addAttribute("list", certificateMapper.selectPage(pagination));
        return "certificate/list" + type;
    }

    @RequestMapping("/certificate/{type}/detail")
    public String edit(Model model, @PathVariable("type") int type, @RequestParam("id") int id, @ModelAttribute("pagination") PaginationReceipt pagination) {
        if (type == 0 && !UserService.canAccess(C.메뉴_증서_장학증서)) return "redirect:/home/logout";
        if (type == 1 && !UserService.canAccess(C.메뉴_증서_기부증서)) return "redirect:/home/logout";
        model.addAttribute("certificate", certificateMapper.selectById(id));
        return "certificate/detail" + type;
    }

    @RequestMapping("/certificate/{type}/delete")
    public String delete(RedirectAttributes ra, @PathVariable("type") int type, @RequestParam("id") int id, @ModelAttribute("pagination") PaginationReceipt pagination) {
        if (type == 0 && !UserService.canAccess(C.메뉴_증서_장학증서)) return "redirect:/home/logout";
        if (type == 1 && !UserService.canAccess(C.메뉴_증서_기부증서)) return "redirect:/home/logout";
        User user = UserService.getCurrentUser();
        Certificate cert = certificateMapper.selectById(id);
        if (user.getUserType().contains("관리자") ||
            cert.getUserId() == UserService.getCurrentUser().getId()) {
            certificateMapper.delete(id);
            ra.addFlashAttribute("successMsg", "삭제했습니다.");
        }
        return "redirect:list?" + pagination.getQueryString();
    }

    @RequestMapping(value = "/certificate/{type}/create", method = RequestMethod.GET)
    public String insert(Model model, @PathVariable("type") int type, @ModelAttribute("pagination") PaginationReceipt pagination) {
        if (type == 0 && !UserService.canAccess(C.메뉴_증서_장학증서)) return "redirect:/home/logout";
        if (type == 1 && !UserService.canAccess(C.메뉴_증서_기부증서)) return "redirect:/home/logout";
        Certificate certificate = new Certificate();
        certificate.setCertificateNo(certificateMapper.generateCertificateNo(type));
        certificate.setUserName(UserService.getCurrentUser().getName());
        if (type == 1) userService.사용자소속제한(model, pagination, null);
        model.addAttribute("certificate", certificate);
        return "certificate/edit" + type;
    }

    @RequestMapping(value = "/certificate/{type}/create", method = RequestMethod.POST)
    public String insert(Model model, @PathVariable("type") int type, Certificate certificate, @ModelAttribute("pagination") PaginationReceipt pagination) {
        try {
            if (type == 0 && !UserService.canAccess(C.메뉴_증서_장학증서)) return "redirect:/home/logout";
            if (type == 1 && !UserService.canAccess(C.메뉴_증서_기부증서)) return "redirect:/home/logout";
            if (type == 0) certificate.setCorporateId(1);
            certificate.setType(type);
            certificate.setCertificateNo(certificateMapper.generateCertificateNo(type));
            certificate.setUserId(UserService.getCurrentUser().getId());
            certificateMapper.insert(certificate);
            return "redirect:detail?id=" + certificate.getId() + "&" + pagination.getQueryString();
        } catch (Exception e) {
            if (type == 1) userService.사용자소속제한(model, pagination, null);
            return logService.logErrorAndReturn(model, e, "certificate/edit" + type);
        }
    }

    @RequestMapping("/certificate/{type}/report")
    public void report(@PathVariable("type") int type, @RequestParam("id") int id,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (type == 0 && !UserService.canAccess(C.메뉴_증서_장학증서)) return;
        if (type == 1 && !UserService.canAccess(C.메뉴_증서_기부증서)) return;
        Certificate certificate = certificateMapper.selectById(id);
        certificate.setUserName(UserService.getCurrentUser().getName());
        List<Certificate> list = new ArrayList<>();
        list.add(certificate);
        String reportName;
        String downloadName;
        if (type == 0){
        	reportName="printScholarship";
        	downloadName="장학증서";
        } else {
        	reportName="printDonation";
        	downloadName="기부증서";
        }
        ReportBuilder reportBuilder = new ReportBuilder(reportName, downloadName + ".pdf", request, response);
        reportBuilder.setCollection(list);
        reportBuilder.setParameter("imagesDir", request.getServletContext().getRealPath("/res/images/") );
        reportBuilder.build("pdf");
    }
}
