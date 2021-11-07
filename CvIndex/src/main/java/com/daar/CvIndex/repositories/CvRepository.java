package com.daar.CvIndex.repositories;

import com.daar.CvIndex.models.CV;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CvRepository extends ElasticsearchRepository<CV,String> {

    List<CV> findByPrenom(String filtre);

    List<CV> findByNom(String filtre);

    @Query("{\"bool\" : {" +
            "\"must\" : [{ \"match\" : {\"nom\" : \"?0\"}},"+
            "{\"match\" : {\"prenom\" : \"?1\"}}]}}")
    List<CV> findByNomAndPrenom(String nom, String prenom);

    List<CV> findByMotsCles(String filtre);

    List<CV> findByMotsCles(List<String> filtre);

    List<CV> findByCompetences(String filtre);

    List<CV> findByCompetences(List<String> filtre);




}