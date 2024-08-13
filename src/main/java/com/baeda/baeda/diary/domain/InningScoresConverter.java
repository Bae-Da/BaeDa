package com.baeda.baeda.diary.domain;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.List;

@Converter
public class InningScoresConverter implements AttributeConverter<List<Integer>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    //List<Integer>를 JSON 문자열로 변환(DB 저장될 때 호출됨)
    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
        try{
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException jpe){
            throw new RuntimeException("Error converting List to Json", jpe);
        }
    }

    //JSON 문자열을 List<Integer>로 변환(DB에서 데이터 읽어옴)
    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        try{
            return objectMapper.readValue(dbData, new TypeReference<List<Integer>>() {});
        }catch (IOException ioe){
            throw new RuntimeException("Error converting Json to List", ioe);
        }
    }
}
