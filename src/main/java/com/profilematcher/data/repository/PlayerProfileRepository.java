package com.profilematcher.data.repository;

import com.profilematcher.data.entity.PlayerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerProfileRepository extends JpaRepository<PlayerProfile, Long> {

    PlayerProfile findByPlayerId(String playerId);
}
