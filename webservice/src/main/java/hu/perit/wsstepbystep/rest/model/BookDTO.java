package hu.perit.wsstepbystep.rest.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BookDTO {

		private final Long id;
		private String title;
		private String author;
		private Integer pages;
		private LocalDate dateIssued;
		
		public BookDTO(Long id) {
			super();
			this.id = id;
		}
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public Integer getPages() {
			return pages;
		}
		public void setPages(Integer pages) {
			this.pages = pages;
		}
		public LocalDate getDateIssued() {
			return dateIssued;
		}
		public void setDateIssued(LocalDate dateIssued) {
			this.dateIssued = dateIssued;
		}
		public Long getId() {
			return id;
		}
		
}
