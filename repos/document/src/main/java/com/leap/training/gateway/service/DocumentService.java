package com.leap.training.gateway.service;

import com.leap.training.gateway.domain.Document;
import com.leap.training.gateway.repository.DocumentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Document}.
 */
@Service
@Transactional
public class DocumentService {

    private final Logger log = LoggerFactory.getLogger(DocumentService.class);

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    /**
     * Save a document.
     *
     * @param document the entity to save.
     * @return the persisted entity.
     */
    public Document save(Document document) {
        log.debug("Request to save Document : {}", document);
        return documentRepository.save(document);
    }

    /**
     * Partially update a document.
     *
     * @param document the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Document> partialUpdate(Document document) {
        log.debug("Request to partially update Document : {}", document);

        return documentRepository
            .findById(document.getId())
            .map(existingDocument -> {
                if (document.getDocumentName() != null) {
                    existingDocument.setDocumentName(document.getDocumentName());
                }
                if (document.getEmployeeId() != null) {
                    existingDocument.setEmployeeId(document.getEmployeeId());
                }

                return existingDocument;
            })
            .map(documentRepository::save);
    }

    /**
     * Get all the documents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Document> findAll(Pageable pageable) {
        log.debug("Request to get all Documents");
        
        return documentRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Document> findAllByEmployeeId(Pageable pageable, Long emid) {
        log.debug("Request to get all Documents");
        
        List<Document> list = documentRepository.findAll(pageable)
                                .stream()
                                .filter(document-> document.getEmployeeId() == emid)
                                .collect(Collectors.toList());

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Document> currentPageList;

        if (list.size() < startItem) {
            currentPageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, list.size());
            currentPageList = list.subList(startItem, toIndex);
        }

        return new PageImpl<>(currentPageList, pageable, list.size());
    }

    /**
     * Get one document by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Document> findOne(Long id) {
        log.debug("Request to get Document : {}", id);
        return documentRepository.findById(id);
    }

    /**
     * Delete the document by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Document : {}", id);
        documentRepository.deleteById(id);
    }
}
