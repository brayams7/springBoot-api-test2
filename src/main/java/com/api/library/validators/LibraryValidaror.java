package com.api.library.validators;

import com.api.library.entity.library.Library;
import com.api.library.exceptions.ResourceBadRequestException;
import org.springframework.stereotype.Component;

@Component
public class LibraryValidaror {

    public void validate(Library library){
        try {
            if(library.getName() == null || library.getName().isEmpty()){
                this.message("El valor de nombre es incorrecto");
            }
        }catch (Exception e){
            this.message("Ingrese correctamente los campos");
        }
    }


    private void message(String message){
        throw new ResourceBadRequestException(message);
    }

}
