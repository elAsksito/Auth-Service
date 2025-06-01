package com.ask.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ask.auth.model.entity.IpBlock;

@Repository
public interface IpBlockRepository extends JpaRepository<IpBlock, String> {
}