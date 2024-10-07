package sansam.team.user.command.domain.repository;

import sansam.team.user.command.domain.aggregate.entity.LoginLog;

public interface LoginLogRepository {

    LoginLog save(LoginLog loginLog);

}
