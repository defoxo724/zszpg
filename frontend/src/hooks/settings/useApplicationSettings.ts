import { useCallback, useEffect, useState } from 'react'
import { ApplicationSettingsApi } from '../../api/ApplicationSettingsApi'
import type { ApplicationSettings, ApplicationSettingsInput } from '../../types'
import { getErrorMessage } from '../../utils/getErrorMessage'

export const useApplicationSettings = () => {
	const [settings, setSettings] = useState<ApplicationSettings | null>(null)
	const [loadError, setLoadError] = useState<string | null>(null)

	const load = useCallback(async () => {
		try {
			setSettings(await ApplicationSettingsApi.getSettings())
			setLoadError(null)
		} catch (error) {
			setLoadError(getErrorMessage(error, 'Nie udało się pobrać ustawień aplikacji.'))
		}
	}, [])

	useEffect(() => {
		void load()
	}, [load])

	const save = async (settingsInput: ApplicationSettingsInput) => {
		setSettings(await ApplicationSettingsApi.updateSettings(settingsInput))
	}

	return { settings, loadError, save }
}
