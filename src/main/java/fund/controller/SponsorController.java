package fund.controller;

import java.io.BufferedOutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fund.dto.Code;
import fund.dto.Sponsor;
import fund.dto.pagination.PaginationDM;
import fund.dto.pagination.PaginationSponsor;
import fund.mapper.CodeMapper;
import fund.mapper.SponsorLogMapper;
import fund.mapper.SponsorMapper;
import fund.mapper2.DataFileMapper;
import fund.service.C;
import fund.service.ExcelService;
import fund.service.LogService;
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

    @RequestMapping("/sponsor/list")
    public String list(Model model, @ModelAttribute("pagination") PaginationSponsor pagination) {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout";
        pagination.setRecordCount(sponsorMapper.selectCount(pagination));
        List<Sponsor> list = sponsorMapper.selectPage(pagination);
        List<Code> sponsorType1Codes = codeMapper.selectEnabledByCodeGroupId(1);
        List<Code> sponsorType2Codes = codeMapper.selectEnabledByCodeGroupId(2);
        model.addAttribute("list", list);
        model.addAttribute("sponsorType1Codes", sponsorType1Codes);
        model.addAttribute("sponsorType2Codes", sponsorType2Codes);
        return "sponsor/list";
    }

    @RequestMapping("/sponsor/excel")
    public void excel(HttpServletRequest req, HttpServletResponse response, @ModelAttribute("pagination") PaginationSponsor pagination) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return;
        pagination.setPageSize(10000000);
        pagination.setCurrentPage(1);
        List<Sponsor> list = sponsorMapper.selectPage(pagination);

        Workbook workbook = ExcelService.downloadSponsorListExcel(list);
        String fileName = URLEncoder.encode("회원목록.xlsx","UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
        try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) {
            workbook.write(output);
        }
    }


    @RequestMapping(value="/sponsor/edit", method=RequestMethod.GET)
    public String edit(@RequestParam("id") int id, @ModelAttribute("pagination") PaginationSponsor pagination, Model model) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout";
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

    @RequestMapping(value="/sponsor/edit", method=RequestMethod.POST, params="cmd=save")
    public String edit(Sponsor sponsor, @ModelAttribute("pagination") PaginationSponsor pagination, Model model) throws Exception {
        try {
            if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout";
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

    @RequestMapping("/sponsor/delete")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String delete(Model model, @RequestParam("id") int id, @ModelAttribute("pagination") PaginationSponsor pagination, RedirectAttributes ra) throws Exception {
        try {
            if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout";
            sponsorLogMapper.deleteBySponsorId(id);
            dataFileMapper.deleteByForeignId("sponsor", id);
            sponsorMapper.delete(id);
            ra.addFlashAttribute("successMsg", "삭제했습니다.");
        } catch (Exception e) {
            logService.logError(e);
            ra.addFlashAttribute("errorMsg", "삭제 실패!");
        }
        return "redirect:list?" + pagination.getQueryString();
    }

    @RequestMapping(value="/sponsor/create", method=RequestMethod.GET)
    public String create(@ModelAttribute("pagination") PaginationSponsor pagination, Model model) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout";
        Sponsor sponsor = new Sponsor();
        sponsor.setSponsorNo(sponsorMapper.generateSponsorNo());
        model.addAttribute("sponsor", sponsor);
        addCodesToModel(0, model);
        return "sponsor/edit";
    }

    @RequestMapping(value="/sponsor/create", method=RequestMethod.POST, params="cmd=check")
    public String createCheck(Sponsor sponsor, @ModelAttribute("pagination") PaginationSponsor pagination, Model model) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout";
        List<Sponsor> list = sponsorMapper.selectDuplicate(sponsor);
        model.addAttribute("list", list);
        return "sponsor/edit";
    }

    @RequestMapping(value="/sponsor/create", method=RequestMethod.POST, params="cmd=save")
    public String createSave(Sponsor sponsor, @ModelAttribute("pagination") PaginationSponsor pagination, Model model) throws Exception {
        try {
            if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout";
            sponsor.setSponsorNo(sponsorMapper.generateSponsorNo());
            sponsorMapper.insert(sponsor);
            logService.actionLog("회원 등록", "sponsor create", sponsor.getId(), sponsor.getSponsorNo());
            sponsorService.changeLog(new Sponsor(), sponsor);
            return "redirect:list";
        } catch (Exception e) {
            addCodesToModel(0, model);
            return logService.logErrorAndReturn(model, e, "sponsor/edit");
        }
    }

    @RequestMapping(value="/sponsor/files", method=RequestMethod.GET)
    public String files(@RequestParam("id") int id, @ModelAttribute("pagination") PaginationSponsor pagination, Model model) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout";
        model.addAttribute("sponsor", sponsorMapper.selectById(id));
        model.addAttribute("files", dataFileMapper.selectByForeignId("sponsor", id));
        String url = "/sponsor/files?id=" + id + "&" + pagination.getQueryString();
        model.addAttribute("url", URLEncoder.encode(url, "UTF-8"));
        addCodesToModel(id, model);
        return "sponsor/files";
    }

    @RequestMapping(value="/sponsor/log/list", method=RequestMethod.GET)
    public String log(@RequestParam("id") int id, @ModelAttribute("pagination") PaginationSponsor pagination, Model model) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout";
        model.addAttribute("sponsor", sponsorMapper.selectById(id));
        model.addAttribute("list", sponsorLogMapper.selectBySponsorId(id));
        String url = "/sponsor/files?id=" + id + "&" + pagination.getQueryString();
        model.addAttribute("url", URLEncoder.encode(url, "UTF-8"));
        addCodesToModel(id, model);
        return "sponsor/log/list";
    }


    @RequestMapping("/sponsor/dm")
    public String sendDM(Model model, @ModelAttribute("pagination") PaginationDM pagination) {
        if (!UserService.canAccess(C.메뉴_회원관리_우편발송)) return "redirect:/home/logout";
        if (StringUtils.isBlank(pagination.getStartDate())) pagination.setStartDate("1990-01-01");
        if (StringUtils.isBlank(pagination.getEndDate())) pagination.setEndDate("2090-01-01");
        pagination.setRecordCount(sponsorMapper.selectCountForDM(pagination));
        model.addAttribute("list", sponsorMapper.selectForDM(pagination));
        model.addAttribute("sponsorType2List", codeMapper.selectEnabledByCodeGroupId(C.코드그룹ID_회원구분));
        model.addAttribute("CodeID_Corporation", C.코드ID_법인);
        return "sponsor/dm";
    }

    @RequestMapping("/sponsor/dmx")
    public void sendDMxlsx(@ModelAttribute("pagination") PaginationDM pagination, HttpServletRequest req, HttpServletResponse response) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_우편발송)) return;
        if (StringUtils.isBlank(pagination.getStartDate()) == false) {
            int count = sponsorMapper.selectCountForDM(pagination);
            pagination.setRecordCount(count);
            pagination.setPageSize(count);
            List<Sponsor> list = sponsorMapper.selectForDM(pagination);

            Workbook workbook = ExcelService.downloadDMExcel(list);
            String fileName = URLEncoder.encode("우편발송.xlsx","UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
            try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) {
                workbook.write(output);
            }
        } else
            response.sendRedirect("dm");
    }

}