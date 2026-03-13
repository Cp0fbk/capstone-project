-- =========================================================================
-- Add initial price settings for existing stations
-- =========================================================================

-- Add default price settings for all existing stations that don't have one yet
INSERT INTO price_settings (
    station_id,
    version,
    charging_rate_per_kwh,
    booking_fee,
    idle_penalty_per_minute,
    grace_period_minutes,
    is_active,
    notes,
    effective_from,
    created_at
)
SELECT 
    id,
    1,
    3150.00,    -- 3,150 VND per kWh (standard EV charging rate)
    20000.00,   -- 20,000 VND booking fee
    1000.00,    -- 1,000 VND penalty per minute of overstay
    30,         -- 30 minutes grace period
    true,
    'Default initial pricing configuration',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM stations
WHERE NOT EXISTS (
    SELECT 1 FROM price_settings ps WHERE ps.station_id = stations.id
);
