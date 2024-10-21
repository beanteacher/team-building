package sansam.team.user.command.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sansam.team.user.command.domain.aggregate.entity.User;
import sansam.team.user.command.domain.repository.AdminUserRepository;

public interface JpaAdminUserRepository extends AdminUserRepository, JpaRepository<User, Long> {
}
