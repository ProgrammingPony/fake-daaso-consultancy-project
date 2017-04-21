/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daaso.Hibernate;

/**
 * Source: http://stackoverflow.com/questions/23890687/hibernate-4-with-java-time-localdate-and-date-construct
 * 
 */
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocalDateTimePersistenceConverter implements AttributeConverter<LocalDateTime, java.sql.Timestamp> {

  @Override
  public java.sql.Timestamp convertToDatabaseColumn(LocalDateTime entityValue) {
    if (entityValue != null) {
      return java.sql.Timestamp.valueOf(entityValue);
    }
    return null;
  }

  @Override
  public LocalDateTime convertToEntityAttribute(java.sql.Timestamp databaseValue) {
    if (databaseValue != null) {
      return databaseValue.toLocalDateTime();
    }
    return null;
  }
}