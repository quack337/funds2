package fund.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import fund.dto.Commitment;

@Mapper
public interface CommitmentMapper {

    Commitment selectById(int id);
	Commitment selectByCommitmentNo(String commitmentNo);
	Commitment selectForEB14(@Param("commitmentNo12") String commitmentNo12, @Param("eb13Date") Date eb13Date);

	List<Commitment> selectBySponsorId(int id);
	String generateCommitmentNo(int sponsorId);

	void insert(Commitment commitment);
	void update(Commitment commitment);
	void delete(int id);
	void updateEndDate(int id);
	void open(int id);

	// CMS
    List<Commitment> selectByEB13Date(Date date);
	List<Commitment> selectEB13Candidate();
    List<Commitment> selectEB21Candidate(Map<String,Object> map);
	List<Commitment> selectCmsResult(Map<String,Object> map);
	void updateEB13(Commitment commitment);
}

