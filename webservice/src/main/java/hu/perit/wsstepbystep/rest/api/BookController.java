package hu.perit.wsstepbystep.rest.api;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import hu.perit.wsstepbystep.rest.model.BookDTO;
import hu.perit.wsstepbystep.rest.model.BookParams;
import hu.perit.wsstepbystep.rest.model.ResponseUri;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@RestController
@Slf4j
public class BookController implements BookApi
{

	@Override
	public List<BookDTO> getAllBooks() {
		
		try {
			String alma = null; // NPE-t is kezeli a @ControllerAdvice
			alma.toLowerCase();
			
		} catch(Exception e) {
			throw new RuntimeException("gebasz van!" ,e);
			
		}
		
		List<BookDTO> books = new ArrayList<>();
		books.add(createBookDTO(12L));
		books.add(createBookDTO(13L));
		
		return books;
	}
	
	//Get Book By Id
	@Override
	public BookDTO getBookById(Long id) {
		
		//log.debug("getBookById()");
		return createBookDTO(id);
	}
	
	//CreateBook
	@Override
	public ResponseUri createBook(BookParams bookParams) {
		
		long newBookId = 12;		//ezt majd a DB adja aut. később
		
		System.out.println("createBook() " + bookParams.toString());
		
		URI location = ServletUriComponentsBuilder		//konvenció, h a létrehozott cucc elérési útvonalát adja vissza create-nél!
						.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(newBookId).toUri();
		
		return new ResponseUri().location(location.toString());
	}
	
	
	@Override
	public void	updateBook(Long id, BookParams bookParams) {
		System.out.println("updateBook, Book Id: " + id + bookParams.toString());
		
	}
	
	@Override
	public void	deleteBook(Long id) {
		System.out.println("deleteBook(), Book Id: " + id);
		
	}
	
	

	private BookDTO createBookDTO(Long id) {
		
		BookDTO bookDTO = new BookDTO(id);
		bookDTO.setAuthor("Richard Feinman");
		bookDTO.setPages(120);
		bookDTO.setTitle("Tréfál, Feinman Úr?");
		bookDTO.setDateIssued(LocalDate.of(2021, 12, 24));
		
		return bookDTO;
	}
	
	
}
