import Swal from 'sweetalert2'
import type { Meal, MealCategory, MealInput } from '../types'

const getInput = (id: string) => document.getElementById(id) as HTMLInputElement
const getSelect = (id: string) => document.getElementById(id) as HTMLSelectElement

const createFormHtml = (showImageInput: boolean) => `
	<input id="meal-name" class="swal2-input" placeholder="Nazwa">
	<input id="meal-description" class="swal2-input" placeholder="Opis">
	<input id="meal-price" class="swal2-input" type="number" min="0" step="0.01" placeholder="Cena">
	<select id="meal-category" class="swal2-select"></select>
	${showImageInput ? '<input id="meal-image" class="swal2-file" type="file" accept="image/*">' : ''}
`

const populateForm = (categories: MealCategory[], meal?: Meal) => {
	getInput('meal-name').value = meal?.name ?? ''
	getInput('meal-description').value = meal?.description ?? ''
	getInput('meal-price').value = meal ? String(meal.price) : ''

	const categorySelect = getSelect('meal-category')
	categories.forEach((category) => {
		const option = document.createElement('option')
		option.value = String(category.id)
		option.textContent = category.name
		option.selected = category.id === meal?.mealCategoryId
		categorySelect.append(option)
	})
}

const readForm = (): MealInput => {
	const imageInput = document.getElementById('meal-image') as HTMLInputElement | null

	return {
		name: getInput('meal-name').value.trim(),
		description: getInput('meal-description').value.trim(),
		price: Number(getInput('meal-price').value),
		mealCategoryId: Number(getSelect('meal-category').value),
		image: imageInput?.files?.[0] ?? null,
	}
}

const getValidationMessage = (mealInput: MealInput) => {
	if (!mealInput.name || !mealInput.description) return 'Uzupełnij nazwę i opis posiłku.'
	if (!Number.isFinite(mealInput.price) || mealInput.price < 0) return 'Podaj prawidłową cenę.'
	if (!mealInput.mealCategoryId) return 'Wybierz kategorię.'
	return null
}

export const showMealFormDialog = async (categories: MealCategory[], meal?: Meal) => {
	const result = await Swal.fire<MealInput>({
		title: meal ? 'Edytuj posiłek' : 'Dodaj posiłek',
		html: createFormHtml(!meal),
		customClass: { htmlContainer: 'swal-form-fields' },
		showCancelButton: true,
		confirmButtonText: meal ? 'Zapisz' : 'Dodaj',
		cancelButtonText: 'Anuluj',
		focusConfirm: false,
		didOpen: () => populateForm(categories, meal),
		preConfirm: () => {
			const mealInput = readForm()
			const validationMessage = getValidationMessage(mealInput)
			if (validationMessage) {
				Swal.showValidationMessage(validationMessage)
				return false
			}
			return mealInput
		},
	})

	return result.isConfirmed ? (result.value ?? null) : null
}
