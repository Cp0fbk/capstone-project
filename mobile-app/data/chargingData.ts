export interface ActiveChargingSession {
    kwh: number;
    time: string;
    percentage: number;
    current: number; // Amp
    fees: number;    // $
}

export const mockChargingSession: ActiveChargingSession = {
    kwh: 30,
    time: "00:14:55",
    percentage: 30,
    current: 12.24,
    fees: 4.27,
};
