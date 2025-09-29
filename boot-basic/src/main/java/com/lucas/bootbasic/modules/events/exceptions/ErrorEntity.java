package com.lucas.bootbasic.modules.events.exceptions;

import com.lucas.bootbasic.modules.events.exceptions.obj.AfterObj;
import com.lucas.bootbasic.modules.events.exceptions.obj.AsyncObj;
import com.lucas.bootbasic.modules.events.exceptions.obj.BeforeObj;
import com.lucas.bootbasic.modules.events.exceptions.obj.ErrorObj;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;


    public ErrorEntity fromErrorObj(ErrorObj obj) {
        return new ErrorEntity(
                obj.getId(),
                obj.getMessage()
        );
    }

    public ErrorEntity fromErrorObj(AfterObj obj) {
        return new ErrorEntity(
                obj.getId(),
                obj.getMessage()
        );
    }

    public ErrorEntity fromErrorObj(BeforeObj obj) {
        return new ErrorEntity(
                obj.getId(),
                obj.getMessage()
        );
    }

    public ErrorEntity fromErrorObj(AsyncObj obj) {
        return new ErrorEntity(
                obj.getId(),
                obj.getMessage()
        );
    }
}
