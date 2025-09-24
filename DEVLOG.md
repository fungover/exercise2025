### 📕This is my Development log

where I share my thought processes during this project, to enhance my own learning.

What to do:

1. Plan packages, class- and file structure (Separation of concerns)
2. Add dependencies in pom.xml file (jackson databind is needed to parse JSON (includes annotations & core)

### What classes will I need?

[x] Main (My CLI entry who Delegates, calls clients, counts and is responsible for bestWindow atm)
[x] PriceData (My Record with time data. Mirrors the data from the API and is easy to work with )
[x] ElPriceCli (Help class with no own main-method, is used by other classes)
[x] Stats (Statistics, numbers and count-logics. No displays at all & Easy to unit test)
[x] Printer (This Help-class provides user with all the System.out.println:s)
[x] SlidingWindow (When is it the most cost-effective to start the Sauna?? This window will provide us with the answer)
[] Zone (Enumeration SE1, SE2, SE3, SE4- This class validates the Zone & throws an early exception if not valid)
[] CsvConsumption (⭐ Shows the exact cost for user based on real electricity usage. Real user value stuff!)

### 🧠❓

In this project I am working with
**Dates, Times, Sorting and JSON Data**

#### _Functionality_:

* Read JSON
* Convert JSON
* Handle Times correctly (UTC-EU)
* Save prices in a List
* Sort the List to know min/max
* Display prices in Swedish

### API structure

When GET https://www.elprisetjustnu.se/api/v1/prices/2025/08-25_SE3.json
API returns: An array with price entries:
"SEK_per_kWh": 0.000,
"EUR_per_kWh": 0.000,
"EXR": 00.00,
"time_start": "YYYY-MM-DDT00:00:00+00:00",
"time_end": "YYYY-MM-DDT00:00:00+00:00".

### Mind-blowing things I learned

* It´s possible to convert a record to a class in IntelliJ





