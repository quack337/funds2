package fund.controller;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fund.dto.Commitment;
import fund.dto.EB21;
import fund.dto.Payment;
import fund.dto.Sal;
import fund.dto.Xfer;
import fund.dto.param.Wrapper;
import fund.mapper.CodeMapper;
import fund.mapper.CommitmentMapper;
import fund.mapper.EB21Mapper;
import fund.mapper.PaymentMapper;
import fund.service.C;
import fund.service.CMSService;
import fund.service.ExcelService;
import fund.service.LogService;
import fund.service.UserService;

@Controller
public class CmsController extends BaseController {

    static final SimpleDateFormat format_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    static final SimpleDateFormat format_yyMMdd = new SimpleDateFormat("yyMMdd");
    static final SimpleDateFormat format_MMdd = new SimpleDateFormat("MMdd");
    static final String EB21_Message = "성공회대발전기금";

    @Autowired CommitmentMapper commitmentMapper;
    @Autowired CodeMapper codeMapper;
    @Autowired EB21Mapper eb21Mapper;
    @Autowired PaymentMapper paymentMapper;
    @Autowired LogService logService;
    @Autowired ExcelService excelService;
    @Autowired CMSService cmsService;

    //// EB13
    @RequestMapping(value="/cms/eb13.do", method=RequestMethod.GET)
    public String eb13(Model model) throws Exception {
        if (!UserService.canAccess(C.메뉴_금융연동_EB13생성)) return "redirect:/home/logout.do";
        List<Commitment> list = commitmentMapper.selectEB13Candidate();
        model.addAttribute("list", list);
        model.addAttribute("today", format_yyyyMMdd.format(new Date()));
        return "cms/eb13";
    }

    // TODO: 에러 발생시 EB13 파일 사용 불가 메시지.
    @RequestMapping(value="/cms/eb13.do", method=RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void eb13Create(@RequestParam("date") String date, HttpServletResponse response) throws Exception {
        if (!UserService.canAccess(C.메뉴_금융연동_EB13생성)) return;
        Date today = format_yyyyMMdd.parse(date);
        String fileName = "EB13" + format_MMdd.format(today);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");

        int count = 0;
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "MS949"))) {
            count = downloadEB13File(writer, fileName, today);
        }
        logService.actionLog("EB13 생성", "cms eb13", 0, count + " 건");
    }

    private int downloadEB13File(BufferedWriter writer, String fileName, Date today) throws Exception {
        String orgCode = "9983010152";
        String yyMMdd = format_yyMMdd.format(today);

        writer.write("H");
        writer.write(StringUtils.repeat('0', 8));
        writer.write(orgCode);
        writer.write(fileName);
        writer.write(yyMMdd);
        writer.write(StringUtils.repeat(' ', 87));

        int count = 0;
        List<Commitment> list = commitmentMapper.selectEB13Candidate();
        for(Commitment c : list) {
            if (c.isValid() == false) continue;

            writer.write("R");
            writer.write(String.format("%08d", count + 1));
            writer.write(orgCode);
            writer.write(yyMMdd);
            writer.write("1");

            String cno = cmsService.getCommitmentNo12(c.getCommitmentNo());

            writer.write(String.format("%-20s", cno));
            writer.write(c.getBankCode());
            writer.write(String.format("%-16s", c.getAccountNo().replaceAll("-", "")));
            writer.write(String.format("%-16s", c.getBirthDate()));
            writer.write(StringUtils.repeat(' ', 4));
            writer.write(StringUtils.repeat(' ', 2));
            writer.write(StringUtils.repeat(' ', 1));
            writer.write(StringUtils.repeat(' ', 4));
            writer.write(StringUtils.repeat(' ', 1));
            writer.write(StringUtils.repeat(' ', 12));
            writer.write(StringUtils.repeat(' ', 1));
            writer.write(StringUtils.repeat(' ', 10));
            count++;

            c.setEb13Date(today);
            c.setEb13State("신청");
            c.setEb13ErrorCode(null);
            commitmentMapper.updateEB13(c);
        }
        writer.write("T");
        writer.write(StringUtils.repeat('9', 8));
        writer.write(orgCode);
        writer.write(fileName);
        writer.write(String.format("%08d", count));
        writer.write(String.format("%08d", count));
        writer.write(StringUtils.repeat('0', 8));
        writer.write(StringUtils.repeat('0', 8));
        writer.write(StringUtils.repeat('0', 8));
        writer.write(StringUtils.repeat(' ', 43));
        writer.write(StringUtils.repeat(' ', 10));
        return count;
    }

    //// EB14
    @RequestMapping(value="/cms/eb14.do", method=RequestMethod.GET)
    public String eb14(Model model) {
        if (!UserService.canAccess(C.메뉴_금융연동_EB14등록)) return "redirect:/home/logout.do";
        model.addAttribute("title", "EB14 파일 업로드");
        return "cms/upload";
    }

    // TODO: 에러 발생시 EB14 재등록 메시지.
    @RequestMapping(value="/cms/eb14.do", method=RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String eb14(Model model, @RequestParam("file") MultipartFile file) throws Exception {
        if (!UserService.canAccess(C.메뉴_금융연동_EB14등록)) return "redirect:/home/logout.do";
        ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
        String s = IOUtils.toString(stream, "ASCII");

        Date today = format_yyMMdd.parse(s.substring(27,27+6));
        int headerLength = 120, trailerLength = 120, recordLength = 120;
        s = s.substring(headerLength);
        s = s.substring(0, s.length() - trailerLength);
        List<String> list = split(s, recordLength);
        for (String r : list) {
            String commitmentNo = r.substring(26, 26 + 20).trim();
            String errorCode = r.substring(92, 92 + 4);

            Commitment c = cmsService.selectCommitmentByCommitmentNo12(commitmentNo, today);
            if (c != null) {
                c.setEb13ErrorCode(errorCode);
                c.setEb13State("에러");
                commitmentMapper.updateEB13(c);
            }
        }
        int count1 = list.size();
        int count2 = 0;
        for (Commitment c : commitmentMapper.selectByEB13Date(today))
            if ("신청".equals(c.getEb13State())) {
                c.setEb13State("성공");
                commitmentMapper.updateEB13(c);
                ++count2;
            }
        logService.actionLog("EB14 등록", "cms eb14", 0, "성공:" + count1 + " 건.  에러:" + count2 + " 건.");
        return "redirect:eb14result.do";
    }

    static List<String> split(String s, int size) {
        List<String> list = new ArrayList<String>();
        int index = 0;
        while (index + size <= s.length()) {
          String t = s.substring(index, index + size);
          list.add(t);
          index += size;
        }
        return list;
    }

    @RequestMapping(value="/cms/eb14result.do", method=RequestMethod.GET)
    public String eb14Result(Model model) throws Exception {
        if (!UserService.canAccess(C.메뉴_금융연동_EB1314결과조회)) return "redirect:/home/logout.do";
        Wrapper wrapper= new Wrapper();
        Map<String, Object> map = wrapper.getMap();
        Calendar cal = Calendar.getInstance(); cal.add(Calendar.MONTH, -1);
        Date date1 = cal.getTime();
        Date date2 = new Date();
        map.put("startDt", format_yyyyMMdd.format(date1));
        map.put("endDt", format_yyyyMMdd.format(date2));
        map.put("state", "all");
        List<Commitment> list = commitmentMapper.selectCmsResult(map);
        model.addAttribute("wrapper", wrapper);
        model.addAttribute("list", list);
        return "cms/eb14result";
    }

    @RequestMapping(value="/cms/eb14result.do", method=RequestMethod.POST)
    public String eb14Result(Model model, Wrapper wrapper) throws Exception {
        if (!UserService.canAccess(C.메뉴_금융연동_EB1314결과조회)) return "redirect:/home/logout.do";
        List<Commitment> list = commitmentMapper.selectCmsResult(wrapper.getMap());
        model.addAttribute("wrapper", wrapper);
        model.addAttribute("list", list);
        return "cms/eb14result";
    }

    //// EB21
    @RequestMapping(value="/cms/eb21.do", method=RequestMethod.GET)
    public String eb21(Model model) throws Exception {
        if (!UserService.canAccess(C.메뉴_금융연동_EB21생성)) return "redirect:/home/logout.do";
        Calendar today = Calendar.getInstance();
        int day = today.get(Calendar.DAY_OF_MONTH);
        int paymentDay = day > 20 ? 25 : 20;
        Calendar paymentDate = Calendar.getInstance();
        paymentDate.set(Calendar.DAY_OF_MONTH, paymentDay);
        Wrapper wrapper = new Wrapper();
        Map<String,Object> map = wrapper.getMap();
        map.put("paymentDay", paymentDay);
        map.put("paymentDate", format_yyyyMMdd.format(paymentDate.getTime()));
        model.addAttribute("wrapper", wrapper);
        return "cms/eb21";
    }

    @RequestMapping(value="/cms/eb21.do", method=RequestMethod.POST, params="cmd=search")
    public String eb21(Model model, Wrapper wrapper) throws Exception {
        if (!UserService.canAccess(C.메뉴_금융연동_EB21생성)) return "redirect:/home/logout.do";
        List<Commitment> list = commitmentMapper.selectEB21Candidate(wrapper.getMap());
        model.addAttribute("list", list);
        return "cms/eb21";
    }

    // TODO: 에러 발생 가능한 부분을 조사해서, 최대한 에러가 발생하지 않게 수정하자.
    // 에러가 발생할 상황을 isValid 메소드에서 걸러내자.
    @RequestMapping(value="/cms/eb21.do", method=RequestMethod.POST, params="cmd=create")
    public void eb21Create(Wrapper wrapper, HttpServletResponse response) throws Exception {
        if (!UserService.canAccess(C.메뉴_금융연동_EB21생성)) return;
        Map<String, Object> map = wrapper.getMap();

        Date today = format_yyyyMMdd.parse((String)map.get("paymentDate"));
        String fileName = "EB21" + format_MMdd.format(today);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");

        int count = 0;
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "MS949"))) {
            count = downloadEB21File(writer, fileName, map);
        }
        logService.actionLog("EB21 생성", "cms eb21", 0, count + " 건");
    }

    private int downloadEB21File(BufferedWriter writer, String fileName, Map<String,Object> map) throws Exception {
        String orgCode = "9983010152";
        String yyMMdd = ((String)map.get("paymentDate")).replaceAll("-", "").substring(2);
        Date paymentDate = format_yyMMdd.parse(yyMMdd);
        eb21Mapper.deleteByPaymentDate(paymentDate);

        writer.write("H");
        writer.write(StringUtils.repeat('0', 8));
        writer.write(orgCode);
        writer.write(fileName);
        writer.write(yyMMdd);
        writer.write("0054593");
        writer.write("15922014245     ");
        writer.write(StringUtils.repeat(' ', 94));

        int count = 0;
        int total = 0;
        EB21 eb21 = new EB21();
        eb21.setPaymentDate(format_yyyyMMdd.parse((String)map.get("paymentDate")));
        eb21.setState("신청");
        List<Commitment> list = commitmentMapper.selectEB21Candidate(map);
        for(Commitment c : list) {
            if (c.isValid() == false) continue;

            writer.write("R");
            writer.write(String.format("%08d", count + 1));
            writer.write(orgCode);
            writer.write(c.getBankCode());
            writer.write(String.format("%-16s", c.getAccountNo().replaceAll("-", "")));
            writer.write(String.format("%013d", c.getAmountPerMonth()));
            total += c.getAmountPerMonth();

            writer.write(String.format("%-13s", c.getBirthDate()));
            writer.write(StringUtils.repeat(' ', 5));

            writer.write(EB21_Message);
            writer.write("  ");

            String cno12 = cmsService.getCommitmentNo12(c.getCommitmentNo());

            writer.write(String.format("%-20s", cno12));
            writer.write(StringUtils.repeat(' ', 5));
            writer.write("1");
            writer.write(StringUtils.repeat(' ', 12));
            writer.write(StringUtils.repeat(' ', 21));
            count++;

            eb21.setCommitmentNo12(cno12);
            eb21.setCommitmentId(c.getId());
            eb21Mapper.insert(eb21);
        }
        writer.write("T");
        writer.write(StringUtils.repeat('9', 8));
        writer.write(orgCode);
        writer.write(fileName);
        writer.write(String.format("%08d", count));
        writer.write(String.format("%08d", count));
        writer.write(String.format("%013d", total));
        writer.write(StringUtils.repeat('0', 8));
        writer.write(StringUtils.repeat('0', 13));
        writer.write(StringUtils.repeat(' ', 63));
        writer.write(StringUtils.repeat(' ', 10));
        return count;
    }

    //// EB22
    @RequestMapping(value="/cms/eb22.do", method=RequestMethod.GET)
    public String eb22(Model model) {
        if (!UserService.canAccess(C.메뉴_금융연동_EB22등록)) return "redirect:/home/logout.do";
        model.addAttribute("title", "EB22 파일 업로드");
        return "cms/upload";
    }

    // TODO: 에러 발생시 EB22 재등록 메시지.
    // 에러가 발생할 상황을 최대한 줄이자.
    @RequestMapping(value="/cms/eb22.do", method=RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String eb22(Model model, @RequestParam("file") MultipartFile file) throws Exception {
        if (!UserService.canAccess(C.메뉴_금융연동_EB22등록)) return "redirect:/home/logout.do";
        ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
        String s = IOUtils.toString(stream, "ASCII");

        Date paymentDate = format_yyMMdd.parse(s.substring(27,27+6));
        int headerLength = 150, trailerLength = 150, recordLength = 150;
        s = s.substring(headerLength);
        s = s.substring(0, s.length() - trailerLength);
        List<String> list = split(s, recordLength);
        for (String r : list) {
            String commitmentNo12 = r.substring(91, 91 + 20).trim();
            String errorCode = r.substring(69, 69 + 4);

            EB21 e = eb21Mapper.selectByCommitmentNo12(commitmentNo12, paymentDate);
            if (e != null) {
                e.setState("에러");
                e.setErrorCode(errorCode);
                eb21Mapper.update(e);
            }
        }
        int count1 = list.size();
        int count2 = 0;
        for (EB21 e : eb21Mapper.selectByPaymentDate(paymentDate))
            if ("신청".equals(e.getState())) {
                e.setState("성공");
                eb21Mapper.update(e);

                Commitment c = commitmentMapper.selectById(e.getCommitmentId());
                Payment payment = new Payment();
                payment.setSponsorId(c.getSponsorId());
                payment.setCommitmentId(c.getId());
                payment.setAmount(c.getAmountPerMonth());
                payment.setPaymentDate(format_yyyyMMdd.format(paymentDate));
                payment.setDonationPurposeId(c.getDonationPurposeId());
                payment.setPaymentMethodId(C.코드ID_CMS);
                paymentMapper.insert(payment);
                ++count2;
            }
        logService.actionLog("EB22 등록", "cms eb22", 0,  "성공:" + count1 + " 건.  에러:" + count2 + " 건.");
        return "redirect:eb22result.do";
    }

    @RequestMapping(value="/cms/eb22result.do", method=RequestMethod.GET)
    public String eb21Result(Model model) throws Exception {
        if (!UserService.canAccess(C.메뉴_금융연동_EB2122결과조회)) return "redirect:/home/logout.do";
        Wrapper wrapper = new Wrapper();
        Map<String,Object> map = wrapper.getMap();
        Calendar cal = Calendar.getInstance(); cal.add(Calendar.DAY_OF_MONTH, -14);
        Date date1 = cal.getTime();
        Date date2 = new Date();
        map.put("startDt", format_yyyyMMdd.format(date1));
        map.put("endDt", format_yyyyMMdd.format(date2));
        map.put("state", "에러");
        List<EB21> list = eb21Mapper.selectCmsResult(map);
        model.addAttribute("wrapper", wrapper);
        model.addAttribute("list", list);
        return "cms/eb22result";
    }

    @RequestMapping(value="/cms/eb22result.do", method=RequestMethod.POST)
    public String eb21Result(Model model, Wrapper wrapper) throws Exception {
        if (!UserService.canAccess(C.메뉴_금융연동_EB2122결과조회)) return "redirect:/home/logout.do";
        List<EB21> list = eb21Mapper.selectCmsResult(wrapper.getMap());
        model.addAttribute("wrapper", wrapper);
        model.addAttribute("list", list);
        return "cms/eb22result";
    }


    //// 자동이체
    @RequestMapping(value="/cms/xfer.do", method=RequestMethod.GET)
    public String xfer(Model model) {
        if (!UserService.canAccess(C.메뉴_금융연동_자동이체결과등록)) return "redirect:/home/logout.do";
        model.addAttribute("title", "자동이체 결과파일 업로드");
        return "cms/upload";
    }

    @RequestMapping(value="/cms/xfer.do", method=RequestMethod.POST, params="cmd=upload")
    public String xfer(Model model, @RequestParam("file") MultipartFile file, HttpSession session) throws Exception {
        if (!UserService.canAccess(C.메뉴_금융연동_자동이체결과등록)) return "redirect:/home/logout.do";
        String redirect1 = "redirect:xfer.do";
        if (file.getSize() <= 0) return redirect1;
        List<Xfer> list = excelService.get자동이체Result(file.getInputStream());
        if (list.size() <= 0) return redirect1;
        session.setAttribute("xfer_notSaved", list);
        session.setAttribute("xfer_saved", null);
        return "cms/xfer";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value="/cms/xfer.do", method=RequestMethod.POST, params="cmd=save")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String xfer(Model model, @RequestParam("commitmentNo") String[] commitmentNo, HttpSession session) throws Exception {
        if (!UserService.canAccess(C.메뉴_금융연동_자동이체결과등록)) return "redirect:/home/logout.do";
        List<Xfer> list = (List<Xfer>)session.getAttribute("xfer_notSaved");
        List<Xfer> list_saved = (List<Xfer>)session.getAttribute("xfer_saved");
        if (list_saved == null) list_saved = new ArrayList<Xfer>();
        List<Xfer> list_notSaved = new ArrayList<Xfer>();

        int count = 0;
        for (int i = 0; i < list.size(); ++i) {
            Xfer x = list.get(i);
            x.setCommitmentNo(commitmentNo[i]);
            if (insertPayment(x)) { list_saved.add(x); ++count; }
            else list_notSaved.add(x);
        }
        session.setAttribute("xfer_notSaved", list_notSaved);
        session.setAttribute("xfer_saved", list_saved);
        logService.actionLog("자동이체 생성", "cms xfer", 0, count + " 건");
        return "cms/xfer";
    }

    private boolean insertPayment(Xfer xfer) {
        try {
            Commitment c = commitmentMapper.selectByCommitmentNo(xfer.getCommitmentNo());
            Payment payment = new Payment();
            payment.setSponsorId(c.getSponsorId());
            payment.setCommitmentId(c.getId());
            payment.setAmount(xfer.getAmount());
            payment.setPaymentDate(format_yyyyMMdd.format(xfer.getDate()));
            payment.setEtc(xfer.getEtc1());
            payment.setDonationPurposeId(c.getDonationPurposeId());
            payment.setPaymentMethodId(C.코드ID_자동이체);
            paymentMapper.insert(payment);
            return true;
        } catch (Exception e) {
            logService.insert(e);
            return false;
        }
    }


    //// 급여공제
    @RequestMapping(value="/cms/sal.do", method=RequestMethod.GET)
    public String salary(Model model) {
        if (!UserService.canAccess(C.메뉴_금융연동_급여공제결과등록)) return "redirect:/home/logout.do";
        model.addAttribute("title", "급여공제 결과파일 업로드");
        return "cms/upload";
    }

    @RequestMapping(value="/cms/sal.do", method=RequestMethod.POST, params="cmd=upload")
    public String sal(@RequestParam("file") MultipartFile file, HttpSession session) throws Exception {
        if (!UserService.canAccess(C.메뉴_금융연동_급여공제결과등록)) return "redirect:/home/logout.do";
        String redirect1 = "redirect:sal.do";
        if (file.getSize() <= 0) return redirect1;
        List<Sal> list = excelService.get급여공제Result(file.getInputStream());
        if (list.size() <= 0) return redirect1;
        session.setAttribute("sal_notSaved", list);
        session.setAttribute("sal_saved", null);
        return "cms/sal";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value="/cms/sal.do", method=RequestMethod.POST, params="cmd=save")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String sal(Model model, @RequestParam("commitmentNo") String[] commitmentNo, HttpSession session) throws Exception {
        if (!UserService.canAccess(C.메뉴_금융연동_급여공제결과등록)) return "redirect:/home/logout.do";
        List<Sal> list = (List<Sal>)session.getAttribute("sal_notSaved");
        List<Sal> list_saved = (List<Sal>)session.getAttribute("sal_saved");
        if (list_saved == null) list_saved = new ArrayList<Sal>();
        List<Sal> list_notSaved = new ArrayList<Sal>();

        int count = 0;
        for (int i = 0; i < list.size(); ++i) {
            Sal s = list.get(i);
            s.setCommitmentNo(commitmentNo[i]);
            if (insertPayment(s)) { list_saved.add(s); ++count; }
            else list_notSaved.add(s);
        }
        session.setAttribute("sal_notSaved", list_notSaved);
        session.setAttribute("sal_saved", list_saved);
        logService.actionLog("급여공제 생성", "cms sal", 0, count + " 건");
        return "cms/sal";
    }

    private boolean insertPayment(Sal sal) {
        try {
            Commitment c = commitmentMapper.selectByCommitmentNo(sal.getCommitmentNo());
            Payment payment = new Payment();
            payment.setSponsorId(c.getSponsorId());
            payment.setCommitmentId(c.getId());
            payment.setAmount(sal.getAmount());
            payment.setPaymentDate(format_yyyyMMdd.format(sal.getDate()));
            payment.setEtc(sal.getEtc());
            payment.setDonationPurposeId(c.getDonationPurposeId());
            payment.setPaymentMethodId(C.코드ID_급여공제);
            paymentMapper.insert(payment);
            return true;
        } catch (Exception e) {
            logService.insert(e);
            return false;
        }
    }
}