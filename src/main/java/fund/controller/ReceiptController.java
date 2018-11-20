package fund.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fund.dto.Corporate;
import fund.dto.Payment;
import fund.dto.Receipt;
import fund.dto.Sponsor;
import fund.dto.pagination.Pagination;
import fund.dto.pagination.PaginationReceipt;
import fund.dto.param.Wrapper;
import fund.mapper.CorporateMapper;
import fund.mapper.DonationPurposeMapper;
import fund.mapper.PaymentMapper;
import fund.mapper.ReceiptMapper;
import fund.mapper.SponsorMapper;
import fund.service.C;
import fund.service.ReceiptService;
import fund.service.ReportBuilder;
import fund.service.UserService;
import fund.service.Util;

@Controller
public class ReceiptController extends BaseController {

    @Autowired ReceiptMapper receiptMapper;
    @Autowired PaymentMapper paymentMapper;
    @Autowired CorporateMapper corporateMapper;
    @Autowired SponsorMapper sponsorMapper;
    @Autowired DonationPurposeMapper donationPurposeMapper;
    //@Autowired @Resource(name="dataSource1") SimpleDriverDataSource dataSource;
    @Autowired @Resource(name="dataSource1") DataSource dataSource;
    @Autowired ReceiptService receiptService;

    @ModelAttribute
    void modela(Model model, @ModelAttribute("pagination") PaginationReceipt pagination) {
    }

    @RequestMapping("/receipt/list.do")
    public String list(Model model, @ModelAttribute("pagination") PaginationReceipt pagination,
            @RequestParam(value="cmd", required=false) String cmd, @RequestParam(value="rid", required=false) int[] rid) throws Exception {
        if (!UserService.canAccess(C.메뉴_영수증_기부금영수증발급대장)) return "redirect:/home/logout.do";
        if (rid != null && "delete".equals(cmd))
            for (int id : rid)
                receiptService.deleteReceipt(id);
        pagination.setRecordCount(receiptMapper.selectCount(pagination));
        model.addAttribute("list", receiptMapper.selectPage(pagination));
        model.addAttribute("corporates", corporateMapper.selectAll());
        return "receipt/list";
    }

    @RequestMapping(value="/receipt/create1.do", method=RequestMethod.GET)
    public String create1(Model model) throws Exception {
        if (!UserService.canAccess(C.메뉴_영수증_영수증개별생성)) return "redirect:/home/logout.do";
        model.addAttribute("wrapper", new Wrapper());
        model.addAttribute("corporates", corporateMapper.selectAll());
        return "receipt/create1";
    }

    @RequestMapping(value="/receipt/create1.do", method=RequestMethod.POST, params="cmd=search")
    public String create1Search(Model model, Wrapper wrapper) throws Exception {
        if (!UserService.canAccess(C.메뉴_영수증_영수증개별생성)) return "redirect:/home/logout.do";
        wrapper.getMap().put("createDate", Util.toYMD());
        model.addAttribute("list", paymentMapper.selectForReceiptCreation1(wrapper.getMap()));
        model.addAttribute("corporates", corporateMapper.selectAll());
        return "receipt/create1";
    }

    @RequestMapping(value="/receipt/create1.do", method=RequestMethod.POST, params="cmd=createReceipt")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String create1CreateReceipt(RedirectAttributes ra, @RequestParam("pid") int[] pid, Wrapper wrapper) throws Exception {
        if (!UserService.canAccess(C.메뉴_영수증_영수증개별생성)) return "redirect:/home/logout.do";
        String createDate = (String)wrapper.getMap().get("createDate");
        receiptService.createReceipt1(createDate, pid);
        ra.addFlashAttribute("successMsg", "영수증이 생성되었습니다.");
        return "redirect:list.do";
    }

    @RequestMapping("/receipt/detail.do")
    public String detail(Model model, @RequestParam("id") int id) throws Exception {
        if (!UserService.canAccess(C.메뉴_영수증_기부금영수증발급대장)) return "redirect:/home/logout.do";
        Receipt receipt = receiptMapper.selectById(id);
        Map<String,Object> paymentSum = paymentMapper.getSumByReceiptId(id);
        Sponsor sponsor = sponsorMapper.selectById(receipt.getSponsorId());
        Corporate corporate = null;
        List<Payment> list = paymentMapper.selectByReceiptId(id);
        if (list.size() > 0)
            corporate = corporateMapper.selectById(donationPurposeMapper.selectById(list.get(0).getDonationPurposeId()).getCorporateId());
        model.addAttribute("receipt", receipt);
        model.addAttribute("paymentSum", paymentSum);
        model.addAttribute("list", list);
        model.addAttribute("sponsor", sponsor);
        model.addAttribute("corporate", corporate);
        return "receipt/detail";
    }

    @RequestMapping("/receipt/report1.do")
    public void report1(@RequestParam("rid") int[] rid, HttpServletRequest req, HttpServletResponse res) throws Exception {
        if (!UserService.canAccess(C.메뉴_영수증_기부금영수증발급대장)) return;
        String s = Arrays.toString(rid);
        s = s.substring(1, s.length()-1);
        String whereClause = "WHERE r.id IN (" + s + ")";

        ReportBuilder reportBuilder = new ReportBuilder("donationReceipt1", "기부금영수증.pdf", req, res);
        reportBuilder.setConnection(dataSource.getConnection());
        reportBuilder.setParameter("whereClause", whereClause);
        reportBuilder.addSubReport("paymentList.jasper");
        reportBuilder.setParameter("key1", sponsorMapper.selectKey1());
        reportBuilder.build("pdf");
    }

    @RequestMapping("/receipt/report2.do")
    public void report2(@RequestParam("rid") int[] rid, HttpServletRequest req, HttpServletResponse res) throws Exception {
        if (!UserService.canAccess(C.메뉴_영수증_기부금영수증발급대장)) return;
        String s = Arrays.toString(rid);
        s = s.substring(1, s.length()-1);
        String whereClause = "WHERE t.id IN (" + s + ")";

        ReportBuilder reportBuilder = new ReportBuilder("donationReceipt2", "기부금영수증.pdf", req, res);
        reportBuilder.setConnection(dataSource.getConnection());
        reportBuilder.setParameter("whereClause", whereClause);
        reportBuilder.setParameter("key1", sponsorMapper.selectKey1());
        reportBuilder.build("pdf");
    }

    @RequestMapping("/receipt/delete.do")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String delete(RedirectAttributes ra, @RequestParam("id") int id, @ModelAttribute("pagination") PaginationReceipt pagination) {
        if (!UserService.canAccess(C.메뉴_영수증_기부금영수증발급대장)) return "redirect:/home/logout.do";
        receiptService.deleteReceipt(id);
        ra.addFlashAttribute("successMsg", "영수증이 삭제되었습니다.");
        return "redirect:list.do?" + pagination.getQueryString();
    }

    @RequestMapping(value="/receipt/create2.do", method=RequestMethod.GET)
    public String create2(Model model) throws Exception {
        if (!UserService.canAccess(C.메뉴_영수증_영수증일괄생성)) return "redirect:/home/logout.do";
        model.addAttribute("wrapper", new Wrapper());
        return "receipt/create2";
    }

    @RequestMapping(value="/receipt/create2.do", method=RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String create2CreateReceipt(RedirectAttributes ra, Model model, Wrapper wrapper) throws Exception {
        if (!UserService.canAccess(C.메뉴_영수증_영수증일괄생성)) return "redirect:/home/logout.do";
        Map<String,Object> map = wrapper.getMap();
        if (StringUtils.isBlank((String)map.get("startDate")) ||
            StringUtils.isBlank((String)map.get("endDate")) ||
            StringUtils.isBlank((String)map.get("createDate"))) {
            model.addAttribute("errorMsg", "날짜를 입력하세요.");
            return "receipt/create2";
        }
        receiptService.createReceipt2(wrapper.getMap());
        ra.addFlashAttribute("successMsg", "영수증이 생성되었습니다.");
        return "redirect:list.do";
    }

    @RequestMapping(value="/receipt/taxData.do", method=RequestMethod.GET)
    public String taxData(Model model) {
        if (!UserService.canAccess(C.메뉴_영수증_국세청보고자료)) return "redirect:/home/logout.do";
        model.addAttribute("wrapper", new Wrapper());
        model.addAttribute("corporates", corporateMapper.selectAll());
        return "receipt/taxData";
    }

    @RequestMapping(value="/receipt/taxData.do", method=RequestMethod.POST)
    public void taxData(Model model, Wrapper wrapper, HttpServletRequest req, HttpServletResponse res) throws Exception {
        if (!UserService.canAccess(C.메뉴_영수증_국세청보고자료)) return;
        List<Map<String, Object>> list = paymentMapper.selectForTaxData(wrapper.getMap());
        ReportBuilder reportBuilder = new ReportBuilder("taxData", "국세청보고자료.xlsx", req, res);
        reportBuilder.setCollection(list);
        reportBuilder.build("xlsx");
    }


    @RequestMapping(value="/receipt/sum.do", method=RequestMethod.GET)
    public String sum(Model model) throws Exception {
        if (!UserService.canAccess(C.메뉴_영수증_기부금영수증발급합계)) return "redirect:/home/logout.do";
        return "receipt/sum";
    }

    @RequestMapping(value="/receipt/sum.do", method=RequestMethod.POST)
    public String sum(Model model, Pagination pagination) throws Exception {
        if (!UserService.canAccess(C.메뉴_영수증_기부금영수증발급합계)) return "redirect:/home/logout.do";
        model.addAttribute("list", receiptMapper.selectSum(pagination));
        return "receipt/sum";
    }

}
