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
public class UpdateApplicationSettingsRequest {
    private String restaurantName;
    private String restaurantDescription;
    private String nameColor;
    private String descriptionColor;
    private String fontFamily;

    public ApplicationSettingsModel toModel() {
        return new ApplicationSettingsModel(
                ApplicationSettingsModel.SINGLETON_ID,
                restaurantName,
                restaurantDescription,
                nameColor,
                descriptionColor,
                fontFamily,
                null);
    }
}
