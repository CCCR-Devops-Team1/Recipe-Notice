package com.recipe.recipearticle.global.exeption;

import com.recipe.recipearticle.global.dto.response.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException {
    private ResponseStatus status;

    public BaseException(ResponseStatus status) {
        super(status.getMsg());
        this.status = status;
    }

}
