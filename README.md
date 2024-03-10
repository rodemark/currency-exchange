# Currency Exchange.

### Opportunities

GET: "/currencies" - Get the list of all currencies. \
POST: "/currencies?name=...&code=...&sign=..." - Add a new currency.

GET: "/currency/_code_" - Get a specific currency.

GET: "/exchangeRate/_codecode_" - Get a specific exchange rate. \
POST: "/exchangeRate/_codecode_?rate=..." - Update an existing exchange rate in the database.

GET: "/exchangeRates" - Get all exchange rates.\
POST: "/exchangeRates?baseCode=...&targetCode=...&rate=..." - Add a new exchange rate to the database.

GET: "/exchange?from=...&to=...&amount=..." - Calculate the conversion of a specific amount of funds from one currency to another.