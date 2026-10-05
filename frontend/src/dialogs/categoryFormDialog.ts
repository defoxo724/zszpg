import Swal from 'sweetalert2'
import { validation } from '../config/validation'
import type { MealCategory, MealCategoryInput } from '../types'

const getInput = (id: string) => document.getElementById(id) as HTMLInputElement

const createFormHtml = () => `
	<input id="category-name" class="swal2-input" placeholder="Nazwa kategorii"
		minlength="${validation.mealCategory.name.minLength}"
		maxlength="${validation.mealCategory.name.maxLength}">
	<input id="category-image" class="swal2-file" type="file" accept="image/*">
`

const readForm = (): MealCategoryInput => ({
	name: getInput('category-name').value.trim(),
	image: getInput('category-image').files?.[0] ?? null,
})

export const showCategoryFormDialog = async (category?: MealCategory) => {
	const limits = validation.mealCategory.name
	const result = await Swal.fire<MealCategoryInput>({
		title: category ? 'Edytuj kategorię' : 'Dodaj kategorię',
		html: createFormHtml(),
		customClass: { htmlContainer: 'swal-form-fields' },
		showCancelButton: true,
		confirmButtonText: category ? 'Zapisz' : 'Dodaj',
		cancelButtonText: 'Anuluj',
		focusConfirm: false,
		didOpen: () => {
			getInput('category-name').value = category?.name ?? ''
		},
		preConfirm: () => {
			const categoryInput = readForm()
			if (!categoryInput.name) {
				Swal.showValidationMessage('Podaj nazwę kategorii.')
				return false
			}
			if (
				categoryInput.name.length < limits.minLength ||
				categoryInput.name.length > limits.maxLength
			) {
				Swal.showValidationMessage(
					`Nazwa kategorii musi mieć od ${limits.minLength} do ${limits.maxLength} znaków.`,
				)
				return false
			}
			return categoryInput
		},
	})

	return result.isConfirmed ? (result.value ?? null) : null
}
