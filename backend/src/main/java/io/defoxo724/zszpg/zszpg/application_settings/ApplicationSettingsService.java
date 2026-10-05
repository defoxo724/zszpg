package io.defoxo724.zszpg.zszpg.application_settings;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.defoxo724.zszpg.zszpg.meal.MealImageStorageService;

@Service
public class ApplicationSettingsService {
    private final ApplicationSettingsRepository applicationSettingsRepository;
    private final MealImageStorageService imageStorageService;

    public ApplicationSettingsService(ApplicationSettingsRepository applicationSettingsRepository,
            MealImageStorageService imageStorageService) {
        this.applicationSettingsRepository = applicationSettingsRepository;
        this.imageStorageService = imageStorageService;
    }

    public ApplicationSettingsModel getApplicationSettings() {
        return applicationSettingsRepository.findById(ApplicationSettingsModel.SINGLETON_ID)
                .orElseGet(ApplicationSettingsModel::defaultSettings);
    }

    public ApplicationSettingsModel updateApplicationSettings(ApplicationSettingsModel settings,
            MultipartFile backgroundImage) {
        ApplicationSettingsModel existingSettings = getApplicationSettings();
        existingSettings.setId(ApplicationSettingsModel.SINGLETON_ID);
        existingSettings.setRestaurantName(settings.getRestaurantName());
        existingSettings.setRestaurantDescription(settings.getRestaurantDescription());
        existingSettings.setNameColor(settings.getNameColor());
        existingSettings.setDescriptionColor(settings.getDescriptionColor());
        existingSettings.setFontFamily(settings.getFontFamily());
        if (backgroundImage != null && !backgroundImage.isEmpty()) {
            existingSettings.setBackgroundImageUrl(imageStorageService.store(backgroundImage));
        }
        return applicationSettingsRepository.save(existingSettings);
    }
}
