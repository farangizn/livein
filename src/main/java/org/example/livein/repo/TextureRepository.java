package org.example.livein.repo;

import org.example.livein.entity.Texture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextureRepository extends JpaRepository<Texture, Integer> {
}