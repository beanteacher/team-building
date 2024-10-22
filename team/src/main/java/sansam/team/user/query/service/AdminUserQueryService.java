package sansam.team.user.query.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sansam.team.exception.CustomException;
import sansam.team.user.query.dto.AdminLoginLogDTO;
import sansam.team.user.query.mapper.AdminLoginLogMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserQueryService {

    private final AdminLoginLogMapper adminLoginLogMapper;

    public List<AdminLoginLogDTO> findAllLoginLog(AdminLoginLogDTO.RequestLoginLogDTO adminLoginLogDTO) throws CustomException {
        return adminLoginLogMapper.findAllLoginLog(adminLoginLogDTO);
    }

}
