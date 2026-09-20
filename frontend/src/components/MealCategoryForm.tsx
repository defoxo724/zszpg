import { useEffect, useState, type FormEvent } from 'react'
import Button from 'react-bootstrap/Button'
import Card from 'react-bootstrap/Card'
import Form from 'react-bootstrap/Form'
import Stack from 'react-bootstrap/Stack'
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
		<Card className="mb-4">
			<Card.Body as={Form} onSubmit={handleSubmit}>
				<Card.Title as="h2" className="h5">
					{categoryBeingEdited ? 'Edytuj kategorię' : 'Nowa kategoria'}
				</Card.Title>
				<Stack direction="horizontal" gap={2}>
					<Form.Control
						placeholder="Nazwa kategorii"
						value={name}
						onChange={(event) => setName(event.target.value)}
						required
					/>
					<Button type="submit">{categoryBeingEdited ? 'Zapisz' : 'Dodaj'}</Button>
					{categoryBeingEdited && (
						<Button type="button" variant="secondary" onClick={onCancel}>
							Anuluj
						</Button>
					)}
				</Stack>
			</Card.Body>
		</Card>
	)
}
export default MealCategoryForm
