package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.ArticleEntity;
import com.app.homeworkoutapplication.entity.DocumentEntity;
import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.document.dto.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface DocumentMapper extends EntityMapper<Document, DocumentEntity> {

    @Mapping(target = "applicantId", source = "applicant.id")
    Document toDto(DocumentEntity entity);

    @Mapping(target = "applicant.id", source = "applicantId")
    DocumentEntity toEntity(Document dto);
} 