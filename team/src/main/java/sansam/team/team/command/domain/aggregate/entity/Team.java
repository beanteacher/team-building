package sansam.team.team.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import sansam.team.common.aggregate.entity.BaseTimeEntity;
import sansam.team.team.command.domain.aggregate.TeamStatusType;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_team")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE tbl_team SET team_status = 'CLOSE' WHERE team_seq = ?")
public class Team extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long teamSeq;   // 팀 번호
    private long projectSeq;    // 프로젝트 번호
    private String teamName;    // 팀명
    @Enumerated(EnumType.STRING)
    private TeamStatusType teamStatus;
    private LocalDateTime endDate;

    public void modifyTeam(String teamName) {
        this.teamName = teamName;
    }

    public void ProjectSeq(Long projectSeq) {
        this.projectSeq = projectSeq;
    }
}
