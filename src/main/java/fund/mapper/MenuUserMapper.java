package fund.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import fund.dto.MenuUser;

@Mapper
 public interface MenuUserMapper {
     List<Integer> selectMenuIdByUserId(int userId);
     List<MenuUser> selectMenuUserByUserId(int userId);

     void delete(@Param("menuId") int menuId, @Param("userId") int userId);
     void insert(@Param("menuId") int menuId, @Param("userId") int userId);
 }

