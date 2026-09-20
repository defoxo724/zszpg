import { useEffect, useState, type FormEvent } from 'react'
import type { MealCategory } from '../types'

interface MealCategoryFormProps {
	categoryBeingEdited: MealCategory | null
	onSave: (name: string) => Promise<void>
	onCancel: () => void
}

const MealCategoryForm = ({ categoryBeingEdited, onSave, onCancel }: MealCategoryFormProps) => {
	const [name, setName] = useState('')

	useEffect(() => {
		setName(categoryBeingEdited?.name ?? '')
	}, [categoryBeingEdited])

	const handleSubmit = async (event: FormEvent) => {
		event.preventDefault()
		await onSave(name)
		setName('')
	}

	return (
		<form className="card card-body mb-4" onSubmit={handleSubmit}>
			<h2 className="h5">{categoryBeingEdited ? 'Edytuj kategorię' : 'Nowa kategoria'}</h2>
			<div className="d-flex gap-2">
				<input
					className="form-control"
					placeholder="Nazwa kategorii"
					value={name}
					onChange={(event) => setName(event.target.value)}
					required
				/>
				<button className="btn btn-primary">
					{categoryBeingEdited ? 'Zapisz' : 'Dodaj'}
				</button>
				{categoryBeingEdited && (
					<button type="button" className="btn btn-secondary" onClick={onCancel}>
						Anuluj
					</button>
				)}
			</div>
		</form>
	)
}
export default MealCategoryForm
