

CREATE SEQUENCE  user_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE Users (

                       user_id INT DEFAULT NEXTVAL('user_id_seq') PRIMARY KEY,
                       firstName VARCHAR (20) NOT NULL,
                       lastName VARCHAR (20) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       phone numeric(10) NOT NULL UNIQUE,
                       is_tenant BOOLEAN DEFAULT FALSE

);
ALTER TABLE Users
    ADD CONSTRAINT password_length_check
        CHECK (LENGTH(password) >=4);

CREATE SEQUENCE  field_id_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE Fields (
                        field_id INT DEFAULT NEXTVAL('field_id_seq') PRIMARY KEY,
                        field_name VARCHAR(100) NOT NULL UNIQUE,
                        size INT NOT NULL CHECK (size > 0),
    status VARCHAR(20) CHECK (status IN ('boş', 'kiralanmış')) NOT NULL,
    price_per_day DECIMAL(10, 2) NOT NULL,
    rented_by INT,
    FOREIGN KEY (rented_by) REFERENCES Users(user_id) ON DELETE SET NULL
);


CREATE SEQUENCE  reservation_id_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE Reservations (
                              reservation_id INT DEFAULT NEXTVAL('reservation_id_seq') PRIMARY KEY,
                              user_id INT NOT NULL,
                              field_id INT NOT NULL,
                              start_date DATE NOT NULL,
                              end_date DATE,
                              status VARCHAR(20) CHECK (status IN ('aktif', 'tamamlandı')) DEFAULT 'aktif',
                              FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
                              FOREIGN KEY (field_id) REFERENCES Fields(field_id) ON DELETE CASCADE

);


CREATE SEQUENCE  request_id_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE EquipmentRequests (
                                   request_id INT DEFAULT NEXTVAL('request_id_seq') PRIMARY KEY,
                                   request varchar(100) NOT NULL,
                                   requested_by INT NOT NULL,
                                   FOREIGN KEY (requested_by) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE SEQUENCE  admin_id_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE Admins (

                        admin_id INT DEFAULT NEXTVAL('admin_id_seq') PRIMARY KEY,
                        username VARCHAR (20) NOT NULL,
                        password VARCHAR(255) NOT NULL


);


CREATE OR REPLACE FUNCTION check_tenant_before_request()
RETURNS TRIGGER AS $$
BEGIN

    IF NOT EXISTS (
        SELECT 1
        FROM Users
        WHERE user_id = NEW.requested_by AND is_tenant = TRUE
    ) THEN
        RAISE EXCEPTION 'Sadece kiracılar ekipman talebi yapabilir!';
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER before_equipment_request_insert
    BEFORE INSERT ON EquipmentRequests
    FOR EACH ROW
    EXECUTE FUNCTION check_tenant_before_request();


CREATE VIEW AvailableFields AS
SELECT
    field_id,
    field_name,
        size,
        price_per_day
        FROM Fields
        WHERE status = 'boş';





CREATE OR REPLACE FUNCTION update_user_tenant_status()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        IF NEW.status = 'aktif' THEN
UPDATE Users
SET is_tenant = TRUE
WHERE user_id = NEW.user_id;
END IF;

    ELSIF TG_OP = 'DELETE' THEN
        IF NOT EXISTS (
            SELECT 1
            FROM Reservations
            WHERE user_id = OLD.user_id
              AND status = 'aktif'
        ) THEN
UPDATE Users
SET is_tenant = FALSE
WHERE user_id = OLD.user_id;
END IF;

    ELSIF TG_OP = 'UPDATE' THEN

        IF NEW.status IN ('tamamlandı') THEN

            IF NOT EXISTS (
                SELECT 1
                FROM Reservations
                WHERE user_id = NEW.user_id
                  AND status = 'aktif'
            ) THEN
UPDATE Users
SET is_tenant = FALSE
WHERE user_id = NEW.user_id;
END IF;
        ELSIF NEW.status = 'aktif' THEN

UPDATE Users
SET is_tenant = TRUE
WHERE user_id = NEW.user_id;
END IF;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER after_reservation_user
    AFTER INSERT OR DELETE OR UPDATE ON Reservations
FOR EACH ROW
EXECUTE FUNCTION update_user_tenant_status();

CREATE OR REPLACE FUNCTION update_field_status()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        IF NEW.status='aktif' THEN
UPDATE Fields
SET status = 'kiralanmış', rented_by = NEW.user_id
WHERE field_id = NEW.field_id;
END IF;

    ELSIF TG_OP = 'DELETE' THEN
     IF OLD.status = 'aktif' THEN
UPDATE Fields
SET status = 'boş', rented_by = NULL
WHERE field_id = OLD.field_id;
END IF;


ELSIF TG_OP = 'UPDATE' THEN
        IF NEW.status IN ('tamamlandı') THEN
UPDATE Fields
SET status = 'boş', rented_by = NULL
WHERE field_id = NEW.field_id;
END IF;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_reservation_fields
    AFTER INSERT OR DELETE OR UPDATE ON Reservations
FOR EACH ROW
EXECUTE FUNCTION update_field_status();


SELECT user_id FROM Users
EXCEPT
SELECT u.user_id FROM Reservations r
                          JOIN Users u ON r.user_id = u.user_id;


SELECT
    u.user_id,
    CONCAT(u.firstName, ' ', u.lastName) AS username,
    COUNT(r.reservation_id) AS total_reservations
FROM Users u
         LEFT JOIN Reservations r ON u.user_id = r.user_id
GROUP BY u.user_id, u.firstName, u.lastName
HAVING COUNT(r.reservation_id) > 2;


CREATE OR REPLACE FUNCTION get_user_fields(input_user_id INT)
RETURNS TABLE(field_id INT, field_name VARCHAR, size INT, start_date DATE, price_per_day NUMERIC) AS $$
DECLARE
user_fields_cursor REFCURSOR;
BEGIN

OPEN user_fields_cursor FOR
SELECT
    r.field_id, f.field_name, f.size, r.start_date, f.price_per_day
FROM
    Reservations r
        JOIN
    Fields f ON r.field_id = f.field_id
WHERE
    r.user_id = input_user_id;


RETURN QUERY FETCH ALL FROM user_fields_cursor;


CLOSE user_fields_cursor;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_user_reservations(input_user_id INT)
RETURNS TABLE(
    reservation_id INT,
    user_id INT,
    field_id INT,
    start_date DATE,
    end_date DATE,
    status VARCHAR
) AS $$
DECLARE
reservations_cursor REFCURSOR;
BEGIN

OPEN reservations_cursor FOR
SELECT
    r.reservation_id,
    r.user_id,
    r.field_id,
    r.start_date,
    r.end_date,
    r.status
FROM
    Reservations r
WHERE
    r.user_id = input_user_id;


LOOP
FETCH reservations_cursor INTO reservation_id, user_id, field_id, start_date, end_date, status;


        EXIT WHEN NOT FOUND;

        RETURN NEXT;
END LOOP;


CLOSE reservations_cursor;
END;
$$ LANGUAGE plpgsql;










INSERT INTO Users (firstName, lastName, password, email, phone) VALUES ('Can', 'Güneş', 'pass9', 'can@mail.com', 5559012345),
                                                                    ('Ahmet', 'Yılmaz', 'pass1', 'ahmet@mail.com', 5551234567),
                                                                    ('Ayşe', 'Demir', 'pass2', 'ayse@mail.com', 5552345678),
                                                                    ('Mehmet', 'Kaya', 'pass3', 'mehmet@mail.com', 5553456789),
                                                                    ('Fatma', 'Çelik', 'pass4', 'fatma@mail.com', 5554567890),
                                                                    ('Ali', 'Koç', 'pass5', 'ali@mail.com', 5555678901),
                                                                    ('Zeynep', 'Öztürk', 'pass6', 'zeynep@mail.com', 5556789012),
                                                                    ('Mert', 'Aydın', 'pass7', 'mert@mail.com', 5557890123),
                                                                    ('Elif', 'Şahin', 'pass8', 'elif@mail.com', 5558901234),
                                                                    ('Can', 'Güneş', 'pass9', 'can@mail.com', 5559012345),
                                                                    ('Deniz', 'Yıldırım', 'pass10', 'deniz@mail.com', 5550123456);

INSERT INTO Fields (field_name, size, status, price_per_day) VALUES
                                                                 ('Bahçe-1', 150, 'boş', 500.00),
                                                                 ('Bahçe-2', 200, 'boş', 750.00),
                                                                 ('Tarla-1', 300, 'boş', 1000.00),
                                                                 ('Tarla-2', 400, 'boş', 1200.00),
                                                                 ('Sera-1', 250, 'boş', 800.00),
                                                                 ('Sera-2', 350, 'boş', 900.00),
                                                                 ('Bahçe-3', 220, 'boş', 800.00),
                                                                 ('Tarla-3', 500, 'boş', 1500.00),
                                                                 ('Bahçe-4', 180, 'boş', 600.00),
                                                                 ('Sera-3', 280, 'boş', 850.00);

INSERT INTO Reservations (user_id, field_id, start_date, end_date, status) VALUES
                                                                               (1, 1, '2024-01-01', '2024-02-01', 'tamamlandı'),
                                                                               (2, 2, '2024-02-01', '2025-02-15', 'tamamlandı'),
                                                                               (3, 3, '2024-03-01', '2024-09-20', 'tamamlandı'),
                                                                               (4, 4, '2024-04-01', '2024-05-24', 'tamamlandı'),
                                                                               (5, 4, '2024-06-01', '2024-06-15', 'tamamlandı'),
                                                                               (6, 5, '2025-06-01', '2025-06-10', 'tamamlandı'),
                                                                               (7, 2, '2024-07-01', '2024-12-31', 'tamamlandı'),
                                                                               (8, 8, '2024-08-01', '2024-08-10', 'tamamlandı'),
                                                                               (9, 9, '2024-09-01', '2024-12-12', 'tamamlandı'),
                                                                               (10, 10,'2024-10-01','2024-10-20', 'tamamlandı'),
                                                                               (7,5,   '2025-01-01',null,'aktif'),
                                                                               (7,1,   '2024-01-01',null,'aktif');


INSERT INTO Admins (username,password) VALUES
                                           ('admin1', 'pass1'),
                                           ('admin2', 'pass2'),
                                           ('admin3', 'pass3'),
                                           ('admin4', 'pass4'),
                                           ('admin5', 'pass5'),
                                           ('admin6', 'pass6'),
                                           ('admin7', 'pass7'),
                                           ('admin8', 'pass8'),
                                           ('admin9', 'pass9'),
                                           ('admin10', 'pass10');

--- sinem fonksiyonlar
-- stat_type adlı özel bir tür tanımlıyoruz
CREATE TYPE stat_type AS (
    full_name VARCHAR,
    total INT
    );

-- İstatistik Rent fonksiyonunun güncellenmiş hali
CREATE OR REPLACE FUNCTION istatistik_rent(n integer)
RETURNS SETOF stat_type AS $$  -- Burada SETOF stat_type kullanıyoruz
DECLARE
result_record stat_type;  -- stat_type türünde bir değişken tanımlıyoruz
BEGIN
FOR result_record IN
SELECT u.firstName || ' ' || u.lastName AS full_name,
       COUNT(*) AS total
FROM users u
         JOIN reservations r ON u.user_id = r.user_id
GROUP BY u.firstName, u.lastName
HAVING COUNT(*) > n
    LOOP
        -- Her satırda sonucu döndürüyoruz
        RETURN NEXT result_record;
END LOOP;

    RETURN;  -- Fonksiyon sonunda dönüş
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION istatistik_talep(n integer)
RETURNS SETOF stat_type AS $$  -- Burada SETOF stat_type kullanıyoruz
DECLARE
result_record stat_type;  -- stat_type türünde bir değişken tanımlıyoruz
BEGIN
FOR result_record IN
SELECT u.firstName || ' ' || u.lastName AS full_name,
       COUNT(*) AS total
FROM users u
         JOIN equipmentrequests r ON u.user_id = r.requested_by
GROUP BY u.firstName, u.lastName
HAVING COUNT(*) > n
    LOOP
        -- Her satırda sonucu döndürüyoruz
        RETURN NEXT result_record;
END LOOP;

    RETURN;  -- Fonksiyon sonunda dönüş
END;
$$ LANGUAGE plpgsql;












