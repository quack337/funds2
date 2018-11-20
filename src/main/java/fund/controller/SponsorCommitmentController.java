package fund.controller;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fund.dto.Commitment;
import fund.dto.Sponsor;
import fund.dto.pagination.PaginationSponsor;
import fund.mapper.CodeMapper;
import fund.mapper.CommitmentMapper;
import fund.mapper.DonationPurposeMapper;
import fund.mapper.SponsorMapper;
import fund.mapper2.DataFileMapper;
import fund.service.C;
import fund.service.LogService;
import fund.service.UserService;
import fund.service.Util;

@Controller
public class SponsorCommitmentController extends BaseController {

    @Autowired CommitmentMapper commitmentMapper;
    @Autowired CodeMapper codeMapper;
    @Autowired DonationPurposeMapper donationPurposeMapper;
    @Autowired SponsorMapper sponsorMapper;
    @Autowired LogService logService;
    @Autowired DataFileMapper dataFileMapper;


    @ModelAttribute
        void modelAttr1(@ModelAttribute("pagination") PaginationSponsor pagination,
                        @RequestParam("sid") int sid, Model model) throws Exception {
        model.addAttribute("sponsor", sponsorMapper.selectById(sid));
        model.addAttribute("donationPurposes", donationPurposeMapper.selectNotClosed());
        model.addAttribute("paymentMethods", codeMapper.selectEnabledByCodeGroupId(C.코드그룹ID_정기납입방법));
        model.addAttribute("banks", codeMapper.selectEnabledByCodeGroupId(C.코드그룹ID_은행));
        model.addAttribute("fileCount", dataFileMapper.selectCountByForeignId("sponsor", sid));
    }

    @RequestMapping(value = "/sponsor/commitment/list.do", method = RequestMethod.GET)
    public String list(Model model, @RequestParam("sid") int sid) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        model.addAttribute("list", commitmentMapper.selectBySponsorId(sid));
        return "sponsor/commitment/list";
    }

    private String redirectToList(Model model, int sid) throws Exception {
        PaginationSponsor pagination = (PaginationSponsor)model.asMap().get("pagination");
        String qs = String.format("sid=%d&%s", sid, pagination.getQueryString());
        return "redirect:list.do?" + qs;
    }


    @RequestMapping(value="/sponsor/commitment/edit.do", method=RequestMethod.GET)
    public String edit(Model model, @RequestParam("id") int id) throws ParseException {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
       model.addAttribute("commitment", commitmentMapper.selectById(id));
       return "sponsor/commitment/edit";
    }

    @RequestMapping(value="/sponsor/commitment/edit.do", method=RequestMethod.POST)
    public String edit(Model model, @RequestParam("sid") int sid, Commitment commitment, @RequestParam("cmd") String cmd) throws Exception {
        try {
            if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
            switch (cmd) {
            case "save":  commitmentMapper.update(commitment); logService.actionLog("약정 수정", "commitment edit save",commitment.getId(), commitment.getSponsorNo()); break;
            case "close":  commitmentMapper.updateEndDate(commitment.getId()); logService.actionLog("약정 종료", "commitment edit close",commitment.getId(), commitment.getSponsorNo()); break;
            case "delete": commitmentMapper.delete(commitment.getId()); break;
            case "open": commitmentMapper.open(commitment.getId()); logService.actionLog("약정 종료 취소", "commitment edit open",commitment.getId(), commitment.getSponsorNo()); break;
            }
            return redirectToList(model, sid);
        } catch (Exception e) {
            return logService.logErrorAndReturn(model, e, "sponsor/commitment/edit");
        }
    }

    String getEB13BirthDate(String juminNo) {
        if (juminNo == null) return "";
        juminNo = juminNo.replaceAll("-", "");
        int length = juminNo.length();
        if (length < 6) return "";
        if (length == 10) return juminNo;
        return juminNo.substring(0, 6);
    }

    @RequestMapping(value="/sponsor/commitment/create.do", method=RequestMethod.GET)
    public String create(Model model, @RequestParam("sid") int sid) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        Sponsor sponsor = sponsorMapper.selectById(sid);
        Commitment commitment = new Commitment();
        commitment.setSponsorId(sid);
        commitment.setBirthDate(getEB13BirthDate(sponsor.getJuminNo()));
        commitment.setAccountHolder(sponsor.getName());
        commitment.setCommitmentNo(commitmentMapper.generateCommitmentNo(sid));
        String today = Util.toYMD(new Date());
        commitment.setCommitmentDate(today);
        commitment.setStartDate(today);
        commitment.setPaymentDay(20);
        model.addAttribute("commitment", commitment);
        model.addAttribute("sponsor", sponsorMapper.selectById(sid));
        return "sponsor/commitment/create";
    }

    @RequestMapping(value="/sponsor/commitment/create.do", method=RequestMethod.POST)
    public String create(Model model, @RequestParam("sid") int sid, Commitment commitment) throws Exception {
        try {
            if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
            commitment.setCommitmentNo(commitmentMapper.generateCommitmentNo(sid));
            commitment.setSponsorId(sid);
            commitmentMapper.insert(commitment);
            logService.actionLog("약정 등록", "commitment create",commitment.getId(), commitment.getSponsorNo());
            return redirectToList(model, sid);
        } catch (Exception e) {
            return logService.logErrorAndReturn(model, e, "sponsor/commitment/create");
        }
    }
}