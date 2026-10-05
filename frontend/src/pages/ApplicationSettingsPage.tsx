import Button from 'react-bootstrap/Button'
import ApplicationSettingsPreview from '../components/settings/ApplicationSettingsPreview'
import PageHeader from '../components/common/PageHeader'
import StatusMessage from '../components/common/StatusMessage'
import { showApplicationSettingsFormDialog } from '../dialogs/applicationSettingsFormDialog'
import { useApplicationSettings } from '../hooks/settings/useApplicationSettings'
import { getErrorMessage } from '../utils/getErrorMessage'
import { showError, showSuccess } from '../utils/sweetAlert'

const ApplicationSettingsPage = () => {
	const settingsState = useApplicationSettings()

	const handleEdit = async () => {
		if (!settingsState.settings) return
		const settingsInput = await showApplicationSettingsFormDialog(settingsState.settings)
		if (!settingsInput) return

		try {
			await settingsState.save(settingsInput)
			void showSuccess('Ustawienia zostały zapisane')
		} catch (error) {
			await showError(
				'Błąd zapisu',
				getErrorMessage(error, 'Nie udało się zapisać ustawień.'),
			)
		}
	}

	return (
		<>
			<PageHeader title="Ustawienia restauracji">
				<Button
					size="sm"
					onClick={() => void handleEdit()}
					disabled={!settingsState.settings}
				>
					Edytuj ustawienia
				</Button>
			</PageHeader>
			<StatusMessage message={settingsState.loadError} />
			{settingsState.settings && (
				<ApplicationSettingsPreview settings={settingsState.settings} />
			)}
		</>
	)
}

export default ApplicationSettingsPage
