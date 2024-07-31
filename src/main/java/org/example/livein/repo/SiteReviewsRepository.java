package org.example.livein.repo;

import org.example.livein.entity.SiteReviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteReviewsRepository extends JpaRepository<SiteReviews, Integer> {
}