package hu.perit.wsstepbystep.rest.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BookParams {
	

	private String title;
	private String author;
	private Integer pages;
	private LocalDate dateIssued;
	
	
	@Override
	public String toString() {
		return "BookParams [title=" + title + ", author=" + author + ", pages=" + pages + ", dateIssued=" + dateIssued
				+ "]";
	}
	
}
