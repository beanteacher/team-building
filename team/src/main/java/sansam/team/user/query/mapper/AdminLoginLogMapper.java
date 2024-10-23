package sansam.team.user.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import sansam.team.user.query.dto.AdminLoginLogDTO;

import java.util.List;

@Mapper
public interface AdminLoginLogMapper {

    List<AdminLoginLogDTO> findAllLoginLog(AdminLoginLogDTO.RequestLoginLogDTO adminLoginLogDTO);

}
