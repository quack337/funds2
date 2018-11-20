package fund.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import fund.dto.Todo;
import fund.dto.pagination.Pagination;

@Mapper
public interface TodoMapper {
    Todo selectById(int id);
    List<Todo> selectPage(Pagination pagination);
    int selectCount(Pagination pagination);
    List<Todo> selectAlert(int userId);
    void insert(Todo todo);
    void update(Todo todo);
    void delete(int id);
    void confirm(int id);
    void cancelConfirm(int id);
}
