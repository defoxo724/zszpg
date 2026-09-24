import Swal from 'sweetalert2'

export const confirmAction = async (title: string, text: string) => {
	const result = await Swal.fire({
		title,
		text,
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Potwierdź',
		cancelButtonText: 'Anuluj',
		confirmButtonColor: '#dc3545',
	})

	return result.isConfirmed
}

export const showSuccess = (title: string) =>
	Swal.fire({
		title,
		icon: 'success',
		toast: true,
		position: 'top-end',
		showConfirmButton: false,
		timer: 1800,
		timerProgressBar: true,
	})

export const showError = (title: string, text: string) =>
	Swal.fire({
		title,
		text,
		icon: 'error',
		confirmButtonText: 'OK',
	})
