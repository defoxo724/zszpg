import type { ApplicationSettings } from '../../types'

export type GetApplicationSettingsResponse = ApplicationSettings
export type UpdateApplicationSettingsResponse = ApplicationSettings

export interface UpdateApplicationSettingsRequest {
	restaurantName: string
	restaurantDescription: string
	nameColor: string
	descriptionColor: string
	fontFamily: string
}
