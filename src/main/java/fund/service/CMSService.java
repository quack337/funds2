package fund.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fund.dto.Commitment;
import fund.dto.Sponsor;
import fund.mapper.CommitmentMapper;
import fund.mapper.SponsorMapper;

@Service
public class CMSService {

    @Autowired SponsorMapper sponsorMapper;
    @Autowired CommitmentMapper commitmentMapper;

    public String getCommitmentNo12(String commitmentNo) {
        String cno = commitmentNo.replaceAll("-", "");
        if (cno.length() > 12) cno = cno.substring(0, 12);
        else if (cno.length() < 12) cno = String.format("%-12s", cno).replace(" ", "0");
        return cno;
    }

    public Commitment selectCommitmentByCommitmentNo12(String commitmentNo, Date eb13Date) {
        if (commitmentNo.substring(10, 2).equals("00")) {
            String cno = commitmentNo.substring(0, 10);
            Commitment commitment = commitmentMapper.selectByCommitmentNo(cno);
            if (commitment != null) return commitment;
        }
        Sponsor sponsor = sponsorMapper.selectBySponsorNo(commitmentNo);
        if (sponsor != null) {
            Commitment commitment = commitmentMapper.selectForEB14(commitmentNo, eb13Date);
            if (commitment != null) return commitment;
        }
        return null;
    }
}
