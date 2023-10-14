package com.recipe.recipearticle.Util;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
  public static List<String> getValidationError(BindingResult bindingResult){
    List<String> error_list = new ArrayList<>();

    if(bindingResult.hasErrors()){
      bindingResult.getFieldErrors().forEach(error -> {
        error_list.add(error.getDefaultMessage());
      });
    }
    return error_list;
  }
}
