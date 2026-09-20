import { useEffect, useState, type FormEvent } from 'react'
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
	}
}

const MealEditorForm = ({ categories, mealBeingEdited, onSave, onCancel }: MealEditorFormProps) => {
	const [meal, setMeal] = useState<MealInput>(() => createEmptyMeal())

	useEffect(() => {
		setMeal(getFormMeal(mealBeingEdited, categories))
	}, [mealBeingEdited, categories])

	const handleSubmit = async (event: FormEvent) => {
		event.preventDefault()
		await onSave(meal)

		if (!mealBeingEdited) {
			setMeal(createEmptyMeal(categories[0]?.id))
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
		<form className="card card-body mb-4" onSubmit={handleSubmit}>
			<h2 className="h5">{mealBeingEdited ? 'Edytuj posiłek' : 'Nowy posiłek'}</h2>
			<div className="row g-2">
				<div className="col-md-3">
					<input
						className="form-control"
						placeholder="Nazwa"
						value={meal.name}
						onChange={(event) => updateMealField('name', event.target.value)}
						required
					/>
				</div>
				<div className="col-md-4">
					<input
						className="form-control"
						placeholder="Opis"
						value={meal.description}
						onChange={(event) => updateMealField('description', event.target.value)}
						required
					/>
				</div>
				<div className="col-md-2">
					<input
						className="form-control"
						type="number"
						min="0"
						step="0.01"
						placeholder="Cena"
						value={meal.price}
						onChange={(event) => updateMealField('price', Number(event.target.value))}
						required
					/>
				</div>
				<div className="col-md-3">
					<select
						className="form-select"
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
					</select>
				</div>
			</div>
			<div className="mt-3 d-flex gap-2">
				<button className="btn btn-primary" disabled={!categories.length}>
					{mealBeingEdited ? 'Zapisz' : 'Dodaj'}
				</button>
				{mealBeingEdited && (
					<button type="button" className="btn btn-secondary" onClick={onCancel}>
						Anuluj
					</button>
				)}
			</div>
		</form>
	)
}
export default MealEditorForm
