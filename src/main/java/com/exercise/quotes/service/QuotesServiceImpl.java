package com.exercise.quotes.service;

import com.exercise.quotes.entity.ItemQuote;
import com.exercise.quotes.entity.Quote;
import com.exercise.quotes.dto.QuoteDTO;
import com.exercise.quotes.exception.QuoteException;
import com.exercise.quotes.repository.ItemQuoteRepository;
import com.exercise.quotes.repository.QuoteRepository;
import com.exercise.quotes.utils.QuoteUtilService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.exercise.quotes.utils.ErrorMessageQuotesConstant.*;
import static org.springframework.http.HttpStatus.*;

@Service
public class QuotesServiceImpl implements IQuotes {

   private QuoteRepository quoteRepository;
   private ItemQuoteRepository itemQuoteRepository;
   private QuoteUtilService quoteUtilService;

    @Autowired
    public QuotesServiceImpl(QuoteRepository quoteRepository, ItemQuoteRepository itemQuoteRepository, QuoteUtilService quoteUtilService) {
        this.quoteRepository = quoteRepository;
        this.itemQuoteRepository = itemQuoteRepository;
        this.quoteUtilService = quoteUtilService;
    }

    @Transactional
    @Override
    public QuoteDTO createQuote(QuoteDTO quoteDTO) {
        Quote quote = quoteUtilService.convertToQuote(quoteDTO);
        existingByQuote(quote.getId());
        quoteRepository.save(quote);

        return quoteUtilService.convertToQuoteDTO(quote);
    }

    private void existingByQuote(Long id) {
        if (quoteRepository.existsById(id)) {
            throw new QuoteException(ALREADY_EXIST_QUOTE, BAD_REQUEST.value());
        }
    }

    @Transactional
    @Override
    public void deleteQuote(long id) {
        Quote quote = ifNotExistQuoteThrow(id);
        quoteRepository.deleteById(id);
        quote.getItems().forEach(i -> itemQuoteRepository.deleteById(i.getId()));


    }

    @Transactional(readOnly = true)
    @Override
    public List<QuoteDTO> getAllQuotes() {
        List<Quote> quotes = quoteRepository.findAllByRemovedIsFalse()
                .map(this::findNoRemoved).collect(Collectors.toList());


        return quoteUtilService.convertToListQuoteDTO(quotes);
    }

    private Quote findNoRemoved(Quote quote) {
        Set<ItemQuote> items = quote.getItems().stream().filter(i -> !i.isRemoved()).collect(Collectors.toSet());
        return Quote.builder()
                .items(items)
                .id(quote.getId())
                .name(quote.getName())
                .price(quote.getPrice())
                .removed(quote.isRemoved())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public QuoteDTO getQuote(String name) {
        return quoteUtilService.convertToQuoteDTO(ifNotExistQuoteThrow(name));

    }

    @Transactional
    @Override
    public QuoteDTO updateQuote(long id, QuoteDTO quoteDTO) {
        Quote quote = ifNotExistQuoteThrow(id);
        quote.setPrice(quoteDTO.getPrice());
        quote.setName(quoteDTO.getName());
        return quoteUtilService.convertToQuoteDTO(quote);
    }


    private Quote ifNotExistQuoteThrow(long id) {
        return quoteRepository.findById(id)
                .orElseThrow(() -> new QuoteException(NOT_FOUND_QUOTE, BAD_REQUEST.value()));
    }

    private Quote ifNotExistQuoteThrow(String name) {
        return quoteRepository.findByName(name)
                .orElseThrow(() -> new QuoteException(NOT_FOUND_QUOTE, BAD_REQUEST.value()));
    }

}
