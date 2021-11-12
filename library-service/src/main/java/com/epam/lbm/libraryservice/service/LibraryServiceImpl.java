package com.epam.lbm.libraryservice.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.lbm.libraryservice.db.bean.entity.Library;
import com.epam.lbm.libraryservice.dto.LibraryDto;
import com.epam.lbm.libraryservice.exceptions.DuplicateEntryException;
import com.epam.lbm.libraryservice.exceptions.NoLibraryDetailsException;
import com.epam.lbm.libraryservice.repository.LibraryRepository;

@Service
public class LibraryServiceImpl implements LibraryService {
	ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	LibraryRepository libraryRepository;
	
	@Override
	public Library saveLibraryDetail(LibraryDto libraryDto) throws DuplicateEntryException {
		if (libraryRepository.existsById(libraryDto.getId())) {
			throw new DuplicateEntryException("Duplicate Entry!");
		}
		
		Library library = modelMapper.map(libraryDto, Library.class);
		return libraryRepository.save(library);
	}
	
	@Override
	public List<Library> findAllLibraryDetails() throws NoLibraryDetailsException {
		List<Library> libraryRecords = libraryRepository.findAll();
		if (libraryRecords.isEmpty()) {
			throw new NoLibraryDetailsException("No Records Available!");
		}
		
		return libraryRecords;
	}

	@Override
	public String deleteLibraryDetailById(Long id) throws NoLibraryDetailsException {
		if (!libraryRepository.existsById(id)) {
			throw new NoLibraryDetailsException("No Records Available!");
		}
		libraryRepository.deleteById(id);
		return "Library Detail record deleted successfully";
	}
}
