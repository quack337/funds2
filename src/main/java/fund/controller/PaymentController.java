package fund.controller;

import java.io.BufferedOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fund.dto.param.OrderBy;
import fund.dto.param.Wrapper;
import fund.mapper.CodeMapper;
import fund.mapper.CorporateMapper;
import fund.mapper.DonationPurposeMapper;
import fund.mapper.PaymentMapper;
import fund.service.C;
import fund.service.ExcelService;
import fund.service.UserService;

@Controller
public class PaymentController extends BaseController {

    @Autowired PaymentMapper paymentMapper;
    @Autowired CodeMapper codeMapper;
    @Autowired DonationPurposeMapper donationPurposeMapper;
    @Autowired CorporateMapper corporateMapper;

    //// report1
    final static OrderBy[] report1aOrderBy = new OrderBy[] {
        new OrderBy("납입일", "ORDER BY paymentDate"),
        new OrderBy("회원번호", "ORDER BY sponsorNo, paymentDate"),
        new OrderBy("이름", "ORDER BY name, sponsorNo, paymentDate"),
        new OrderBy("회원구분", "ORDER BY sponsorType2Name, sponsorNo, paymentDate"),
        new OrderBy("교회", "ORDER BY churchName, sponsorNo, PaymentDate"),
        new OrderBy("금액", "ORDER BY amount DESC, paymentDate"),
        new OrderBy("약정번호", "ORDER BY commitmentNo, paymentDate")
    };

    //// report2
    final static OrderBy[] report1bOrderBy = new OrderBy[] {
        new OrderBy("회원번호", "ORDER BY sponsorNo"),
        new OrderBy("이름", "ORDER BY name"),
        new OrderBy("회원구분", "ORDER BY sponsorType2Name"),
        new OrderBy("교회", "ORDER BY churchName"),
        new OrderBy("금액", "ORDER BY amount DESC"),
    };

    @RequestMapping(value="/payment/srch1a", method=RequestMethod.GET)
    public String report1aGET(Model model,
            @RequestParam(value="commitmentNo", required=false) String commitmentNo,
            @RequestParam(value="sponsorNo", required=false) String sponsorNo,
            @RequestParam(value="regular", required=false) String regular) {
        if (!UserService.canAccess(C.메뉴_납입조회_납입내역조회)) return "redirect:/home/logout";
        addModel1(model);
        Wrapper wrapper = new Wrapper();
        if (commitmentNo != null) wrapper.getMap().put("commitmentNo", commitmentNo);
        if (sponsorNo != null) wrapper.getMap().put("sponsorNo", sponsorNo);
        if (regular != null) wrapper.getMap().put("regular", regular);
        model.addAttribute("wrapper", wrapper);
        return "payment/srch1a";
    }

    @RequestMapping(value="/payment/srch1a", method=RequestMethod.POST, params="cmd=search")
    public String report1aPOST(Model model, Wrapper wrapper) {
        if (!UserService.canAccess(C.메뉴_납입조회_납입내역조회)) return "redirect:/home/logout";
        addModel1(model);
        model.addAttribute("list", paymentMapper.selectReport1a(wrapper.getMap()));
        return "payment/srch1a";
    }

    private void addModel1(Model model) {
        model.addAttribute("sponsorType2List", codeMapper.selectEnabledByCodeGroupId(C.코드그룹ID_회원구분));
        model.addAttribute("donationPurposes", donationPurposeMapper.selectNotClosed());
        model.addAttribute("paymentMethods", codeMapper.selectEnabledByCodeGroupId(C.코드그룹ID_정기납입방법));
        model.addAttribute("churchList", codeMapper.selectEnabledByCodeGroupId(C.코드그룹ID_소속교회));
        model.addAttribute("corporates", corporateMapper.selectAll());
        model.addAttribute("report1aOrderBy", report1aOrderBy);
        model.addAttribute("report1bOrderBy", report1bOrderBy);
    }

    @RequestMapping(value="/payment/srch1a", method=RequestMethod.POST, params="cmd=excel")
    public void report1(Model model, Wrapper mapParam, HttpServletRequest req, HttpServletResponse response) throws Exception {
        if (!UserService.canAccess(C.메뉴_납입조회_납입내역조회)) return;

        List<Map<String,Object>> list = paymentMapper.selectReport1a(mapParam.getMap());
        Workbook workbook = ExcelService.downloadExcel1a(list);
        String fileName = URLEncoder.encode("납입내역.xlsx","UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
        try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) {
            workbook.write(output);
        }
    }

    @RequestMapping(value="/payment/srch1b", method=RequestMethod.POST, params="cmd=excel")
    public void report1bReport(Model model, Wrapper wrapper, HttpServletRequest req, HttpServletResponse response) throws Exception {
        if (!UserService.canAccess(C.메뉴_납입조회_회원별납입합계)) return;

        List<Map<String,Object>> list = paymentMapper.selectReport1b(wrapper.getMap());
        Workbook workbook = ExcelService.downloadExcel1b(list);
        String fileName = URLEncoder.encode("회원별납입합계.xlsx","UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
        try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) {
            workbook.write(output);
        }
    }

    @RequestMapping(value="/payment/srch1b", method=RequestMethod.GET)
    public String report1b(Model model) {
        if (!UserService.canAccess(C.메뉴_납입조회_회원별납입합계)) return "redirect:/home/logout";
        Wrapper wrapper = new Wrapper();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        wrapper.getMap().put("startDate", String.format("%d-01-01", year));
        wrapper.getMap().put("endDate", String.format("%d-12-31", year));
        model.addAttribute("wrapper", wrapper);
        addModel1(model);
        return "payment/srch1b";
    }

    @RequestMapping(value="/payment/srch1b", method=RequestMethod.POST, params="cmd=search")
    public String report1b(Model model, Wrapper wrapper) {
        if (!UserService.canAccess(C.메뉴_납입조회_회원별납입합계)) return "redirect:/home/logout";
        addModel1(model);
        model.addAttribute("list", paymentMapper.selectReport1b(wrapper.getMap()));
        return "payment/srch1b";
    }

    static String[] report2Title = new String[] { "기부목적", "회원구분", "소속" };

    //// report2
    @RequestMapping(value="/payment/srch2/{i}", method=RequestMethod.GET)
    public String report2a(Model model, @PathVariable("i") int i) {
        if (i == 0 && !UserService.canAccess(C.메뉴_납입조회_기부목적별납입합계)) return "redirect:/home/logout";
        if (i == 1 && !UserService.canAccess(C.메뉴_납입조회_회원구분별납입합계)) return "redirect:/home/logout";
        if (i == 2 && !UserService.canAccess(C.메뉴_납입조회_소속교회별납입합계)) return "redirect:/home/logout";

        Wrapper wrapper = new Wrapper();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        wrapper.getMap().put("startDate", String.format("%d-01-01", year));
        wrapper.getMap().put("endDate", String.format("%d-12-31", year));
        model.addAttribute("wrapper", wrapper);
        model.addAttribute("title", report2Title[i]);
        model.addAttribute("corporates", corporateMapper.selectAll());
        return "payment/srch2";
    }

    @RequestMapping(value="/payment/srch2/{i}", method=RequestMethod.POST, params="cmd=search")
    public String report2a(Model model, Wrapper wrapper, @PathVariable("i") int i) {
        if (i == 0 && !UserService.canAccess(C.메뉴_납입조회_기부목적별납입합계)) return "redirect:/home/logout";
        if (i == 1 && !UserService.canAccess(C.메뉴_납입조회_회원구분별납입합계)) return "redirect:/home/logout";
        if (i == 2 && !UserService.canAccess(C.메뉴_납입조회_소속교회별납입합계)) return "redirect:/home/logout";

        switch (i) {
        case 0: model.addAttribute("list", paymentMapper.selectReport2a(wrapper.getMap())); break;
        case 1: model.addAttribute("list", paymentMapper.selectReport2b(wrapper.getMap())); break;
        case 2: model.addAttribute("list", paymentMapper.selectReport2c(wrapper.getMap())); break;
        }
        model.addAttribute("title", report2Title[i]);
        model.addAttribute("corporates", corporateMapper.selectAll());
        return "payment/srch2";
    }

    @RequestMapping(value="/payment/srch2/{i}", method=RequestMethod.POST, params="cmd=excel")
    public void report2aReport(Model model, Wrapper wrapper, @PathVariable("i") int i, HttpServletRequest req, HttpServletResponse response) throws Exception {
        if (i == 0 && !UserService.canAccess(C.메뉴_납입조회_기부목적별납입합계)) return;
        if (i == 1 && !UserService.canAccess(C.메뉴_납입조회_회원구분별납입합계)) return;
        if (i == 2 && !UserService.canAccess(C.메뉴_납입조회_소속교회별납입합계)) return;

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

        switch (i) {
        case 0: list = paymentMapper.selectReport2a(wrapper.getMap()); break;
        case 1: list = paymentMapper.selectReport2b(wrapper.getMap()); break;
        case 2: list = paymentMapper.selectReport2c(wrapper.getMap()); break;
        }

        Map<String, Object> map = list.get(list.size()-1);
        String title = report2Title[i];
        map.put("title", title);
        list.remove(list.size()-1);

        Workbook workbook = ExcelService.downloadExcel2(title, list);
        String fileName = URLEncoder.encode(title + "별납입합계.xlsx","UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
        try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) {
            workbook.write(output);
        }
    }


}
