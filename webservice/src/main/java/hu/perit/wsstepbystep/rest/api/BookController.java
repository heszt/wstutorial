package hu.perit.wsstepbystep.rest.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import hu.perit.wsstepbystep.rest.model.BookDTO;
import lombok.Data;

@Data
@RestController
public class BookController implements BookApi
{

	@Override
	public List<BookDTO> getAllBooks() {
		
		List<BookDTO> books = new ArrayList<>();
		books.add(createBookDTO(12L));
		books.add(createBookDTO(13L));
		
		return books;
	}

	private BookDTO createBookDTO(Long id) {
		
		BookDTO bookDTO = new BookDTO(id);
		bookDTO.setAuthor("Richard Feinman");
		bookDTO.setPages(120);
		bookDTO.setTitle("Tréfál, Feinman Úr?");
		bookDTO.setDateIssued(LocalDate.now());
		
		return bookDTO;
	}
	
}
