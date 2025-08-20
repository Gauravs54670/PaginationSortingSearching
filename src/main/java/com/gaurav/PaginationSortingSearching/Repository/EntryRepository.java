package com.gaurav.PaginationSortingSearching.Repository;

import com.gaurav.PaginationSortingSearching.Entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntryRepository extends JpaRepository<Entry, Long> {
    public Optional<Entry> findByEmail(String email);
    public Optional<Entry> findByContact(String contact);
}
