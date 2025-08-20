package com.gaurav.PaginationSortingSearching.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor
@Builder @Data
@Entity @Table(name = "entry_entity")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entryId;
    private String name;
    private String email;
    private String contact;
    private String entryDescription;
    private LocalDateTime entryCreatedAt;
}
