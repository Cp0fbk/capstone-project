import React, { useState, useEffect } from "react";
import { View, Text, ScrollView, Dimensions } from "react-native";
import MultiSlider from "@ptomasroos/react-native-multi-slider";
import { StationFilterParams, StationStatus, FilterMetadata } from "@/types/station.types";
import { mockFilterMetaData } from "@/data/filterMeta";
import Modal from "@/components/ui/Modal";
import Chip from "@/components/ui/Chip";
import Button from "@/components/ui/Button";

interface FilterBottomSheetProps {
    visible: boolean;
    onClose: () => void;
    onApply: (filters: StationFilterParams) => void;
    initialFilters?: StationFilterParams;
}

const SCREEN_WIDTH = Dimensions.get("window").width;
const SLIDER_LENGTH = SCREEN_WIDTH - 80;

export default function FilterBottomSheet({
    visible,
    onClose,
    onApply,
    initialFilters,
}: FilterBottomSheetProps) {
    const meta: FilterMetadata = mockFilterMetaData.data;

    const [powerRange, setPowerRange] = useState<number[]>([
        initialFilters?.minPower ?? meta.minPower,
        initialFilters?.maxPower ?? meta.maxPower,
    ]);
    const [selectedConnectors, setSelectedConnectors] = useState<string[]>(
        initialFilters?.connectorTypes ?? []
    );
    const [selectedStatuses, setSelectedStatuses] = useState<string[]>(
        initialFilters?.status ? [initialFilters.status as string] : [StationStatus.ACTIVE]
    );

    // Reset when modal opens
    useEffect(() => {
        if (visible) {
            setPowerRange([
                initialFilters?.minPower ?? meta.minPower,
                initialFilters?.maxPower ?? meta.maxPower,
            ]);
            setSelectedStatuses(initialFilters?.status ? [initialFilters.status as string] : [StationStatus.ACTIVE]);
        }
    }, [visible, initialFilters]);

    // Quick Select handler
    const handleQuickPowerSelect = (type: "ac" | "dc" | "uf") => {
        switch (type) {
            case "ac":
                setPowerRange([meta.minPower, 22]);
                break;
            case "dc":
                setPowerRange([22, 60]);
                break;
            case "uf":
                setPowerRange([60, meta.maxPower]);
                break;
        }
    };

    const toggleConnector = (connector: string) => {
        if (selectedConnectors.includes(connector)) {
            setSelectedConnectors(selectedConnectors.filter((c) => c !== connector));
        } else {
            setSelectedConnectors([...selectedConnectors, connector]);
        }
    };

    const toggleStatus = (status: string) => {
        if (selectedStatuses.includes(status)) {
            setSelectedStatuses(selectedStatuses.filter((s) => s !== status));
        } else {
            setSelectedStatuses([...selectedStatuses, status]);
        }
    };

    const handleApply = () => {
        onApply({
            minPower: powerRange[0],
            maxPower: powerRange[1],
            connectorTypes: selectedConnectors,
            status: selectedStatuses.length > 0 ? selectedStatuses[0] : undefined,
        });
        onClose();
    };

    const handleClear = () => {
        setPowerRange([meta.minPower, meta.maxPower]);
        setSelectedConnectors([]);
        setSelectedStatuses([StationStatus.ACTIVE]);
    };

    // UI Helpers
    const getConnectorDetails = (type: string) => {
        switch (type) {
            case "IEC_TYPE_2":
                return { label: "Type 2", icon: "flash-outline" as const };
            case "VINFAST_STD":
                return { label: "VinFast STD", icon: "flash-outline" as const };
            default:
                return { label: type, icon: "hardware-chip-outline" as const };
        }
    };

    const getStatusLabel = (status: string) => {
        return status.charAt(0).toUpperCase() + status.slice(1).toLowerCase();
    };

    return (
        <Modal
            visible={visible}
            onClose={onClose}
            variant="bottom-sheet"
            title="Filter Stations"
        >
            <ScrollView className="max-h-[85%]" showsVerticalScrollIndicator={false}>
                {/* Power Output Section */}
                <View className="mb-6 bg-surface">
                    <Text className="text-secondary font-semibold text-base mb-3">Power Output (kW)</Text>

                    <View className="flex-row flex-wrap gap-2 mb-4">
                        <Chip
                            label="AC (< 22kW)"
                            selected={powerRange[0] === meta.minPower && powerRange[1] === 22}
                            onPress={() => handleQuickPowerSelect("ac")}
                        />
                        <Chip
                            label="Fast DC (22-60kW)"
                            selected={powerRange[0] === 22 && powerRange[1] === 60}
                            onPress={() => handleQuickPowerSelect("dc")}
                        />
                        <Chip
                            label="Ultra-Fast (> 60kW)"
                            selected={powerRange[0] === 60 && powerRange[1] === meta.maxPower}
                            onPress={() => handleQuickPowerSelect("uf")}
                        />
                    </View>

                    <View className="items-center w-full px-2">
                        <MultiSlider
                            values={[powerRange[0], powerRange[1]]}
                            min={meta.minPower}
                            max={meta.maxPower}
                            step={1}
                            onValuesChange={(values) => setPowerRange(values)}
                            selectedStyle={{ backgroundColor: "#00A452" }}
                            unselectedStyle={{ backgroundColor: "#E2E8F0" }}
                            markerStyle={{ backgroundColor: "#FFB000", width: 24, height: 24, borderRadius: 12, elevation: 3, shadowColor: "#000", shadowOffset: { width: 0, height: 2 }, shadowOpacity: 0.2, shadowRadius: 2, borderWidth: 2, borderColor: "#FFF" }}
                            containerStyle={{ height: 40 }}
                            sliderLength={SLIDER_LENGTH}
                            snapped
                        />
                        <View className="w-full flex-row justify-between -mt-2">
                            <Text className="text-text-secondary text-sm font-medium">{powerRange[0]} kW</Text>
                            <Text className="text-text-secondary text-sm font-medium">{powerRange[1]} kW</Text>
                        </View>
                    </View>
                </View>

                {/* Connector Types Section */}
                <View className="mb-6">
                    <Text className="text-secondary font-semibold text-base mb-3">Connector Types</Text>
                    <View className="flex-row flex-wrap gap-2">
                        {meta.connectorTypes.map((type) => {
                            const details = getConnectorDetails(type);
                            return (
                                <Chip
                                    key={type}
                                    label={details.label}
                                    icon={details.icon}
                                    selected={selectedConnectors.includes(type)}
                                    onPress={() => toggleConnector(type)}
                                />
                            );
                        })}
                    </View>
                </View>

                {/* Status Section */}
                <View className="mb-8">
                    <Text className="text-secondary font-semibold text-base mb-3">Status</Text>
                    <View className="flex-row flex-wrap gap-2">
                        {meta.statuses.map((status) => (
                            <Chip
                                key={status as string}
                                label={getStatusLabel(status as string)}
                                selected={selectedStatuses.includes(status as string)}
                                onPress={() => toggleStatus(status as string)}
                            />
                        ))}
                    </View>
                </View>
            </ScrollView>

            {/* Actions */}
            <View className="flex-row gap-4 pt-4 border-t border-border mt-2">
                <Button
                    variant="outline"
                    onPress={handleClear}
                    className="flex-1"
                >
                    Clear All
                </Button>
                <Button
                    variant="primary"
                    onPress={handleApply}
                    className="flex-1"
                >
                    Apply Filters
                </Button>
            </View>
        </Modal>
    );
}
