package com.ask.auth.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ask.auth.model.entity.IpBlock;

@Repository
public interface IpBlockRepository extends JpaRepository<IpBlock, String> {
    Optional<IpBlock> findByIpAddress(String ipAddress);

    boolean existsByIpAddressAndBlockedUntilAfter(String ipAddress, Instant now);
}