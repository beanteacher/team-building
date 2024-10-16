package sansam.team.user.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import sansam.team.user.query.dto.AdminLoginLogDTO;

import java.util.Optional;

@Mapper
public interface AdminLoginLogMapper {

    Optional<AdminLoginLogDTO> findAllLoginLog(AdminLoginLogDTO adminLoginLogDTO);

}
