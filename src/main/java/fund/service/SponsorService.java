package fund.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fund.dto.Code;
import fund.dto.Sponsor;
import fund.dto.SponsorLog;
import fund.mapper.CodeMapper;
import fund.mapper.SponsorLogMapper;
import fund.mapper.SponsorMapper;

@Service
public class SponsorService {

    String[] 동의 = new String[] { "미입력", "동의", "미동의" };
    String[] 발송지 = new String[] { "자택", "직장" };

    @Autowired SponsorMapper sponsorMapper;
    @Autowired SponsorLogMapper sponsorLogMapper;
    @Autowired CodeMapper codeMapper;

    public void changeLog(Sponsor s1, Sponsor s2) throws Exception {
        ArrayList<SponsorLog> list = new ArrayList<SponsorLog>();

        if (Objects.equals(s1.getName(), s2.getName()) == false) list.add(createLog("이름", s1.getName(), s2.getName()));
        if (Objects.equals(s1.getJuminNo(), s2.getJuminNo()) == false) list.add(createLog("주민번호", s1.getJuminNo(), s2.getJuminNo()));

        if (s1.getSponsorType1Id() != s2.getSponsorType1Id()) {
            Code code1 = codeMapper.selectById(s1.getSponsorType1Id());
            Code code2 = codeMapper.selectById(s2.getSponsorType1Id());
            String value1 = code1 != null ? code1.getCodeName() : "";
            String value2 = code2 != null ? code2.getCodeName() : "";
            list.add(createLog("가입구분", value1, value2));
        }

        if (s1.getSponsorType2Id() != s2.getSponsorType2Id()) {
            Code code1 = codeMapper.selectById(s1.getSponsorType2Id());
            Code code2 = codeMapper.selectById(s2.getSponsorType2Id());
            String value1 = code1 != null ? code1.getCodeName() : "";
            String value2 = code2 != null ? code2.getCodeName() : "";
            list.add(createLog("회원구분", value1, value2));
        }

        if (Objects.equals(s1.getSignUpDate(), s2.getSignUpDate()) == false) list.add(createLog("가입일", s1.getSignUpDate(), s2.getSignUpDate()));
        if (Objects.equals(s1.getRecommender(), s2.getRecommender()) == false) list.add(createLog("추천인", s1.getRecommender(), s2.getRecommender()));
        if (Objects.equals(s1.getRecommenderRelation(), s2.getRecommenderRelation()) == false) list.add(createLog("추천인관계", s1.getRecommenderRelation(), s2.getRecommenderRelation()));
        if (Objects.equals(s1.getMobilePhone(), s2.getMobilePhone()) == false) list.add(createLog("핸드폰 번호", s1.getMobilePhone(), s2.getMobilePhone()));
        if (Objects.equals(s1.getHomeAddress(), s2.getHomeAddress()) == false) list.add(createLog("집주소", s1.getHomeAddress(), s2.getHomeAddress()));
        if (Objects.equals(s1.getHomeRoadAddress(), s2.getHomeRoadAddress()) == false) list.add(createLog("집주소", s1.getHomeRoadAddress(), s2.getHomeRoadAddress()));
        if (Objects.equals(s1.getHomeDetailAddress(), s2.getHomeDetailAddress()) == false) list.add(createLog("집주소", s1.getHomeDetailAddress(), s2.getHomeDetailAddress()));
        if (Objects.equals(s1.getHomePostCode(), s2.getHomePostCode()) == false) list.add(createLog("집주소", s1.getHomePostCode(), s2.getHomePostCode()));
        if (Objects.equals(s1.getHomePhone(), s2.getHomePhone()) == false) list.add(createLog("자택 전화번호", s1.getHomePhone(), s2.getHomePhone()));
        if (Objects.equals(s1.getEmail(), s2.getEmail()) == false) list.add(createLog("이메일", s1.getEmail(), s2.getEmail()));

        if (s1.getChurchId() != s2.getChurchId()) {
            Code code1 = codeMapper.selectById(s1.getChurchId());
            Code code2 = codeMapper.selectById(s2.getChurchId());
            String value1 = code1 != null ? code1.getCodeName() : "";
            String value2 = code2 != null ? code2.getCodeName() : "";
            list.add(createLog("소속교회", value1, value2));
        }

        if (Objects.equals(s1.getSponsorTypeDetail(), s2.getSponsorTypeDetail()) == false) list.add(createLog("회원구분상세", s1.getSponsorTypeDetail(), s2.getSponsorTypeDetail()));
        if (Objects.equals(s1.getCompany(), s2.getCompany()) == false) list.add(createLog("회사", s1.getCompany(), s2.getCompany()));
        if (Objects.equals(s1.getDepartment(), s2.getDepartment()) == false) list.add(createLog("부서", s1.getDepartment(), s2.getDepartment()));
        if (Objects.equals(s1.getPosition(), s2.getPosition()) == false) list.add(createLog("직위", s1.getPosition(), s2.getPosition()));
        if (Objects.equals(s1.getOfficePhone(), s2.getOfficePhone()) == false) list.add(createLog("직장 전화번호", s1.getOfficePhone(), s2.getOfficePhone()));
        if (Objects.equals(s1.getOfficeAddress(), s2.getOfficeAddress()) == false) list.add(createLog("직장주소", s1.getOfficeAddress(), s2.getOfficeAddress()));
        if (Objects.equals(s1.getOfficeRoadAddress(), s2.getOfficeRoadAddress()) == false) list.add(createLog("직장주소", s1.getOfficeRoadAddress(), s2.getOfficeRoadAddress()));
        if (Objects.equals(s1.getOfficeDetailAddress(), s2.getOfficeDetailAddress()) == false) list.add(createLog("직장주소", s1.getOfficeDetailAddress(), s2.getOfficeDetailAddress()));
        if (Objects.equals(s1.getOfficePostCode(), s2.getOfficePostCode()) == false) list.add(createLog("직장주소", s1.getOfficePostCode(), s2.getOfficePostCode()));

        if (s1.getMailTo() != s2.getMailTo()) list.add(createLog("우편물발송지", 발송지[s1.getMailTo()], 발송지[s2.getMailTo()]));
        if (s1.getMailReceiving() != s2.getMailReceiving()) list.add(createLog("메일발송", 동의[s1.getMailReceiving()], 동의[s2.getMailReceiving()]));
        if (s1.getEmailReceiving() != s2.getEmailReceiving()) list.add(createLog("이메일수신", 동의[s1.getEmailReceiving()], 동의[s2.getEmailReceiving()]));
        if (s1.getSmsReceiving() != s2.getSmsReceiving()) list.add(createLog("SMS수신", 동의[s1.getSmsReceiving()], 동의[s2.getSmsReceiving()]));
        if (s1.getPiuaRequiredItem() != s2.getPiuaRequiredItem()) list.add(createLog("개인정보 필수항목", 동의[s1.getPiuaRequiredItem()], 동의[s2.getPiuaRequiredItem()]));
        if (s1.getPiuaOptionalItem() != s2.getPiuaOptionalItem()) list.add(createLog("개인정보 선택항목", 동의[s1.getPiuaOptionalItem()], 동의[s2.getPiuaOptionalItem()]));
        if (s1.getPiuaIdentification() != s2.getPiuaIdentification()) list.add(createLog("개인정보 고유식별정보", 동의[s1.getPiuaIdentification()], 동의[s2.getPiuaIdentification()]));

        if (s1.isDmError() != s2.isDmError()) list.add(createLog("우편물 반송", s1.isDmError() ? "반송" : "정상", s2.isDmError() ? "반송" : "정상"));
        if (Objects.equals(s1.getDmErrorEtc(), s2.getDmErrorEtc()) == false) list.add(createLog("우편물 반송사유", s1.getDmErrorEtc(), s2.getDmErrorEtc()));

        if (Objects.equals(s1.getEtc(), s2.getEtc()) == false) list.add(createLog("비고", s1.getEtc(), s2.getEtc()));

        if (s1.isDmError() != s2.isDmError()) list.add(createLog("우편물 반송여부", s1.isDmError() ? "반송" : "", s2.isDmError() ? "반송" : ""));
        if (Objects.equals(s1.getDmErrorEtc(), s2.getDmErrorEtc()) == false) list.add(createLog("우편물 반송사유", s1.getDmErrorEtc(), s2.getDmErrorEtc()));

        Timestamp date = new Timestamp(System.currentTimeMillis());
        for (SponsorLog log : list) {
            log.setUserId(UserService.getCurrentUser().getId());
            log.setSponsorId(s2.getId());
            log.setWriteTime(date);
            sponsorLogMapper.insert(log);
        }
    }

    private SponsorLog createLog(String fieldName, String value1, String value2) {
        SponsorLog log = new SponsorLog();
        log.setField(fieldName);
        log.setValue1(value1);
        log.setValue2(value2);
        return log;
    }
}
