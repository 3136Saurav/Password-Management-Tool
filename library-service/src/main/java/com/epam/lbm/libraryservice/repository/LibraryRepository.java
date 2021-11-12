package com.epam.lbm.libraryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.lbm.libraryservice.db.bean.entity.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {

}
