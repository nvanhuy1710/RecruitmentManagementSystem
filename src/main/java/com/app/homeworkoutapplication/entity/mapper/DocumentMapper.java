package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.DocumentEntity;
import com.app.homeworkoutapplication.module.document.dto.Document;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface DocumentMapper extends EntityMapper<Document, DocumentEntity> {
} 