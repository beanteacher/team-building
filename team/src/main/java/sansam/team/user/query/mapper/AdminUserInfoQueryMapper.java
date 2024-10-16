package sansam.team.user.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import sansam.team.user.query.dto.AdminUserInfoAllQueryDTO;

import java.util.List;

@Mapper
public interface AdminUserInfoQueryMapper {


    List<AdminUserInfoAllQueryDTO> findAllUserInfo();
}
