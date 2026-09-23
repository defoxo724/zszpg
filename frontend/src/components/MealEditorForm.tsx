import { useEffect, useState, type FormEvent } from 'react'
import Button from 'react-bootstrap/Button'
import Card from 'react-bootstrap/Card'
import Col from 'react-bootstrap/Col'
import Form from 'react-bootstrap/Form'
import Row from 'react-bootstrap/Row'
import Stack from 'react-bootstrap/Stack'
import type { Meal, MealCategory, MealInput } from '../types'

interface MealEditorFormProps {
	categories: MealCategory[]
	mealBeingEdited: Meal | null
	onSave: (meal: MealInput) => Promise<void>
	onCancel: () => void
}

const createEmptyMeal = (categoryId = 0): MealInput => ({
	name: '',
	description: '',
	price: 0,
	mealCategoryId: categoryId,
	image: null,
})

const getFormMeal = (mealBeingEdited: Meal | null, categories: MealCategory[]): MealInput => {
	if (!mealBeingEdited) {
		return createEmptyMeal(categories[0]?.id)
	}

	return {
		name: mealBeingEdited.name,
		description: mealBeingEdited.description,
		price: mealBeingEdited.price,
		mealCategoryId: mealBeingEdited.mealCategoryId,
		image: null,
	}
}

const MealEditorForm = ({ categories, mealBeingEdited, onSave, onCancel }: MealEditorFormProps) => {
	const [meal, setMeal] = useState<MealInput>(() => createEmptyMeal())
	const [fileInputKey, setFileInputKey] = useState(0)

	useEffect(() => {
		setMeal(getFormMeal(mealBeingEdited, categories))
	}, [mealBeingEdited, categories])

	const handleSubmit = async (event: FormEvent) => {
		event.preventDefault()
		await onSave(meal)

		if (!mealBeingEdited) {
			setMeal(createEmptyMeal(categories[0]?.id))
			setFileInputKey((currentKey) => currentKey + 1)
		}
	}

	const updateMealField = <Field extends keyof MealInput>(
		field: Field,
		value: MealInput[Field],
	) => {
		setMeal((currentMeal) => ({
			...currentMeal,
			[field]: value,
		}))
	}

	return (
		<Card className="mb-4">
			<Card.Body as={Form} onSubmit={handleSubmit}>
				<Card.Title as="h2" className="h5">
					{mealBeingEdited ? 'Edytuj posiłek' : 'Nowy posiłek'}
				</Card.Title>
				<Row className="g-2">
					<Col md={3}>
						<Form.Control
							placeholder="Nazwa"
							value={meal.name}
							onChange={(event) => updateMealField('name', event.target.value)}
							required
						/>
					</Col>
					<Col md={4}>
						<Form.Control
							placeholder="Opis"
							value={meal.description}
							onChange={(event) => updateMealField('description', event.target.value)}
							required
						/>
					</Col>
					<Col md={2}>
						<Form.Control
							type="number"
							min="0"
							step="0.01"
							placeholder="Cena"
							value={meal.price}
							onChange={(event) =>
								updateMealField('price', Number(event.target.value))
							}
							required
						/>
					</Col>
					<Col md={3}>
						<Form.Select
							value={meal.mealCategoryId}
							onChange={(event) =>
								updateMealField('mealCategoryId', Number(event.target.value))
							}
							required
						>
							<option value={0} disabled>
								Wybierz kategorię
							</option>
							{categories.map((category) => (
								<option key={category.id} value={category.id}>
									{category.name}
								</option>
							))}
						</Form.Select>
					</Col>
				</Row>
				{!mealBeingEdited && (
					<Form.Group className="mt-3">
						<Form.Label>Obraz posiłku</Form.Label>
						<Form.Control
							key={fileInputKey}
							type="file"
							accept="image/*"
							onChange={(event) => {
								const input = event.currentTarget as HTMLInputElement
								updateMealField('image', input.files?.[0] ?? null)
							}}
						/>
					</Form.Group>
				)}
				<Stack direction="horizontal" gap={2} className="mt-3">
					<Button type="submit" disabled={!categories.length}>
						{mealBeingEdited ? 'Zapisz' : 'Dodaj'}
					</Button>
					{mealBeingEdited && (
						<Button type="button" variant="secondary" onClick={onCancel}>
							Anuluj
						</Button>
					)}
				</Stack>
			</Card.Body>
		</Card>
	)
}
export default MealEditorForm
