**📌 Pagination & Sorting Implementation**

In this project, I have implemented pagination and sorting to efficiently handle large datasets instead of fetching everything at once.
This not only improves performance but also gives flexibility in how data is presented.

🔹 **What is Pagination?**
Pagination is the process of dividing a dataset into smaller parts (pages) instead of retrieving all records in a single request.
Example: If there are 1000 entries and the pageSize = 10, the data will be split into 100 pages.
Only the requested page is fetched from the database.

🔹 **What is Sorting?**
Sorting allows arranging the data in a particular order based on a chosen field (entry date creation or something comparision field).
Example: Sorting entries by name in ascending (asc) or descending (desc) order.

🔹**Attributes and Method to use**
public ResponseEntity<?> getAllInPagingAndSorting(
            @RequestParam(defaultValue = "3",name = "page-size") int pageSize,
            @RequestParam(defaultValue = "entryCreatedAt",name = "entry-created-at") String sortBy,
            @RequestParam(defaultValue = "asc",name = "sorting-direction") String direction,
            @RequestParam(defaultValue = "0",name = "page-no") int pageNo) {
        List<EntryDTO> entryDTOPage = this.entryService.getAllWithPaginationAndSorting(pageNo, pageSize, sortBy, direction);
        return ResponseEntity.ok(entryDTOPage);
    }
pageNo → which page to fetch (0-based index).
pageSize → number of records per page.
sortBy → the field by which results are sorted.
direction → asc (ascending) or desc (descending).

**Flow:**
Backend receives the request.
It calculates the offset (pageNo * pageSize).
Fetches only that slice of data from the DB.
Returns metadata like:
totalPages
totalElements
first, last page indicators
content (actual data for that page)

**Pagination and Sorting (Frontend + Backend)**
When the user clicks the Next or Previous button on the UI, the frontend triggers an API call to the backend with an updated page number. 
In the service layer, this page number is adjusted (incremented or decremented) based on the user’s action.
The backend then uses the new page number, along with the existing page size, sort field, and sorting direction, to fetch the corresponding slice of data.
Finally, this new set of entries is returned to the frontend, ensuring smooth navigation between pages.
