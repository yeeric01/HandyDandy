-- Service Category table
CREATE TABLE service_category (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    icon_url VARCHAR(500),
    active BOOLEAN NOT NULL DEFAULT true
);

-- Handyman Service table (links handyman to categories)
CREATE TABLE handyman_service (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    handyman_id UUID NOT NULL,
    category_id UUID NOT NULL,
    hourly_rate DECIMAL(10, 2) NOT NULL,
    years_experience INTEGER,
    certifications TEXT,
    active BOOLEAN NOT NULL DEFAULT true,
    CONSTRAINT fk_hs_handyman FOREIGN KEY(handyman_id) REFERENCES profile(id) ON DELETE CASCADE,
    CONSTRAINT fk_hs_category FOREIGN KEY(category_id) REFERENCES service_category(id) ON DELETE CASCADE,
    CONSTRAINT uq_handyman_category UNIQUE(handyman_id, category_id)
);

-- Quote table
CREATE TABLE quote (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    handyman_id UUID NOT NULL,
    customer_id UUID NOT NULL,
    service_request_id UUID,
    category_id UUID NOT NULL,
    labor_cost DECIMAL(10, 2) NOT NULL,
    materials_cost DECIMAL(10, 2),
    total_cost DECIMAL(10, 2) NOT NULL,
    estimated_hours INTEGER,
    description VARCHAR(1000),
    terms_and_conditions VARCHAR(500),
    valid_until TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    responded_at TIMESTAMP,
    CONSTRAINT fk_quote_handyman FOREIGN KEY(handyman_id) REFERENCES profile(id) ON DELETE CASCADE,
    CONSTRAINT fk_quote_customer FOREIGN KEY(customer_id) REFERENCES profile(id) ON DELETE CASCADE,
    CONSTRAINT fk_quote_sr FOREIGN KEY(service_request_id) REFERENCES service_request(id) ON DELETE SET NULL,
    CONSTRAINT fk_quote_category FOREIGN KEY(category_id) REFERENCES service_category(id) ON DELETE CASCADE
);

-- Booking table
CREATE TABLE booking (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id UUID NOT NULL,
    handyman_id UUID NOT NULL,
    category_id UUID NOT NULL,
    location_id UUID NOT NULL,
    quote_id UUID,
    scheduled_start TIMESTAMP NOT NULL,
    scheduled_end TIMESTAMP NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(20) NOT NULL,
    estimated_cost DECIMAL(10, 2),
    final_cost DECIMAL(10, 2),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    completed_at TIMESTAMP,
    CONSTRAINT fk_booking_customer FOREIGN KEY(customer_id) REFERENCES profile(id) ON DELETE CASCADE,
    CONSTRAINT fk_booking_handyman FOREIGN KEY(handyman_id) REFERENCES profile(id) ON DELETE CASCADE,
    CONSTRAINT fk_booking_category FOREIGN KEY(category_id) REFERENCES service_category(id) ON DELETE CASCADE,
    CONSTRAINT fk_booking_location FOREIGN KEY(location_id) REFERENCES location(id) ON DELETE CASCADE,
    CONSTRAINT fk_booking_quote FOREIGN KEY(quote_id) REFERENCES quote(id) ON DELETE SET NULL
);

-- Payment table
CREATE TABLE payment (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    booking_id UUID NOT NULL,
    payer_id UUID NOT NULL,
    payee_id UUID NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    transaction_id VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    processed_at TIMESTAMP,
    CONSTRAINT fk_payment_booking FOREIGN KEY(booking_id) REFERENCES booking(id) ON DELETE CASCADE,
    CONSTRAINT fk_payment_payer FOREIGN KEY(payer_id) REFERENCES profile(id) ON DELETE CASCADE,
    CONSTRAINT fk_payment_payee FOREIGN KEY(payee_id) REFERENCES profile(id) ON DELETE CASCADE
);

-- Review table
CREATE TABLE review (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    reviewer_id UUID NOT NULL,
    handyman_id UUID NOT NULL,
    booking_id UUID NOT NULL UNIQUE,
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment VARCHAR(1000),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_review_reviewer FOREIGN KEY(reviewer_id) REFERENCES profile(id) ON DELETE CASCADE,
    CONSTRAINT fk_review_handyman FOREIGN KEY(handyman_id) REFERENCES profile(id) ON DELETE CASCADE,
    CONSTRAINT fk_review_booking FOREIGN KEY(booking_id) REFERENCES booking(id) ON DELETE CASCADE
);

-- Message table
CREATE TABLE message (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sender_id UUID NOT NULL,
    receiver_id UUID NOT NULL,
    booking_id UUID,
    content VARCHAR(2000) NOT NULL,
    sent_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT false,
    CONSTRAINT fk_message_sender FOREIGN KEY(sender_id) REFERENCES profile(id) ON DELETE CASCADE,
    CONSTRAINT fk_message_receiver FOREIGN KEY(receiver_id) REFERENCES profile(id) ON DELETE CASCADE,
    CONSTRAINT fk_message_booking FOREIGN KEY(booking_id) REFERENCES booking(id) ON DELETE SET NULL
);

-- Availability table
CREATE TABLE availability (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    handyman_id UUID NOT NULL,
    day_of_week VARCHAR(10) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    available BOOLEAN NOT NULL DEFAULT true,
    CONSTRAINT fk_availability_handyman FOREIGN KEY(handyman_id) REFERENCES profile(id) ON DELETE CASCADE,
    CONSTRAINT uq_handyman_day UNIQUE(handyman_id, day_of_week)
);

-- Add indexes for common queries
CREATE INDEX idx_handyman_service_handyman ON handyman_service(handyman_id);
CREATE INDEX idx_handyman_service_category ON handyman_service(category_id);
CREATE INDEX idx_booking_customer ON booking(customer_id);
CREATE INDEX idx_booking_handyman ON booking(handyman_id);
CREATE INDEX idx_booking_status ON booking(status);
CREATE INDEX idx_booking_scheduled ON booking(scheduled_start, scheduled_end);
CREATE INDEX idx_payment_booking ON payment(booking_id);
CREATE INDEX idx_payment_status ON payment(status);
CREATE INDEX idx_review_handyman ON review(handyman_id);
CREATE INDEX idx_review_rating ON review(rating);
CREATE INDEX idx_message_sender ON message(sender_id);
CREATE INDEX idx_message_receiver ON message(receiver_id);
CREATE INDEX idx_message_booking ON message(booking_id);
CREATE INDEX idx_quote_handyman ON quote(handyman_id);
CREATE INDEX idx_quote_customer ON quote(customer_id);
CREATE INDEX idx_quote_status ON quote(status);
CREATE INDEX idx_availability_handyman ON availability(handyman_id);

-- Seed service categories
INSERT INTO service_category (name, description) VALUES
    ('Plumbing', 'Pipe repairs, fixture installation, drain cleaning, water heater services'),
    ('Electrical', 'Wiring, outlet installation, lighting, panel upgrades'),
    ('Carpentry', 'Wood repairs, furniture assembly, custom builds, deck construction'),
    ('Painting', 'Interior and exterior painting, staining, wallpaper'),
    ('HVAC', 'Heating and cooling system repairs, installation, maintenance'),
    ('Landscaping', 'Lawn care, garden maintenance, tree trimming, irrigation'),
    ('Cleaning', 'Deep cleaning, move-out cleaning, pressure washing'),
    ('Appliance Repair', 'Washer, dryer, refrigerator, dishwasher repairs'),
    ('Roofing', 'Roof repairs, inspections, gutter cleaning'),
    ('General Maintenance', 'Minor repairs, handyman tasks, home improvements');
