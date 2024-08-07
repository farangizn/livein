package com.xcdm.livein.repo;

import com.xcdm.livein.entity.CallRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallRequestRepository extends JpaRepository<CallRequest, Integer> {
}