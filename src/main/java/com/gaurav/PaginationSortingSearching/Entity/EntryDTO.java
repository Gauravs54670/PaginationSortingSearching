package com.gaurav.PaginationSortingSearching.Entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder @Data
public class EntryDTO {
    private String name;
    private String email;
    private String contact;
    private String entryDescription;
    private LocalDateTime entryCreatedAt;
}
