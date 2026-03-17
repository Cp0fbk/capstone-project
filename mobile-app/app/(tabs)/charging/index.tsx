import React from "react";
import { View, ScrollView } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";
import { Ionicons } from "@expo/vector-icons";

import GradientBackground from "@/components/ui/GradientBackground";
import AppHeader from "@/components/ui/AppHeader";
import Button from "@/components/ui/Button";
import ChargingIndicator from "@/components/charging/ChargingIndicator";
import StatsGrid from "@/components/charging/StatsGrid";
import { mockChargingSession } from "@/data/chargingData";

export default function ChargingPage() {
    const session = mockChargingSession;

    const handleStop = () => {
        console.log("Stop charging");
    };

    return (
        <GradientBackground preset="main">
            <SafeAreaView style={{ flex: 1 }} edges={["top", "bottom"]}>
                <AppHeader
                    title="Charging"
                    showBack={true}
                    onBack={() => console.log("Close")}
                    // Using a close icon as per image
                    right={
                        <View className="flex-row items-center gap-4">
                            <Ionicons name="ellipsis-horizontal-circle" size={28} color="white" />
                        </View>
                    }
                />

                <ScrollView
                    style={{ flex: 1 }}
                    showsVerticalScrollIndicator={false}
                    contentContainerStyle={{ paddingBottom: 100 }}
                >
                    {/* Central Indicator */}
                    <ChargingIndicator
                        kwh={session.kwh}
                        percentage={session.percentage}
                    />

                    {/* Stats Grid */}
                    <StatsGrid
                        time={session.time}
                        percentage={session.percentage}
                        current={session.current}
                        fees={session.fees}
                    />
                </ScrollView>

                {/* Bottom Action Button */}
                <View className="px-6 py-4 border-t border-white/5 bg-black/20">
                    <Button
                        variant="primary"
                        onPress={handleStop}
                        className="rounded-full h-16"
                        textClassName="text-lg font-bold"
                    >
                        Stop Charging
                    </Button>
                </View>
            </SafeAreaView>
        </GradientBackground>
    );
}
