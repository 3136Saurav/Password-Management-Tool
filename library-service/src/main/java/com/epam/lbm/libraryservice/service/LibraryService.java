package com.epam.lbm.libraryservice.service;

import java.util.List;

import com.epam.lbm.libraryservice.db.bean.entity.Library;
import com.epam.lbm.libraryservice.dto.LibraryDto;
import com.epam.lbm.libraryservice.exceptions.DuplicateEntryException;
import com.epam.lbm.libraryservice.exceptions.NoLibraryDetailsException;

public interface LibraryService {

	Library saveLibraryDetail(LibraryDto libraryDto) throws DuplicateEntryException;

	List<Library> findAllLibraryDetails() throws NoLibraryDetailsException;

	String deleteLibraryDetailById(Long id) throws NoLibraryDetailsException;
}