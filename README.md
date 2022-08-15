# rx-hotels

## General description

There is a bunch of databases that contain information about hotels, the application should query them, then fetch properties for obtained hotels and prepare them for displaying to a user.

## Task statement

1. Consolidate hotels from `ExpediaDataSource` and `TripadvisorDataSource` into a single list (both queries to databases should be asynchronous)
2. If either query fails, you should log the error and return data from `LegacyDataSource` instead
3. Filter out non-available hotels
4. For each hotel load properties asynchronously
5. If some hotel fails to return properties, then the error should be logged and empty list should be returned only for failed hotel
6. All loaded properties should be aggregated into a single list and sorted by rating in the descendant order
7. Display obtained and sorted properties to a user
8. Log an error message in case some unexpected error happens
9. Database queries should be performed in IO thread pool
10. Sorting properties ought to be done in the computation thread-pool (as it potentially could be a heavy operation in case of a huge collection of properties)
11. Also, user should be able to cancel all running operations
12. K O T L I N