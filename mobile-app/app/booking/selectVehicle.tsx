import SelectVehicleCard from "@/components/booking/SelectVehicleCard";
import AppHeader from "@/components/ui/AppHeader";
import Button from "@/components/ui/Button";
import { mockVehicles } from "@/data/vehicles";
import { LinearGradient } from "expo-linear-gradient";
import { router, useLocalSearchParams } from "expo-router";
import { useState } from "react";
import { ScrollView, View } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";

export default function SelectVehiclePage() {
    const { stationId } = useLocalSearchParams<{ stationId: string }>();

    const [selectedId, setSelectedId] = useState<string | null>(
        mockVehicles[0]?.id ?? null
    );

    const handleContinue = () => {
        if (!selectedId) return;
        // TODO: Navigate to next booking step (e.g. select charger or confirm)
        // router.push(`/booking/confirm?stationId=${stationId}&vehicleId=${selectedId}`);
        router.back();
    };

    return (
        <LinearGradient
            colors={["#33404F", "#000000"]}
            start={{ x: 0, y: 0 }}
            end={{ x: 0, y: 1 }}
            className="flex-1"
        >
            <SafeAreaView className="flex-1">
                <AppHeader title="Select Your Vehicle" />

                <ScrollView className="flex-1 px-6 mt-4">
                    {mockVehicles.map((vehicle) => (
                        <SelectVehicleCard
                            key={vehicle.id}
                            vehicle={vehicle}
                            isSelected={selectedId === vehicle.id}
                            onSelect={setSelectedId}
                        />
                    ))}
                    <View className="h-6" />
                </ScrollView>

                {/* Continue Button */}
                <View className="px-6 pb-8">
                    <Button
                        variant="primary"
                        size="lg"
                        fullWidth
                        disabled={!selectedId}
                        onPress={handleContinue}
                    >
                        Continue
                    </Button>
                </View>
            </SafeAreaView>
        </LinearGradient>
    );
}
