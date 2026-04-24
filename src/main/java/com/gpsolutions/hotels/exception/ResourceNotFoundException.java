package com.gpsolutions.hotels.exception;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message) {
    super(message);
  }

  public static ResourceNotFoundException byId(String entity, Long id) {
    return new ResourceNotFoundException("%s with id %d not found".formatted(entity, id));
  }

}
