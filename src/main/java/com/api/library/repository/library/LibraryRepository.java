package com.api.library.repository.library;

import com.api.library.entity.library.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Integer> {

}
