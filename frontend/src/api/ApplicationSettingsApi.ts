import { backendClient } from '../config/backendClient'
import type { ApplicationSettings, ApplicationSettingsInput } from '../types'
import type {
	GetApplicationSettingsResponse,
	UpdateApplicationSettingsRequest,
	UpdateApplicationSettingsResponse,
} from './dto/ApplicationSettingsDto'

export const ApplicationSettingsApi = {
	async getSettings(): Promise<ApplicationSettings> {
		const response =
			await backendClient.get<GetApplicationSettingsResponse>('/application-settings')
		return response.data
	},

	async updateSettings(settingsInput: ApplicationSettingsInput): Promise<ApplicationSettings> {
		const request: UpdateApplicationSettingsRequest = {
			restaurantName: settingsInput.restaurantName,
			restaurantDescription: settingsInput.restaurantDescription,
			nameColor: settingsInput.nameColor,
			descriptionColor: settingsInput.descriptionColor,
			fontFamily: settingsInput.fontFamily,
		}
		const formData = new FormData()
		formData.append(
			'settings',
			new Blob([JSON.stringify(request)], { type: 'application/json' }),
		)
		if (settingsInput.backgroundImage) {
			formData.append('backgroundImage', settingsInput.backgroundImage)
		}
		const response = await backendClient.put<UpdateApplicationSettingsResponse>(
			'/application-settings',
			formData,
		)
		return response.data
	},
}
