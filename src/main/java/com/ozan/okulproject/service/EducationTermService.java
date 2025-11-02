package com.ozan.okulproject.service;

import com.ozan.okulproject.dto.logic.EducationTermDTO;
import com.ozan.okulproject.exception.OkulProjectException;

import java.util.List;

public interface EducationTermService {

    void save(EducationTermDTO dto);

    EducationTermDTO findById(Long id) throws OkulProjectException;

    List<EducationTermDTO> listAllEducationTerms();

    EducationTermDTO deleteEducationTermById(Long id);

    EducationTermDTO updateEducationTerm(Long id, EducationTermDTO dto) throws OkulProjectException;


}
