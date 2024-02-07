INSERT INTO Currencies (Code, full_name, Sign) VALUES
                                                  ('USD', 'United States Dollar', '$'),
                                                  ('EUR', 'Euro', '€'),
                                                  ('GBP', 'British Pound Sterling', '£');


INSERT INTO exchange_rates (base_currency_id, target_currency_id, Rate) VALUES
                                                                       (1, 2, 1.08), -- Курс обмена от USD к EUR
                                                                       (1, 3, 1.26); -- Курс обмена от USD к GBP