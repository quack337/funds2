package fund.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import fund.dto.Certificate;
import fund.dto.pagination.Pagination;

@Mapper
public interface CertificateMapper {

  Certificate selectById(int id);
  List<Certificate> selectPage(Pagination pagination);
  int selectCount(Pagination pagination);
  void insert(Certificate certificate);
  void delete(int id);
  String generateCertificateNo(int type);
}
