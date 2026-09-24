import Swal from 'sweetalert2'
import type { MealCategory } from '../types'

export const showCategoryFormDialog = async (category?: MealCategory) => {
	const result = await Swal.fire<string>({
		title: category ? 'Edytuj kategorię' : 'Dodaj kategorię',
		input: 'text',
		inputValue: category?.name ?? '',
		inputPlaceholder: 'Nazwa kategorii',
		showCancelButton: true,
		confirmButtonText: category ? 'Zapisz' : 'Dodaj',
		cancelButtonText: 'Anuluj',
		inputValidator: (value) => (value.trim() ? undefined : 'Podaj nazwę kategorii.'),
		preConfirm: (value) => value.trim(),
	})

	return result.isConfirmed ? (result.value ?? null) : null
}
