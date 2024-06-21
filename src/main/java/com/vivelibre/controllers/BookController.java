package com.vivelibre.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivelibre.exception.BookLoadingException;
import com.vivelibre.model.Book;
import com.vivelibre.model.BookDate;

@RestController
public class BookController {

	private List<Book> bookList;
	Map<String, List<BookDate>> groupedAndSortedBooks;

	public BookController() {

		ObjectMapper mapper = new ObjectMapper();
		ClassPathResource resource = new ClassPathResource("books.json");

		try (InputStream inputStream = resource.getInputStream()) {
			
            Book[] booksArray = mapper.readValue(inputStream, Book[].class);
            bookList = Arrays.asList(booksArray);
        
		} catch (IOException e) {
            throw new BookLoadingException("No se ha podido cargar el archivo", e);
        }

	}

	@GetMapping(value = "filter/{filter}")
	public ResponseEntity<BookDate> filter(@PathVariable String filter) {
		return ResponseEntity.ok(filter(filter, bookList).get());
	}

	private Optional<BookDate> filter(String filter, List<Book> books) {

		// 1-Escriba por pantalla los libros que no tengan fecha de publicación
		List<Book> bookSinFechaList = books.stream()
				.filter(b -> b.getPublicationTimestamp() == null)
				.toList();

		bookSinFechaList.forEach(System.out::println);

		/*
		 * 2-Devuelva los libros que contengan la cadena de caracteres en el nombre, en
		 * el resumen y en la biografia del autor del libro. En caso de encontrar más de
		 * un libro en la lista devolver aquel más recientemente publicado. Además se
		 * deberá devolver el libro con un campo date adicional que contenga el
		 * timestamp con el siguiente formato de fecha: mm-dd-yyyy.
		 */

		List<BookDate> booksDate = books.stream()
				.filter(book -> containsString(book.getAuthor().getName(), filter)
						|| containsString(book.getSummary(), filter)
						|| containsString(book.getAuthor().getBio(), filter))
				.sorted(Comparator.comparing(book -> safeGetTimestamp(book.getPublicationTimestamp())))
				.map(BookDate::new)
				.toList();

		/*
		 * 3-Por último, la lista deberá quedar ordenada de la siguiente manera: Primero
		 * agrupada por fecha de publicación y luego ordenada por la biografia de autor
		 * más corta.
		 */
		groupedAndSortedBooks = booksDate.stream()
				.collect(Collectors.groupingBy(book -> Objects.toString(book.getPublicationTimestamp(), ""),
						Collectors.collectingAndThen(Collectors.toList(),
								list -> list.stream()
										.sorted(Comparator.comparingInt(book -> book.getAuthor().getBio().length()))
										.toList())));

		/*
		 * groupedAndSortedBooks.forEach((timestamp, sortedBooks) -> {
		 * System.out.println("Timestamp: " + timestamp); sortedBooks.forEach(book -> {
		 * String bio = book.getAuthor().getBio(); System.out.println("  Book: " +
		 * book.getTitle() + ", Bio Length: " + (bio != null ? bio.length() : 0)); });
		 * });
		 */
		// retorno un elemento como pone el punto 2 y la firma de la funcion
		return Optional.of(booksDate.get(0));

	}

	private static boolean containsString(String str, String searchStr) {
		return str != null && str.toLowerCase().contains(searchStr.toLowerCase());
	}

	private static Long safeGetTimestamp(String timestamp) {
		return (timestamp == null) ? 1L : Long.parseLong(timestamp);
	}

}
