package com.api.library.controller.library;

import com.api.library.entity.library.Library;
import com.api.library.repository.library.LibraryRepository;
import com.api.library.validators.LibraryValidaror;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/library")
public class LibraryController {

    LibraryRepository libraryRepository;
    LibraryValidaror libraryValidaror;
    public LibraryController(LibraryRepository libraryRepository, LibraryValidaror libraryValidaror) {
        this.libraryRepository = libraryRepository;
        this.libraryValidaror = libraryValidaror;
    }

    @GetMapping(path = "/list")
    public List<Library> listLibraries(){
        return this.libraryRepository.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<Library> createLibrary(@RequestBody Library library){
        this.libraryValidaror.validate(library);
        Library newLibrary = this.libraryRepository.save(library);
        return new ResponseEntity<Library>(newLibrary,HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Library> deleteLibrary(@PathVariable Integer id){
        Optional<Library> isFind = this.libraryRepository.findById(id);
        if(isFind.isPresent()){
            this.libraryRepository.deleteById(id);
            return new ResponseEntity<Library>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Library>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<Library> getLibrary(@PathVariable Integer id){
        //El response entity retorna un cuerpo y un codigo http
        /*try {

            Library library = this.libraryRepository.getById(id);
            System.out.println("entró al get: "+library.getName());
            return new ResponseEntity<Library>(library, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Library>(HttpStatus.NOT_FOUND);
        }*/
        Optional<Library> isFind = this.libraryRepository.findById(id);
        if(isFind.isPresent()){
            return new ResponseEntity<Library>(isFind.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Library>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/update/{id}")
    ResponseEntity<Library> updateLibrary(@RequestBody Library newLibrary, @PathVariable Integer id){
        Optional<Library> isFind = this.libraryRepository.findById(id);
        if(isFind.isPresent()){
            Library library = isFind.map(
                    lib -> {
                        lib.setName(newLibrary.getName());
                        return this.libraryRepository.save(lib);
                    }).get();
            return new ResponseEntity<Library>(library,HttpStatus.OK);
        }
        return new ResponseEntity<Library>(HttpStatus.NOT_FOUND);
    }
}
