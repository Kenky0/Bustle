package com.bustle.bustlebackend.service;

import com.bustle.hackto2022.constants.BustleConstants;
import com.bustle.hackto2022.model.Suggestion;
import com.bustle.hackto2022.repository.SuggestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SuggestionsService {

    private SuggestionRepository suggestionRepository;

    public boolean saveMessageDetails(Suggestion suggestion){
        boolean isSaved = false;
        suggestion.setStatus(BustleConstants.OPEN);
        Suggestion savedSuggestion = suggestionRepository.save(suggestion);
        if(null != savedSuggestion && savedSuggestion.getSuggestionId()>0) {
            isSaved = true;
        }
        return isSaved;
    }

    public List<Suggestion> findMsgsWithOpenStatus(){
        List<Suggestion> suggestionMsgs = suggestionRepository.findByStatus(BustleConstants.OPEN);
        return suggestionMsgs;
    }

    public boolean updateMsgStatus(int suggestionId){
        boolean isUpdated = false;
        Optional<Suggestion> suggestion = suggestionRepository.findById(suggestionId);
        suggestion.ifPresent(suggestion1 -> {
            suggestion1.setStatus(BustleConstants.CLOSE);
        });
        Suggestion updatedSuggestion = suggestionRepository.save(suggestion.get());
        if(null != updatedSuggestion && updatedSuggestion.getUpdatedBy()!=null) {
            isUpdated = true;
        }
        return isUpdated;
    }

}
