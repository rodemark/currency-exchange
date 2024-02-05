INSERT INTO Currencies (Code, FullName, Sign) VALUES
                                                  ('USD', 'United States Dollar', '$'),
                                                  ('EUR', 'Euro', '€'),
                                                  ('GBP', 'British Pound Sterling', '£');


INSERT INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES
                                                                       (1, 2, 0.85), -- Курс обмена от USD к EUR
                                                                       (1, 3, 0.75); -- Курс обмена от USD к GBP