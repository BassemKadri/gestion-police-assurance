package com.tinubu.assurance.infrastructure.storage.repository;

import com.tinubu.assurance.infrastructure.storage.entity.InsurancePolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InsurancePolicyEntityRepository extends JpaRepository<InsurancePolicyEntity, UUID> {

}
