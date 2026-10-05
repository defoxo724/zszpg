package io.defoxo724.zszpg.zszpg.application_settings;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationSettingsModel {
    public static final Long SINGLETON_ID = 1L;

    @Id
    private Long id;
    @Column(name = "restaurant_name", nullable = false)
    private String restaurantName;
    @Column(name = "restaurant_description", nullable = false, length = 1000)
    private String restaurantDescription;
    @Column(name = "name_color", nullable = false, length = 20)
    private String nameColor;
    @Column(name = "description_color", nullable = false, length = 20)
    private String descriptionColor;
    @Column(name = "font_family", nullable = false, length = 100)
    private String fontFamily;
    @Column(name = "background_image_url", length = 2048)
    private String backgroundImageUrl;

    public static ApplicationSettingsModel defaultSettings() {
        return new ApplicationSettingsModel(
                SINGLETON_ID,
                "Nasza restauracja",
                "Poznaj nasze menu i wybierz coś dla siebie.",
                "#ffffff",
                "#f1f1f1",
                "Arial, sans-serif",
                null);
    }
}
