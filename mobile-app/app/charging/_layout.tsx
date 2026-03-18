import { Stack } from "expo-router";

export default function ChargingLayout() {
    return (
        <Stack>
            <Stack.Screen name="charging" options={{ headerShown: false }} />

        </Stack>
    );
}
