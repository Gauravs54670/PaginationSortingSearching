package com.gaurav.PaginationSortingSearching.Service;

import com.gaurav.PaginationSortingSearching.Entity.Entry;
import com.gaurav.PaginationSortingSearching.Entity.EntryDTO;
import com.gaurav.PaginationSortingSearching.Repository.EntryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntryService {
    private final EntryRepository entryRepository;
    public EntryService (EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }
    public EntryDTO uploadEntry(Entry entry) {
        boolean exist = this.entryRepository.findByEmail(entry.getEmail()).isPresent();
        if(exist) throw new RuntimeException("Credentials are already existed");
        exist = this.entryRepository.findByContact(entry.getContact()).isPresent();
        if(exist) throw new RuntimeException("Credentials are already existed");
        Entry entryEntity = Entry.builder()
                .name(entry.getName())
                .email(entry.getEmail())
                .contact(entry.getContact())
                .entryDescription(entry.getEntryDescription())
                .entryCreatedAt(LocalDateTime.now())
                .build();
        entryEntity = this.entryRepository.save(entryEntity);
        return convertToDTO(entryEntity);
    }
    public EntryDTO getIndividualEntry(Long entryId) {
       Entry entry = this.entryRepository.findById(entryId)
               .orElseThrow(() -> new RuntimeException("Entry not found"));
       return convertToDTO(entry);
    }
    public List<EntryDTO> getAllMyEntries() {
        return this.entryRepository.findAll()
                .stream()
                .map(this :: convertToDTO)
                .toList();
    }
    public List<EntryDTO> getAllWithPaginationAndSorting(int pageNo, int pageSize, String sortBy, String sortingDirection) {
        Sort sort = sortingDirection.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return this.entryRepository.findAll(pageable)
                .stream()
                .map(this :: convertToDTO)
                .collect(Collectors.toList());
    }
    public void deleteEntry(Long entryId) {
        Entry entry = this.entryRepository.findById(entryId)
                .orElseThrow(() -> new RuntimeException("Entry not found"));
        this.entryRepository.delete(entry);
    }
    public EntryDTO updatedEntry(Long entryId, Entry entryDTO) {
        Entry entry = this.entryRepository.findById(entryId)
                .orElseThrow(() -> new RuntimeException("Entry not found"));
        if(entryDTO.getName() != null && !entryDTO.getName().isEmpty())
            entry.setName(entryDTO.getName());
        if(entryDTO.getEmail() != null && !entryDTO.getEmail().isEmpty())
            entry.setEmail(entryDTO.getEmail());
        if(entryDTO.getContact() != null && !entryDTO.getContact().isEmpty())
            entry.setContact(entryDTO.getContact());
        if(entryDTO.getEntryDescription() != null && !entryDTO.getEntryDescription().isEmpty())
            entry.setEntryDescription(entryDTO.getEntryDescription());
        if(entryDTO.getEntryCreatedAt() != null)
            entry.setEntryCreatedAt(entryDTO.getEntryCreatedAt());
        entry = this.entryRepository.save(entry);
        return convertToDTO(entry);
    }
    //Helper method
    private EntryDTO convertToDTO(Entry entry) {
        return EntryDTO.builder()
                .name(entry.getName())
                .email(entry.getEmail())
                .contact(entry.getContact())
                .entryDescription(entry.getEntryDescription())
                .entryCreatedAt(entry.getEntryCreatedAt())
                .build();
    }
}
