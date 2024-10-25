package sansam.team.project.command.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sansam.team.common.aggregate.DevelopType;

@Getter
@Setter
@NoArgsConstructor
public class ProjectMemberAddDTO {
    private Long userSeq;
    private DevelopType projectMemberDevelopType;  // Enum 타입 사용

    // 생성자, getter, setter 메서드 추가
    @JsonCreator  // 문자열을 Enum으로 변환할 수 있도록 Jackson 설정
    public ProjectMemberAddDTO(@JsonProperty("userSeq") Long userSeq,
                               @JsonProperty("projectMemberDevelopType") DevelopType projectMemberDevelopType) {
        this.userSeq = userSeq;
        this.projectMemberDevelopType = projectMemberDevelopType;
    }

}
