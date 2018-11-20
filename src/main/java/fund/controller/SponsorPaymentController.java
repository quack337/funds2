package fund.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fund.dto.Code;
import fund.dto.Commitment;
import fund.dto.Payment;
import fund.dto.pagination.PaginationSponsor;
import fund.mapper.CodeMapper;
import fund.mapper.CommitmentMapper;
import fund.mapper.DonationPurposeMapper;
import fund.mapper.PaymentInKindMapper;
import fund.mapper.PaymentMapper;
import fund.mapper.SponsorMapper;
import fund.mapper2.DataFileMapper;
import fund.service.C;
import fund.service.LogService;
import fund.service.UserService;

@Controller
public class SponsorPaymentController extends BaseController {

    @Autowired CodeMapper codeMapper;
    @Autowired SponsorMapper sponsorMapper;
    @Autowired PaymentMapper paymentMapper;
    @Autowired PaymentInKindMapper paymentInKindMapper;
    @Autowired CommitmentMapper commitmentMapper;
    @Autowired DonationPurposeMapper donationPurposeMapper;
    @Autowired DataFileMapper dataFileMapper;
    @Autowired LogService logService;

    List<Code> 비정기납입방법;

    @ModelAttribute
    void modelAttr1(Model model, @RequestParam("sid") int sid, @ModelAttribute("pagination") PaginationSponsor pagination) throws Exception {
        model.addAttribute("fileCount", dataFileMapper.selectCountByForeignId("sponsor", sid));
    }

    static final String[] orderBy = new String[] { "paymentDate DESC", "paymentDate", "ID DESC", "ID" };

    // 정기 납입
    @RequestMapping("/sponsor/payment/list1.do")
    public String list1(@RequestParam("sid") int sid, @ModelAttribute("pagination") PaginationSponsor pagination, Model model) {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        model.addAttribute("commitments", commitmentMapper.selectBySponsorId(sid));
        addCodesToModel(model, sid);
        return "sponsor/payment/list1";
    }

    @RequestMapping(value="/sponsor/payment/list1ajax.do", method=RequestMethod.POST)
    public String list1ajax(@RequestParam("sid") int sid, @RequestParam(value="commitmentId", required=false, defaultValue="0") int commitmentId, Model model) {
        model.addAttribute("list", paymentMapper.selectPaymentList1(sid, commitmentId));
        return "sponsor/payment/list1ajax/ajax";
    }

    @RequestMapping(value="/sponsor/payment/list1updateajax.do", method=RequestMethod.POST)
    public String list1updateajax(Commitment commitment, Model model) {
        paymentMapper.updateDonationPurposeId(commitment);
        return list1ajax(0, commitment.getId(), model);
    }

    // 비정기 납입
    @RequestMapping(value="/sponsor/payment/list2.do", method=RequestMethod.GET)
    public String list2(@RequestParam("sid") int sid, @ModelAttribute("pagination") PaginationSponsor pagination, Model model) {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        model.addAttribute("list", paymentMapper.selectPaymentList2(sid));
        addCodesToModel(model, sid);
        return "sponsor/payment/list2";
    }

    @RequestMapping(value="/sponsor/payment/list2.do", method=RequestMethod.POST)
    public String list2(@RequestParam("sid") int sid, @ModelAttribute("pagination") PaginationSponsor pagination, Commitment commitment, Model model) {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        paymentMapper.updateDonationPurposeId2(commitment);
        model.addAttribute("list", paymentMapper.selectPaymentList2(sid));
        addCodesToModel(model, sid);
        return "sponsor/payment/list2";
    }

    @RequestMapping(value="/sponsor/payment/edit2.do", method=RequestMethod.GET)
    public String edit2(Model model, @RequestParam("sid") int sid, @RequestParam("id") int id) {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        model.addAttribute("payment", paymentMapper.selectById(id));
        addCodesToModel(model, sid);
        return "sponsor/payment/edit2";
    }

    public List<Code> get비정기납입방법() {
        List<Code> list = codeMapper.selectEnabledByCodeGroupId(C.코드그룹ID_비정기납입방법);
        for (int i = 0; i < list.size(); ++i) {
            Code code = list.get(i);
            if (code.getCodeName().equals("현물")) { list.remove(i); break; }
        }
        return list;
    }

    private void addCodesToModel(Model model, int sponsorId) {
        model.addAttribute("sponsor", sponsorMapper.selectById(sponsorId));
        model.addAttribute("donationPurposes", donationPurposeMapper.selectNotClosed());
        if (비정기납입방법 == null) 비정기납입방법 = get비정기납입방법();
        model.addAttribute("paymentMethods", 비정기납입방법);
    }

    private String redirectToList2(Model model, int sid) {
        PaginationSponsor pagination = (PaginationSponsor)model.asMap().get("pagination");
        String qs = String.format("sid=%d&%s", sid, pagination.getQueryString());
        return "redirect:list2.do?" + qs;
    }

    @RequestMapping(value="/sponsor/payment/edit2.do", method=RequestMethod.POST, params="cmd=save")
    public String edit2save(Model model, @RequestParam("sid") int sid, Payment payment) throws Exception {
        try {
            if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
            paymentMapper.update(payment);
            return redirectToList2(model, sid);
        } catch (Exception e) {
            addCodesToModel(model, sid);
            return logService.logErrorAndReturn(model, e, "sponsor/payment/edit2");
        }
    }

    @RequestMapping(value="/sponsor/payment/edit2.do", method=RequestMethod.POST, params="cmd=delete")
    public String edit2delete(Model model, @RequestParam("sid") int sid, @RequestParam("id") int id) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        paymentMapper.delete(id);
        return redirectToList2(model, sid);
    }

    @RequestMapping(value="/sponsor/payment/create2.do", method=RequestMethod.GET)
    public String create2(Model model, @RequestParam("sid") int sid) {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        model.addAttribute("payment", new Payment());
        addCodesToModel(model, sid);
        return "sponsor/payment/edit2";
    }

    @RequestMapping(value="/sponsor/payment/create2.do", method=RequestMethod.POST)
    public String create2(Model model, @RequestParam("sid") int sid, Payment payment) throws Exception {
        try {
            if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
            payment.setSponsorId(sid);
            paymentMapper.insert(payment);
            return redirectToList2(model, sid);
        } catch (Exception e) {
            addCodesToModel(model, sid);
            return logService.logErrorAndReturn(model, e, "sponsor/payment/edit2");
        }
    }

    // 현물 납입
    @RequestMapping(value="/sponsor/payment/list3.do", method=RequestMethod.GET)
    public String list3(@RequestParam("sid") int sid, @ModelAttribute("pagination") PaginationSponsor pagination, Model model) {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        model.addAttribute("list", paymentMapper.selectPaymentList3(sid));
        addCodesToModel(model, sid);
        return "sponsor/payment/list3";
    }

    @RequestMapping(value="/sponsor/payment/create3.do", method=RequestMethod.GET)
    public String create3(Model model, @RequestParam("sid") int sid) {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        model.addAttribute("payment", new Payment());
        addCodesToModel(model, sid);
        return "sponsor/payment/edit3";
    }

    private String redirectToList3(Model model, int sid) {
        PaginationSponsor pagination = (PaginationSponsor)model.asMap().get("pagination");
        String qs = String.format("sid=%d&%s", sid, pagination.getQueryString());
        return "redirect:list3.do?" + qs;
    }

    private String redirectToEdit3(Model model, int sid, int pid) {
        PaginationSponsor pagination = (PaginationSponsor)model.asMap().get("pagination");
        String qs = String.format("sid=%d&pid=%d&%s", sid, pid, pagination.getQueryString());
        return "redirect:edit3.do?" + qs;
    }

    @RequestMapping(value="/sponsor/payment/edit3.do", method=RequestMethod.GET)
    public String edit3(Model model, @RequestParam("sid") int sid, @RequestParam("id") int id) {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        model.addAttribute("payment", paymentMapper.selectById(id));
        model.addAttribute("list", paymentInKindMapper.selectByPaymentId(id));
        addCodesToModel(model, sid);
        return "sponsor/payment/edit3";
    }

    @RequestMapping(value="/sponsor/payment/edit3.do", method=RequestMethod.POST, params="cmd=save")
    public String edit3save(Model model, @RequestParam("sid") int sid, Payment payment) throws Exception {
        try {
            if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
            paymentMapper.update(payment);
            return redirectToList3(model, sid);
        } catch (Exception e) {
            addCodesToModel(model, sid);
            return logService.logErrorAndReturn(model, e, "sponsor/payment/edit3");
        }
    }

    @RequestMapping(value="/sponsor/payment/edit3.do", method=RequestMethod.POST, params="cmd=delete")
    public String edit3delete(Model model, @RequestParam("sid") int sid, @RequestParam("id") int id) throws Exception {
        if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
        paymentMapper.delete(id);
        return redirectToList3(model, sid);
    }

    @RequestMapping(value="/sponsor/payment/create3.do", method=RequestMethod.POST)
    public String create3(Model model, @RequestParam("sid") int sid, Payment payment, @RequestParam("cmd") String cmd) throws Exception {
        try {
            if (!UserService.canAccess(C.메뉴_회원관리_회원관리)) return "redirect:/home/logout.do";
            payment.setSponsorId(sid);
            payment.setPaymentMethodId(C.코드ID_현물);
            paymentMapper.insert(payment);
            if ("addItem".equals(cmd))
                return redirectToEdit3(model, sid, payment.getId());
            else
                return redirectToList3(model, sid);
        } catch (Exception e) {
            addCodesToModel(model, sid);
            return logService.logErrorAndReturn(model, e, "sponsor/payment/edit3");
        }
    }


}
