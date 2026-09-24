import Swal from 'sweetalert2'
import { validation } from '../config/validation'
import type { MealCategory } from '../types'

export const showCategoryFormDialog = async (category?: MealCategory) => {
	const limits = validation.mealCategory.name
	const result = await Swal.fire<string>({
		title: category ? 'Edytuj kategorię' : 'Dodaj kategorię',
		input: 'text',
		inputValue: category?.name ?? '',
		inputPlaceholder: 'Nazwa kategorii',
		inputAttributes: {
			minlength: String(limits.minLength),
			maxlength: String(limits.maxLength),
		},
		showCancelButton: true,
		confirmButtonText: category ? 'Zapisz' : 'Dodaj',
		cancelButtonText: 'Anuluj',
		inputValidator: (value) => {
			const name = value.trim()
			if (!name) return 'Podaj nazwę kategorii.'
			if (name.length < limits.minLength || name.length > limits.maxLength) {
				return `Nazwa kategorii musi mieć od ${limits.minLength} do ${limits.maxLength} znaków.`
			}
			return undefined
		},
		preConfirm: (value) => value.trim(),
	})

	return result.isConfirmed ? (result.value ?? null) : null
}
