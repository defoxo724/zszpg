package io.defoxo724.zszpg.zszpg.application_settings.dto;

import io.defoxo724.zszpg.zszpg.application_settings.ApplicationSettingsModel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UpdateApplicationSettingsResponse {
    private String restaurantName;
    private String restaurantDescription;
    private String nameColor;
    private String descriptionColor;
    private String fontFamily;
    private String backgroundImageUrl;

    public static UpdateApplicationSettingsResponse fromModel(ApplicationSettingsModel settings) {
        return new UpdateApplicationSettingsResponse(
                settings.getRestaurantName(),
                settings.getRestaurantDescription(),
                settings.getNameColor(),
                settings.getDescriptionColor(),
                settings.getFontFamily(),
                settings.getBackgroundImageUrl());
    }
}
