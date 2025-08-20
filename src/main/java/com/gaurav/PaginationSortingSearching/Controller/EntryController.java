package com.gaurav.PaginationSortingSearching.Controller;

import com.gaurav.PaginationSortingSearching.Entity.Entry;
import com.gaurav.PaginationSortingSearching.Entity.EntryDTO;
import com.gaurav.PaginationSortingSearching.Service.EntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagination")
public class EntryController {
    private final EntryService entryService;
    public EntryController (EntryService entryService) {
        this.entryService = entryService;
    }
    @PostMapping("/post")
    public ResponseEntity<?> postEntry(@RequestBody Entry newEntry) {
        EntryDTO entry = this.entryService.uploadEntry(newEntry);
        return ResponseEntity.ok(entry);
    }
    @GetMapping("/getEntry/entryId")
    public ResponseEntity<?> getEntry(@PathVariable(name = "entryId") Long entryId) {
        EntryDTO entryDTO = this.entryService.getIndividualEntry(entryId);
        return ResponseEntity.ok(entryDTO);
    }
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllEntry() {
        List<EntryDTO> entryDTOList = this.entryService.getAllMyEntries();
        return ResponseEntity.ok(entryDTOList);
    }
    //get all entries in pagination and sorting
    @GetMapping("/getIn-pages")
    public ResponseEntity<?> getAllInPagingAndSorting(
            @RequestParam(defaultValue = "3",name = "page-size") int pageSize,
            @RequestParam(defaultValue = "entryCreatedAt",name = "entry-created-at") String sortBy,
            @RequestParam(defaultValue = "asc",name = "sorting-direction") String direction,
            @RequestParam(defaultValue = "0",name = "page-no") int pageNo) {
        List<EntryDTO> entryDTOPage = this.entryService.getAllWithPaginationAndSorting(pageNo, pageSize, sortBy, direction);
        return ResponseEntity.ok(entryDTOPage);
    }
    @DeleteMapping("/delete/entryId")
    public ResponseEntity<?> deleteEntry(@PathVariable(name = "entryId") Long entryId) {
        this.entryService.deleteEntry(entryId);
        return (ResponseEntity<?>) ResponseEntity.noContent();
    }
    @PutMapping("/updateEntry/entryId")
    public ResponseEntity<?> updateEntry(
            @RequestBody Entry entry,
            @PathVariable(name = "entryId") Long entryId) {
        EntryDTO entryDTO = this.entryService.updatedEntry(entryId, entry);
        return ResponseEntity.ok(entryDTO);
    }
}
