package com.recipe.recipearticle.Exception;

import com.recipe.recipearticle.Dto.Response.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException{
  private ResponseStatus status;

  public BaseException(ResponseStatus status){
    super(status.getMsg());
    this.status = status;
  }

}
