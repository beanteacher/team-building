package sansam.team.user.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sansam.team.user.command.domain.aggregate.entity.LoginLog;
import sansam.team.user.command.domain.repository.LoginLogRepository;

public interface JpaLoginLogRepository extends LoginLogRepository, JpaRepository<LoginLog, Long> {

}
