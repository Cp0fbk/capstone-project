import React from "react";
import { Text, View, ViewProps } from "react-native";

export interface ListItemCardProps extends ViewProps {
    /**
     * The main icon/element to display on the top left
     */
    icon?: React.ReactNode;

    /**
     * The main title text or ReactNode
     */
    title: string | React.ReactNode;

    /**
     * Custom styling for the title text
     * @default "text-[#4CAF50]"
     */
    titleClassName?: string;

    /**
     * The main content/body of the card below the title
     */
    children?: React.ReactNode;

    /**
     * Action buttons or elements to display on the right side
     */
    actions?: React.ReactNode;

    /**
     * Custom className for the root container
     */
    elementClassName?: string;
}

export default function ListItemCard({
    icon,
    title,
    titleClassName = "text-[#4CAF50]",
    children,
    actions,
    elementClassName = "",
    ...props
}: ListItemCardProps) {
    return (
        <View
            className={`bg-[#4A5568]/20 mb-4 p-4 border border-[#4A5568] rounded-lg ${elementClassName}`}
            {...props}
        >
            <View className="flex-row justify-between items-start">
                <View className="flex-1 mr-2">
                    {/* Header: Icon + Title */}
                    <View className="flex-row items-center">
                        {icon && <View className="mr-2">{icon}</View>}
                        {typeof title === "string" ? (
                            <Text className={`font-semibold ${titleClassName}`}>
                                {title}
                            </Text>
                        ) : (
                            title
                        )}
                    </View>

                    {/* Body */}
                    {children && <View className="mt-2">{children}</View>}
                </View>

                {/* Actions (Right Side) */}
                {actions && (
                    <View className="flex-row items-center ml-2">
                        {actions}
                    </View>
                )}
            </View>
        </View>
    );
}
