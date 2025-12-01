-- Sample data for testing PayLense API functionality

-- Users
INSERT INTO users (id, name, phone_number, email) VALUES
(1, 'Chinedu Okafor', '+2348012345678', 'chinedu.okafor@example.com'),
(2, 'Amaka Nwosu', '+2348098765432', 'amaka.nwosu@example.com'),
(3, 'Tunde Balogun', '+2348023456789', 'tunde.balogun@example.com');

-- Wallets
INSERT INTO wallets (id, user_id, balance, currency) VALUES
(1, 1, 50000.00, 'NGN'),
(2, 2, 75000.00, 'NGN'),
(3, 3, 30000.00, 'NGN');

-- Transactions
INSERT INTO transactions (id, wallet_id, type, amount, description, transaction_date) VALUES
(1, 1, 'credit', 20000.00, 'Initial deposit', '2024-01-01 10:00:00'),
(2, 1, 'debit', 5000.00, 'Payment for groceries', '2024-01-02 15:30:00'),
(3, 2, 'credit', 75000.00, 'Initial deposit', '2024-01-01 11:00:00'),
(4, 3, 'credit', 30000.00, 'Initial deposit', '2024-01-01 12:00:00'),
(5, 3, 'debit', 10000.00, 'Electricity bill payment', '2024-01-03 09:00:00');
