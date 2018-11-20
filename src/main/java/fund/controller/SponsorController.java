package fund.controller;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fund.dto.Code;
import fund.dto.Sponsor;
import fund.dto.pagination.Pagination;
import fund.dto.pagination.PaginationSponsor;
import fund.mapper.CodeMapper;
import fund.mapper.SponsorLogMapper;
import fund.mapper.SponsorMapper;
import fund.mapper2.DataFileMapper;
import fund.service.C;
import fund.service.LogService;
import fund.service.ReportBuilder;
import fund.service.SponsorService;
import fund.service.UserService;

@Controller
public class SponsorController extends BaseController {

    @Autowired SponsorMapper sponsorMapper;
    @Autowired CodeMapper codeMapper;
    @Autowired DataFileMapper dataFileMapper;
    @Autowired LogService logService;
    @Autowired SponsorService sponsorService;
    @Autowired SponsorLogMapper sponsorLogMapper;

    @RequestMapping("/sponsor/list.do")
    public String list(Model model, @ModelAttribute("pagination") PaginationSponsor pagination) {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        pagination.setRecordCount(sponsorMapper.selectCount(pagination));
        List<Sponsor> list = sponsorMapper.selectPage(pagination);
        List<Code> sponsorType1Codes = codeMapper.selectEnabledByCodeGroupId(1);
        List<Code> sponsorType2Codes = codeMapper.selectEnabledByCodeGroupId(2);
        model.addAttribute("list", list);
        model.addAttribute("sponsorType1Codes", sponsorType1Codes);
        model.addAttribute("sponsorType2Codes", sponsorType2Codes);
        return "sponsor/list";
    }

    @RequestMapping(value="/sponsor/edit.do", method=RequestMethod.GET)
    public String edit(@RequestParam("id") int id, @ModelAttribute("pagination") PaginationSponsor pagination, Model model) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        model.addAttribute("sponsor", sponsorMapper.selectById(id));
        addCodesToModel(id, model);
        return "sponsor/edit";
    }

    private void addCodesToModel(int sponsorId, Model model) {
        model.addAttribute("sponsorType1List", codeMapper.selectEnabledByCodeGroupId(C.코드그룹ID_가입구분));
        model.addAttribute("sponsorType2List", codeMapper.selectEnabledByCodeGroupId(C.코드그룹ID_회원구분));
        model.addAttribute("churchList", codeMapper.selectEnabledByCodeGroupId(C.코드그룹ID_소속교회));
        model.addAttribute("fileCount", dataFileMapper.selectCountByForeignId("sponsor", sponsorId));
    }

    @RequestMapping(value="/sponsor/edit.do", method=RequestMethod.POST, params="cmd=save")
    public String edit(Sponsor sponsor, @ModelAttribute("pagination") PaginationSponsor pagination, Model model) throws Exception {
        try {
            if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
            Sponsor s1 = sponsorMapper.selectById(sponsor.getId());
            sponsorMapper.update(sponsor);
            sponsorService.changeLog(s1, sponsor);
            model.addAttribute("successMsg", "저장했습니다.");
            logService.actionLog("회원 수정", "sponsor edit", sponsor.getId(), sponsor.getSponsorNo());
            return edit(sponsor.getId(), pagination, model);
        } catch (Exception e) {
            addCodesToModel(sponsor.getId(), model);
            return logService.logErrorAndReturn(model, e, "sponsor/edit");
        }
    }

    @RequestMapping(value="/sponsor/edit.do", method=RequestMethod.POST, params="cmd=delete")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String delete(Model model, @RequestParam("id") int id, @ModelAttribute("pagination") PaginationSponsor pagination) throws Exception {
        try {
            if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
            dataFileMapper.deleteByForeignId("sponsor", id);
            sponsorMapper.delete(id);
        } catch (Exception e) {
            return logService.logErrorAndReturn(model, e, "sponsor/edit");
        }
        return "redirect:list.do?" + pagination.getQueryString();
    }

    @RequestMapping(value="/sponsor/create.do", method=RequestMethod.GET)
    public String create(@ModelAttribute("pagination") PaginationSponsor pagination, Model model) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        Sponsor sponsor = new Sponsor();
        sponsor.setSponsorNo(sponsorMapper.generateSponsorNo());
        model.addAttribute("sponsor", sponsor);
        addCodesToModel(0, model);
        return "sponsor/edit";
    }

    @RequestMapping(value="/sponsor/create.do", method=RequestMethod.POST, params="cmd=check")
    public String createCheck(Sponsor sponsor, @ModelAttribute("pagination") PaginationSponsor pagination, Model model) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        List<Sponsor> list = sponsorMapper.selectDuplicate(sponsor);
        model.addAttribute("list", list);
        return "sponsor/edit";
    }

    @RequestMapping(value="/sponsor/create.do", method=RequestMethod.POST, params="cmd=save")
    public String createSave(Sponsor sponsor, @ModelAttribute("pagination") PaginationSponsor pagination, Model model) throws Exception {
        try {
            if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
            sponsor.setSponsorNo(sponsorMapper.generateSponsorNo());
            sponsorMapper.insert(sponsor);
            logService.actionLog("회원 등록", "sponsor create", sponsor.getId(), sponsor.getSponsorNo());
            sponsorService.changeLog(new Sponsor(), sponsor);
            return "redirect:list.do";
        } catch (Exception e) {
            addCodesToModel(0, model);
            return logService.logErrorAndReturn(model, e, "sponsor/edit");
        }
    }

    @RequestMapping(value="/sponsor/files.do", method=RequestMethod.GET)
    public String files(@RequestParam("id") int id, @ModelAttribute("pagination") PaginationSponsor pagination, Model model) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        model.addAttribute("sponsor", sponsorMapper.selectById(id));
        model.addAttribute("files", dataFileMapper.selectByForeignId("sponsor", id));
        String url = "/sponsor/files.do?id=" + id + "&" + pagination.getQueryString();
        model.addAttribute("url", URLEncoder.encode(url, "UTF-8"));
        addCodesToModel(id, model);
        return "sponsor/files";
    }

    @RequestMapping(value="/sponsor/log/list.do", method=RequestMethod.GET)
    public String log(@RequestParam("id") int id, @ModelAttribute("pagination") PaginationSponsor pagination, Model model) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        model.addAttribute("sponsor", sponsorMapper.selectById(id));
        model.addAttribute("list", sponsorLogMapper.selectBySponsorId(id));
        String url = "/sponsor/files.do?id=" + id + "&" + pagination.getQueryString();
        model.addAttribute("url", URLEncoder.encode(url, "UTF-8"));
        addCodesToModel(id, model);
        return "sponsor/log/list";
    }


    @RequestMapping("/sponsor/excel.do")
    public void excel(HttpServletRequest req, HttpServletResponse res) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return;
        List<Sponsor> list = sponsorMapper.selectAll();
        String fname = "회원목록.xlsx";
        ReportBuilder reportBuilder = new ReportBuilder("sponsorList", fname, req, res);
        reportBuilder.setCollection(list);
        reportBuilder.build("xlsx");
    }

    @RequestMapping("/sponsor/dm.do")
    public String sendDM(Model model, Pagination pagination) {
        if (!UserService.canAccess(C.메뉴_회원관리_우편발송)) return "redirect:/home/logout.do";
        if (StringUtils.isBlank(pagination.getStartDate()) == false) {
            pagination.setRecordCount(sponsorMapper.selectCountForDM(pagination));
            model.addAttribute("list", sponsorMapper.selectForDM(pagination));
        }
        model.addAttribute("sponsorType2List", codeMapper.selectEnabledByCodeGroupId(C.코드그룹ID_회원구분));
        return "sponsor/dm";
    }

    @RequestMapping("/sponsor/dmx.do")
    public void sendDMxlsx(Pagination pagination, HttpServletRequest req, HttpServletResponse res) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_우편발송)) return;
        if (StringUtils.isBlank(pagination.getStartDate()) == false) {
            int count = sponsorMapper.selectCountForDM(pagination);
            pagination.setRecordCount(count);
            pagination.setPageSize(count);
            List<Sponsor> list = sponsorMapper.selectForDM(pagination);
            String fname = "우편발송_" + pagination.getStartDate() + "_" + pagination.getEndDate() + ".xlsx";
            ReportBuilder reportBuilder = new ReportBuilder("sendDM", fname, req, res);
            reportBuilder.setCollection(list);
            reportBuilder.build("xlsx");
        } else
            res.sendRedirect("dm.do");
    }

}