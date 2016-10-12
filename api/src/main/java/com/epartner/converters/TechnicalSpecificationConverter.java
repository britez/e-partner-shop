package com.epartner.converters;

import com.epartner.domain.TechnicalSpecification;
import com.epartner.representations.TechnicalSpecificationRepresentation;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Created by mbritez on 24/09/16.
 */
@Component
public class TechnicalSpecificationConverter {

    public List<TechnicalSpecification> convertList(List<TechnicalSpecificationRepresentation> technicalSpecificationRepresentationList){
        return technicalSpecificationRepresentationList
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private TechnicalSpecification convert(TechnicalSpecificationRepresentation technicalSpecificationRepresentation) {
        TechnicalSpecification result = new TechnicalSpecification();
        result.setId(technicalSpecificationRepresentation.getId());
        result.setKey(technicalSpecificationRepresentation.getKey());
        result.setValue(technicalSpecificationRepresentation.getValue());
        return result;
    }

    private TechnicalSpecificationRepresentation convert(TechnicalSpecification technicalSpecification){
        TechnicalSpecificationRepresentation result = new TechnicalSpecificationRepresentation();
        result.setId(technicalSpecification.getId());
        result.setKey(technicalSpecification.getKey());
        result.setValue(technicalSpecification.getValue());
        return result;
    }

    public List<TechnicalSpecificationRepresentation> convert(List<TechnicalSpecification> technicalSpecifications) {
        return technicalSpecifications.stream().map(this::convert).collect(Collectors.toList());
    }
}
