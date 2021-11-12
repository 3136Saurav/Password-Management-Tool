package com.epam.lbm.libraryservice.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.epam.lbm.libraryservice.db.bean.entity.Library;
import com.epam.lbm.libraryservice.dto.LibraryDto;
import com.epam.lbm.libraryservice.exceptions.DuplicateEntryException;
import com.epam.lbm.libraryservice.exceptions.NoLibraryDetailsException;
import com.epam.lbm.libraryservice.repository.LibraryRepository;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

	@InjectMocks
	LibraryServiceImpl libraryService;
	
	@Mock
	LibraryRepository libraryRepositoryMock;
	
	@Mock
	ModelMapper modelMapper;
	
	@Test
	void testSaveLibraryDetail_withDuplicateEntryException() throws DuplicateEntryException {
		LibraryDto libraryDto = new LibraryDto();
		libraryDto.setId(1001L);
		when(libraryRepositoryMock.existsById(1001L)).thenReturn(true);
		
		Assertions.assertThrows(DuplicateEntryException.class, () -> {
			libraryService.saveLibraryDetail(libraryDto);
		});
	}
	
	@Test
	void testSaveLibraryDetail() throws DuplicateEntryException {
		LibraryDto libraryDto = new LibraryDto();
		libraryDto.setId(1001L);
		
		Library library = new Library();
		library.setId(1001L);
		
		when(libraryRepositoryMock.existsById(1001L)).thenReturn(false);
		when(modelMapper.map(libraryDto, Library.class)).thenReturn(library);
		when(libraryRepositoryMock.save(library)).thenReturn(library);
		
		Assertions.assertEquals(library, libraryService.saveLibraryDetail(libraryDto));
	}
	
	
	@Test
	void testFindAllLibraryDetails() throws NoLibraryDetailsException {
		Library library = new Library();
		library.setId(1001L);
		
		List<Library> libraryBooksRecord = new ArrayList<>();
		libraryBooksRecord.add(library);
		
		when(libraryRepositoryMock.findAll()).thenReturn(libraryBooksRecord);
		
		Assertions.assertEquals(libraryBooksRecord, libraryService.findAllLibraryDetails());
	}
	
	@Test
	void testFindAllLibraryDetails_withNoLibraryDetailsException() throws NoLibraryDetailsException {
		List<Library> libraryBooksRecord = new ArrayList<>();
		
		when(libraryRepositoryMock.findAll()).thenReturn(libraryBooksRecord);
		
		Assertions.assertThrows(NoLibraryDetailsException.class, () -> {
			libraryService.findAllLibraryDetails();
		});	
	}
		
	@Test
	void testDeleteLibraryDetailById() throws NoLibraryDetailsException {
		
		when(libraryRepositoryMock.existsById(1001L)).thenReturn(true);
		libraryService.deleteLibraryDetailById(1001L);
		
		verify(libraryRepositoryMock, times(1)).deleteById(1001L);
	}

	@Test
	void testDeleteLibraryDetailById_withNoLibraryDetailsException() throws NoLibraryDetailsException {
		
		when(libraryRepositoryMock.existsById(1001L)).thenReturn(false);
		
		Assertions.assertThrows(NoLibraryDetailsException.class, () -> {
			libraryService.deleteLibraryDetailById(1001L);
		});	
	}
}
