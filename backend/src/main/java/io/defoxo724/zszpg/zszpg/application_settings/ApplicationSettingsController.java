package io.defoxo724.zszpg.zszpg.application_settings;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.defoxo724.zszpg.zszpg.application_settings.dto.GetApplicationSettingsResponse;
import io.defoxo724.zszpg.zszpg.application_settings.dto.UpdateApplicationSettingsRequest;
import io.defoxo724.zszpg.zszpg.application_settings.dto.UpdateApplicationSettingsResponse;

@RestController
@RequestMapping("/application-settings")
public class ApplicationSettingsController {
    private final ApplicationSettingsService applicationSettingsService;

    public ApplicationSettingsController(ApplicationSettingsService applicationSettingsService) {
        this.applicationSettingsService = applicationSettingsService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ORDERER')")
    public ResponseEntity<GetApplicationSettingsResponse> getApplicationSettings() {
        return ResponseEntity.ok(GetApplicationSettingsResponse.fromModel(
                applicationSettingsService.getApplicationSettings()));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UpdateApplicationSettingsResponse> updateApplicationSettings(
            @RequestPart("settings") UpdateApplicationSettingsRequest request,
            @RequestPart(value = "backgroundImage", required = false) MultipartFile backgroundImage) {
        if (!isValid(request)) {
            return ResponseEntity.badRequest().build();
        }
        ApplicationSettingsModel updatedSettings = applicationSettingsService
                .updateApplicationSettings(request.toModel(), backgroundImage);
        return ResponseEntity.ok(UpdateApplicationSettingsResponse.fromModel(updatedSettings));
    }

    private boolean isValid(UpdateApplicationSettingsRequest request) {
        return hasLengthBetween(request.getRestaurantName(), 1, 255)
                && hasLengthBetween(request.getRestaurantDescription(), 1, 1000)
                && isColor(request.getNameColor())
                && isColor(request.getDescriptionColor())
                && hasLengthBetween(request.getFontFamily(), 1, 100);
    }

    private boolean hasLengthBetween(String value, int minimum, int maximum) {
        return value != null && !value.isBlank() && value.length() >= minimum && value.length() <= maximum;
    }

    private boolean isColor(String value) {
        return value != null && value.matches("#[0-9a-fA-F]{6}");
    }
}
