CREATE TABLE IF NOT EXISTS system (
    id SERIAL PRIMARY KEY,
    named VARCHAR(100) NOT NULL,
    stated BOOLEAN NOT NULL,
    intensity INTEGER NOT NULL,
    ontime TIME NOT NULL,
    offtime TIME NOT NULL,
    last_sensor_value DOUBLE PRECISION,
    last_sensor_record TIMESTAMP
    );
