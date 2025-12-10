package com.practice.wingsMockT4.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.wingsMockT4.entity.TouristAttraction;

public interface TouristAttractionRepo extends JpaRepository<TouristAttraction, Long> {

	List<TouristAttraction> findByNameContainsIgnoreCaseOrDescriptionContainingIgnoreCaseOrLocationContainingIgnoreCase(
			String name, String description, String location);

}
