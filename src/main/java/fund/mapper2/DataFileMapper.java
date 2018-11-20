package fund.mapper2;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import fund.dto.DataFile;

@Mapper
public interface DataFileMapper {

    List<DataFile> selectByForeignId(@Param("foreignType") String foreignType, @Param("foreignId") int foreignId);
    int selectCountByForeignId(@Param("foreignType") String foreignType, @Param("foreignId") int foreignId);
    DataFile selectById(int id);
    void insert(DataFile file);
    void delete(int id);
    void deleteByForeignId(@Param("foreignType") String foreignType, @Param("foreignId") int foreignId);

}
