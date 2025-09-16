package com.jan_elia.warehouse.service;

// Test plan for Warehouse
//
// addProduct
// TODO: success when adding a valid product
// TODO: failure when product name is empty
// TODO: failure when product id already exists
// TODO: failure when product is null
// TODO: failure when category is null
// TODO: failure when rating < 0 or > 10
//
// updateProduct
// TODO: success: fields updated; createdDate unchanged; modifiedDate = now(clock)
// TODO: failure: id not found -> NoSuchElementException
// TODO: failure: name blank -> IllegalArgumentException
// TODO: failure: category null -> IllegalArgumentException
// TODO: failure: rating out of range -> IllegalArgumentException
// TODO: failure: id null/blank -> IllegalArgumentException
//
// getAllProducts
// TODO: success when all products are returned
// TODO: failure when trying to modify the returned list
//
// getProductById
// TODO: success when product id exists
// TODO: failure when id is null or blank
// TODO: failure when product id does not exist
//
// getProductsByCategorySorted
// TODO: success: returns only this category
// TODO: success: sorted A–Z (case-insensitive)
// TODO: success: empty list when none
// TODO: failure: category is null -> IllegalArgumentException
//
// getProductsCreatedAfter
// TODO: success: returns only products strictly after date
// TODO: success: product on the same date is excluded
// TODO: failure: date is null -> IllegalArgumentException
//
// getModifiedProducts
// TODO: success: returns only products where modifiedDate != createdDate
// TODO: success: returns empty list when none are modified
