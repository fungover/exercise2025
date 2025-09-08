## API & Contracts

- **Public class:** `service/Warehouse` (main API).
- **Entities:**
    - `entities/Product` 
    - `entities/Category`
- **Validation rules:**
    - `id` must be unique and non-blank
    - `name` must be non-blank
    - `category` must not be null
    - `rating` must be between 0–10 inclusive
- **Dates:**
    - `createdDate` set when product is created
    - `modifiedDate` updated on changes
    - `Warehouse` uses injected `Clock` for deterministic tests
- **Errors:**
    - `IllegalArgumentException` for invalid values
    - `NoSuchElementException` when ID is missing
