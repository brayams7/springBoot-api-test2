package com.api.library.controller.library;

import com.api.library.entity.library.Book;
import com.api.library.entity.library.Library;
import com.api.library.repository.library.BookRepository;
import com.api.library.repository.library.LibraryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/book")
public class BookController {

    BookRepository bookRepository;
    LibraryRepository libraryRepository;

    public BookController(BookRepository bookRepository, LibraryRepository libraryRepository) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
    }

    @GetMapping(path = "/list")
    public ResponseEntity<Page<Book>> listBooks(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                @RequestParam(name = "size", defaultValue = "5", required = false) int size){
        return new ResponseEntity<Page<Book>>(this.bookRepository.findAll(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Book> createBook(@Validated @RequestBody Book book){

        Optional<Library> libraryOptional = this.libraryRepository.findById(book.getLibrary().getId());
        if(!libraryOptional.isPresent()){
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }
        book.setLibrary(libraryOptional.get());
        Book newLibrary = this.bookRepository.save(book);
        return new ResponseEntity<Book>(newLibrary, HttpStatus.CREATED);

    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Integer id){
        Optional<Book> isFind = this.bookRepository.findById(id);
        if(isFind.isPresent()){
            this.bookRepository.deleteById(id);
            return new ResponseEntity<Book>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Integer id){
        //El response entity retorna un cuerpo y un codigo http
        try {
            Book book = this.bookRepository.getById(id);
            return new ResponseEntity<Book>(book, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping(path = "/update/{id}")
    ResponseEntity<Book> updateBook(@RequestBody Book newBook, @PathVariable Integer id){

        Optional<Library> libraryOptional = this.libraryRepository.findById(newBook.getLibrary().getId());
        if(!libraryOptional.isPresent()){
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }

        Book book = this.bookRepository.getById(id);
        if(!(book.getClass() == Book.class)){
            System.out.println("No entró");
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }

        book.setName(newBook.getName());
        book.setLibrary(libraryOptional.get());
        this.bookRepository.save(book);

        return new ResponseEntity<Book>(book,HttpStatus.OK);

    }
}
