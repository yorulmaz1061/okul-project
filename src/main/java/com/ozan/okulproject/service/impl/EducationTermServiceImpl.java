package com.ozan.okulproject.service.impl;

import com.ozan.okulproject.dto.logic.EducationTermDTO;
import com.ozan.okulproject.entity.logic.EducationTerm;
import com.ozan.okulproject.enums.Term;
import com.ozan.okulproject.exception.OkulProjectException;
import com.ozan.okulproject.mapper.MapperUtil;
import com.ozan.okulproject.repository.EducationTermRepository;
import com.ozan.okulproject.service.EducationTermService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationTermServiceImpl implements EducationTermService {
    private final EducationTermRepository educationTermRepository;
    private final MapperUtil mapperUtil;

    public EducationTermServiceImpl(EducationTermRepository educationTermRepository, MapperUtil mapperUtil) {
        this.educationTermRepository = educationTermRepository;
        this.mapperUtil = mapperUtil;
    }
    @Override
    public void save(EducationTermDTO dto) {
        EducationTerm educationTerm = mapperUtil.convert(dto, EducationTerm.class);

        int month = dto.getStartDate().getMonthValue();
        if (month <= 3) {
            educationTerm.setTerm(Term.SPRING_SEMESTER);
        } else if (month >= 8 && month <= 10) {
            educationTerm.setTerm(Term.FALL_SEMESTER);
        } else {
            educationTerm.setTerm(Term.SPECIAL_TERM);
        }

        educationTermRepository.save(educationTerm);
    }


    @Override
    public EducationTermDTO findById(Long id) throws OkulProjectException {
        EducationTerm educationTerm = educationTermRepository.findByIsDeletedAndId(false, id);
        if (educationTerm == null) {
            throw new OkulProjectException("No education term is found with that id: " + id);
        }
        return mapperUtil.convert(educationTerm, EducationTermDTO.class);

    }

    @Override
    public List<EducationTermDTO> listAllEducationTerms() {
        List<EducationTerm> educationTerms = educationTermRepository.findAllByIsDeleted(false);
        return educationTerms.stream().map(educationTerm -> mapperUtil.convert(educationTerm, EducationTermDTO.class)).toList();

    }

    @Override
    public EducationTermDTO deleteEducationTermById(Long id) {
        EducationTerm educationTerm = educationTermRepository.findByIsDeletedAndId(false, id);
        educationTerm.setIsDeleted(true);
        educationTermRepository.save(educationTerm);
        return mapperUtil.convert(educationTerm, EducationTermDTO.class);
    }
    @Override
    public EducationTermDTO updateEducationTerm(Long id, EducationTermDTO dto) throws OkulProjectException {
        EducationTerm educationTerm = educationTermRepository.findByIsDeletedAndId(false, id);
        if (educationTerm == null) {
            throw new OkulProjectException("No education term is found with that id: " + id);
        }
        educationTerm.setStartDate(dto.getStartDate());
        educationTerm.setEndDate(dto.getEndDate());
        educationTerm.setLastRegistrationDate(dto.getLastRegistrationDate());
        EducationTerm saved = educationTermRepository.save(educationTerm);
        return mapperUtil.convert(saved, EducationTermDTO.class);
    }



}
