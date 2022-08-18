package com.api.library.validators;

import com.api.library.entity.library.Book;
import com.api.library.exceptions.ResourceBadRequestException;
import org.springframework.stereotype.Component;

@Component
public class BookValidators {

    public void validate(Book book){
        try {
            if(book.getName() == null || book.getName().isEmpty() || book.getName().equals("")){
                this.message("Agrega un valor correcto al campo nombre");
            }
            if(book.getLibrary().getId() == null || String.valueOf(book.getLibrary().getId()).isEmpty()){
                this.message("Ingresa un id de biblioteca");
            }
        }catch (Exception e){
            this.message("Agrega los campos correctos");
        }
    }

    private void message(String message){
        throw new ResourceBadRequestException(message);
    }
}
