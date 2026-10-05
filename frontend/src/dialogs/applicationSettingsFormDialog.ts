import Swal from 'sweetalert2'
import type { ApplicationSettings, ApplicationSettingsInput } from '../types'

const getInput = (id: string) => document.getElementById(id) as HTMLInputElement
const getTextArea = (id: string) => document.getElementById(id) as HTMLTextAreaElement
const getSelect = (id: string) => document.getElementById(id) as HTMLSelectElement

const createFormHtml = () => `
	<input id="settings-name" class="swal2-input" placeholder="Nazwa restauracji" maxlength="255">
	<textarea id="settings-description" class="swal2-textarea" placeholder="Opis restauracji" maxlength="1000"></textarea>
	<label class="swal-field-label" for="settings-name-color">Kolor nazwy</label>
	<input id="settings-name-color" class="swal2-input swal-color-input" type="color">
	<label class="swal-field-label" for="settings-description-color">Kolor opisu</label>
	<input id="settings-description-color" class="swal2-input swal-color-input" type="color">
	<select id="settings-font-family" class="swal2-select">
		<option value="Arial, sans-serif">Arial</option>
		<option value="Georgia, serif">Georgia</option>
		<option value="Tahoma, sans-serif">Tahoma</option>
		<option value="Verdana, sans-serif">Verdana</option>
		<option value="'Trebuchet MS', sans-serif">Trebuchet MS</option>
	</select>
	<input id="settings-background" class="swal2-file" type="file" accept="image/*">
`

const populateForm = (settings: ApplicationSettings) => {
	getInput('settings-name').value = settings.restaurantName
	getTextArea('settings-description').value = settings.restaurantDescription
	getInput('settings-name-color').value = settings.nameColor
	getInput('settings-description-color').value = settings.descriptionColor
	getSelect('settings-font-family').value = settings.fontFamily
}

const readForm = (): ApplicationSettingsInput => ({
	restaurantName: getInput('settings-name').value.trim(),
	restaurantDescription: getTextArea('settings-description').value.trim(),
	nameColor: getInput('settings-name-color').value,
	descriptionColor: getInput('settings-description-color').value,
	fontFamily: getSelect('settings-font-family').value,
	backgroundImage: getInput('settings-background').files?.[0] ?? null,
})

export const showApplicationSettingsFormDialog = async (settings: ApplicationSettings) => {
	const result = await Swal.fire<ApplicationSettingsInput>({
		title: 'Ustawienia restauracji',
		html: createFormHtml(),
		customClass: { htmlContainer: 'swal-form-fields' },
		showCancelButton: true,
		confirmButtonText: 'Zapisz',
		cancelButtonText: 'Anuluj',
		focusConfirm: false,
		didOpen: () => populateForm(settings),
		preConfirm: () => {
			const settingsInput = readForm()
			if (!settingsInput.restaurantName || !settingsInput.restaurantDescription) {
				Swal.showValidationMessage('Uzupełnij nazwę i opis restauracji.')
				return false
			}
			return settingsInput
		},
	})

	return result.isConfirmed ? (result.value ?? null) : null
}
