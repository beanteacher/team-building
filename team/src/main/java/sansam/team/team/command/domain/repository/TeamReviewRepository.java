package sansam.team.team.command.domain.repository;

import sansam.team.team.command.domain.aggregate.entity.TeamReview;

import java.util.Optional;

public interface TeamReviewRepository {
    TeamReview save(TeamReview teamReview);
    Optional<TeamReview> findById(long reviewSeq);
    void deleteById(long reviewSeq);

    Optional<TeamReview> findAllByReceiveMemberSeq(long userSeq);
}