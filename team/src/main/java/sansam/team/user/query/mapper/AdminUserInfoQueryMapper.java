package sansam.team.user.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sansam.team.user.query.dto.AdminUserInfoAllQueryDTO;
import sansam.team.user.query.dto.AdminUserInfoQueryDTO;

import java.util.List;

@Mapper
public interface AdminUserInfoQueryMapper {


    List<AdminUserInfoAllQueryDTO> findAllUserInfo();

    AdminUserInfoQueryDTO findUserInfo(@Param("userSeq") Long userSeq);
}
