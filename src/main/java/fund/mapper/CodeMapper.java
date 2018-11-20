package fund.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import fund.dto.Code;
import fund.dto.CodeGroup;

@Mapper
public interface CodeMapper {

    Code selectById(int id);
	Code selectByCodeName(String codeName);
	CodeGroup selectCodeGroupById(int id);
    List<Code> selectEnabledByCodeGroupId(int codeGroupID);
    List<Code> selectAllByCodeGroupId(int codeGroupID);

    void insert(Code code);
    void update(Code code);
    void delete(int ID);

	List<Code> selectAllPaymentMethod(@Param("name1") String name1, @Param("name2") String name2);
	List<CodeGroup> selectCodeGroup();
}
