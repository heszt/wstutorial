package hu.perit.wsstepbystep.rest.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import hu.perit.wsstepbystep.rest.model.BookDTO;

public interface BookApi {
	
final String BASE_URL_BOOKS = "/books";
	
	@GetMapping(BASE_URL_BOOKS)
	List<BookDTO> getAllBooks();

}
