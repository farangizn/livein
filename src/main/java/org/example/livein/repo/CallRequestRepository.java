package org.example.livein.repo;

import org.example.livein.entity.CallRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallRequestRepository extends JpaRepository<CallRequest, Integer> {
}