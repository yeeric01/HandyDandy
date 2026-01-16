-- AppUser table
CREATE TABLE app_user (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255),
    role VARCHAR(50)
);

-- Location table
CREATE TABLE location (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    city VARCHAR(255),
    state VARCHAR(255),
    zip VARCHAR(20)
);

-- Profile table
CREATE TABLE profile (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(50),
    user_id UUID NOT NULL UNIQUE,
    location_id UUID,
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES app_user(id),
    CONSTRAINT fk_location FOREIGN KEY(location_id) REFERENCES location(id)
);

-- ServiceRequest table
CREATE TABLE service_request (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id UUID NOT NULL,
    handyman_id UUID NOT NULL,
    location_id UUID NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_customer FOREIGN KEY(customer_id) REFERENCES profile(id),
    CONSTRAINT fk_handyman FOREIGN KEY(handyman_id) REFERENCES profile(id),
    CONSTRAINT fk_sr_location FOREIGN KEY(location_id) REFERENCES location(id)
);