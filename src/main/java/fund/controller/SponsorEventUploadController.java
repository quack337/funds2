package fund.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fund.dto.Sponsor;
import fund.dto.SponsorEvent;
import fund.mapper.SponsorEventMapper;
import fund.mapper.SponsorMapper;
import fund.service.C;
import fund.service.ExcelService;
import fund.service.LogService;
import fund.service.UserService;

@Controller
public class SponsorEventUploadController extends BaseController {

    @Autowired SponsorMapper sponsorMapper;
    @Autowired SponsorEventMapper sponsorEventMapper;
    @Autowired LogService logService;
    @Autowired ExcelService excelService;

    @RequestMapping(value="/sponsor/event/upload", method=RequestMethod.GET)
    public String upload() {
        if (!UserService.canAccess(C.메뉴_회원관리_예우업로드)) return "redirect:/home/logout";
        return "sponsor/event/upload";
    }

    @RequestMapping(value="/sponsor/event/upload", method=RequestMethod.POST, params="cmd=upload")
    public String upload(Model model, @RequestParam("file") MultipartFile file, HttpSession session) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_예우업로드)) return "redirect:/home/logout";

        String redirect1 = "redirect:upload";
        if (file.getSize() <= 0) return redirect1;
        List<SponsorEvent> list = excelService.get예우업로드Result(file.getInputStream());
        for (SponsorEvent e : list) {
            Sponsor sponsor = sponsorMapper.selectBySponsorNo(e.getSponsorNo());
            if (sponsor != null) {
                e.setSponsorId(sponsor.getId());
                e.setSponsorName(sponsor.getName());
                e.setUserId(UserService.getCurrentUser().getId());
            }
        }
        if (list.size() <= 0) return redirect1;
        session.setAttribute("list_notSaved", list);
        return "sponsor/event/upload_result";
    }

    @RequestMapping(value="/sponsor/event/upload", method=RequestMethod.POST, params="cmd=save")
    public String upload(Model model, HttpSession session) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_예우업로드)) return "redirect:/home/logout";

        @SuppressWarnings("unchecked")
        List<SponsorEvent> list = (List<SponsorEvent>)session.getAttribute("list_notSaved");
        List<SponsorEvent> list_saved = new ArrayList<SponsorEvent>();
        List<SponsorEvent> list_notSaved = new ArrayList<SponsorEvent>();
        for (SponsorEvent e : list)
            if (e.getSponsorId() > 0) {
                sponsorEventMapper.insert(e);
                list_saved.add(e);
            } else
                list_notSaved.add(e);
        session.setAttribute("list_notSaved", list_notSaved);
        session.setAttribute("list_saved", list_saved);
        return "sponsor/event/upload_result";
    }

}
